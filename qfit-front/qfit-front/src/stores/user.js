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

    // 트레이니 로그인
    const traineeLogin = async (id, password) => {
      try {
        const res = await axios.post(`${REST_API_URL}/login`, {
          userId: id,
          userPassword: password,
        });
  
        const accessToken = res.data;
        if (!accessToken) throw new Error('No access token received');
        
        sessionStorage.setItem('access-token', accessToken);
        setUserFromToken(accessToken); // 토큰에서 유저 정보 설정
  
        await router.push({ name: 'traineeMain' });
        return true;
      } catch (err) {
        console.error('Login failed:', err);
        await router.push({ name: 'traineeLogin' });
        return false;
      }
    };

  // 회원 로그아웃
  const logout = () => {
    sessionStorage.removeItem('access-token');
    loginUser.value = null;
  };

  // 패스워드 체크 기능
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

  const decodeBase64Url = (str) => {
    // Base64Url 포맷에서 '+'를 '-', '/'를 '_', 그리고 '='를 제거한 형태로 변환
    str = str.replace(/-/g, '+').replace(/_/g, '/');
    const paddedStr = str.padEnd(str.length + (4 - (str.length % 4)) % 4, '=');
    return decodeURIComponent(escape(atob(paddedStr)));
  };
  
  const setUserFromToken = (token) => {
    const tokenParts = token.split('.');
    if (tokenParts.length !== 3) throw new Error('Invalid token format');
  
    // 수정된 디코딩 함수 사용
    const payload = JSON.parse(decodeBase64Url(tokenParts[1]));
  
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

  const updateUser = async(numberId, user) => {
    try{
      const url = `${REST_API_URL}/update/${numberId}`;
      const res = await axios.put(url, user);
      console.log('유저정보 수정 성공');
      return res.data;
    }catch(err){
      console.log('수정 중 에러 발생', err);
      throw new Error('회원 정보 수정에 실패했습니다.');
    }
  }

  return {
    userType,
    loginUser,
    trainerLogin,
    traineeLogin,
    logout,
    passwordCheck,
    getUserDetails,
    loadUserFromToken,
    updateUser,
  };
});
