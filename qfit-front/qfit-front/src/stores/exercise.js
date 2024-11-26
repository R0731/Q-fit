import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_API_URL = `http://localhost:8080/exercise`;

export const useExerciseStore = defineStore('exercise', () => {
  const exercises = ref([]);
  const selectedExercises = ref([]);

  // 전체 운동 데이터 가져오기
  const getAllExercises = async () => {
    try {
      const response = await axios.get(`${REST_API_URL}/list`);
      exercises.value = response.data;
      return exercises.value;
    } catch (error) {
      console.error('전체 운동 데이터를 가져오는 중 오류 발생:', error.message);
      alert('운동 데이터를 불러오지 못했습니다.');
      exercises.value = [];
      return exercises.value;
    }
  };

  /**
   * 선택된 운동 추가
   * @param {Object} exercise - 추가할 운동 객체
   */
  const addSelectedExercise = (exercise) => {
    if (!selectedExercises.value.some(e => e.exerciseId === exercise.exerciseId)) {
      selectedExercises.value.push(exercise);
    }
  };

   /**
   * 선택된 운동 제거
   * @param {Object} exercise - 제거할 운동 객체
   */
  const removeSelectedExercise = (exercise) => {
    selectedExercises.value = selectedExercises.value.filter(e => e.exerciseId !== exercise.exerciseId);
  };

   /**
   * 선택된 운동 배열 설정
   * @param {Array} exercisesArray - 새로 설정할 운동 배열
   */
  const setSelectedExercises = (exercisesArray) => {
    selectedExercises.value = exercisesArray;
  };

  /**
   * 운동 ID로 운동 데이터 가져오기
   * @param {number} exerciseId - 운동 ID
   * @returns {Object} - 운동 데이터
   */
  const getExerciseById = async (exerciseId) => {
    try {
      const url = `${REST_API_URL}/list/${exerciseId}`;
      const res = await axios.get(url);
      return res;
    } catch (error) {
      console.error(`운동 정보를 가져오는 중 오류 발생: ${error.message}`);
      throw error;
    }
  };

   /**
   * 신체 부위별 운동 데이터 가져오기
   * @param {string} parts - 신체 부위 키
   * @returns {Array} - 신체 부위에 해당하는 운동 데이터
   */
  const getExerciseByParts = async (parts) => {
    try {
      const url = `${REST_API_URL}/list/${parts}`;
      const res = await axios.get(url);
      return res;
    } catch (error) {
      console.error(`운동 정보를 가져오는 중 오류 발생: ${error.message}`);
      throw error;
    }
  };

  return { exercises, selectedExercises, getAllExercises, addSelectedExercise, removeSelectedExercise, setSelectedExercises, getExerciseById, getExerciseByParts };
});