<template>
  <div class="page-container">
    <h2>회원가입</h2>
    <form @submit.prevent="register" class="form-container">
      <!-- 아이디 입력 -->
      <div class="form-group">
        <label for="userId" class="form-label">아이디</label>
        <div class="input-container">
          <input type="text" id="userId" v-model="userId" class="form-input" required />
          <button @click="userIdAvailable" class="small-btn" type="button">중복 확인</button>
        </div>
      </div>

      <!-- 비밀번호 입력 -->
      <div class="form-group">
        <label for="password" class="form-label">비밀번호</label>
        <input type="password" id="password" v-model="password" class="form-input" required />
      </div>
      
      <!-- 비밀번호 확인 -->
      <div class="form-group">
        <label for="confirmPassword" class="form-label">비밀번호 확인</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" class="form-input" required />
      </div>
      
      <!-- 이름 입력 -->
      <div class="form-group">
        <label for="name" class="form-label">이름</label>
        <input type="text" id="name" v-model="name" class="form-input" required />
      </div>
      
      <!-- 전화번호 입력 -->
      <div class="form-group">
        <label for="phone" class="form-label">휴대전화</label>
        <input type="tel" id="phone" v-model="phone" placeholder="전화번호 입력" class="form-input" required />
      </div>
      
      <!-- 이메일 입력 -->
      <div class="form-group">
        <label for="email" class="form-label">이메일</label>
        <input type="email" id="email" v-model="email" class="form-input" required />
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
        <div class="input-container">
          <input type="number" v-model="birthYear" placeholder="년(4자)" min="1900" max="2020" required class="form-input small-input" />
          <input type="number" v-model="birthMonth" placeholder="월" min="1" max="12" required class="form-input small-input" />
          <input type="number" v-model="birthDay" placeholder="일" min="1" max="31" required class="form-input small-input" />
        </div>
      </div>
      
      <!-- 회원가입 버튼 -->
      <button type="submit" class="register-btn">가입하기</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/user';
import { useMemberStore } from '@/stores/member'; 
import { useRouter } from 'vue-router';

const router = useRouter();
const userStore = useUserStore();
const memberStore = useMemberStore();

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

const idCheck = ref(false);

const userIdAvailable = async () => {
  const isOkay = await memberStore.userIdCheck(userId.value);
  console.log(isOkay)
  if (isOkay) {
    idCheck.value = true;
    alert('사용가능한 아이디입니다');
  } else {
    alert('중복된 아이디입니다.');
    idCheck.value = false;
    userId.value = null;
  }
};

const register = () => {
  if (!idCheck.value){
    console.log('아이디체크', idCheck.value)
    alert('아이디 중복 확인을 해주세요.')
    return;
  }
  if (password.value !== confirmPassword.value) {
    alert('비밀번호가 일치하지 않습니다.');
    return;
  }
  registUser();

  if(userStore.userType === 'trainer'){
    console.log('트레이너 로그인실행!')
    router.push({name : 'trainerLogin'});
  }
  if(userStore.userType === 'trainee'){
    console.log('트레이니 로그인실행!')
    router.push({name : 'traineeLogin'});
  }
};

const registUser = async () => {
  const user = {
    userId: userId.value,
    userPassword: password.value,
    userName: name.value,
    phoneNumber: phone.value,
    email: email.value,
    birthDate: `${birthYear.value}-${birthMonth.value}-${birthDay.value}`,
    gender: gender.value,
  };

  const userType = userStore.userType;

  try {
    await memberStore.userRegist(userType, user);
    alert('회원가입 성공');
  } catch (error) {
    console.error('회원가입 실패:', error);
  }
};

</script>

<style scoped>
/* 페이지 컨테이너 */
.page-container {
  max-width: 600px; /* 페이지 최대 폭 제한 */
  margin: 0 auto;
  padding: 20px;
  padding-bottom: 20vh;
  position: relative;
}

/* 폼 컨테이너 */
.form-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 폼 그룹 */
.form-group {
  display: flex;
  align-items: flex-start; /* 입력창 정렬 */
  gap: 20px;
}

/* 라벨 스타일 */
.form-label {
  width: 120px; /* 라벨 너비 조정 */
  font-size: 1rem;
  color: #333;
  text-align: left;
  line-height: 2.5rem;
}

/* 공통 입력 필드 스타일 */
.form-input,
.form-select {
  width: 100%; /* 입력창 기본 크기 */
  max-width: 200px; /* 입력창 최대 크기 제한 */
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
.input-container {
  display: flex;
  gap: 10px;
}

.small-input {
  max-width: 100px; /* 생년월일 입력 칸 크기 제한 */
  padding: 10px;
}

/* 성별 칸 스타일 */
.form-select {
  max-width: 200px; /* 선택 필드 크기 제한 */
}

/* 버튼 스타일 */
.small-btn {
  padding: 0px 20px;
  border-radius: 8px;
  font-size: 0.8rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

/* .register-btn:hover,
.small-btn:hover {
  background-color: #0056b3;
} */
</style>
