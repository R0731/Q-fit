<template>
  <div>
    <h2>회원가입</h2>
    <form @submit.prevent="register">
      <!-- 아이디 입력 -->
      <div>
        <label for="userId">아이디</label>
        <input type="text" id="userId" v-model="userId" required />
      </div>

      <button @click="userIdAvailable">중복 확인</button>

      <!-- 비밀번호 입력 -->
      <div>
        <label for="password">비밀번호</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      
      <!-- 비밀번호 확인 -->
      <div>
        <label for="confirmPassword">비밀번호 확인</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" required />
      </div>
      
      <!-- 이름 입력 -->
      <div>
        <label for="name">이름</label>
        <input type="text" id="name" v-model="name" required />
      </div>
      
      <!-- 전화번호 입력 -->
      <div>
        <label for="phone">휴대전화</label>
        <input type="tel" id="phone" v-model="phone" placeholder="전화번호 입력" required />
      </div>
      
      <!-- 이메일 입력 -->
      <div>
        <label for="email">이메일</label>
        <input type="email" id="email" v-model="email" required />
      </div>
      
      <!-- 성별 선택 -->
      <div>
        <label for="gender">성별</label>
        <select v-model="gender" id="gender">
          <option value="">성별 선택</option>
          <option value="Male">남성</option>
          <option value="Female">여성</option>
        </select>
      </div>
      
      <!-- 생년월일 입력 -->
      <div>
        <label>생년월일</label>
        <input type="number" v-model="birthYear" placeholder="년(4자)" required />
        <input type="number" v-model="birthMonth" placeholder="월" min="1" max="12" required />
        <input type="number" v-model="birthDay" placeholder="일" min="1" max="31" required />
      </div>
      
      <!-- 회원가입 버튼 -->
      <button type="submit">가입하기</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { useUserStore } from '@/stores/user'
import { useMemberStore } from '@/stores/member'; 

const route = useRoute();

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

const register = () => {
  if (password.value !== confirmPassword.value) {
    alert('비밀번호가 일치하지 않습니다.');
    return;
  }
  registUser();
}

const registUser = async() => {
  const user = {
    userId: userId.value,
    userPassword: password.value,
    userName: name.value,
    phoneNumber: phone.value,
    email: email.value,
    birthDate: `${birthYear.value}-${birthMonth.value}-${birthDay.value}`,
    gender: gender.value,
  };

  // console.log(route.path)

  // const userType = route.path.split('/')[1];

  const userType = userStore.userType;

  console.log('userType확인', userType);
  // console.log('회원가입 정보:', user);

  try {
    await memberStore.userRegist(userType, user);
    alert('회원가입 성공');
  } catch (error) {
    console.error('회원가입 실패:', error);
  }
};

const userIdAvailable = async() => {
  const isDuplicate = userStore.userIdCheck(userId.value);
  if(isDuplicate){
    alert('중복된 아이디입니다.')
  }else{
    alert('사용가능한 아이디입니다')
  }
}
</script>

<style scoped>

</style>