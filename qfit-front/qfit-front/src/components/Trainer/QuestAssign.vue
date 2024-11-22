<template>
  <div class="container">
    <h6 class="assign-header">{{ viewStore.selectedDate }}</h6>
    <h5 class="assign-header">{{ traineeName }} 회원님의 운동 선택하기</h5>

    <div class="button-group">
      <button @click="selectPart('leg')" :class="{ selected: selectedPart === 'leg' }">하체</button>
      <button @click="selectPart('chest')" :class="{ selected: selectedPart === 'chest' }">가슴</button>
      <button @click="selectPart('back')" :class="{ selected: selectedPart === 'back' }">등</button>
      <button @click="selectPart('shoulder')" :class="{ selected: selectedPart === 'shoulder' }">어깨</button>
      <button @click="selectPart('arm')" :class="{ selected: selectedPart === 'arm' }">팔</button>
      <button @click="selectPart('cardio')" :class="{ selected: selectedPart === 'cardio' }">유산소</button>
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
        @click.prevent="goToQuestSetting"
      >
        {{ selectedExercises.length === 0 ? '운동을 선택해주세요' : selectedExercises.length + '개 운동 추가하기' }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { useExerciseStore } from '@/stores/exercise';
import { useTraineeStore } from '@/stores/trainee';
import { computed, ref, onMounted } from 'vue';
import { useViewStore } from '@/stores/viewStore';
import { useRouter } from 'vue-router';

const viewStore = useViewStore();
const traineeStore = useTraineeStore();
const exerciseStore = useExerciseStore();
const router = useRouter();

const traineeName = computed(() => traineeStore.selectedTrainee.userName);

const selectedPart = ref('leg'); // 기본적으로 'leg' 선택
const exercises = ref([]);

const fetchExercises = async () => {
  try {
    const result = await exerciseStore.getAllExercises();
    exercises.value = result.map(exercise => ({
      ...exercise,
      selected: exerciseStore.selectedExercises.some(selected => selected.exerciseId === exercise.exerciseId)
    }));
  } catch (error) {
    console.error('운동 데이터 가져오기 실패:', error);
    exercises.value = [];
  }
};

onMounted(() => {
  fetchExercises();
});

const selectPart = (part) => {
  selectedPart.value = part;
  fetchExercises();
};

const updateSelectedExercises = (exercise) => {
  if (exercise.selected) {
    exerciseStore.addSelectedExercise(exercise);
  } else {
    exerciseStore.removeSelectedExercise(exercise);
  }
};

const filteredExercises = computed(() => {
  if (!selectedPart.value) return exercises.value;
  return exercises.value.filter(exercise => exercise.exerciseParts === selectedPart.value);
});

const selectedExercises = computed(() => {
  return exerciseStore.selectedExercises;
});

const goToQuestSetting = () => {
  router.push({ name: 'questSetting' });
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

button.active {
  background-color: #6f2be8; /* 선택된 버튼 색 */
}

.exercise-list {
  margin-top: 20px;
  text-align: center;
}

<<<<<<< HEAD
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
