<template>
    <div class="page-container">
      <!-- 페이지 제목 -->
      <h3 class="page-title">내 회원 목록</h3>
  
      <!-- 회원 관리 버튼 -->
      <div class="button-container">
        <button class="blue-btn" @click="goToUpdate">회원 추가</button>
        <button class="blue-btn delete-btn" @click="goToDelete">회원 삭제</button>
      </div>
  
      <!-- 회원 목록 카드 -->
      <div class="card">
        <ul>
          <!-- 회원 리스트 -->
          <li v-for="(trainee, index) in trainees" :key="index" class="trainee-item">
            <!-- 프로필 이미지 -->
            <img
              :src="trainee.profileImageUrl"
              alt="Profile"
              class="profile-img">
            <!-- 회원 정보 -->
            <div class="trainee-info">
              <span class="trainee-name">{{ trainee.userName }}</span>
              <small class="trainee-age">{{ trainee.age }}세</small>
            </div>
            <!-- 퀘스트 조회하기 버튼 -->
            <button class="small-btn" @click="viewQuest(trainee.id)">퀘스트 조회하기</button>
          </li>
        </ul>
      </div>
    </div>
  </template>
  
  <script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useTraineeStore } from "@/stores/trainee";
import { useImageStore } from "@/stores/imageStore";
import defaultProfileImage from "@/assets/default_profile.png";

const router = useRouter();


// 페이지 이동 함수
const goToUpdate = () => router.push({ name: "MyTraineesUpdate" });
const goToDelete = () => router.push({ name: "MyTraineesDelete" });

// 퀘스트 조회 함수
const viewQuest = (id) => {
  router.push({ name: "QuestView", params: { id } });
};

// 사용자 및 트레이니 데이터 로드
const userStore = useUserStore();
const traineeStore = useTraineeStore();
const imageStore = useImageStore();

const trainees = ref([]);

// const loadProfileImages = async () => {
//   // 각 트레이니의 프로필 이미지를 S3에서 로드
//   for (const trainee of trainees.value) {
//     if (trainee.userImg) {
//       console.log(`이미지 파일 이름: ${trainee.userImg}`); // 디버그 로그 추가
//       try {
//         const blob = await imageStore.loadFile(trainee.userImg);
//         trainee.profileImageUrl = URL.createObjectURL(blob); // Blob URL 생성
//         console.log('트레이니이미지', trainee.profileImageUrl)
//         console.log(`이미지 로드 성공: ${trainee.userImg}`); // 디버그 로그 추가
//       } catch (error) {
//         console.error(`이미지 로드 실패 (${trainee.userImg}):`, error);
//         trainee.profileImageUrl = defaultProfileImage; // 실패 시 기본 이미지 설정
//       }
//     } else {
//       console.log("이미지 파일 이름이 없습니다."); // 디버그 로그 추가
//       trainee.profileImageUrl = defaultProfileImage; // 실패 시 기본 이미지 설정
//     }
//   }
// };

const loadProfileImages = async () => {
  for (const trainee of trainees.value) {
    if (trainee.userImg) {
      console.log(`이미지 파일 이름: ${trainee.userImg}`);
      try {
        const blob = await imageStore.loadFile(trainee.userImg);
        if (blob) {
          console.log('Blob 생성 성공:', blob);
          trainee.profileImageUrl = URL.createObjectURL(blob);
        } else {
          console.error('Blob 데이터가 비어 있습니다.');
          trainee.profileImageUrl = defaultProfileImage;
        }
        console.log('트레이니이미지', trainee.profileImageUrl);
      } catch (error) {
        console.error(`이미지 로드 실패 (${trainee.userImg}):`, error);
        trainee.profileImageUrl = defaultProfileImage;
      }
    } else {
      console.log("이미지 파일 이름이 없습니다.");
      trainee.profileImageUrl = defaultProfileImage;
    }
  }
};

onMounted(async () => {
  const trainerId = userStore.loginUser.numberId;
  // console.log(`트레이너 ID: ${trainerId}`);
  await traineeStore.fetchTraineeList(trainerId); 
  trainees.value = traineeStore.trainees;
  // console.log("트레이니 리스트 로드 완료:", trainees.value);
  await loadProfileImages();
});

</script>

<style scoped>
/* 페이지 컨테이너 */
.page-container {
  width: 100%;
  margin: 0 auto;
  padding: 1px;
  text-align: center; /* 텍스트와 버튼을 가운데 정렬 */
}

/* 페이지 제목 */
.page-title {
  font-size: 1.3rem; /* 크기를 조금 작게 설정 */
  padding: auto;
  padding-top: 10px;
  font-weight: bold;
  margin-bottom: 15px;
  color: var(--text-color);
}

/* 버튼 컨테이너 */
.button-container {
  display: flex;
  justify-content: center; /* 버튼을 가운데 정렬 */
  gap: 10px;
  margin-bottom: 20px;
}


.blue-btn {
  padding: 8px 16px;
  font-size: 0.9rem;
  font-weight: bold;
  border: none;
  border-radius: 20px;
  background: linear-gradient(90deg, #007bff, #0056b3); /* 파란색 그라데이션 */
  color: #fff;
  cursor: pointer;
  transition: all 0.3s ease;
}

.blue-btn:hover {
  background: #fff;
  color: #007bff; /* Hover 시 텍스트 파란색 */
  border: 1px solid #007bff;
}


/* 삭제 버튼 추가 스타일 */
.delete-btn {
  background: linear-gradient(90deg, #ff4d4f, #ff7875);
}

.delete-btn:hover {
  background: #fff;
  color: #ff4d4f;
  border: 1px solid #ff4d4f;
}

/* 카드 스타일 */
.card {
  padding: 20px;
  width: 100%;
  border-radius: 10px;
  background-color: #f9f9f9;
  border: none;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

/* 리스트 스타일 */
ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

/* 리스트 아이템 */
.trainee-item {
  display: flex;
  align-items: center;
  justify-content: space-between; /* 양쪽 정렬 */
  margin-bottom: 15px;
  padding: 10px;
  border-radius: 10px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease;
}

.trainee-item:hover {
  background-color: #f1f1f1;
}

/* 프로필 이미지 */
.profile-img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 15px;
}

/* 회원 정보 */
.trainee-info {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: left; /* 왼쪽 정렬 */
}

.trainee-name {
  font-weight: bold;
  font-size: 1.1rem;
  color: var(--text-color);
}

.trainee-age {
  font-size: 0.9rem;
  color: #777;
}

</style>
