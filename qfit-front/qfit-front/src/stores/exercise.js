import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/exercise`
export const useExerciseStore = defineStore('exercise', () => {
  
  const getExerciseById = async (exerciseId) => {
    try {
      const url = `${REST_API_URL}/list/${exerciseId}`;
      const res = await axios.get(url);

      // 응답 확인
      console.log('운동 정보 응답:', res);

      // 데이터 반환
      return res;
    } catch (error) {
      console.error(`운동 정보를 가져오는 중 오류 발생: ${error.message}`);
      throw error; // 호출한 쪽에서 처리할 수 있도록 오류 전달
    }
  };

  return { getExerciseById }
})
