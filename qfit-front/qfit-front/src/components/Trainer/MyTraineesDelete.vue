<template>
  <div class="page-container">
    <!-- 제목 -->
    <h3 class="page-title">회원 삭제하기</h3>

    <!-- 회원 목록 -->
    <ul class="card">
      <li v-for="trainee in trainees" :key="trainee.id" class="trainee-item">
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
        <!-- 삭제 버튼 -->
        <button class="delete-btn small-btn" @click="openDeleteModal(trainee)">삭제하기</button>
      </li>
    </ul>

    <!-- 삭제 확인 모달 -->
    <div v-if="showDeleteModal" class="modal-overlay">
      <div class="modal-content">
        <h2>회원 삭제 확인</h2>
        <p>{{ selectedTrainee.userName }} 님을 삭제하시겠습니까?</p>
        <div class="modal-actions">
          <button class="close-btn" @click="confirmDelete">확인</button>
          <button class="close-btn cancel-btn" @click="closeDeleteModal">취소</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useTraineeStore } from "@/stores/trainee";
import { useNotificationStore } from "@/stores/notification";
import { useUserStore } from "@/stores/user";
import { useRouter } from "vue-router";
import { useImageStore } from "@/stores/imageStore";
import defaultProfileImage from "@/assets/default_profile.png";

const traineeStore = useTraineeStore();
const notificationStore = useNotificationStore();
const userStore = useUserStore();
const router = useRouter();
const trainees = traineeStore.trainees;
const imageStore = useImageStore();

// 모달 상태 및 선택된 회원
const showDeleteModal = ref(false);
const selectedTrainee = ref(null);

// 삭제 모달 열기
const openDeleteModal = (trainee) => {
  selectedTrainee.value = trainee;
  showDeleteModal.value = true;
};

// 삭제 확인
const confirmDelete = async () => {
  if (!selectedTrainee.value) return;

  try {
    await traineeStore.deleteTrainee(selectedTrainee.value.id);
    alert(`${selectedTrainee.value.userName} 님이 삭제되었습니다.`);
    makeNotification();
    router.push({name: 'MyTrainees'})
  } catch (error) {
    console.error("회원 삭제에 실패했습니다:", error);
    alert("삭제 중 오류가 발생했습니다.");
  } finally {
    closeDeleteModal();
  }
};

// 삭제 모달 닫기
const closeDeleteModal = () => {
  showDeleteModal.value = false;
  selectedTrainee.value = null;
};

// 알림 생성
const makeNotification = async() => {
  try{
    const notification = {userId: selectedTrainee.value.id, message: `${userStore.loginUser.name}님이 당신을 회원 목록에서 삭제하였습니다.`}
    await notificationStore.createNotification(notification)
  }catch(err){
    console.log('프론트 등록 중 오류 발생', err)
  }
}

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
  try {
    await loadProfileImages();
  } catch (err) {
    console.warn(err);
  }
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

/* 버튼 스타일 */
.delete-btn {
  padding: 8px 16px;
  font-size: 0.9rem;
  font-weight: bold;
  border: none;
  border-radius: 20px;
  background: linear-gradient(90deg, #ff4d4f, #ff7875);
  color: #fff;
  cursor: pointer;
  transition: all 0.3s ease;
}

.delete-btn:hover {
  background: #fff;
  color: #ff4d4f;
  border: 1px solid #ff4d4f;
}

/* 모달 오버레이 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

/* 모달 콘텐츠 */
.modal-content {
  background: #f9f9f9;
  padding: 30px;
  border-radius: 15px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  max-width: 400px;
  width: 90%;
}

.modal-content h2 {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 20px;
  color: var(--text-color);
}

.modal-content p {
  font-size: 1rem;
  margin-bottom: 20px;
  color: #555;
}

/* 모달 버튼 */
.close-btn {
  padding: 10px 20px;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 20px;
  background: linear-gradient(90deg, #ff4d4f, #ff7875);
  color: #fff;
  cursor: pointer;
  transition: all 0.3s ease;
  margin: 0 10px;
}

.close-btn:hover {
  background: #fff;
  color: #ff4d4f;
  border: 1px solid #ff4d4f;
}

.cancel-btn {
  background: #ddd;
  color: #555;
}

.cancel-btn:hover {
  background: #fff;
  color: #555;
  border: 1px solid #ddd;
}
</style>
