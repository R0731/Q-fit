<template>
  <div class="container">
    <h6 class="assign-header">{{ viewStore.selectedDate }}</h6>
    <h5 class="assign-header">{{ traineeName }} 회원님의 운동 선택 확인</h5>

    <div v-if="selectedExercises.length > 0" class="exercise-list">
      <div class="exercise-cards">
        <div 
          v-for="(exercise, index) in selectedExercises" 
          :key="exercise.exerciseId" 
          class="exercise-card">
          
          <!-- 운동 부위와 운동 이름 -->
          <div class="exercise-info">
            <span class="exercise-part">{{ translateExercisePart(exercise.exerciseParts) }}</span>
            <span class="exercise-name">{{ exercise.exerciseName }}</span>
          </div>

          <!-- 조건부 렌더링으로 운동 타입에 따라 입력 필드 표시 -->
          <div class="input-group">
            <!-- Cardio 운동일 경우 -->
            <div v-if="exercise.exerciseType === 'Cardio'" class="input-field-group">
              <label for="cardioMinutes" class="input-label">시간</label>
              <input 
                v-model="exercise.cardioMinutes"
                type="text" 
                id="cardioMinutes" 
                class="input-field" 
                placeholder="분" 
              />
            </div>

            <!-- Weight 운동일 경우 -->
            <div v-else-if="exercise.exerciseType === 'Weight'" class="input-field-group">
              <label for="weightKg" class="input-label">무게</label>
              <input 
                v-model="exercise.weightKg"
                type="text" 
                id="weightKg" 
                class="input-field" 
                placeholder="kg" 
              />
            </div>
            <div v-if="exercise.exerciseType === 'Weight'" class="input-field-group">
              <label for="count" class="input-label">횟수</label>
              <input 
                v-model="exercise.count"
                type="text" 
                id="count" 
                class="input-field" 
                placeholder="회" 
              />
            </div>
          </div>

          <!-- 세트 추가와 삭제 버튼 그룹 -->
          <div class="button-group">
            <button class="add-set-button" @click="addSet(index)">세트 추가</button>
            <button 
              class="delete-exercise-button" 
              @click="removeExercise(index)"
            >
              삭제
            </button>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <p>선택된 운동이 없습니다.</p>
    </div>

    <div class="submit">
      <button 
        :disabled="selectedExercises.length === 0"
        @click="registerQuest"
      >
        {{ selectedExercises.length === 0 ? '운동을 선택해주세요' : '퀘스트 등록하기' }}
      </button>
    </div>
    <br>
    <br>
    <br>
  </div>
</template>

<script setup>
import { useExerciseStore } from '@/stores/exercise';
import { useTraineeStore } from '@/stores/trainee';
import { computed, ref } from 'vue';
import { useViewStore } from '@/stores/viewStore';
import axios from 'axios'

const viewStore = useViewStore();
const traineeStore = useTraineeStore();
const exerciseStore = useExerciseStore();

const traineeName = computed(() => traineeStore.selectedTrainee.userName);

// selectedExercises를 ref로 정의하여 배열을 직접 수정할 수 있게 합니다.
const selectedExercises = ref(exerciseStore.selectedExercises.map(exercise => {
  if (exercise.exerciseType === 'Cardio') {
    exercise.cardioMinutes = exercise.cardioMinutes || null;
  } else if (exercise.exerciseType === 'Weight') {
    exercise.weightKg = exercise.weightKg || null;
    exercise.count = exercise.count || null;
  }
  return exercise;
}));

// 운동 삭제 메서드
const removeExercise = (index) => {
  selectedExercises.value.splice(index, 1);
  console.log(`운동 삭제 완료. 남은 운동: ${selectedExercises.value.length}`);
};

// 운동 부위 한글로 변환
const translateExercisePart = (part) => {
  const partTranslations = {
    leg: '하체',
    chest: '가슴',
    arm: '팔',
    shoulder: '어깨',
    back: '등',
    cardio: '유산소',
  };

  return partTranslations[part] || part;
};

// 세트 추가 메서드
const addSet = (index) => {
  const exercise = { ...selectedExercises.value[index] };  // 새로운 세트 객체 복사
  selectedExercises.value.splice(index + 1, 0, exercise);  // 세트 추가
  exerciseStore.setSelectedExercises(selectedExercises.value); // 변경된 selectedExercises 값을 exerciseStore에 반영
  console.log(`세트 추가 완료. 총 ${selectedExercises.value.length} 세트.`);
};

// // 빈 객체로 반응형 상태를 초기화
// const quest = ref({
//   trainee_id: null,
//   trainer_id: null,
//   startAt: null,
//   endAt: null,
//   tasks: []
// });

// // 날짜를 DATETIME 형식으로 변환하는 유틸리티 함수
// const formatDateToDatetime = (date) => {
//   const d = new Date(date);
//   const year = d.getFullYear();
//   const month = String(d.getMonth() + 1).padStart(2, '0'); // 1월 = 0이므로 +1
//   const day = String(d.getDate()).padStart(2, '0');
//   const hours = String(d.getHours()).padStart(2, '0');
//   const minutes = String(d.getMinutes()).padStart(2, '0');
//   const seconds = String(d.getSeconds()).padStart(2, '0');
//   return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
// };

// // 퀘스트 등록 함수
// const registerQuest = async () => {
//   try {
//     // 기본 정보 설정 (traineeId, trainerId, startAt, endAt)
//     const traineeId = traineeStore.selectedTrainee.id;
//     const trainerId = traineeStore.selectedTrainee.trainerId;
//     const startAt = formatDateToDatetime(viewStore.selectedDate);
//     const endAt = formatDateToDatetime(new Date(viewStore.selectedDate).getTime() + 24 * 60 * 60 * 1000); // 24시간 후
    
//     // 예시로 사용할 운동 데이터
//     const getSelectedExercise = () => {
//       return {
//         exerciseType: 'Cardio', // Cardio 또는 Weight
//         exerciseId: 1,
//         cardioMinutes: 30, // Cardio 운동의 경우 시간
//         count: null,  // Weight 운동의 경우 count 사용
//         weightKg: null,  // Weight 운동의 경우 weightKg 사용
//       };
//     };
//     // Quest 데이터 설정
    
//     // 운동 정보를 조건에 맞춰 tasks 배열에 추가
//     const exercise = getSelectedExercise(); // 사용자가 선택한 운동 객체
//     if (exercise) {
//       addExerciseToTasks(exercise);
//     }

//     console.log(quest.value);  // quest 객체의 상태 확인

// // 퀘스트 데이터 설정 함수
// const setQuestData = (traineeId, trainerId, startDate, endDate) => {
//   quest.value.trainee_id = traineeId;
//   quest.value.trainer_id = trainerId;
//   quest.value.startAt = startDate;
//   quest.value.endAt = endDate;
// };

// setQuestData(traineeId, trainerId, startAt, endAt);
// // 운동 데이터를 tasks 배열에 추가하는 함수
// const addExerciseToTasks = (exercise) => {
//   if (exercise.exerciseType === 'Cardio') {
//     quest.value.tasks.push({
//       cardioMinutes: exercise.cardioMinutes || null,  // cardioMinutes가 없으면 null
//       exerciseId: exercise.exerciseId,  // exerciseId 추가
//       count: null,  // Cardio 운동은 count 없음
//       weightKg: null,  // Cardio 운동은 weightKg 없음
//     });
//   } else if (exercise.exerciseType === 'Weight') {
//     quest.value.tasks.push({
//       count: exercise.count || null,
//       weightKg: exercise.weightKg || null,
//       cardioMinutes: null,  // Weight 운동은 cardioMinutes 없음
//       exerciseId: exercise.exerciseId,  // exerciseId
//     });
//   }
// };



//퀘스트 등록 메서드
const registerQuest = async () => {
  try {
    // 날짜를 DATETIME 형식으로 변환하는 유틸리티 함수
    const formatDateToDatetime = (date) => {
      const d = new Date(date);
      const year = d.getFullYear();
      const month = String(d.getMonth() + 1).padStart(2, '0'); // 1월 = 0이므로 +1
      const day = String(d.getDate()).padStart(2, '0');
      const hours = String(d.getHours()).padStart(2, '0');
      const minutes = String(d.getMinutes()).padStart(2, '0');
      const seconds = String(d.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    };

    const questData = selectedExercises.value.map((exercise) => {
  // task 배열을 구성
  const tasks = [];

  // Cardio 운동일 경우
  if (exercise.exerciseType === 'Cardio') {
    tasks.push({
      cardioMinutes: exercise.cardioMinutes || null,
      exerciseId: exercise.exerciseId,  // exerciseId
      count: null,  // Cardio 운동은 count 없음
      weightKg: null,  // Cardio 운동은 weightKg 없음
    });
  }

  // Weight 운동일 경우
  if (exercise.exerciseType === 'Weight') {
    tasks.push({
      count: exercise.count || null,
      weightKg: exercise.weightKg || null,
      cardioMinutes: null,  // Weight 운동은 cardioMinutes 없음
      exerciseId: exercise.exerciseId,  // exerciseId
    });
  }
  console.log("태스크 데이터:", tasks.value);

  // 최종적으로 반환하는 데이터 구조
  return {
    traineeId: traineeStore.selectedTrainee.id,
    trainerId: traineeStore.selectedTrainee.trainerId,
    startAt: formatDateToDatetime(viewStore.selectedDate),
    endAt: formatDateToDatetime(new Date(viewStore.selectedDate).getTime() + 24 * 60 * 60 * 1000),
    tasks: tasks,  // tasks 배열만 다수로 포함
  };
});




    // 콘솔에 questData 찍기 (디버깅용)
    console.log("퀘스트 데이터:", questData);

    // 퀘스트 등록 API 호출 (예시)
    const response = await axios.post('http://localhost:8080/quest/', { questData });

    if (response.status === 200) {
      // 퀘스트 등록 성공
      console.log('퀘스트 등록 완료');
      // 필요한 후속 처리 (예: 퀘스트 목록 업데이트)
      // 예시: exerciseStore.fetchQuests();
    } else {
      console.error('퀘스트 등록 실패');
    }
  } catch (error) {
    console.error('퀘스트 등록 중 오류 발생:', error);
  }
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

.exercise-list {
  margin-top: 20px;
  text-align: center;
}

.exercise-cards {
  display: grid;
  grid-template-columns: count(auto-fill, minmax(200px, 400px));
  gap: 20px;
}

.exercise-card {
  padding: 10px;
  background-color: #f4f4f4;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.exercise-info {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 60%; 
  margin-right: 10px;
}

.separator {
  width: 5px;
  height: 35px;
  background-color: #8504e8;
  margin: 0 10px;
}

.exercise-part {
  width: 38px;
  font-size: 14px;
  color: #8504e8;
  font-weightKg: bold;
  white-space: nowrap;
  margin-right: 10px;
}

.exercise-name {
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 100%;
}

.input-group {
  display: flex;
  align-items: center;  
  justify-content: flex-end;
  width: 110px; 
  margin-right: 10px;
}

.input-field-group {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 50%;
}

.input-label {
  font-size: 12px;
  color: #555;
  margin-bottom: 5px;
}

.input-field {
  width: 100%;
  padding: 5px;
  font-size: 14px;
  text-align: center;
  align-items: center;
  max-width: 40px; 
}

.button-group {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-top: 20px;
}

.add-set-button {
  padding: 6px 12px;
  background-color: #8504e8;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 10px;
  width: 70px;
}

.add-set-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.delete-exercise-button {
  width: 100%;
  padding: 6px 12px;
  margin-bottom: 5px;
  font-size: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.delete-exercise-button {
  background-color: #e85050;
  color: white;
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
