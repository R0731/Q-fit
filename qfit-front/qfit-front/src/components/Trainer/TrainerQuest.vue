<!-- TrainerQuest.vue -->
<template>
  <div v-if="trainee">
    <!-- 퀘스트 헤더 -->
    <p>{{ trainee.userName || '정보 없음' }} 회원님의 {{ viewStore.selectedDate }} 퀘스트</p>
    
    <!-- 퀘스트 내용 -->
    <div v-if="hasQuest">
      <Exercise />
      <Review />
      <FeedbackUser />
    </div>
    
    <!-- 퀘스트가 없는 경우 -->
    <div v-else>
      <button @click="createQuest">퀘스트등록하기</button>
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
.text-center {
  text-align: center;
}
</style>