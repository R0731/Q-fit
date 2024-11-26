<!-- TraineeList.vue -->
<template>
  <div>
    <!-- 캘린더 -->
    <TheCalender @update:selectedDate="onDateSelected" />
    <div class="card">
      <div class="list-header">
        <!-- 타이틀 -->
        <h3>회원 조회</h3>
        <!-- 피드백 버튼 -->
        <button class="register-btn" @click="goFeedbackList">피드백</button>
      </div>
      <ul v-if="trainees.length > 0">
        <!-- 트레이니 리스트 -->
        <li v-for="trainee in trainees" :key="trainee.id" 
            @click="selectTrainee(trainee)" 
            :class="['trainee-item', getStatusClass(trainee.questStatus)]">
          <!-- 프로필 이미지 -->
          <img
              :src="trainee.profileImageUrl || defaultProfileImage"
              alt="Profile"
              class="profile-img">
          <!-- 트레이니 정보 -->
          <div class="trainee-info">
            <span class="trainee-name">{{ trainee.userName }}</span>
            <span class="trainee-age">{{ trainee.age }}세</span>
          </div>
          <div class="trainee-status">{{ trainee.questStatus }}</div>
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
import { useImageStore } from "@/stores/imageStore";
import { useViewStore } from "@/stores/viewStore";
import defaultProfileImage from "@/assets/default_profile.png";

const traineeStore = useTraineeStore();
const imageStore = useImageStore();
const viewStore = useViewStore();
const userStore = useUserStore();

const router = useRouter();

const trainerId = userStore.loginUser.numberId; // 트레이너(자신)의 고유 id
const trainees = ref([]); // 트레이니 리스트
const selectedDate = ref(viewStore.selectedDate); // 선택된 날짜

// 날짜 선택 시 호출되는 메서드
const onDateSelected = (date) => {
  selectedDate.value = date; // 선택한 날짜 업데이트
  fetchTrainees(); // 새 날짜에 맞는 데이터를 다시 가져옴
};

// 트레이니 리스트 로드
const fetchTrainees = () => {
  console.log(viewStore.selectedDate)
  traineeStore
    .fetchTraineesWithQuestStatuses(trainerId, viewStore.selectedDate)
    .then(() => {
      trainees.value = traineeStore.trainees;

      // 데이터 로드 후 프로필 이미지 로드 실행
      if (trainees.value.length > 0) {
        loadProfileImages(); // 프로필 이미지 로드
      } else {
        console.error("트레이니 데이터가 비어 있습니다.");
      }
    })
    .catch((err) => {
      console.error(err);
    });
};

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

const loadProfileImages = async () => {
  // 각 트레이니의 프로필 이미지를 S3에서 로드
  for (const trainee of trainees.value) {
    if (trainee.userImg) {
      try {
        const blob = await imageStore.loadFile(trainee.userImg);
        trainee.profileImageUrl = URL.createObjectURL(blob); // Blob URL 생성
      } catch (error) {
        console.error(`이미지 로드 실패 (${trainee.userImg}):`, error);
        trainee.profileImageUrl = defaultProfileImage; // 실패 시 기본 이미지 설정
      }
    } else {
      trainee.profileImageUrl = defaultProfileImage; // 실패 시 기본 이미지 설정
    }
  }
};

// 퀘스트 상태에 따른 클래스 변화
const getStatusClass = (status) => {
  switch (status) {
    case '퀘스트 미등록':
      return 'status-unregistered';
    case '퀘스트 수행중':
      return 'status-in-progress';
    case '퀘스트 완료':
      return 'status-completed';
    default:
      return ''; // 기본값 (클래스 없음)
  }
};

// 컴포넌트가 마운트될 때 데이터 로드
onMounted(async () => {
  fetchTrainees();
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
  margin-left: auto; /* 자동으로 오른쪽 끝으로 밀림 */
  color: #555;
}

/* 상태별 스타일 */
/* 퀘스트 미등록 */
.status-unregistered {
  background-color: #f8d7da; /* 연한 빨간색 */
}

/* 퀘스트 수행중 */
.status-in-progress {
  background-color: #fff3cd; /* 연한 노란색 */
}

/* 퀘스트 완료 */
.status-completed {
  background-color: #d4edda; /* 연한 녹색 */
}
</style>