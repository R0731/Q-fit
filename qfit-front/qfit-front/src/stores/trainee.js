// trainee.js
import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useQuestStore } from '@/stores/quest';

const REST_API_URL = `http://localhost:8080/trainee`;

export const useTraineeStore = defineStore('trainee', () => {
  const trainees = ref([]);
  const traineeWithQuests = ref([]);

  const questStore = useQuestStore();

  const fetchTraineeList = async (trainerId) =>{
    try{
      const response = await axios.get(`${REST_API_URL}/${trainerId}/trainee-list`);
      trainees.value = response.data || [];
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
  

  return {
    trainees,
    fetchTraineeList,
    traineeWithQuests,
    loadTraineeWithQuests,
  };
});
