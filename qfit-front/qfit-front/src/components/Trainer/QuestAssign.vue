<template>
  <TheCalender />
  <div class="container">
    <h5 class="assign-header">{{ traineeName }} 회원님의 운동 선택하기</h5>

    <div class="button-group">
      <button @click="fetchExercises('leg')">하체</button>
      <button @click="fetchExercises('chest')">가슴</button>
      <button @click="fetchExercises('back')">등</button>
      <button @click="fetchExercises('shoulder')">어깨</button>
      <button @click="fetchExercises('arm')">팔</button>
      <button @click="fetchExercises('cardio')">유산소</button>
    </div>

    <div v-if="exercises.length > 0" class="exercise-list">
      <h6>선택된 운동 부위: {{ selectedPart }}</h6>
      <ul>
        <li v-for="exercise in exercises" :key="exercise.id">
          {{ exercise.name }}
        </li>
      </ul>
    </div>
    <div v-else>
      <p>운동을 선택해주세요.</p>
    </div>
  </div>
</template>

<script setup>
import { useExerciseStore } from '@/stores/exercise';
import { useTraineeStore } from '@/stores/trainee';
import { computed, ref } from 'vue';
import TheCalender from '../common/TheCalender.vue';

const traineeStore = useTraineeStore();
const exerciseStore = useExerciseStore();

const traineeName = computed(() => traineeStore.selectedTrainee.userName);

// 선택된 운동 부위와 운동 리스트
const selectedPart = ref('');
const exercises = ref([]);

// 운동 조회 함수
const fetchExercises = async (part) => {
  selectedPart.value = part; // 선택된 부위 업데이트
  exercises.value = await exerciseStore.getExerciseByParts(part); // 스토어 메서드 호출
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

.button-group {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

button:hover {
  background-color: #0056b3;
}

.exercise-list {
  margin-top: 20px;
  text-align: center;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  margin: 5px 0;
}
</style>
