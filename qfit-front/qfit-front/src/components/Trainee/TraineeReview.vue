<template>
  <div>
    <!-- 이모지 섹션 -->
    <div class="difficulty-icons d-flex justify-content-center align-items-center">
      <!-- EASY -->
      <span
        :class="{ active: difficulty === 'EASY', easy: true }"
        @click="isSelecting && selectDifficulty('EASY')"
      >
        <i class="bi bi-emoji-smile"></i>
        <p class="difficulty-text">easy</p>
      </span>
      <!-- MEDIUM -->
      <span
        :class="{ active: difficulty === 'MEDIUM', medium: true }"
        @click="isSelecting && selectDifficulty('MEDIUM')"
      >
        <i class="bi bi-emoji-neutral"></i>
        <p class="difficulty-text">soso</p>
      </span>
      <!-- HARD -->
      <span
        :class="{ active: difficulty === 'HARD', hard: true }"
        @click="isSelecting && selectDifficulty('HARD')"
      >
        <i class="bi bi-emoji-frown"></i>
        <p class="difficulty-text">hard</p>
      </span>
    </div>

    <!-- 버튼 섹션 -->
    <div class="button-section mt-3 text-center">
      <!-- 선택 모드 -->
      <div v-if="isSelecting">
        <button v-if="isEditing" @click="updateReview" class="btn btn-success">
          수정 완료
        </button>
        <button v-else @click="saveReview" class="btn btn-success">
          등록 완료
        </button>
        <button @click="cancelSelection" class="btn btn-secondary ms-2">
          취소
        </button>
      </div>

      <!-- 기본 모드 -->
      <div v-else>
        <button v-if="difficulty === 'NONE'" @click="startSelection" class="btn btn-primary">
          리뷰 등록하기
        </button>
        <button v-else @click="enterEditMode" class="btn btn-secondary">
          수정
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useQuestStore } from '@/stores/quest';
import { useReviewStore } from '@/stores/review';
import { useUserStore } from '@/stores/user';

const questStore = useQuestStore();
const reviewStore = useReviewStore();
const userStore = useUserStore();

// 현재 퀘스트 정보
const quest = computed(() => questStore.quest);
const questId = ref(null); // 현재 퀘스트 ID

// 현재 리뷰 정보
const review = computed(() => reviewStore.review);
const difficulty = ref('NONE'); // 난이도 상태 ('EASY', 'MEDIUM', 'HARD' 등)
const originalDifficulty = ref('NONE'); // 수정 취소 시 복구용

// 선택 모드 관리
const isSelecting = ref(false); // true면 선택 가능
const isEditing = ref(false); // true면 수정 모드

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

// "리뷰 등록하기" 버튼 클릭 시 선택 모드로 전환
const startSelection = () => {
  isSelecting.value = true; // 선택 모드 활성화
  isEditing.value = false; // 등록 모드
};

// "수정" 버튼 클릭 시 수정 모드로 전환
const enterEditMode = () => {
  isSelecting.value = true; // 선택 모드 활성화
  isEditing.value = true; // 수정 모드 활성화
  originalDifficulty.value = difficulty.value; // 기존 난이도를 저장 (취소 시 복구용)
};

// 이모지를 클릭하여 난이도 선택
const selectDifficulty = (level) => {
  if (isSelecting.value) {
    difficulty.value = level; // 선택한 난이도로 업데이트
  }
};

// 선택 완료 버튼 클릭 시 (리뷰 등록)
const saveReview = async () => {
  try {
    if (questId.value) {
      // 서버에 전송할 리뷰 객체 생성
      const newReview = {
        questId: questId.value,
        traineeId: userStore.loginUser.numberId,
        difficulty: difficulty.value,
      };
      console.log('새 리뷰:', newReview);

      await reviewStore.createReview(newReview); // 새로운 리뷰 생성
      alert('리뷰가 성공적으로 저장되었습니다.');
      isSelecting.value = false; // 선택 모드 종료
    }
  } catch (err) {
    console.error('리뷰 저장 실패:', err);
    alert('리뷰 저장에 실패했습니다.');
  }
};

// 선택 완료 버튼 클릭 시 (리뷰 수정)
const updateReview = async () => {
  try {
    if (questId.value) {
      // 서버에 전송할 리뷰 객체 생성
      const updatedReview = {
        questId: questId.value,
        difficulty: difficulty.value,
      };
      console.log('수정된 리뷰:', updatedReview);

      await reviewStore.updateReview(questId.value, updatedReview); // 기존 리뷰 업데이트
      alert('리뷰가 성공적으로 수정되었습니다.');
      isSelecting.value = false; // 선택 모드 종료
      isEditing.value = false; // 수정 모드 종료
    }
  } catch (err) {
    console.error('리뷰 수정 실패:', err);
    alert('리뷰 수정에 실패했습니다.');
  }
};

// 선택 취소 버튼 클릭 시
const cancelSelection = () => {
  isSelecting.value = false; // 선택 모드 종료
  if (isEditing.value) {
    difficulty.value = originalDifficulty.value; // 난이도를 수정 전 상태로 복구
  }
  isEditing.value = false; // 수정 모드 종료
};
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
