<template>
  <div class="container mt-4">
    <!-- 화면 제목 -->
    <!-- <h3 class="text-center">트레이니 퀘스트</h3> -->

    <!-- 선택된 날짜 표시 -->
    <!-- <p class="text-center text-muted">{{ formattedDate }}</p> -->

    <!-- 퀘스트가 없는 경우 -->
    <div v-if="tasks.length === 0" class="text-center">
      <p>퀘스트가 없습니다.</p>
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
            @click="changeComplete(task)"
          >
            {{ task.completed ? '완료' : '미완료' }}
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
/* Vue 및 Pinia 관련 유틸리티 */
import { ref, computed, onMounted, watch } from 'vue';
import { useExerciseStore } from '@/stores/exercise';
import { useQuestStore } from '@/stores/quest';
import { useUserStore } from '@/stores/user';
import { useViewStore } from '@/stores/viewStore';
import { useTaskStore } from '@/stores/task';

/* Pinia 스토어 인스턴스 */
const questStore = useQuestStore();
const userStore = useUserStore();
const viewStore = useViewStore();
const exerciseStore = useExerciseStore();
const taskStore = useTaskStore();

/* 날짜 포맷 (YYYY-MM-DD 형식) */
const formattedDate = computed(() => {
  // const rawDate = viewStore.selectedDate;
  return viewStore.selectedDate;
});

/* 퀘스트 목록 가져오기 */
const tasks = computed(() => questStore.getTasks || []);

/* 운동 정보 로컬 상태 */
const exerciseData = ref({});

/* 특정 exerciseId의 운동 정보를 가져오기 */
const loadExerciseInfo = async (exerciseId) => {
  if (exerciseData.value[exerciseId]) return;

  try {
    const res = await exerciseStore.getExerciseById(exerciseId);
    if (res && res.data) {
      exerciseData.value[exerciseId] = {
        exerciseId: res.data.exerciseId || null,
        exerciseName: res.data.exerciseName || 'Unknown',
        exerciseParts: res.data.exerciseParts || 'Unknown',
        exerciseType: res.data.exerciseType || 'Unknown',
      };
    }
  } catch (error) {
    console.error(`운동 정보를 가져오는 중 오류 발생 (exerciseId: ${exerciseId})`, error);
  }
};

/* 고유한 exerciseId를 기준으로 모든 운동 정보 로드 */
const loadAllExerciseInfo = async () => {
  const uniqueExerciseIds = [...new Set(tasks.value.map((task) => task.exerciseId))];
  for (const exerciseId of uniqueExerciseIds) {
    await loadExerciseInfo(exerciseId);
  }
};

/* 퀘스트 및 관련 운동 데이터 로드 */
const loadQuest = async () => {
  const traineeId = userStore.loginUser.numberId;

  try {
    await questStore.getQuestByIdAndStartDate(traineeId, formattedDate.value);
    await loadAllExerciseInfo();
  } catch (error) {
    console.error('퀘스트 로드 실패:', error);
  }
};

/* task.completed 상태 토글 */
const changeComplete = async(task) => {
  const newStatus = !task.completed;
  try{
    await taskStore.updateCompleted(task.taskId, newStatus); 
    task.completed = newStatus;
    console.log('업데이트완료')
  }catch(err){
    console.err('업데이트실패', error)
  }
};

/* 컴포넌트 초기화 */
onMounted(loadQuest);

/* 날짜 변경 시 데이터 다시 로드 */
watch(formattedDate, loadQuest);
</script>

<style scoped>
/* 스타일은 필요에 따라 추가 */
</style>
