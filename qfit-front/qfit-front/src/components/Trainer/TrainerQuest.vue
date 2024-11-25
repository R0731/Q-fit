<template>
  <div v-if="trainee" class="container">
    <!-- 퀘스트 헤더 -->
    <div class="quest-header">
      <h1 class="quest-header-title">{{ trainee.userName || '정보 없음' }} 회원님의 퀘스트</h1>
      <p class="quest-header-subtitle">{{ viewStore.selectedDate }}</p>
    </div>

    <!-- 퀘스트 내용 -->
    <div v-if="hasQuest" class="quest-section">
      <component :is="currentComponent" @switchToEditor="switchToEditor" @switchToView="switchToView"/>
      <!-- <component :is="currentComponent" :onSwitchToView="switchToView" /> -->
      <!-- <Exercise /> -->
      <Review />
      <FeedbackUser />
    </div>

    <!-- 퀘스트가 없는 경우 -->
    <div v-else class="no-quest">
      <p class="no-quest-message">현재 등록된 퀘스트가 없습니다.</p>
      <button @click="createQuest" class="register-btn">퀘스트 등록하기</button>
    </div>
  </div>
</template>

<script setup>
import { useViewStore } from '@/stores/viewStore';
import { useQuestStore } from '@/stores/quest';
import Exercise from './Exercise.vue';
import FeedbackUser from './FeedbackUser.vue';
import Review from './TrainerReview.vue';
import { computed, onMounted, ref, onActivated } from 'vue';
import { useRouter } from 'vue-router';
import { useTraineeStore } from '@/stores/trainee';
import ExerciseEditor from './ExerciseEditor.vue';

// Vue Router 사용을 위한 초기화
const router = useRouter();

// Pinia 스토어
const questStore = useQuestStore(); // 퀘스트 관련 상태 및 메서드를 관리하는 스토어
const traineeStore = useTraineeStore(); // 트레이니 데이터 관리하는 스토어const viewStore = useViewStore(); // 뷰 상태(날짜 선택 등)를 관리하는 스토어

// 현재 선택된 트레이니를 가져오기 (Pinia 상태로부터 계산된 값)
const trainee = computed(() => traineeStore.selectedTrainee);

// 퀘스트의 존재 여부를 확인하는 계산된 속성
// questStore의 quest 상태가 null 또는 undefined인지 여부를 boolean 값으로 반환
const hasQuest = computed(() => !!questStore.quest);

/**
 * 퀘스트 데이터를 로드하는 함수
 * - 트레이니 ID와 선택된 날짜를 기반으로 퀘스트를 로드
 */
const loadQuest = async () => {
  try {
    console.log('트레이니:', trainee.value); // 트레이니 정보를 로그로 출력

    // 퀘스트 로드
    await questStore.getQuestByIdAndStartDate(trainee.value.id, viewStore.selectedDate);
    console.log('퀘스트 로드 완료'); // 로드 성공 시 출력
  } catch (error) {
    console.error('퀘스트 로드 실패:', error.message); // 에러 발생 시 에러 메시지 출력
  }
};

// 컴포넌트가 마운트될 때 실행되는 라이프사이클 훅
onMounted(() => {
  if (trainee.value) {
    loadQuest(); // 트레이니 데이터가 있는 경우 퀘스트 로드
  } else {
    console.warn('트레이니 데이터가 없습니다.'); // 트레이니 데이터가 없을 경우 경고 출력
  }
});

// 컴포넌트가 활성화될 때 실행되는 라이프사이클 훅
onActivated(() => {
  questStore.fetchQuestList(); // 퀘스트 목록 새로고침
});

// '퀘스트 등록하기' 버튼 클릭 시 호출되는 함수
// 퀘스트 등록 페이지로 이동
const createQuest = () => {
  router.push({ name: 'questAssign' });
};

// 현재 렌더링 중인 컴포넌트를 관리하는 상태 (기본값: Exercise)
const currentComponent = ref(Exercise);

// 동적으로 렌더링할 컴포넌트 맵
// 현재는 Exercise와 ExerciseEditor를 지원
const components = {
  Exercise,
  ExerciseEditor,
};

// 수정 버튼 클릭 시 호출되는 함수
// 현재 컴포넌트를 ExerciseEditor로 전환
const switchToEditor = () => {
  currentComponent.value = ExerciseEditor;
};

// 편집 종료 후 호출되는 함수
// 현재 컴포넌트를 Exercise로 복원
const switchToView = () => {
  currentComponent.value = Exercise;
};
</script>


<style scoped>
/* 전체 컨테이너 스타일 */
.container {
  position:relative;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

/* 퀘스트 헤더 섹션 */
.quest-header {
  text-align: center;
  margin-bottom: 30px;
}

.quest-header-title {
  font-size: 1.6rem;
  font-weight: bold;
  color: #242424;
  margin: 0;
}

.quest-header-subtitle {
  font-size: 1.2rem;
  color: #777;
  margin-top: 5px;
}

/* 퀘스트 섹션 */
.quest-section {
  display: flex;
  flex-direction: column;
  gap: 50px;
}

/* 퀘스트가 없는 경우 */
.no-quest {
  text-align: center;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
}

.no-quest-message {
  font-size: 1.2rem;
  color: #555;
  margin-bottom: 20px;
}

.create-quest-btn {
  padding: 10px 20px;
  font-size: 1rem;
  font-weight: bold;
  background-color: var(--theme-color);
  color: white;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.create-quest-btn:hover {
  background-color: darken(var(--theme-color), 10%);
}

/* 반응형 레이아웃 */
@media (max-width: 768px) {
  .container {
    padding: 15px;
  }

  .quest-header-title {
    font-size: 1.5rem;
  }

  .quest-header-subtitle {
    font-size: 1rem;
  }

  .create-quest-btn {
    font-size: 0.9rem;
    padding: 8px 16px;
  }
}
</style>
