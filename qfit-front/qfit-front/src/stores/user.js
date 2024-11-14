import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/user`
export const useUserStore = defineStore('user', () => {
  const loginUser = ref(null);

  const trainerLogin = async (id, password) => {
    try {
      // console.log(id, password)
      const res = await axios.post(`${REST_API_URL}/login`, {
        userId: id,
        userPassword: password,
      });
  
      // console.log('Response:', res.data);
  
      // 서버에서 받은 토큰이 존재하는지 확인
      const accessToken = res.data;
      if (!accessToken) {
        throw new Error('No access token received');
      }
  
      sessionStorage.setItem('access-token', accessToken);
  
      // 토큰 디코딩
      const tokenParts = accessToken.split('.');
      if (tokenParts.length !== 3) {
        throw new Error('Invalid token format');
      }
  
      // Base64 URL 디코딩
      const payload = JSON.parse(atob(tokenParts[1]));
  
      const userId = payload.userId;
      const name = payload.name;
      const userType = payload.userType;

      // userType이 1인 아닌 경우 경고 및 로그인 중단
      if(userType !== 1){
        sessionStorage.removeItem('access-token');
        return false;
      }

      // 로그인 성공 시 정보 저장
      loginUser.value = { userId, name, userType };
  
      // console.log('Decoded Payload:', payload);
  
      // 로그인 성공 시 페이지 이동
      await router.push({ name: 'traineeList' });
      return true;
    } catch (err) {
      console.error('Login failed:', err);
      await router.push({ name: 'trainerLogin' });
      return false;
    }
  };
  
  const traineeLogin = async (id, password) => {
    try {
      console.log(id, password)
      const res = await axios.post(`${REST_API_URL}/login`, {
        userId: id,
        userPassword: password,
      });
  
      // console.log('Response:', res.data);
  
      // 서버에서 받은 토큰이 존재하는지 확인
      const accessToken = res.data;
      if (!accessToken) {
        throw new Error('No access token received');
      }
  
      sessionStorage.setItem('access-token', accessToken);
  
      // 토큰 디코딩
      const tokenParts = accessToken.split('.');
      if (tokenParts.length !== 3) {
        throw new Error('Invalid token format');
      }
  
      // Base64 URL 디코딩
      const payload = JSON.parse(atob(tokenParts[1]));
  
      const userId = payload.userId;
      const name = payload.name;
      const userType = payload.userType;

      // userType이 2인 아닌 경우 경고 및 로그인 중단
      if(userType !== 2){
        sessionStorage.removeItem('access-token');
        console.log('이쪽인가')
        return false;
      }

      // 로그인 성공 시 정보 저장
      loginUser.value = { userId, name, userType };
  
      // console.log('Decoded Payload:', payload);
  
      // 로그인 성공 시 페이지 이동
      await router.push({ name: 'traineeHome' });
      return true;
    } catch (err) {
      console.error('Login failed:', err);
      await router.push({ name: 'traineeLogin' });
      return false;
    }
  };

  return { loginUser, trainerLogin, traineeLogin }
})
