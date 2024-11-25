<template>
  <div class="editor-container">
    <ul class="list-group">
      <li
        v-for="(task, index) in newTasks"
        :key="task.taskId"
        class="list-group-item d-flex justify-content-between align-items-center"
      >
        <!-- 왼쪽 섹션: 운동 부위, 운동 이름, 운동 시간 -->
        <div class="d-flex align-items-center">
          <!-- 운동 부위 -->
          <div class="body-part">
            <select
              v-model="newTasks[index].bodyPart"
              :disabled="task.completed"
              @change="handleBodyPartChange(index)"
            >
              <option
                v-for="(label, key) in bodyPartMap"
                :key="key"
                :value="key"
              >
                {{ label }}
              </option>
            </select>
          </div>

          <!-- 운동 이름 -->
          <div class="exercise-name">
            <select
              v-model="newTasks[index].exerciseName"
              :disabled="task.completed || !newTasks[index].bodyPart"
              @change="handleExerciseChange(index)"
            >
              <option value="" disabled>운동을 선택하세요</option>
              <option
                v-for="exercise in filteredExercises(newTasks[index].bodyPart)"
                :key="exercise.exerciseId"
                :value="exercise.exerciseName"
              >
                {{ exercise.exerciseName }}
              </option>
            </select>
          </div>
          
          <!-- 무게 (kg) -->
          <div
            class="exercise-weight"
            v-show="newTasks[index].bodyPart !== 'cardio'"
          >
            <input
              type="number"
              v-model="newTasks[index].weightKg"
              placeholder="kg"
              :disabled="task.completed"
              min="0"
            />
          </div>
          <!-- 운동 시간/횟수 -->
          <div class="exercise-count">
            <input
              type="number"
              v-model="newTasks[index][newTasks[index].bodyPart === 'cardio' ? 'cardioMinutes' : 'count']"
              :placeholder="newTasks[index].bodyPart === 'cardio' ? '분' : '횟수'"
              :disabled="task.completed"
              min="0"
            />
          </div>

        </div>

        <!-- 삭제 버튼 -->
      <div class="delete-icon">
        <i
          :class="task.completed ? 'bi bi-check-square-fill' : 'bi bi-x-square-fill'"
          :style="{ color: task.completed ? 'var(--theme-color)' : 'red',
          cursor: task.completed ? 'not-allowed' : 'pointer'}"
          @click="!task.completed && deleteTaskConfirm(index)"
        ></i>
      </div>
      </li>
    </ul>

    <!-- 저장 및 취소 버튼 -->
    <div class="button-group mt-4">
      <button class="small-btn" @click="addTask">운동 추가</button>
      <button class="small-btn" @click="saveChanges(newTasks)">저장</button>
      <button class="small-btn" @click="$emit('switchToView')">취소</button>
    </div>
  </div>
</template>


<script setup>
import { useQuestStore } from '@/stores/quest';
import { useExerciseStore } from '@/stores/exercise';
import { ref, onMounted } from 'vue';
import { useTaskStore } from '@/stores/task';
import { useNotificationStore } from '@/stores/notification';

// Pinia 스토어
const questStore = useQuestStore();
const exerciseStore = useExerciseStore();
const taskStore = useTaskStore();
const notificationStore = useNotificationStore();

// 로컬 상태
const exercises = exerciseStore.exercises; // 운동 데이터 배열
const newTasks = ref([]); // 편집용 상태
const deleteTaskId = ref([]); // 삭제할 테스크Id 저장

// 영어 - 한글 매핑 객체
const bodyPartMap = {
  leg: '하체',
  chest: '가슴',
  back: '등',
  shoulder: '어깨',
  arm: '팔',
  cardio: '유산소',
};

// 컴포넌트 초기화
const initializeNewTasks = () => {
  newTasks.value = questStore.tasks.map((task) => {
    const exercise = exercises.find((ex) => ex.exerciseId === task.exerciseId) || {};
    return {
      ...task,
      exerciseType: exercise.exerciseType,
      exerciseName: exercise.exerciseName || '',
      bodyPart: task.bodyPart || exercise.exerciseParts || '',
      cardioMinutes: task.cardioMinutes ?? null,
      count: task.count ?? null,
    };
  });
  console.log(newTasks.value)
};

// 알림 생성
const makeNotification = async() => {
  try{
    const traineeId = questStore.quest.traineeId;
    const notification = {userId: traineeId, message: '퀘스트가 수정되었습니다'}
    // console.log('넘어가는 메시지 확인', notification)
    await notificationStore.createNotification(notification)
  }catch(err){
    console.log('프론트 등록 중 오류 발생', err)
  }
}

// 신체 부위 변경 처리
const handleBodyPartChange = (index) => {
  // 운동 부위가 변경되면 task의 데이터를 초기화
  const task = newTasks.value[index];
  task.exerciseId = null;
  task.exerciseName = '';
  task.weightKg = null;
  task.count = null;
  task.cardioMinutes = null;
};

// 운동 변경 처리
const handleExerciseChange = (index) => {
  // 운동 부위가 변경되면 task의 데이터를 초기화
  const task = newTasks.value[index];

  const selectedExercise = exercises.find(
    (exercise) => exercise.exerciseName === task.exerciseName
  );

  if(selectedExercise){
    task.exerciseId = selectedExercise.exerciseId;
  }else{
    task.exerciseId = null;
  }
  task.weightKg = null;
  task.count = null;
  task.cardioMinutes = null;
};

// 운동 이름 필터링
const filteredExercises = (bodyPart) => {
  return exercises.filter((exercise) => exercise.exerciseParts === bodyPart);
};

// 삭제 기능
const deleteTaskConfirm = (index) => {
  if (confirm('이 항목을 삭제하시겠습니까?')) {
    const deleteId = newTasks.value[index].taskId;
    if(deleteId !== 'new'){
      deleteTaskId.value.push(deleteId);
    }
    newTasks.value.splice(index, 1);
  }
};

// 새로운 운동 항목 추가
const addTask = () => {
  newTasks.value.push({
    taskId: 'new', // 고유 ID 생성
    questId: questStore.quest.questId,
    bodyPart: null, // 기본 신체 부위
    exerciseName: null, // 기본 운동 이름
    weightKg: null, // 기본 무게
    count: null, // 기본 횟수
    cardioMinutes: null, // 기본 운동 시간
    completed: false, // 기본 완료 상태
  });
  console.log('새 테스크', newTasks.value);
};

const deleteMethod = (idArray) => {
  idArray.forEach((taskId) => {
    // console.log('삭제하는 taskId', taskId)
    taskStore.deleteTask(taskId);
  });
  deleteTaskId.value = [];
  // console.log('삭제배열 초기화 완료', deleteTaskId.value)
}

const editMehod = (taskArray) => {
  taskArray.forEach((task) => {
    const taskId = task.taskId;
    if(taskId === 'new'){
      // task.taskId = null;
      // console.log('생성되는 task', task);
      taskStore.createTask(task);
    }else{
      // console.log('수정되는 task', task);
      taskStore.updateTask(task);
    }
  })
}

const emit = defineEmits(['switchToView']);

// 저장 버튼 클릭 시
const saveChanges = async(tasks) => {
  // console.log('현재 newTasks:', newTasks.value);
  newTasks.value.forEach((task, index) => {
    // console.log(`newTasks[${index}] =`, task); // 각 객체를 출력
  });

  const checkTask = (task) => {
  // console.log('검사 중인 task:', task); // 현재 검사 중인 task를 출력

  if (!task.exerciseName) {
    // console.log('운동 이름이 누락된 task:', task);
    alert('운동을 선택해주세요');
    return false;
  }

  if (task.bodyPart === 'cardio') {
    if (!task.cardioMinutes || task.cardioMinutes <= 0) {
      // console.log('운동 시간이 누락되었거나 0인 task:', task);
      alert('운동 시간을 입력해주세요');
      return false;
    }
  } else {
    if (!task.count || task.count <= 0 || !task.weightKg || task.weightKg <= 0) {
      // console.log('운동 횟수 또는 무게가 누락된 task:', task);
      alert('운동 횟수와 무게를 입력해주세요');
      return false;
    }
  }

  // console.log('task가 유효합니다:', task);
  return true; // 모든 조건 통과
};

const notCompleteTask = newTasks.value.some((task) => {
  // console.log('현재 검사 중인 task:', task); // 각 task를 확인
  return !checkTask(task);
});

if (notCompleteTask) {
  console.log('유효하지 않은 작업 발견. 저장 중단');
  return;
}

  // 기존의 생성된 task를 삭제하는 경우
  if(deleteTaskId.value.length > 0){
    console.log('삭제배열', deleteTaskId.value);
    deleteMethod(deleteTaskId.value);
  }

  // 기존 task외의 새로운 task를 추가하는 경우
  if(newTasks.value){
    const taskToEdit = newTasks.value.filter(task => task.completed !== true);
    await editMehod(taskToEdit);
    questStore.tasks = [...newTasks.value];
  }

  console.log('저장성공');
  makeNotification();
  questStore.updateTasks(newTasks.value);
  setTimeout(() => {
    emit('switchToView');
  }, 200); // 200ms 딜레이
};

// 초기화
onMounted(() => {
  initializeNewTasks();
});
</script>

<style scoped>
.editor-container {
  max-width: 100%; /* 상위 컨테이너의 너비를 초과하지 않음 */
  margin: 20px auto;
  padding: 10px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border: 1px solid #ddd;
  overflow-x: auto; /* 내용이 넘칠 경우 가로 스크롤 */
}

.list-group {
  margin: 0;
  padding: 0;
  list-style-type: none;
  width: 100%; /* 상위 컨테이너 너비에 맞춤 */
  display: block; /* 강제로 블록 레이아웃 */
  overflow-x: auto; /* 리스트가 넘칠 경우 가로 스크롤 */
}

.list-group-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px;
  border-bottom: 1px solid #eee;
  font-size: 0.9rem;
  gap: 3px; /* 각 칸 간격 */
  flex-wrap: nowrap; /* 한 줄에 유지 */
  box-sizing: border-box; /* 패딩 포함 크기 계산 */
}

/* 고정된 칸 크기와 동일 높이 설정 */
.body-part,
.exercise-name,
.exercise-count,
.exercise-weight {
  flex: none; /* 고정 크기 유지 */
  text-align: center;
  height: 40px; /* 동일한 높이 설정 */
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 6px;
}

/* 칸별 크기 설정 */
.body-part {
  width: 70px; /* 고정된 크기 */
  font-weight: bold;
}

.exercise-name {
  width: 125px; /* 고정된 크기 */
  text-align: left;
  font-weight: bold;
}

.exercise-count,
.exercise-weight {
  width: 40px; /* 동일한 고정 크기 */
}

/* 삭제 버튼 위치 조정 */
.delete-icon {
  margin-left: 5px; /* weightKg 칸에서 5px 떨어지도록 설정 */
  display: flex;
  align-items: center;
  justify-content: center;
}

.delete-icon i {
  font-size: 1.2rem; /* 크기를 적절히 유지 */
  cursor: pointer;
  color: red;
  transition: transform 0.2s ease, color 0.2s ease;
}

/* 공통 스타일 */
.body-part select,
.exercise-name select,
.exercise-count input,
.exercise-weight input {
  width: 100%;
  height: 100%; /* 칸 내부의 높이를 채우도록 설정 */
  padding: 4px 2px;
  font-size: 0.85rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  transition: border-color 0.2s ease;
  box-sizing: border-box; /* 높이 및 간격 계산 */
}

.body-part select:focus,
.exercise-name select:focus,
.exercise-count input:focus,
.exercise-weight input:focus {
  border-color: var(--theme-color);
  outline: none;
}

/* 반응형 처리 */
@media (max-width: 768px) {
  .list-group-item {
    font-size: 0.85rem;
    flex-wrap: wrap;
  }

  .body-part {
    width: 60px;
  }

  .delete-icon {
    margin-left: 5px; /* 작은 화면에서도 삭제 버튼 가까이 유지 */
  }
}

.button-group {
  display: flex;
  justify-content: center;
  gap: 8px; /* 버튼 사이 간격 */
  margin-top: 16px; /* 상단 여백 */
}

</style>
