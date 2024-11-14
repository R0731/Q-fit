<template>
  <div class="login-container">
    <!-- 로그인 폼 -->
    <form class="login-form">
      <!-- 유저명 입력 필드 -->
      <div class="mb-3">
        <label for="userId" class="form-label">UserId</label>
        <input 
          type="text" 
          class="form-control" 
          id="userId" 
          placeholder="Enter your userId" 
          v-model.trim="id"
        />
      </div>

      <!-- 비밀번호 입력 필드 -->
      <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input 
          type="password" 
          class="form-control" 
          id="password" 
          placeholder="Enter your password"
          v-model.trim="password"
        />
      </div>

      <!-- 로그인 버튼 -->
      <button type="button" class="btn btn-primary w-100" @click='login'>Log in</button>

      <!-- 추가 항목 (비밀번호 찾기, 아이디 찾기, 회원가입) -->
      <div class="additional-links mt-4">
        <a href="#">비밀번호 찾기</a>
        <span class="divider">|</span>
        <a href="#">아이디 찾기</a>
        <span class="divider">|</span>
        <a href="#">회원가입</a>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'LoginForm',
};
</script>


<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();

const id = ref("")
const password = ref("")

const login = () =>{
    userStore.userLogin(id.value, password.value)
}
</script>

<style scoped>
/* 로그인 컨테이너 스타일 */
.login-container {
  max-width: 400px; /* 최대 너비 지정 */
  min-width: 320px; /* 최소 너비 지정 */
  margin: 50px auto; /* 화면 가운데 정렬 */
  padding: 30px; /* 내부 여백 */
  border: 1px solid #ddd; /* 테두리 색상 */
  border-radius: 10px; /* 둥근 테두리 */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  background-color: #fff; /* 배경 색상 */
}

/* 추가 항목 스타일 */
.additional-links {
  text-align: center; /* 가운데 정렬 */
  margin-top: 20px; /* 위쪽 여백 */
  font-size: 0.9rem; /* 글자 크기를 작게 조정 */
  color: #555; /* 텍스트 기본 색상 */
}

/* 링크 텍스트 스타일 */
.additional-links a {
  color: #555; /* 링크 기본 색상 */
  text-decoration: none; /* 밑줄 제거 */
  margin: 0 8px; /* 좌우 여백 조정 */
}

/* 링크 호버 스타일 */
.additional-links a:hover {
  color: #8504e8; /* 호버 시 색상 변경 */
  text-decoration: underline; /* 호버 시 밑줄 표시 */
}

/* 로그인 버튼 색상 */
button.btn-primary {
  background-color: #8504e8; /* 버튼 배경 색상 변경 */
  border-color: #8504e8; /* 버튼 테두리 색상 변경 */
}

button.btn-primary:hover {
  background-color: #6b03c1; /* 버튼 호버 시 색상 변경 */
  border-color: #6b03c1;
}

/* 구분자 스타일 */
.divider {
  color: #aaa; /* 구분자 색상 */
  margin: 0 5px; /* 구분자 좌우 여백 */
  font-size: 0.9rem; /* 구분자 글자 크기 */
}

/* 반응형 디자인: 화면이 좁아질 때 레이아웃 조정 */
@media (max-width: 375px) {
  .login-container {
    min-width: 100%; /* 작은 화면에서는 전체 너비로 확장 */
    padding: 20px; /* 여백 줄이기 */
  }
}
</style>
