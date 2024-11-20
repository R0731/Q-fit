<template>
  <div class="container mt-4">
    <!-- 퀘스트가 없는 경우 -->
    <div v-if="tasks.length === 0" class="text-center">
      <button class="btn btn-primary create-quest-btn" @click="createQuest">퀘스트 등록하기</button>
    </div>

    <!-- 퀘스트가 있는 경우 -->
    <div v-else>
      <ul class="list-group">
        <li 
          v-for="task in tasks" 
          :key="task.taskId" 
          class="list-group-item d-flex justify-content-between align-items-center"
        >
          <!-- 왼쪽 섹션 -->
          <div class="d-flex align-items-center">
            <!-- bodyPart 텍스트 -->
            <div class="body-part">
              <span class="text-theme">{{ task.cardioMinutes === null ? exerciseData[task.exerciseId]?.exerciseParts || 'Unknown' : 'cardio' }}</span>
            </div>
            <!-- exerciseName -->
            <div class="exercise-name">
              {{ exerciseData[task.exerciseId]?.exerciseName || 'Loading...' }}
            </div>
            <!-- count -->
            <div class="exercise-count">
              {{ task.count ? task.count + '회' : task.cardioMinutes + '분' }}
            </div>
          </div>

          <!-- 오른쪽 버튼 -->
          <button 
            class="btn btn-sm " disabled
            :class="task.completed ? 'btn-completed' : 'btn-not-completed'"
          >
            {{ task.completed ? '완료' : '미완료' }}
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useExerciseStore } from '@/stores/exercise';
import { useQuestStore } from '@/stores/quest';

// Pinia 스토어
const questStore = useQuestStore();
const exerciseStore = useExerciseStore();

// 퀘스트 목록
const tasks = computed(() => questStore.getTasks || []);

// 운동 정보
const exerciseData = ref({});

/**
 * 특정 exerciseId의 운동 정보를 가져오기
 */
const loadExerciseInfo = async (exerciseId) => {
  if (exerciseData.value[exerciseId]) return;

  try {
    const res = await exerciseStore.getExerciseById(exerciseId);
    if (res && res.data) {
      exerciseData.value[exerciseId] = {
        exerciseId: res.data.exerciseId || null,
        exerciseName: res.data.exerciseName || 'Unknown',
        exerciseParts: res.data.exerciseParts || 'Unknown',
      };
    }
  } catch (error) {
    console.error(`운동 정보를 가져오는 중 오류 발생 (exerciseId: ${exerciseId})`, error);
  }
};

/**
 * 모든 운동 정보를 로드
 */
const loadAllExerciseInfo = async () => {
  const uniqueExerciseIds = [...new Set(tasks.value.map((task) => task.exerciseId))];
  for (const exerciseId of uniqueExerciseIds) {
    await loadExerciseInfo(exerciseId);
  }
};

// 컴포넌트 초기화
onMounted(loadAllExerciseInfo);

// 퀘스트 생성
const createQuest = () => {
  console.log('퀘스트 등록 페이지로 이동');
};
</script>

<style scoped>
button {
  cursor: default; /* 기본 포인터(화살표)로 설정 */
}
/* 퀘스트 등록 버튼 */
.create-quest-btn {
  background-color: var(--theme-color);
  color: #fff;
  border: none;
  padding: 10px 20px;
  font-size: 1rem;
  border-radius: 25px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.create-quest-btn:hover {
  background-color: darken(var(--theme-color), 10%);
}

/* 완료된 버튼 */
.btn-completed {
  background-color: var(--theme-color);
  color: #fff;
  border: none;
}

/* 미완료된 버튼 */
.btn-not-completed {
  background-color: #fff;
  color: var(--theme-color);
  border: 1px solid var(--theme-color);
}

/* .btn-not-completed:hover {
  background-color: var(--theme-color);
  color: #fff;
} */

/* 텍스트 테마 색상 */
.text-theme {
  color: var(--theme-color);
}

/* bodyPart와 exerciseName 간의 간격 */
.body-part {
  width: 80px; /* bodyPart의 고정된 너비 */
  flex-shrink: 0; /* bodyPart가 축소되지 않도록 설정 */
  text-align: left; /* 텍스트 정렬 */
  margin-right: 8px; /* exerciseName과의 간격 */
}

/* exercise-name 스타일 */
.exercise-name {
  font-weight: bold;
  color: #333; /* 텍스트 색상 */
  margin-right: 8px; /* exerciseName과 count 간격 */
  font-size: 0.95rem; /* 약간 작은 글씨 크기 */
  white-space: nowrap; /* 텍스트가 한 줄로 표시되도록 설정 */
}

/* exercise-count 스타일 */
.exercise-count {
  font-size: 0.9rem;
  color: #666; /* 약간 어두운 텍스트 색상 */
}
</style>
