<!-- TraineeList.vue -->
<template>
  <div>
    <!-- 공통 캘린더 컴포넌트 -->
    <TheCalender />
    <div class="card">
      <div class="list-header">
        <!-- 타이틀 -->
        <h3>회원 조회</h3>
        <!-- 피드백 버튼 -->
        <button class="register-btn" @click="goFeedbackList">피드백</button>
      </div>
      <ul>
        <!-- 트레이니 리스트 -->
        <li v-for="(trainee,) in trainees" :key="trainee.userId" @click="selectTrainee(trainee)" class="trainee-item">
          <!-- 프로필 이미지 -->
          <img src="@/assets/default_profile.png" alt="Profile" class="profile-img" />
          <!-- 트레이니 정보 -->
          <div class="trainee-info">
            <span class="trainee-name">{{ trainee.userName }}</span>
            <span class="trainee-age">{{ trainee.age }}세</span>
            <span class="trainee-status">{{ trainee.status }}</span>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import TheCalender from "../common/TheCalender.vue";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useTraineeStore } from "@/stores/trainee";

const router = useRouter();
const userStore = useUserStore();
const trainerId = userStore.loginUser.numberId;

const traineeStore = useTraineeStore();
const trainees = ref([]);

onMounted(()=>{
  traineeStore.fetchTraineeList(trainerId)
    .then(()=>{
      trainees.value = traineeStore.trainees;
    })
    .catch((err) => {
      console.error(err);
    });
});

// onMounted(() => {
//   // 실제 데이터를 불러오는 대신 임시 데이터를 설정
//   trainees.value = [
//     {
//       userId: 1,
//       userName: "김철수",
//       age: 25,
//       status: "운동 중",
//     },
//     {
//       userId: 2,
//       userName: "이영희",
//       age: 30,
//       status: "휴식 중",
//     },
//     {
//       userId: 3,
//       userName: "박민수",
//       age: 22,
//       status: "운동 완료",
//     },
//   ];
// });

// 피드백 리스트 화면으로 이동
const goFeedbackList = () => {
  router.push({ name: "feedbackList" });
};

// 선택한 트레이니 데이터 저장 후 화면 전환
const selectTrainee = (trainee) => {
  // 선택한 훈련생 데이터를 상태로 유지 후 화면 전환
  traineeStore.selectedTrainee = trainee; // Store에 선택된 훈련생 저장
  router.push({ name: 'quest' }); // 라우터 이동
};
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

/* 트레이니 리스트 */
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

/* 트레이니 리스트 아이템 */
.trainee-item {
  display: flex;
  align-items: center;
  justify-content: flex-start; /* 왼쪽 정렬 */
  margin-bottom: 15px;
  padding: 10px;
  border: none; /* 테두리 제거 */
  border-radius: 10px;
  transition: background-color 0.2s ease;
}

.trainee-item:hover {
  background-color: #f1f1f1;
}

/* 트레이니 프로필 이미지 */
.profile-img {
  width: 50px;
  height: 50px;
  border-radius: 50%; /* 원형 이미지 */
  margin-right: 15px;
  object-fit: cover;
}

/* 트레이니 정보 섹션 */
.trainee-info {
  display: flex;
  flex-direction: column;
}

/* 트레이니 이름 강조 */
.trainee-name {
  font-weight: bold;
  font-size: 1.1rem;
}

/* 트레이니 나이 */
.trainee-age {
  color: #777;
  font-size: 0.9rem;
}

/* 트레이니 상태 */
.trainee-status {
  margin-top: 5px;
  color: #555;
}
</style>