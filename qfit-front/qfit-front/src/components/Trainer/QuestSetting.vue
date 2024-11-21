<template>
    <div class="container">
      <h6 class="assign-header">{{ viewStore.selectedDate }}</h6>
      <h5 class="assign-header">{{ traineeName }} 회원님의 운동 선택 확인</h5>
  
      <!-- 선택된 운동 카드 목록 -->
      <div v-if="selectedExercises.length > 0" class="exercise-list">
        <div class="exercise-cards">
          <div 
            v-for="exercise in selectedExercises" 
            :key="exercise.exerciseId" 
            class="exercise-card">
            <span>{{ exercise.exerciseName }}</span>
          </div>
        </div>
      </div>
      <div v-else>
        <p>선택된 운동이 없습니다.</p>
      </div>
  
      <!-- 퀘스트 등록하기 버튼 -->
      <div class="footer2">
        <button 
          :disabled="selectedExercises.length === 0"
          @click="registerQuest"
        >
          {{ selectedExercises.length === 0 ? '운동을 선택해주세요' : '퀘스트 등록하기' }}
        </button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { useExerciseStore } from '@/stores/exercise';
  import { useTraineeStore } from '@/stores/trainee';
  import { computed } from 'vue';
  import { useViewStore } from '@/stores/viewStore';
  import { useRoute } from 'vue-router';
  
  const viewStore = useViewStore();
  const traineeStore = useTraineeStore();
  const exerciseStore = useExerciseStore();
  const route = useRoute();
  
  const traineeName = computed(() => traineeStore.selectedTrainee.userName);
  
  // 페이지 로드 시 전달된 운동 데이터 가져오기 (이전 페이지에서 선택한 운동 데이터)
//   const selectedExercises = computed(() => route.params.selectedExercises || []);
const selectedExercises = computed(() => {
  // state에서 selectedExercises 값 받기
  return route.state?.selectedExercises || [];
});
  
  // 퀘스트 등록하기
  const registerQuest = () => {
    // 퀘스트 등록 로직 (예: API 요청)
    console.log('퀘스트가 등록되었습니다.', selectedExercises.value);
    // 퀘스트 등록 후 페이지 이동 (예: 퀘스트 목록 페이지)
    // router.push({ name: 'questList' });
  };
  </script>
  
  <style scoped>
  .assign-header {
    margin-top: 20px;
    text-align: center;
  }
  
  .container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  }
  
  .exercise-list {
    margin-top: 20px;
    text-align: center;
  }
  
  .exercise-cards {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
  }
  
  .exercise-card {
    padding: 10px;
    background-color: #f4f4f4;
    border-radius: 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  }
  
  .exercise-card span {
    font-size: 16px;
  }
  
  .footer2 {
    position: absolute;
    bottom: 80px;
    display: inline-flex;
  }
  
  .footer2 button {
    padding: 10px 20px;
    font-size: 16px;
    border-radius: 5px;
    background-color: #8504e8;
    color: white;
    cursor: pointer;
    width: 400px;
  }
  
  .footer2 button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
  }
  </style>
  