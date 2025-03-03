<template>
  <div>
    <!-- 내 프로필 섹션 -->
    <div class="section-container">
      <h4>내 프로필</h4>
      <!-- 프로필 이미지 -->
      <div class="profile-section">
      <img
        :src="profileImageUrl"
        alt="Profile"
        class="profile-img">
      <!-- 이미지 수정 -->
      <div class="image-buttons">
      <button class="small-btn-mini" @click="openFileInput">이미지 수정</button>
      <!-- 파일 입력 숨기기 -->
      <input
        type="file"
        class="hidden-input"
        ref="fileInput"
        @change="fileUpload"
      />
      <!-- 이미지 삭제 -->
      <button class="small-btn-mini" @click="deleteImage">이미지 삭제</button>
    </div>
    <p class="section-content">{{ userName }} 회원님, 안녕하세요.</p>
    <!-- 회원정보 수정 버튼 -->
    <button class="small-btn" @click="openPasswordModal">회원정보수정</button>
   </div>
    </div>

    <!-- 나의 트레이너 -->
    <div class="section-container">
      <h4>나의 체육관</h4>
      <div v-if="trainerStore.trainer.gym">
        <p>체육관 : {{ trainerStore.trainer.gym }}</p>
        <p>트레이너 : {{ traineeStore.trainer.name }}</p>
      </div>
      <p v-else>
        아직 트레이너가 등록되지 않았습니다.
      </p>
    </div>
    <!-- 비밀번호 확인 모달 -->
    <div v-if="isModalOpen" class="modal-overlay">
      <div class="modal-content">
        <h2>비밀번호 확인</h2>
        <p>회원정보를 수정하려면 비밀번호를 입력하세요.</p>
        
        <!-- 비밀번호 입력 필드 -->
        <input 
          type="password" 
          v-model="password" 
          placeholder="비밀번호를 입력하세요" 
          class="input-password"
        />

        <!-- 확인 및 취소 버튼 -->
        <div class="button-container">
          <button class="confirm-button" @click="changeInfo">확인</button>
          <button class="cancel-button" @click="closeModal">취소</button>
        </div>
        
        <!-- 오류 메시지 표시 -->
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      </div>
    </div>

    <!-- 로그아웃 버튼 -->
    <button class="logout-btn" @click="logout">로그아웃</button>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useTrainerStore } from '@/stores/trainer';
import { useTraineeStore } from '@/stores/trainee';
import { useImageStore } from "@/stores/imageStore";
import defaultProfileImage from "@/assets/default_profile.png";

const router = useRouter();
const userStore = useUserStore();
const trainerStore = useTrainerStore();
const traineeStore = useTraineeStore();
const imageStore = useImageStore();

const isModalOpen = ref(false);  // 모달 표시 여부
const password = ref('');        // 입력된 비밀번호
const errorMessage = ref('');    // 오류 메시지

const userName = computed(() => userStore.loginUser.name); // 사용자 이름

// 모달 열기 함수
const openPasswordModal = () => {
  isModalOpen.value = true;
};

// 모달 닫기 함수
const closeModal = () => {
  isModalOpen.value = false;
  password.value = '';
  errorMessage.value = '';
};

// 비밀번호 확인 및 개인정보 수정 페이지 이동
const changeInfo = async () => {
  if (!password.value) {
    errorMessage.value = '비밀번호를 입력해주세요.';
    return;
  }

  try {
    const userId = userStore.loginUser.userId;
    const userPassword = password.value;
    const isPasswordCorrect = await userStore.passwordCheck({ userId: userId, userPassword: userPassword });
    if (isPasswordCorrect) {
      router.push({ name: 'traineeUpdate' });
      closeModal();
    } else {
      errorMessage.value = '비밀번호가 틀렸습니다.';
    }
  } catch (err) {
    console.error('서버 통신 오류:', err);
    errorMessage.value = '서버 오류가 발생했습니다.';
  }
};

// 로그아웃
const logout = async () => {
  try {
    await userStore.logout();
    alert('로그아웃 완료');
  } catch {
    console.error('로그아웃 실패');
  }
};

// 트레이너 정보 조회
async function fetchTrainerDetail() {
  const trainerId = await traineeStore.getTrainerId(userStore.loginUser.numberId);
  await trainerStore.getGym(trainerId); // 트레이너 체육관 정보 가져오기
  await traineeStore.getTrainerName(userStore.loginUser.numberId); // 트레이너 이름 가져오기
}

// 프로필 이미지 URL
const profileImageUrl = ref('');

// 프로필 이미지 로드
const loadProfileImages = async () => {
  try {
    const numberId = userStore.loginUser.numberId;
    const imgUrl = await userStore.getUserImageUrl(numberId);

    if (imgUrl) {
      const blob = await imageStore.loadFile(imgUrl); // 이미지 로드
      profileImageUrl.value = URL.createObjectURL(blob); // Blob URL 생성
    } else {
      throw new Error("사용자 이미지 URL이 없습니다.");
    }
  } catch (err) {
    console.error("이미지 로드 실패:", err);
    profileImageUrl.value = defaultProfileImage; // 실패 시 기본 이미지 설정
  }
};

// 프로필 이미지 수정
const fileUpload = async(event) => {
  const file = event.target.files[0];
  if(!file){
    console.error('파일선택안함');
    return;
  }
  try{
    const url = await imageStore.uploadFile(file);
    const user = {id: userStore.loginUser.numberId, userImg: url}
    userStore.updateUserImageUrl(user)
    const blob = await imageStore.loadFile(url);
    profileImageUrl.value = URL.createObjectURL(blob);
  }catch(err){
    console.error('이미지업데이트실패', err);
  }
}

// 파일 input 상태
const fileInput = ref(null);
const openFileInput = () => {
  if(fileInput.value){
    fileInput.value.click();
  }
}

// 프로필 이미지 삭제
const deleteImage = async() => {
  if(profileImageUrl.value != defaultProfileImage){
    try{
      await imageStore.deleteFile(profileImageUrl.value);
      const user = {id: userStore.loginUser.numberId, userImg: null};
      await userStore.updateUserImageUrl(user);
      profileImageUrl.value = defaultProfileImage;
    }catch(err){
      console.error('이미지 삭제 실패', err);
    }
  }
}

onMounted(async() => {
  fetchTrainerDetail();
  await loadProfileImages(); // 프로필 이미지 로드
});
</script>

<style scoped>
/* 섹션 컨테이너 */
.section-container {
  width: 350px;
  margin-bottom: 20px; /* 섹션 간 간격 */
  padding: 20px; /* 내부 여백 */
  border: 1px solid #ddd; /* 섹션 경계선 */
  border-radius: 10px; /* 섹션 모서리 둥글게 */
  background-color: #f9f9f9; /* 섹션 배경 */
}

/* 프로필 섹션 (이미지 및 버튼 포함) */
.profile-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 10px;
}

/* 프로필 이미지 */
.profile-img {
  width: 200px; /* 첫 번째 코드와 동일한 크기 */
  height: 200px; /* 첫 번째 코드와 동일한 크기 */
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 10px; /* 첫 번째 코드와 동일 */
  border: 2px solid #e0e0e0; /* 첫 번째 코드와 동일 */
}


/* 이미지 버튼 컨테이너 */
.image-buttons {
  display: flex;
  gap: 10px;
  justify-content: center;
  margin-bottom: 15px;
}

/* small-btn-mini (작은 버튼 스타일) */
.small-btn-mini {
  padding: 6px 12px;
  font-size: 0.8rem;
  border-radius: 15px;
  background: #ffffff; /* 기본 색상: 흰색 */
  color: var(--theme-color); /* 텍스트 색상 */
  border: none; /* 테두리 */
  cursor: pointer;
  transition: all 0.3s ease; /* 애니메이션 */
}

.small-btn-mini:hover {
  background: #e0e0e0; /* Hover 시 회색으로 변경 */
  color: var(--theme-color); /* 텍스트는 동일한 테마 색상 유지 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 약간의 그림자 효과 */
}

.small-btn-mini:active {
  background: #e0e0e0;
}

/* 모달 오버레이 (배경을 반투명하게 덮음) */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.3); /* 반투명 검은색 */
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

/* 모달 컨텐츠 박스 */
.modal-content {
  background: #f4f8ff; /* 부드러운 파란색 배경 */
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  max-width: 400px;
  width: 90%;
}

/* 모달 제목 */
.modal-content h2 {
  margin-bottom: 15px;
  font-size: 1.5rem;
  font-weight: bold;
  color: var(--theme-color); /* 테마 색상 */
}

/* 모달 설명 텍스트 */
.modal-content p {
  margin-bottom: 20px;
  color: #666;
  font-size: 1rem;
}

/* 입력 필드 스타일 */
.input-password {
  width: 100%;
  padding: 12px;
  margin-bottom: 20px;
  border: 1px solid #dcdcdc;
  border-radius: 8px;
  outline: none;
  font-size: 1rem;
}

/* 버튼 컨테이너 (버튼을 좌우로 배치) */
.button-container {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

/* 확인 버튼 */
.confirm-button {
  background: linear-gradient(90deg, var(--theme-color), #9d47f4); /* 그라데이션 배경 */
  color: #fff;
  padding: 10px 20px;
  border: none; /* 테두리 제거 */
  border-radius: 20px; /* 둥근 모서리 */
  flex: 1;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease; /* 부드러운 애니메이션 */
}

.confirm-button:hover {
  background: #fff; /* Hover 시 흰색 배경 */
  color: var(--theme-color); /* 글자 색상 테마 색상 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 약간의 그림자 효과 */
}

/* 취소 버튼 */
.cancel-button {
  background: #f5f5f5; /* 흰색 배경 */
  color: #333;
  border: none; /* 테두리 제거 */
  padding: 10px 20px;
  border-radius: 20px;
  flex: 1;
  text-align: center;
}

.cancel-button:hover {
  background: #e0e0e0; /* Hover 시 약간 어두운 배경 */
  color: #333;
}

/* 오류 메시지 */
.error {
  color: red;
  margin-top: 10px;
  font-size: 0.9rem;
  font-weight: bold;
}

/* 로그아웃 버튼 */
.logout-btn {
  background: linear-gradient(90deg, var(--theme-color), #9d47f4); /* 그라데이션 배경 */
  color: #fff; /* 텍스트 색상 */
  font-size: 1.2rem; /* 글자 크기 */
  font-weight: bold;
  padding: 15px 0; /* 상하 여백 */
  text-align: center; /* 텍스트 중앙 정렬 */
  border: none; /* 테두리 제거 */
  border-radius: 20px; /* 둥근 모서리 */
  width: 100%; /* 가로를 꽉 채움 */
  cursor: pointer; /* 클릭 가능한 커서 */
  transition: all 0.3s ease; /* 부드러운 애니메이션 */
  margin-top: 13vh; /* 맨 위 항목과 뷰포트 높이 35% 간격 */
}

.logout-btn:hover {
  background: #fff; /* Hover 시 흰색 배경 */
  color: var(--theme-color); /* 텍스트 테마 색상 */
  border: 1px solid var(--theme-color); /* 테두리 강조 */
}

/* 트레이니 프로필 이미지 */
.profile-img {
  width: 100px;
  height: 100px;
  border-radius: 50%; /* 원형 이미지 */
  object-fit: cover;
}

.hidden-input{
  display: none;
}
</style>
