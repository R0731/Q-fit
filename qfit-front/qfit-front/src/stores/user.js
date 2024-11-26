import { ref, computed } from 'vue';
import { useRoute } from 'vue-router'
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';

const REST_API_URL = `http://localhost:8080/user`;

export const useUserStore = defineStore('user', () => {
  
  const route = useRoute();

  const userType = computed(() => route.path.split('/')[1]); // 주소를 통해 userType 확정
  const loginUser = ref(null); // 로그인한 유저 정보를 담을 객체
  
  // 회원 로그아웃
  const logout = () => {
    const userType = loginUser.value.userType;
    sessionStorage.removeItem('access-token');
    loginUser.value = null;
  
    if (userType === 1) {
      router.push({ name: 'trainerLogin' }); // 트레이너 로그인 페이지
    } else if (userType === 2) {
      router.push({ name: 'traineeLogin' }); // 트레이니 로그인 페이지
    } else {
      router.push({ name: 'Home' }); // 기본 홈으로 이동
    }
  };
  
  /**
  * 트레이너 로그인
  * @param {string} id
  * @param {string} password
  * 
 */
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

      const userType = loginUser.value.userType;

      if(userType !== 1){
        console.warn('잘못된 유저 접근')
        sessionStorage.removeItem('access-token');
        loginUser.value = null;
        return false;
      }

      await router.push({ name: 'traineeList' });
      return true;
    } catch (err) {
      console.error('Login failed:', err);
      await router.push({ name: 'trainerLogin' });
      return false;
    }
  };
  
  /**
  * 트레이니 로그인
  * @param {string} id
  * @param {string} password
  * 
 */
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
      
      const userType = loginUser.value.userType;

      if(userType !== 2){
        console.warn('잘못된 유저 접근')
        sessionStorage.removeItem('access-token');
        loginUser.value = null;
        return false;
      }

      await router.push({ name: 'traineeMain' });
      return true;
    } catch (err) {
      console.error('Login failed:', err);
      await router.push({ name: 'traineeLogin' });
      return false;
    }
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

  /**
   * 유저 상세 정보 가져오기
   * @param {string} userId - 업데이트할 유저 id
  */
  const getUserDetails = async (userId) => {
    try {
      const res = await axios.get(`${REST_API_URL}/info/${userId}`);
      return res.data;
    } catch (err) {
      console.error('유저 정보 가져오기 실패:', err);
      throw err;
    }
  };

  // 토큰 디코딩
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

  const loadUserFromToken = () => {
    const token = sessionStorage.getItem('access-token');
    if (token) {
      try {
        setUserFromToken(token);
      } catch (err) {
        console.error('Failed to load user from token:', err);
        logout(); // 잘못된 토큰인 경우 로그아웃 처리
      }
    }
  };

  /**
   * 유저 정보 업데이트
   * @param {nubmer} numberId - 업데이트할 유저 고유 id
   * @param {Object} user - 업데이트할 유저 정보
   * 
  */
  const updateUser = async(numberId, user) => {
    try{
      const url = `${REST_API_URL}/update/${numberId}`;
      const res = await axios.put(url, user);
      return res.data;
    }catch(err){
      console.error('수정 중 에러 발생', err);
      throw new Error('회원 정보 수정에 실패했습니다.');
    }
  }

    /**
  * 트레이니 로그인
  * @param {number} numberId - 유저 고유 ID
  * 
 */
  const getUserImageUrl = async(numberId) => {
    try{
      const url = `${REST_API_URL}/image-load/${numberId}`
      const res = await axios.get(url)
      return res.data;
    }catch(err){
      console.error('유저 이미지 로드 에러', err);
      throw new err;
    }
  }

  // 유저 정보에서 imgUrl 업데이트하기
  const updateUserImageUrl = async(user) => {
    try{
      const url = `${REST_API_URL}/image-update`;
      const res = await axios.put(url, user);
      return res.data;
    }catch(err){
      console.error('사진 업데이트에러', err);
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
    getUserImageUrl,
    updateUserImageUrl,
  };
});
