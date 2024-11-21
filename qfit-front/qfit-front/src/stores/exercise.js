import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_API_URL = `http://localhost:8080/exercise`;

export const useExerciseStore = defineStore('exercise', () => {
  const exercises = ref([]);
  const selectedExercises = ref([]);

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

  const addSelectedExercise = (exercise) => {
    if (!selectedExercises.value.some(e => e.exerciseId === exercise.exerciseId)) {
      selectedExercises.value.push(exercise);
    }
  };

  const removeSelectedExercise = (exercise) => {
    selectedExercises.value = selectedExercises.value.filter(e => e.exerciseId !== exercise.exerciseId);
  };

  const setSelectedExercises = (exercisesArray) => {
    selectedExercises.value = exercisesArray;
  };

  return { exercises, selectedExercises, getAllExercises, addSelectedExercise, removeSelectedExercise, setSelectedExercises };
});
