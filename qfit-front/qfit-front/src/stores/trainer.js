import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

const REST_API_URL = `http://localhost:8080/trainer`
export const useTrainerStore = defineStore('trainer', () => {
  const trainer = ref({gym: ''}); // 트레이너 정보를 담을 객체

  /**
  * 체육관 정보를 가져오기
  * @param {number} numberId - 사용자 고유 ID
  */
  const getGym = async(numberId) => {
    try{
      const url = `${REST_API_URL}/${numberId}/gym`
      const res = await axios.get(url);
      trainer.value.gym = res.data.gym;
    }catch(err){
      console.error('체육관 정보 조회 실패', err)
    }
  }

  /**
   * 체육관 정보 업데이트
   * @param {number} numberId - 사용자 고유 ID
   * @param {String} gym - 업데이트할 체육관 정보
   */
  const updateGym = async(numberId, gym) => {
    try{
      const url = `${REST_API_URL}/${numberId}/add-gym`
      const res = await axios.put(url, gym);
    }catch(err){
      console.error('체육관 정보 업데이트 실패', err)
    }
  } 

  return { trainer, updateGym, getGym }
})