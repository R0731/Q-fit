<template>
  <div v-if="trainee" class="container">
    <!-- 퀘스트 헤더 -->
    <div class="quest-header">
      <h1 class="quest-header-title">{{ trainee.userName || '정보 없음' }} 회원님의 퀘스트</h1>
      <p class="quest-header-subtitle">{{ viewStore.selectedDate }}</p>
    </div>

    <!-- 퀘스트 내용 -->
    <div v-if="hasQuest" class="quest-section">
      <Exercise />
      <Review />
      <FeedbackUser />
    </div>

    <!-- 퀘스트가 없는 경우 -->
    <div v-else class="no-quest">
      <p class="no-quest-message">현재 등록된 퀘스트가 없습니다.</p>
      <button @click="createQuest" class="create-quest-btn">퀘스트 등록하기</button>
    </div>
  </div>
</template>

<script setup>
import { useTrainerStore } from '@/stores/trainer';
import { useViewStore } from '@/stores/viewStore';
import { useQuestStore } from '@/stores/quest';
import Exercise from './Exercise.vue';
import FeedbackUser from './FeedbackUser.vue';
import Review from './TrainerReview.vue';
import { computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();
// Pinia 스토어
const questStore = useQuestStore();
const trainerStore = useTrainerStore();
const viewStore = useViewStore();

// 현재 선택된 트레이니
const trainee = computed(() => trainerStore.selectedTrainee);

// 퀘스트 상태 확인 (퀘스트가 null이 아니면 true)
const hasQuest = computed(() => !!questStore.quest);

/**
 * 퀘스트 데이터 로드
 */
const loadQuest = async () => {
  try {
    console.log('트레이니:', trainee.value);

    // 퀘스트 로드
    await questStore.getQuestByIdAndStartDate(trainee.value.id, viewStore.selectedDate);

    console.log('퀘스트 로드 완료');
  } catch (error) {
    console.error('퀘스트 로드 실패:', error.message);
  }
};

/* 컴포넌트 초기화 */
onMounted(() => {
  if (trainee.value) {
    loadQuest();
  } else {
    console.warn('트레이니 데이터가 없습니다.');
  }
});

const createQuest = () => {
  router.push({ name: 'questAssign' });
};
</script>

<style scoped>
/* 전체 컨테이너 스타일 */
.container {
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
  font-size: 1.8rem;
  font-weight: bold;
  color: var(--theme-color);
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
  gap: 20px;
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
