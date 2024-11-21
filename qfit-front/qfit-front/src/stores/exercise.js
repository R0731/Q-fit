import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_API_URL = `http://localhost:8080/exercise`;

export const useExerciseStore = defineStore('exercise', () => {
  const exercises = ref([]);

  // 운동 ID로 운동 정보 가져오기
  const getExerciseById = async (exerciseId) => {
    try {
      const url = `${REST_API_URL}/list/${exerciseId}`;
      const res = await axios.get(url);
      console.log('운동 정보 응답:', res.data);
      return res.data;
    } catch (error) {
      console.error(`운동 정보를 가져오는 중 오류 발생: ${error.message}`);
      throw error;
    }
  };

  // 부위별 운동 리스트 가져오기
  const getExerciseByParts = async (part) => {
    try {
      const response = await axios.get(`${REST_API_URL}/list/parts`, {
        params: { parts: part },
      });
      exercises.value = response.data;
      console.log('운동 데이터:', exercises.value);
      return exercises.value;
    } catch (error) {
      console.error('운동 데이터를 가져오는 중 오류 발생:', error.message);
      alert('운동 데이터를 불러오지 못했습니다.');
      exercises.value = [];
      return exercises.value;
    }
  };

  // 전체 운동 리스트 가져오기
  const getAllExercises = async () => {
    try {
      const response = await axios.get(`${REST_API_URL}/list`);
      exercises.value = response.data;
      console.log('전체 운동 데이터:', exercises.value);
      return exercises.value;
    } catch (error) {
      console.error('전체 운동 데이터를 가져오는 중 오류 발생:', error.message);
      alert('운동 데이터를 불러오지 못했습니다.');
      exercises.value = [];
      return exercises.value;
    }
  };

  return { exercises, getExerciseById, getExerciseByParts, getAllExercises };
});
