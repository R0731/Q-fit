<template>
  <div>
    <div class="calender">
      <TheCalender />
    </div>
    <div class="card">
      <div class="list-header">
        <h3>피드백 리스트</h3>
        <p v-if="!feedbacks.length" class="no-feedback">등록할 피드백이 없습니다.</p>
      </div>
      <ul v-if="feedbacks.length">
        <!-- 트레이니 리스트 -->
        <li v-for="(feedback) in feedbacks" :key="feedback.trainee_id" class="feedback-item">
          <!-- 프로필 이미지 -->
          <img src="@/assets/default_profile.png" alt="Profile" class="profile-img" />
          <!-- 트레이니 정보 -->
          <div class="feedback-info">
            <small class="feedback-date">{{ feedback.quest_start_at }}</small> <!-- 날짜 표시 -->
            <span class="trainee-name">{{ feedback.trainee_name }}</span> <!-- 이름 -->
          </div>
          <!-- 피드백 등록 버튼 -->
          <button class="small-btn" @click="selectTrainee(feedback.trainee_id, feedback.quest_start_at)">피드백 등록</button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { useFeedbackStore } from "@/stores/feedback";
import TheCalender from "../common/TheCalender.vue";
import { onMounted, ref } from "vue";
import { useUserStore } from "@/stores/user";
import { useTraineeStore } from "@/stores/trainee";
import { useRouter } from "vue-router";
import { useViewStore } from "@/stores/viewStore";

const feedbackStore = useFeedbackStore();
const userStore = useUserStore();
const traineeStore = useTraineeStore();
const viewStore = useViewStore();

// 피드백 리스트 임시 데이터
const feedbacks = ref([]);
const trainerId = userStore.loginUser.numberId;
const router = useRouter();

// 피드백 등록 버튼 클릭 핸들러
const selectTrainee = (trainee_id, quest_start_at) => {
  // 선택한 데이터를 상태로 저장 후 화면 전환
  traineeStore.selectedTrainee = traineeStore.trainees.find((trainee) => trainee.id === trainee_id); // Store에 선택된 훈련생 저장
  viewStore.selectedDate = quest_start_at;
  // 퀘스트 상세창으로 이동
  router.push({ name: 'quest' }); // 라우터 이동
};

onMounted(() => {
  feedbackStore
    .getFeedbackPendingQuests(trainerId)
    .then(()=>{
      feedbacks.value = feedbackStore.feedbacks;
    })
    .catch((err)=>{
      console.error(err);
    })
});
</script>

<style scoped>
/* 전체 카드 컨테이너 스타일 */
.card {
  padding: 20px;
  border-radius: 10px;
  background-color: transparent; /* 배경색 투명 */
  box-shadow: none; /* 그림자 제거 */
  border: none; /* 테두리 제거 */
}

/* 리스트 스타일 */
ul {
  padding: 0;
  margin: 0;
  list-style: none; /* 불릿 포인트 제거 */
}

/* 헤더 섹션 스타일 */
.list-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

/* 피드백 리스트 아이템 스타일 */
.feedback-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 15px;
  padding: 10px;
  border-radius: 10px;
  transition: background-color 0.2s ease;
  background-color: #f9f9f9; /* 리스트 배경 */
}

.feedback-item:hover {
  background-color: #f1f1f1; /* Hover 시 색상 변경 */
}

/* 프로필 이미지 */
.profile-img {
  width: 50px;
  height: 50px;
  border-radius: 50%; /* 원형 이미지 */
  margin-right: 15px;
  object-fit: cover;
}

/* 피드백 정보 섹션 */
.feedback-info {
  flex-grow: 1; /* 정보 섹션 확장 */
  display: flex;
  flex-direction: column;
}

/* 날짜 스타일 */
.feedback-date {
  font-size: 0.8rem;
  color: #888; /* 날짜 텍스트 색상 */
}

/* 이름 강조 */
.trainee-name {
  font-weight: bold;
  font-size: 1.2rem; /* 이름 크기 */
  color: #333;
}

/* 피드백 등록 버튼 */
.small-btn {
  padding: 8px 16px; /* 간단한 여백 */
  font-size: 0.9rem; /* 텍스트 크기 */
  font-weight: bold; /* 굵은 텍스트 */
  border: none; /* 테두리 제거 */
  border-radius: 20px; /* 둥근 모서리 */
  background: linear-gradient(90deg, var(--theme-color), #9d47f4); /* 기본 그라데이션 배경 */
  color: #fff; /* 기본 텍스트 색상 */
  cursor: pointer; /* 클릭 가능한 커서 */
  transition: all 0.3s ease; /* 부드러운 애니메이션 */
}

.small-btn:hover {
  background: #fff; /* hover 시 흰색 배경 */
  color: var(--theme-color); /* hover 시 텍스트 테마 색상 */
  border: 1px solid var(--theme-color); /* 테두리 추가 */
}
</style>
