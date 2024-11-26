<template>
  <div>
    <!-- 이모지 섹션 -->
    <div class="difficulty-icons d-flex justify-content-center align-items-center">
      <!-- EASY -->
      <span
        :class="{ active: difficulty === 'EASY', easy: true }"
      >
        <i class="bi bi-emoji-smile"></i>
        <p class="difficulty-text">easy</p>
      </span>
      <!-- MEDIUM -->
      <span
        :class="{ active: difficulty === 'MEDIUM', medium: true }"
      >
        <i class="bi bi-emoji-neutral"></i>
        <p class="difficulty-text">soso</p>
      </span>
      <!-- HARD -->
      <span
        :class="{ active: difficulty === 'HARD', hard: true }"
      >
        <i class="bi bi-emoji-frown"></i>
        <p class="difficulty-text">hard</p>
      </span>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useQuestStore } from '@/stores/quest';
import { useReviewStore } from '@/stores/review';

const questStore = useQuestStore();
const reviewStore = useReviewStore();

const quest = computed(() => questStore.quest); // 현재 퀘스트 정보
const questId = ref(null); // 현재 퀘스트 ID

const review = computed(() => reviewStore.review); // 현재 리뷰 정보
const difficulty = ref('NONE'); // 난이도 상태 ('EASY', 'MEDIUM', 'HARD' 등)

// 퀘스트 변경 감지
watch(
  quest,
  async (newQuest) => {
    if (newQuest?.questId) {
      questId.value = newQuest.questId; // questId 업데이트
      try {
        await reviewStore.getReview(questId.value); // 리뷰 데이터 로드
        difficulty.value = review.value?.difficulty || 'NONE'; // 기존 리뷰 난이도 설정
      } catch (err) {
        console.error('리뷰 로드 실패:', err);
      }
    }
  },
  { immediate: true } // 컴포넌트 로드시 실행
);
</script>

<style scoped>
/* 이모지 섹션 */
.difficulty-icons {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px; /* 이모지 간 간격 */
}

.difficulty-icons span {
  text-align: center;
  cursor: pointer;
  transition: transform 0.2s ease; /* 클릭 시 부드럽게 크기 변경 */
}

.difficulty-icons i {
  font-size: 3rem; /* 이모지 크기 */
  color: gray;
}

.difficulty-text {
  margin-top: 5px;
  font-size: 0.9rem;
}

span.active.easy i {
  color: green;
}
span.active.medium i {
  color: orange;
}
span.active.hard i {
  color: red;
}

span.active {
  transform: scale(1.1);
}
</style>
