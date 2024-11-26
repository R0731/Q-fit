<template>
  <div class="container">
    <h6 class="assign-header">{{ viewStore.selectedDate }}</h6>
    <h5 class="assign-header">{{ traineeName }} 회원님의 운동 선택하기</h5>

    <!-- 부위 선택 버튼을 모아서 css처리 -->
    <div class="button-group">
      <button @click="selectPart('leg')" :class="{ selected: selectedPart === 'leg' }">하체</button>
      <button @click="selectPart('chest')" :class="{ selected: selectedPart === 'chest' }">가슴</button>
      <button @click="selectPart('back')" :class="{ selected: selectedPart === 'back' }">등</button>
      <button @click="selectPart('shoulder')" :class="{ selected: selectedPart === 'shoulder' }">어깨</button>
      <button @click="selectPart('arm')" :class="{ selected: selectedPart === 'arm' }">팔</button>
      <button @click="selectPart('cardio')" :class="{ selected: selectedPart === 'cardio' }">유산소</button>
    </div>

    <!-- 부위별로 필터링된 운동 목록 -->
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

    <!-- 선택된 운동 수를 보여주는 버튼, 운동 횟수 입력 페이지로 연결 -->
    <div class="submit">
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
const exercises = ref([]); // 로드된 운동 정보를 담을 배열

// 운동 전체 조회 메서드
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

// 선택된 운동 부위 반영 메서드
const selectPart = (part) => {
  selectedPart.value = part;
  fetchExercises();
};


// 체크박스에서 선택된 운동 반영 메서드
const updateSelectedExercises = (exercise) => {
  if (exercise.selected) {
    exerciseStore.addSelectedExercise(exercise);
  } else {
    exerciseStore.removeSelectedExercise(exercise);
  }
};

// 선택한 부위별 운동 필터링 메서드
const filteredExercises = computed(() => {
  if (!selectedPart.value) return exercises.value;
  return exercises.value.filter(exercise => exercise.exerciseParts === selectedPart.value);
});

// 엑서사이즈 스토어 컴퓨팅 메서드
const selectedExercises = computed(() => {
  return exerciseStore.selectedExercises;
});

// 횟수 입력화면 이동 메서드
const goToQuestSetting = () => {
  router.push({ name: 'questSetting' });
};

onMounted(() => {
  fetchExercises();
});
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

button.selected {
  background-color: #4b0581; /* 선택된 버튼 색 */
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

.submit {
  position: absolute;
  bottom: 80px;
  display: inline-flex;
}

.submit button {
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 5px;
  background-color: #8504e8;
  color: white;
  cursor: pointer;
  width: 400px;
}

.submit button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>