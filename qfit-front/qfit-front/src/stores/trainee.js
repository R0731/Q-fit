// trainee.js
import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useQuestStore } from '@/stores/quest';

const REST_API_URL = `http://localhost:8080/trainee`;

export const useTraineeStore = defineStore('trainee', () => {
  const trainees = ref([]);
  const selectedTrainee = ref(null);
  const traineeWithQuests = ref([]);

  const questStore = useQuestStore();


  const calculateAge = (birthDate) => {
    const today = new Date();
    const birth = new Date(birthDate);
    
    let age = today.getFullYear() - birth.getFullYear(); // 연도 차이 계산
    const monthDiff = today.getMonth() - birth.getMonth(); // 월 차이 계산

    // 생일이 아직 지나지 않았다면 나이에서 1을 뺍니다.
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
      age--;
    }

    return age;
  }

  const fetchTraineeList = async (trainerId) =>{
    try{
      const response = await axios.get(`${REST_API_URL}/${trainerId}/trainee-list`);
      // trainees.value = response.data || [];
      trainees.value = response.data.map((trainee) => ({
        ...trainee,
        age: calculateAge(trainee.birthdate), // 나이 추가
      }));
    }catch(err){
      console.error('Failed to fetch trainee list:', err)
    }
  }


  /**
   * Load trainees with their quest statuses
   */
  const loadTraineeWithQuests = async (trainerId, startDate) => {
    try {
      // Fetch trainees
      await fetchTraineeList(trainerId);
  
      // Extract trainee IDs (use 'id' instead of 'userId')
      const traineeIds = trainees.value.map((trainee) => trainee.id);
  
      // Fetch quest statuses
      const questStatuses = await questStore.getQuestsByTraineesAndDate(
        traineeIds,
        startDate
      );
  
      // Merge quest statuses into trainees
      traineeWithQuests.value = trainees.value.map((trainee) => ({
        ...trainee,
        questStatus: questStatuses[trainee.id] || '미등록',
      }));
  
      console.log('Trainees with quests:', traineeWithQuests.value);
    } catch (err) {
      console.error('Failed to load trainees with quests:', err);
    }
  };
  

  const searchResult = ref(null);

  const searchTrainee = async (userId) =>{
    try {
      const response = await axios.get(`${REST_API_URL}/search-trainee`, {
        params: {userId},
      });
      searchResult.value = response.data;

      // `searchResult`에 나이(age) 추가
      searchResult.value = {
        ...response.data,
        age: calculateAge(response.data.birthdate), // 나이 추가
      };

    } catch (err){
      searchResult.value = null;
      console.error('Failed to search trainee: ', err)
    }
  }

  const addTrainerToTrainee = async (traineeId, trainerId) => {
    try {
      const response = await axios.put(
        `${REST_API_URL}/${traineeId}/add-trainer/${trainerId}`
      );
      console.log(response.data);
    } catch (err){
      console.error('Failed to add trainer to trainee:', err)
    }
  }

  const deleteTrainee = async (traineeId) => {
    try {
      await axios.delete(`${REST_API_URL}/${traineeId}/delete`);
      trainees.value = trainees.value.filter((t) => t.id !== traineeId);
    } catch (error) {
      console.error(error);
    }
  };

  return {
    trainees,
    fetchTraineeList,
    traineeWithQuests,
    loadTraineeWithQuests,
    searchResult,
    searchTrainee,
    addTrainerToTrainee,
    selectedTrainee,
    deleteTrainee,
  };
});
