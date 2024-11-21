// trainee.js
import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_API_URL = `http://localhost:8080/trainee`;
const QUEST_API_URL = `http://localhost:8080/quest`;

export const useTraineeStore = defineStore('trainee', () => {
  const trainees = ref([]);
  const selectedTrainee = ref(null);
  const traineeWithQuests = ref([]);
  const trainer = ref({ trainerId: null });

  // 나이 계산
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

  // 트레이너에 해당하는 트레이니 찾기
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

  // 트레이니 검색
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

  // 트레이너가 트레이니 추가
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

  // 트레이너가 트레이니 삭제
  const deleteTrainee = async (traineeId) => {
    try {
      await axios.delete(`${REST_API_URL}/${traineeId}/delete`);
      trainees.value = trainees.value.filter((t) => t.id !== traineeId);
    } catch (error) {
      console.error(error);
    }
  };

  // 퀘스트 상태 지닌 트레이니
  const fetchTraineesWithQuestStatuses = async (trainerId, startAt) => {
    try {
      // 1. 훈련생 목록 가져오기
      const traineeResponse = await axios.get(`${REST_API_URL}/${trainerId}/trainee-list`);
      const traineeList = traineeResponse.data.map((trainee) => ({
        ...trainee,
        age: calculateAge(trainee.birthdate), // 나이 계산 추가
      }));

      // 2. 퀘스트 상태 가져오기
      const questResponse = await axios.get(`${QUEST_API_URL}/${trainerId}/status`, {
        params: { startAt },
      });
      const questStatuses = questResponse.data;
      console.log("questStatuses: ", questStatuses)

      // 3. 훈련생 목록과 퀘스트 상태 병합
      trainees.value = traineeList.map((trainee) => ({
        ...trainee,
        questStatus: questStatuses.find((status) => status.trainee_id === trainee.id)?.quest_status || '미등록',
      }));
    } catch (err) {
      console.error('Failed to fetch trainees with quest statuses:', err);
    }
  };

  // 트레이니id로 트레이너 조회 메서드
  const getTrainerId = async(traineeId) => {
    try{
      const url = `${REST_API_URL}/${traineeId}/read-trainer`
      // console.log('조회 url', url)
      const res = await axios.get(url)
      return res.data;
      // console.log('res조회', res)
      if(res.status === 200){
        // console.log('res.data 조회', res.data);
        trainer.value = { trainerId: res.data };
        // console.log('trainer조회', trainer.value);
      }
    }catch(err){
      console.log(err)
    }
  }

  return {
    trainees,
    fetchTraineeList,
    traineeWithQuests,
    searchResult,
    searchTrainee,
    addTrainerToTrainee,
    selectedTrainee,
    deleteTrainee,
    fetchTraineesWithQuestStatuses,
    getTrainerId,
    trainer,
  };
});
