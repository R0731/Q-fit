<template>
  <div>
    <h4>내 프로필</h4>
    <!-- 회원정보 수정 버튼 -->
    <button @click="openPasswordModal">회원정보수정</button>

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

        <!-- 확인 및 취소 버튼을 좌우로 배치 -->
        <div class="button-container">
          <button class="confirm-button" @click="changeInfo">확인</button>
          <button class="cancel-button" @click="closeModal">취소</button>
        </div>
        
        <!-- 오류 메시지 표시 -->
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      </div>
    </div>

    <h4>체육관 정보 수정</h4>
    <!-- 체육관 수정 버튼 복구 -->
    <button @click="navigateTo('trainerGym')">체육관 정보</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';

const router = useRouter();
const userStore = useUserStore();

const isModalOpen = ref(false);  // 모달 표시 여부
const password = ref('');        // 입력된 비밀번호
const errorMessage = ref('');    // 오류 메시지

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
      router.push({ name: 'trainerUpdate' });
      closeModal();
    } else {
      errorMessage.value = '비밀번호가 틀렸습니다.';
    }
  } catch (err) {
    console.error('서버 통신 오류:', err);
    errorMessage.value = '서버 오류가 발생했습니다.';
  }
};

// 페이지 이동 함수
const navigateTo = (routeName) => {
  if (routeName) {
    router.push({ name: routeName });
  }
};
</script>

<style scoped>
/* 모달 오버레이 (배경을 반투명하게 덮음) */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

/* 모달 컨텐츠 박스 */
.modal-content {
  background: #f4f8ff;
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  max-width: 400px;
  width: 90%;
}

/* 제목 스타일 */
.modal-content h2 {
  margin-bottom: 10px;
  font-size: 24px;
  color: #333;
}

/* 설명 텍스트 스타일 */
.modal-content p {
  margin-bottom: 20px;
  color: #666;
}

/* 입력 필드 스타일 */
.input-password {
  width: 100%;
  padding: 12px;
  margin-bottom: 20px;
  border: 1px solid #dcdcdc;
  border-radius: 8px;
  outline: none;
}

/* 버튼 컨테이너 (버튼을 좌우로 배치) */
.button-container {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

/* 확인 버튼 스타일 */
.confirm-button {
  background-color: var(--theme-color);
  color: #fff;
  padding: 12px 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  flex: 1;
}

.confirm-button:hover {
  background-color: var(--theme-color);
}

/* 취소 버튼 스타일 */
.cancel-button {
  background-color: #f5f5f5;
  color: #333;
  padding: 12px 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  flex: 1;
}

.cancel-button:hover {
  background-color: #e0e0e0;
}

/* 오류 메시지 스타일 */
.error {
  color: red;
  margin-top: 10px;
  font-size: 14px;
}
</style>
