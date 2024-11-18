<template>
  <div>
    <h4>회원정보 수정 </h4>
    <form @submit.prevent="updateUser">
      <!-- 아이디 (수정 불가) -->
      <div>
        <label for="userId">아이디</label>
        <input type="text" id="userId" v-model="userId" disabled />
      </div>

      <!-- 비밀번호 입력 -->
      <div>
        <label for="password">비밀번호</label>
        <input type="password" id="password" v-model="password" placeholder="비밀번호 변경 시 입력" />
      </div>
      
      <!-- 비밀번호 확인 -->
      <div>
        <label for="confirmPassword">비밀번호 확인</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" placeholder="변경된 비밀번호 확인" />
      </div>
      
      <!-- 이름 입력 -->
      <div>
        <label for="name">이름</label>
        <input type="text" id="name" v-model="name" required />
      </div>
      
      <!-- 전화번호 입력 -->
      <div>
        <label for="phone">휴대전화</label>
        <input type="tel" id="phone" v-model="phone" required />
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
        <input type="number" v-model="birthYear" placeholder="년(4자)" min="1900" max="2100" required />
        <input type="number" v-model="birthMonth" placeholder="월" min="1" max="12" required />
        <input type="number" v-model="birthDay" placeholder="일" min="1" max="31" required />
      </div>

      <!-- 회원정보 수정 버튼 -->
      <button type="submit">회원정보 수정</button>
    </form>

    <!-- 회원 탈퇴 버튼 -->
    <button @click="resignCheck">회원 탈퇴</button>
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

// 유저 정보 로드 함수
const loadUserData = async () => {
  try {
    const loginUser = userStore.loginUser;
    // 로그인된 유저가 있는지 확인
    if (!loginUser || !loginUser.value) {
      console.error('로그인된 유저 정보가 없습니다.');
      return;
    }

    // 유저 ID 가져오기
    const userIdValue = loginUser.value.userId;
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

// 컴포넌트가 마운트될 때 유저 정보 로드
onMounted(async () => {
  await loadUserData();
});

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
    await memberStore.updateUser(route.path.split('/')[1], updatedUser);
    alert('회원정보가 수정되었습니다.');
    router.push('/profile');
  } catch (error) {
    console.error('회원정보 수정 실패:', error);
    alert('회원정보 수정에 실패했습니다.');
  }
};

// 회원 탈퇴 함수
const resignCheck = async () => {
  const confirmed = confirm('정말로 탈퇴하시겠습니까?');
  if (confirmed) {
    await resignUser();
  }
};

const resignUser = async () => {
  try {

    if (!userStore.loginUser) {
      console.error('로그인된 유저 정보가 없습니다.');
      return;
    }

    const { userType, numberId } = userStore.loginUser;

    console.log('정보조회:', userType, numberId)

    await memberStore.userResign(userType, numberId);
    userStore.logout();
    alert('회원 탈퇴가 완료되었습니다.');
    router.push({name: 'trainerLogin'});
  } catch (error) {
    console.error('회원 탈퇴 실패:', error);
    alert('회원 탈퇴에 실패했습니다.');
  }
};
</script>

<style scoped>

</style>
