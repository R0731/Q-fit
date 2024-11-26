<template>
  <div class="container mt-4">
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
              <span class="text-theme">{{ task.cardioMinutes === null ? bodyPartMap[exerciseData[task.exerciseId]?.exerciseParts] || 'Unknown' : '유산소' }}</span>
            </div>
            <!-- exerciseName -->
            <div class="exercise-name">
              {{ exerciseData[task.exerciseId]?.exerciseName || 'Loading...' }}
            </div>
            <!-- kg -->
            <div class="exercise-count">
              {{ task.weightKg ? task.weightKg + 'kg' : null }}
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
      <!-- 수정 버튼 -->
      <div class="edit-section">
        <button v-if="canEdit" class="small-btn" @click="$emit('switchToEditor')">수정</button>
        <p v-else>수정할 수 없습니다.</p>
      </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useExerciseStore } from '@/stores/exercise';
import { useQuestStore } from '@/stores/quest';
import { useViewStore } from '@/stores/viewStore';

const questStore = useQuestStore();
const exerciseStore = useExerciseStore();
const viewStore = useViewStore();

const tasks = computed(() => questStore.tasks || []); // 퀘스트 목록
const exerciseData = ref({}); // 운동 정보
const selectedDate = computed(() => viewStore.selectedDate); // 선택 날짜

// 영어 - 한글 매핑 객체
const bodyPartMap = {
  leg: '하체',
  chest: '가슴',
  back: '등',
  shoulder: '어깨',
  arm: '팔',
  cardio: '유산소',
};

// 특정 exerciseId의 운동 정보를 가져오기
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

// 모든 운동 정보를 로드
const loadAllExerciseInfo = async () => {
  const uniqueExerciseIds = [...new Set(tasks.value.map((task) => task.exerciseId))];
  for (const exerciseId of uniqueExerciseIds) {
    await loadExerciseInfo(exerciseId);
  }
};

// 전체 운동 정보 로드
const getAllExercises = async() => {
  exerciseStore.getAllExercises()
}

// 선택한 날짜가 현재 날짜 이전이면 수정 불가능
const canEdit = ref(false);

// 수정 가능 상태 확인
const updateCanEdit = () => {
  const today = formatDateToYYYYMMDD(new Date()); // 오늘 날짜 계산

  if (selectedDate.value) {
    canEdit.value = selectedDate.value >= today; // 날짜 비교
  } else {
    canEdit.value = false; // 선택된 날짜가 없으면 수정 불가
  }
};

// 날짜를 yyyy-mm-dd형식으로 변환
const formatDateToYYYYMMDD = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 필요
  const day = String(date.getDate()).padStart(2, '0'); // 1자리 수일 경우 앞에 0 추가
  return `${year}-${month}-${day}`;
};

// 컴포넌트 초기화
onMounted(async() => {
  await getAllExercises();
  await loadAllExerciseInfo();
  await updateCanEdit();
});

watch(
  () => tasks.value,
  (newTasks) => {
    console.log('computed tasks 변경 감지:', newTasks);
  }
);
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

/* 텍스트 테마 색상 */
.text-theme {
  color: var(--theme-color);
}

/* bodyPart와 exerciseName 간의 간격 */
.body-part {
  width: 50px; /* bodyPart의 고정된 너비 */
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
  margin-left: 5px;
}

.edit-section{
  margin-top: 13px;
}
</style>
