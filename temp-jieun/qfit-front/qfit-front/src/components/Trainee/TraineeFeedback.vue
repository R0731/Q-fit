<template>
  <div class="container">
    <h5>피드백</h5>
    <div v-if="feedback === 'NONE'">
      <p>피드백이 없습니다.</p>
    </div>
    <div v-else>
      {{ feedback.content }}
    </div>
  </div>
</template>

<script setup>
import { useFeedbackStore } from '@/stores/feedback';
import { useQuestStore } from '@/stores/quest';
import { ref, computed, watch } from 'vue';

const questStore = useQuestStore();
const feedbackStore = useFeedbackStore();

const feedback = ref('NONE');
const quest = computed(() => questStore.quest);
const questId = ref(null); // 현재 퀘스트 ID

// 퀘스트 변경 감지
watch(
  quest,
  async(newQuest) => {
    if (newQuest?.questId) {
      questId.value = newQuest.questId; // questId 업데이트
      try {
        const res = await feedbackStore.getFeedback(questId.value);
        feedback.value = res || 'NONE';
      } catch (err) {
        console.error('리뷰 로드 실패:', err);
      }
    }
  },
  { immediate: true } // 컴포넌트 로드시 실행
);

</script>

<style scoped>
.container {
  max-width: 100%;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
</style>