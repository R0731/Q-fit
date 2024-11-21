<template>
  <div class="container">
    <h6 class="assign-header">{{ viewStore.selectedDate }}</h6>
    <h5 class="assign-header">{{ traineeName }} 회원님의 운동 선택하기</h5>

    <div class="button-group">
      <button @click="selectPart('leg')">하체</button>
      <button @click="selectPart('chest')">가슴</button>
      <button @click="selectPart('back')">등</button>
      <button @click="selectPart('shoulder')">어깨</button>
      <button @click="selectPart('arm')">팔</button>
      <button @click="selectPart('cardio')">유산소</button>
    </div>

    <div v-if="filteredExercises.length > 0" class="exercise-list">
      <div class="exercise-cards">
        <div 
          v-for="exercise in filteredExercises" 
          :key="exercise.exerciseId" 
          class="exercise-card">
          <input 
            type="checkbox" 
            v-model="exercise.selected"
            @change="updateSelectedExercises(exercise)" 
          />
          <span>{{ exercise.exerciseName }}</span>
        </div>
      </div>
    </div>
    <div v-else>
      <p>운동을 선택해주세요.</p>
    </div>

    <div class="footer2">
      <button 
        :disabled="selectedExercises.length === 0"
        @click="goToQuestSetting"
      >
        {{ selectedExercises.length === 0 ? '운동을 선택해주세요' : selectedExercises.length + '개 운동 추가하기' }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { useExerciseStore } from '@/stores/exercise';
import { useTraineeStore } from '@/stores/trainee';
import { computed, ref } from 'vue';
import { useViewStore } from '@/stores/viewStore';
import { useRouter } from 'vue-router';

const viewStore = useViewStore();
const traineeStore = useTraineeStore();
const exerciseStore = useExerciseStore();
const router = useRouter();

const traineeName = computed(() => traineeStore.selectedTrainee.userName);

// 선택된 운동 부위와 운동 리스트
const selectedPart = ref('');
const exercises = ref([]);
const selectedExercisesByPart = ref({}); // 부위별 선택된 운동 추적

// 운동 조회 함수 (전체 운동을 가져옴)
const fetchExercises = async () => {
  try {
    const result = await exerciseStore.getAllExercises(); // 모든 운동 데이터 가져오기
    exercises.value = result.map(exercise => ({
      ...exercise,
      selected: selectedExercisesByPart.value[exercise.exerciseId] || false
    }));
    console.log('Fetched Exercises:', exercises.value); // 디버깅: 운동 데이터 확인
  } catch (error) {
    console.error('운동 데이터 가져오기 실패:', error);
    exercises.value = []; // 오류 시 초기화
  }
};

// 운동 부위 선택 함수
const selectPart = (part) => {
  selectedPart.value = part; // 선택된 부위 업데이트
  console.log('Selected Part:', selectedPart.value); // 디버깅: 선택된 부위 확인
  fetchExercises(); // 운동 데이터 조회
};

// 선택된 운동 상태를 업데이트
const updateSelectedExercises = (exercise) => {
  if (exercise.selected) {
    selectedExercisesByPart.value[exercise.exerciseId] = true;
  } else {
    delete selectedExercisesByPart.value[exercise.exerciseId];
  }
};

// 필터링된 운동 목록 (선택된 부위에 맞는 운동만 보여주기)
const filteredExercises = computed(() => {
  if (!selectedPart.value) return exercises.value; // 부위가 선택되지 않으면 모든 운동을 보여줌
  return exercises.value.filter(exercise => exercise.exerciseParts === selectedPart.value); // 선택된 부위의 운동만 필터링
});

// 선택된 운동 목록 (체크된 운동만 필터링)
const selectedExercises = computed(() => {
  return exercises.value.filter(exercise => exercise.selected);
});

// 운동 추가하기 버튼 클릭 시 퀘스트 셋팅 페이지로 이동
const goToQuestSetting = () => {
  router.push({ name: 'questSetting', state: { selectedExercises: selectedExercises.value } });
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
  background-color: #8504e8;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

button:hover {
  background-color: #4b0581;
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
  display: flex;
  align-items: center;
}

.exercise-card input {
  margin-right: 10px;
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
