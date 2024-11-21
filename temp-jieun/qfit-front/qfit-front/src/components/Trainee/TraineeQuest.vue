<template>
  <div class="container">
    <!-- 퀘스트가 없는 경우 -->
    <div v-if="tasks.length === 0" class="no-quest">
      <p class="no-quest-message">퀘스트가 없습니다.</p>
    </div>

    <!-- 퀘스트가 있는 경우 -->
    <div class>
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
            class="btn btn-sm "
            :class="task.completed ? 'btn-completed' : 'btn-not-completed'" @click="changeComplete(task)"
          >
            {{ task.completed ? '완료' : '미완료' }}
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useExerciseStore } from "@/stores/exercise";
import { useQuestStore } from "@/stores/quest";
import { useUserStore } from "@/stores/user";
import { useViewStore } from "@/stores/viewStore";
import { useTaskStore } from "@/stores/task";

const questStore = useQuestStore();
const userStore = useUserStore();
const viewStore = useViewStore();
const exerciseStore = useExerciseStore();
const taskStore = useTaskStore();

const formattedDate = computed(() => viewStore.selectedDate);

const tasks = computed(() => questStore.getTasks || []);

const exerciseData = ref({});

const loadExerciseInfo = async (exerciseId) => {
  if (exerciseData.value[exerciseId]) return;

  try {
    const res = await exerciseStore.getExerciseById(exerciseId);
    if (res && res.data) {
      exerciseData.value[exerciseId] = {
        exerciseId: res.data.exerciseId || null,
        exerciseName: res.data.exerciseName || "Unknown",
        exerciseParts: res.data.exerciseParts || "Unknown",
      };
    }
  } catch (error) {
    console.error(`운동 정보를 가져오는 중 오류 발생 (exerciseId: ${exerciseId})`, error);
  }
};

const loadAllExerciseInfo = async () => {
  const uniqueExerciseIds = [...new Set(tasks.value.map((task) => task.exerciseId))];
  for (const exerciseId of uniqueExerciseIds) {
    await loadExerciseInfo(exerciseId);
  }
};

const loadQuest = async () => {
  const traineeId = userStore.loginUser.numberId;

  try {
    await questStore.getQuestByIdAndStartDate(traineeId, formattedDate.value);
    await loadAllExerciseInfo();
  } catch (error) {
    console.error("퀘스트 로드 실패:", error);
  }
};

const changeComplete = async (task) => {
  const newStatus = !task.completed;
  try {
    await taskStore.updateCompleted(task.taskId, newStatus);
    task.completed = newStatus;
  } catch (error) {
    console.error("업데이트 실패", error);
  }
};

onMounted(loadQuest);

watch(formattedDate, loadQuest);
</script>

<style scoped>
button {
  cursor: default; /* 기본 포인터(화살표)로 설정 */
}
/* 퀘스트 등록 버튼 */
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
  background: linear-gradient(90deg, var(--theme-color), #9d47f4); /* 그라데이션 배경 */;
  color: #fff;
  border: none;
  padding: 8px 16px;
  font-size: 0.9rem;
  border-radius: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s ease, transform 0.3s ease;
  transform: scale(1); /* 기본 크기 */
}

.btn-completed:hover {
  background-color: white;
  color: white;
  border: 1px solid var(--theme-color);
  content: "미완료"; /* hover 시 미완료로 텍스트 변경 */
  transform: scale(1.1); /* hover 시 크기 증가 */
}

/* 미완료된 버튼 */
.btn-not-completed {
  background-color: #fff;
  color: var(--theme-color);
  border: 1px solid var(--theme-color);
  padding: 8px 16px;
  font-size: 0.9rem;
  border-radius: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s ease, transform 0.3s ease;
  transform: scale(1); /* 기본 크기 */
}

.btn-not-completed:hover {
  background: linear-gradient(90deg, var(--theme-color), #9d47f4); /* 그라데이션 배경 */
  color: #fff;
  border: none;
  content: "완료"; /* hover 시 완료로 텍스트 변경 */
  transform: scale(1.1); /* hover 시 크기 증가 */
}

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

.no-quest-message {
  font-size: 1.5rem; /* 글자 크기 수정 */
}
</style>
