<template>
  <div>
    <h5>피드백</h5>

    <!-- 피드백이 없는 경우 -->
    <div v-if="feedback === 'NONE'">
      <p>피드백이 없습니다.</p>
      <div class="register-btn-container">
      </div>
      <button class="register-btn" @click="startCreateFeedback">등록하기</button>
    </div>

    <!-- 피드백 작성 모드 -->
    <div v-if="isCreating">
      <textarea v-model="newFeedbackContent" placeholder="피드백을 작성하세요" class="form-control mt-2"></textarea>
      <button @click="submitCreateFeedback" class="btn btn-primary mt-2">등록 완료</button>
      <button @click="cancelAction" class="btn btn-secondary mt-2">취소</button>
    </div>

    <!-- 피드백 수정 모드 -->
    <div v-if="isEditing">
      <textarea v-model="newFeedbackContent" class="form-control"></textarea>
      <button @click="submitUpdateFeedback" class="btn btn-success mt-2">수정 완료</button>
      <button @click="cancelAction" class="btn btn-secondary mt-2">취소</button>
    </div>

    <!-- 피드백이 존재할 때 -->
    <div v-else-if="feedback !== 'NONE' && !isEditing && !isCreating">
      <p>{{ feedback.content }}</p>
      <button @click="startEditFeedback" class="btn btn-outline-secondary">수정하기</button>
    </div>
  </div>
</template>

<script setup>
import { useFeedbackStore } from '@/stores/feedback';
import { useQuestStore } from '@/stores/quest';
import { useUserStore } from '@/stores/user';
import { ref, computed, watch } from 'vue';

// 스토어 사용
const feedbackStore = useFeedbackStore();
const questStore = useQuestStore();
const userStore = useUserStore();

// 상태 관리
const feedback = ref('NONE'); // 현재 피드백 상태
const quest = computed(() => questStore.quest); // 현재 퀘스트
const questId = ref(null); // 현재 퀘스트 ID

const isCreating = ref(false); // 피드백 작성 모드
const isEditing = ref(false); // 피드백 수정 모드
const newFeedbackContent = ref(''); // 작성/수정 중인 피드백 내용
const newTrainerId = ref(null); // 트레이너 ID (기본값 1)

// 퀘스트 변경 감지
watch(
  quest,
  async (newQuest) => {
    if (newQuest?.questId) {
      questId.value = newQuest.questId; // questId 업데이트
      try {
        const res = await feedbackStore.getFeedback(questId.value);
        feedback.value = res || 'NONE';
      } catch (err) {
        console.error('피드백 로드 실패:', err);
      }
    }
  },
  { immediate: true } // 컴포넌트 로드시 실행
);

/**
 * 피드백 작성 시작
 */
const startCreateFeedback = () => {
  isCreating.value = true; // 작성 모드 활성화
  newFeedbackContent.value = ''; // 텍스트 초기화
  newTrainerId.value = 1; // 기본 트레이너 ID 설정
};

/**
 * 피드백 등록 완료
 */
const submitCreateFeedback = async () => {
  if (!newFeedbackContent.value.trim()) {
    alert('내용을 입력해주세요.');
    return;
  }

  try {
    const newFeedback = {
      questId: questId.value,
      trainerId: userStore.loginUser.numberId, // 트레이너 ID 추가
      content: newFeedbackContent.value,
    };

    // console.log(newFeedback);

    await feedbackStore.createFeedback(newFeedback); // 피드백 등록
    feedback.value = newFeedback; // 로컬 상태 업데이트
    isCreating.value = false; // 작성 모드 종료
    alert('피드백이 등록되었습니다.');
  } catch (err) {
    console.error('피드백 등록 실패:', err);
    alert('피드백 등록에 실패했습니다.');
  }
};

/**
 * 피드백 수정 시작
 */
const startEditFeedback = () => {
  isEditing.value = true; // 수정 모드 활성화
  newFeedbackContent.value = feedback.value.content; // 기존 내용 불러오기
};

/**
 * 피드백 수정 완료
 */
const submitUpdateFeedback = async () => {
  if (!newFeedbackContent.value.trim()) {
    alert('내용을 입력해주세요.');
    return;
  }

  try {
    const updatedFeedback = {
      content: newFeedbackContent.value,
    };

    await feedbackStore.updateFeedback(questId.value, updatedFeedback); // 피드백 수정
    feedback.value = { ...feedback.value, ...updatedFeedback }; // 로컬 상태 업데이트
    isEditing.value = false; // 수정 모드 종료
    alert('피드백이 수정되었습니다.');
  } catch (err) {
    console.error('피드백 수정 실패:', err);
    alert('피드백 수정에 실패했습니다.');
  }
};

/**
 * 작성/수정 취소
 */
const cancelAction = () => {
  isCreating.value = false; // 작성 모드 종료
  isEditing.value = false; // 수정 모드 종료
};
</script>

<style scoped>
textarea {
  width: 100%;
  min-height: 100px;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  margin-right: 10px;
}

input {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
</style>
