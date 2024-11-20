<template>
  <div>
    <div class="calender">
    <TheCalender/>
  </div>
    <div class="card">
      <h3>피드백 리스트</h3>
      <ul>
        <!-- 트레이니 리스트 -->
        <li v-for="(feedback,) in feedbacks" :key="feedback.userId" class="feedback-item">
          <!-- 트레이니 정보 -->
          <div class="feedback-info">
            <small class="feedback-date">{{ feedback.feedbackDate }}</small> <!-- 날짜 표시 -->
            <span class="trainee-name">{{ feedback.userName }}</span> <!-- 이름 -->
          </div>
          <!-- 피드백 버튼 -->
          <button class="feedback-btn" @click="leaveFeedback(feedback.userId)">피드백 남기기</button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import TheCalender from "../common/TheCalender.vue";
import { onMounted, ref } from "vue";

// 피드백 리스트 임시 데이터
const feedbacks = ref([]);

onMounted(() => {
  feedbacks.value = [
    {
      userId: 1,
      userName: "김철수",
      feedbackDate: "2024-11-19",
    },
    {
      userId: 2,
      userName: "이영희",
      feedbackDate: "2024-11-18",
    },
    {
      userId: 3,
      userName: "박민수",
      feedbackDate: "2024-11-17",
    },
  ];
});

// 피드백 남기기 버튼 클릭 핸들러
const leaveFeedback = (userId) => {
  console.log(`피드백 남기기 버튼 클릭 - User ID: ${userId}`);
};
</script>

<style scoped>
.calender{
  display: hidden;
}
/* 전체 카드 컨테이너 스타일 */
.card {
  /* display: flex; */
  width: 100%; /* 부모인 app-container와 동일한 너비 */
  margin: 0; /* 상하좌우 여백 제거 */
  padding: 10px; /* 내부 여백 */
  background-color: transparent;
  border: none;
}

/* 리스트 스타일 */
ul {
  padding: 0;
  margin: 0;
  list-style: none;
  width: 100%; /* 리스트도 가로로 꽉 채움 */
}

/* 리스트 아이템 스타일 */
.feedback-item {
  width: 100%; /* 리스트 항목도 부모 너비에 맞게 */
  margin: 0 0 15px 0; /* 아래쪽에만 여백 */
  padding: 10px;
  box-sizing: border-box; /* 패딩 포함 크기 계산 */
  display: flex;
  justify-content: space-between; /* 이름과 버튼 양쪽 끝 배치 */
  align-items: center;
  border-radius: 10px;
  background-color: #f9f9f9;
}


.feedback-item:hover {
  background-color: #f1f1f1;
}

/* 트레이니 정보 섹션 */
.feedback-info {
  display: flex;
  flex-direction: column;
}

/* 날짜 스타일 */
.feedback-date {
  font-size: 0.8rem;
  color: #888;
}

/* 이름 스타일 */
.trainee-name {
  font-weight: bold;
  font-size: 1rem; /* 글자 크기를 줄임 */
}

/* 피드백 버튼 스타일 */
.feedback-btn {
  padding: 5px 15px;
  border: none;
  border-radius: 20px;
  background-color: var(--theme-color);
  color: #fff;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.feedback-btn:hover {
  background-color: #4e00b7; /* Hover 시 색상이 20% 어두워짐 */
}
</style>
