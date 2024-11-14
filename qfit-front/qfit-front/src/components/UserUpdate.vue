<template>
  <div>
    <h4>회원정보 수정 </h4>
    <form @submit.prevent="updateUser">
      <!-- 아이디 (수정 불가) -->
      <div>
        <label for="userId">아이디 (수정 불가)</label>
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
      
      <!-- 회원정보 수정 버튼 -->
      <button type="submit">회원정보 수정</button>
    </form>

    <button @click="resignCheck">회원 탈퇴</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { useMemberStore } from '@/stores/member';
import { useUserStore } from '@/stores/user';

const route = useRoute();
const memberStore = useMemberStore();
const userStore = useUserStore();

// 회원 정보 수정 관련 로직
// 기존 유저 정보를 담을 변수
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

// 초기 데이터 로드
const loadUserData = async () => {
  try {
    const userType = route.path.split('/')[1];
    const userData = await memberStore.getUserDetails(userType);
    // 받아온 유저 정보로 각 입력 필드를 채움
    userId.value = userData.userId;
    name.value = userData.userName;
    phone.value = userData.phoneNumber;
    email.value = userData.email;
    gender.value = userData.gender;
    const [year, month, day] = userData.birthDate.split('-');
    birthYear.value = year;
    birthMonth.value = month;
    birthDay.value = day;
  } catch (error) {
    console.error('회원정보 로드 실패:', error);
  }
};

// 컴포넌트 마운트 시 유저 정보 로드
loadUserData();

// 컴포넌트 마운트 시 유저 정보 로드
loadUserData();

// 회원정보 수정 함수
const updateUser = async () => {
  // 비밀번호 확인 로직
  if (password.value && password.value !== confirmPassword.value) {
    alert('비밀번호가 일치하지 않습니다.');
    return;
  }

  // 수정할 유저 데이터 객체 생성
  const updatedUser = {
    userId: userId.value,
    userPassword: password.value,
    userName: name.value,
    phoneNumber: phone.value,
    email: email.value,
    birthDate: `${birthYear.value}-${birthMonth.value}-${birthDay.value}`,
    gender: gender.value,
  };
  
  // 서버에 업데이트 요청
  const userType = route.path.split('/')[1];
    try {
      await memberStore.updateUser(userType, updatedUser);
      alert('회원정보가 수정되었습니다.');
    } catch (error) {
      console.error('회원정보 수정 실패:', error);
      alert('회원정보 수정에 실패했습니다.');
    }
};


// 이 아래는 회원 탈퇴 관련 로직
const resignCheck = async() => {
  const confirmed = confirm("정말로 탈퇴하시겠습니까?");
  if (confirmed) {
    console.log("삭제 처리");
    resignUser();
  } else {
    console.log("삭제 취소");
  }
};

const userType = userStore.loginUser.userType;
const numberId = userStore.loginUser.numberId;

console.log('조회', userType, numberId)
const resignUser = async() => {
  userStore.logout();
  await memberStore.userResign(userType, numberId);
}
</script>

<style scoped>

</style>