<template>
  <div class="page-container">
    <form @submit.prevent="updateUser" class="form-container">
      <!-- 아이디 (수정 불가) -->
      <div class="form-group">
        <label for="userId" class="form-label">아이디</label>
        <input type="text" id="userId" v-model="userId" disabled class="form-input" />
      </div>

      <!-- 비밀번호 입력 -->
      <div class="form-group">
        <label for="password" class="form-label">비밀번호</label>
        <input type="password" id="password" v-model="password" placeholder="비밀번호 변경 시 입력" class="form-input" />
      </div>

      <!-- 비밀번호 확인 -->
      <div class="form-group">
        <label for="confirmPassword" class="form-label">비밀번호 확인</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" placeholder="변경된 비밀번호 확인" class="form-input" />
      </div>

      <!-- 이름 입력 -->
      <div class="form-group">
        <label for="name" class="form-label">이름</label>
        <input type="text" id="name" v-model="name" required class="form-input" />
      </div>

      <!-- 전화번호 입력 -->
      <div class="form-group">
        <label for="phone" class="form-label">휴대전화</label>
        <input type="tel" id="phone" v-model="phone" required class="form-input" />
      </div>

      <!-- 이메일 입력 -->
      <div class="form-group">
        <label for="email" class="form-label">이메일</label>
        <input type="email" id="email" v-model="email" required class="form-input" />
      </div>

      <!-- 성별 선택 -->
      <div class="form-group">
        <label for="gender" class="form-label">성별</label>
        <select v-model="gender" id="gender" class="form-select">
          <option value="">성별 선택</option>
          <option value="Male">남성</option>
          <option value="Female">여성</option>
        </select>
      </div>

      <!-- 생년월일 입력 -->
      <div class="form-group">
        <label class="form-label">생년월일</label>
        <div class="birthdate-inputs">
          <input type="number" v-model="birthYear" placeholder="년(4자)" min="1900" max="2100" required class="form-input small-input" />
          <input type="number" v-model="birthMonth" placeholder="월" min="1" max="12" required class="form-input small-input" />
          <input type="number" v-model="birthDay" placeholder="일" min="1" max="31" required class="form-input small-input" />
        </div>
      </div>

      <!-- 회원정보 수정 버튼 -->
      <button type="submit" class="register-btn">회원정보 수정</button>
    </form>

  <!-- 회원 탈퇴 버튼 -->
  <button @click="openResignModal" class="red-btn">회원 탈퇴</button>

<!-- 탈퇴 확인 모달 -->
<div v-if="isResignModalOpen" class="modal-overlay">
  <div class="modal-content">
    <h2>회원 탈퇴</h2>
    <p>정말로 탈퇴하시겠습니까? 이 작업은 되돌릴 수 없습니다.</p>
    
    <!-- 확인 및 취소 버튼 -->
    <div class="button-container">
      <button class="confirm-button" @click="confirmResign">탈퇴</button>
      <button class="cancel-button" @click="closeResignModal">취소</button>
    </div>
  </div>
  </div>
  </div>

</template>
<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useMemberStore } from '@/stores/member';
import { useUserStore } from '@/stores/user';

const router = useRouter();
const route = useRoute();
const memberStore = useMemberStore();
const userStore = useUserStore();

// 유저 정보를 저장할 변수들
const userId = ref('');
const password = ref('');
const confirmPassword = ref('');
const name = ref('');
const birthYear = ref('');
const birthMonth = ref('');
const birthDay = ref('');
const gender = ref('');
const email = ref('');
const phone = ref('');

// 컴포넌트가 마운트될 때 유저 정보 로드
onMounted(async () => {
  await loadUserData();
});

// 유저 정보 로드 함수
const loadUserData = async () => {
  try {
    const loginUser = userStore.loginUser;
    // 로그인된 유저가 있는지 확인
    if (!loginUser) {
      console.error('로드하는데 로그인된 유저가 없습니다.');
      return;
    }

    // 유저 ID 가져오기
    const userIdValue = loginUser.userId;
    console.log('폼에서 유저 ID:', userIdValue);

    const userData = await userStore.getUserDetails(userIdValue);

    if (userData) {
      // 받아온 유저 정보로 폼 필드 채우기
      userId.value = userData.userId;
      name.value = userData.userName;
      phone.value = userData.phoneNumber;
      email.value = userData.email;
      gender.value = userData.gender;
      
      if (userData.birthDate) {
        const [year, month, day] = userData.birthDate.split('-');
        birthYear.value = year;
        birthMonth.value = month;
        birthDay.value = day;
      }
    }
  } catch (error) {
    console.error('회원정보 로드 실패:', error);
  }
};


// 회원정보 수정 함수
const updateUser = async () => {
  if (password.value && password.value !== confirmPassword.value) {
    alert('비밀번호가 일치하지 않습니다.');
    return;
  }

  const updatedUser = {
    userId: userId.value,
    userPassword: password.value,
    userName: name.value,
    phoneNumber: phone.value,
    email: email.value,
    birthDate: `${birthYear.value}-${birthMonth.value.padStart(2, '0')}-${birthDay.value.padStart(2, '0')}`,
    gender: gender.value,
  };

  try {
    const result = await userStore.updateUser(userStore.loginUser.numberId, updatedUser);
    if (result) {
      alert('회원정보가 성공적으로 수정되었습니다.');
    }
  } catch (error) {
    console.error('회원정보 수정 실패:', error);
    alert('회원정보 수정에 실패하였습니다.');
  }
};

// 모달 상태 관리
const isResignModalOpen = ref(false);

// 모달 열기
const openResignModal = () => {
  isResignModalOpen.value = true;
};

// 모달 닫기
const closeResignModal = () => {
  isResignModalOpen.value = false;
};

// 회원 탈퇴 처리
const confirmResign = async () => {
  try {
    const { userType, numberId } = userStore.loginUser;

    await memberStore.userResign(userType, numberId);
    userStore.logout();
    alert("회원 탈퇴가 완료되었습니다.");
    router.push({ name: "trainerLogin" });
  } catch (error) {
    console.error("회원 탈퇴 실패:", error);
    alert("회원 탈퇴에 실패했습니다.");
  } finally {
    closeResignModal();
  }
};
</script>
<style scoped>
/* 페이지 컨테이너 */
.page-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  padding-bottom: 20vh;
  position: relative;
}

/* 폼 컨테이너 */
.form-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

/* 폼 그룹 */
.form-group {
  display: flex;
  align-items: center;
  justify-content: left;
  gap: 10px;
}

/* 라벨 스타일 */
.form-label {
  width: 150px;
  font-size: 1rem;
  color: #333;
}

/* 공통 입력 필드 스타일 */
.form-input,
.form-select {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  outline: none;
  transition: border-color 0.3s ease;
}

/* 포커스 시 효과 */
.form-input:focus,
.form-select:focus {
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

/* 생년월일 칸 스타일 */
.small-input {
  width: 33%; /* 칸을 더 넓게 설정 */
  padding: 10px;
}

/* 성별 칸 스타일 */
.form-select {
  width: 150px; /* 고정 너비를 줄임 */
}

/* 모달 오버레이 */
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

/* 모달 내용 */
.modal-content {
  background: #fff;
  padding: 30px;
  border-radius: 10px;
  max-width: 400px;
  width: 90%;
  text-align: center;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

/* 모달 제목 */
.modal-content h2 {
  font-size: 1.5rem;
  margin-bottom: 15px;
  color: #ff4d4f;
}

/* 모달 설명 */
.modal-content p {
  font-size: 1rem;
  color: #555;
  margin-bottom: 20px;
}

/* 버튼 컨테이너 */
.button-container {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

/* 확인 버튼 */
.confirm-button {
  background: #ff4d4f;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  flex: 1;
  transition: all 0.3s ease;
}

.confirm-button:hover {
  background: #fff;
  color: #ff4d4f;
  border: 1px solid #ff4d4f;
}

/* 취소 버튼 */
.cancel-button {
  background: #f5f5f5;
  color: #333;
  padding: 10px 20px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  flex: 1;
}

.cancel-button:hover {
  background: #e0e0e0;
}
</style>