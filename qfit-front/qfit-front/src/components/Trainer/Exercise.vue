<template>
  <div class="container mt-4">
    <!-- 퀘스트가 없는 경우 -->
    <div v-if="tasks.length === 0" class="text-center">
      <button class="btn btn-primary" @click="createQuest">퀘스트 등록하기</button>
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
            <div class="me-2">
              <span class="text-primary">{{ task.cardioMinutes === null ? exerciseData[task.exerciseId]?.exerciseParts || 'Unknown' : 'cardio' }}</span>
            </div>
            <div>
              <div>{{ exerciseData[task.exerciseId]?.exerciseName || 'Loading...' }}</div>
              <div>{{ task.count ? task.count + '회' : task.cardioMinutes + '분' }}</div>
            </div>
          </div>

          <!-- 오른쪽 버튼 -->
          <button 
            class="btn btn-sm"
            :class="task.completed ? 'btn-primary' : 'btn-outline-primary'"
          >
            {{ task.completed ? '완료' : '미완료' }}
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
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
