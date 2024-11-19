import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/trainer`
export const useTrainerStore = defineStore('trainer', () => {
  const trainer = ref({gym: ''});
  const selectedTrainee = ref(null);

  const getGym = async(numberId) => {
    try{
      const url = `${REST_API_URL}/${numberId}/gym`
      const res = await axios.get(url);
      console.log('체육관 정보 조회 성공', res)
      trainer.value.gym = res.data.gym;
    }catch(err){
      console.log('체육관 정보 조회 실패')
    }
  }
  const updateGym = async(numberId, gym) => {
    try{
      console.log('조회', numberId, gym)
      const url = `${REST_API_URL}/${numberId}/add-gym`
      const res = await axios.put(url, gym);
      console.log('체육관 정보 업데이트 성공')
    }catch(err){
      console.log('체육관 정보 업데이트 실패')
    }
  } 


  const trainees = ref([]);

  const fetchTraineeList = async (trainerId) =>{
    try{
      const response = await axios.get(`http://localhost:8080/trainee/${trainerId}/trainee-list`);
      trainees.value = response.data || [];
    }catch(err){
      console.error('Failed to fetch trainee list:', err)
    }
  }

  return { trainer, updateGym, getGym, trainees, fetchTraineeList, selectedTrainee }
})
