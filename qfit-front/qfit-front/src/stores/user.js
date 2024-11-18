import { ref, computed } from 'vue';
import { useRoute } from 'vue-router'
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';

const REST_API_URL = `http://localhost:8080/user`;

export const useUserStore = defineStore('user', () => {

  const route = useRoute();
  const userType = computed(() => route.path.split('/')[1]);

  const loginUser = ref(null);

  // 트레이너 로그인
  const trainerLogin = async (id, password) => {
    try {
      const res = await axios.post(`${REST_API_URL}/login`, {
        userId: id,
        userPassword: password,
      });

      const accessToken = res.data;
      if (!accessToken) throw new Error('No access token received');
      
      sessionStorage.setItem('access-token', accessToken);
      setUserFromToken(accessToken); // 토큰에서 유저 정보 설정

      await router.push({ name: 'traineeList' });
      return true;
    } catch (err) {
      console.error('Login failed:', err);
      await router.push({ name: 'trainerLogin' });
      return false;
    }
  };

  // 회원 로그아웃
  const logout = () => {
    sessionStorage.removeItem('access-token');
    loginUser.value = null;
  };

  // 패스워드 체크 기능 (기존 코드 유지)
  const passwordCheck = async (check) => {
    try {
      const url = `${REST_API_URL}/password-correct`;
      const res = await axios.post(url, check);
      return res.data;
    } catch (err) {
      console.error('패스워드 체크 실패:', err);
      throw err;
    }
  };

  // 유저 정보 가져오기
  const getUserDetails = async (userId) => {
    try {
      console.log('getUserDetails 호출:', userId);
      const res = await axios.get(`${REST_API_URL}/info/${userId}`);
      return res.data;
    } catch (err) {
      console.error('유저 정보 가져오기 실패:', err);
      throw err;
    }
  };

  // 세션에서 토큰을 사용해 유저 정보 설정
  const setUserFromToken = (token) => {
    const tokenParts = token.split('.');
    if (tokenParts.length !== 3) throw new Error('Invalid token format');
    
    const payload = JSON.parse(atob(tokenParts[1]));
    loginUser.value = {
      numberId: payload.id,
      userId: payload.userId,
      name: payload.name,
      userType: payload.userType,
    };
  };

  // 세션에서 토큰을 이용해 유저 정보 로드
  const loadUserFromToken = async () => {
    const token = sessionStorage.getItem('access-token');
    if (token) setUserFromToken(token);
  };

  return {
    userType,
    loginUser,
    trainerLogin,
    logout,
    passwordCheck,
    getUserDetails,
    loadUserFromToken,
  };
});
