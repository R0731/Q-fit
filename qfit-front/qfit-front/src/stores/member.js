import { defineStore } from 'pinia'
import axios from 'axios'

const REST_API_URL = `http://localhost:8080/member`
export const useMemberStore = defineStore('member', () => {

   /**
   * 회원가입
   * @param {string} userType - 유저 타입
   * @param {Object} user - 사용자 정보 객체
   * @returns {Object} - 등록된 사용자 정보
   */
  const userRegist = async(userType, user) => {
    try{
      if (!userType || !user) {
        throw new Error("유효한 유저 타입 또는 데이터가 없습니다.");
      }
      const url = `${REST_API_URL}/${userType}/regist`;
      const res = await axios.post(url, user);
      return res.data;
    }catch(err){
      console.error('에러발생', err)
      throw error;
    }

  }

  /**
   * 사용자 탈퇴
   * @param {string} userType - 사용자 유형
   * @param {number} numberId - 사용자 고유 ID
   * @returns {void}
   */
  const userResign = async(userType, numberId) => {
    try{
      const url = `${REST_API_URL}/${userType}/resign/${numberId}`
      const res = await axios.delete(url);
    }catch(err){
      console.error('삭제실패', err)
      throw err;
    }
  } 

  /**
   * 사용자 ID 중복 확인
   * @param {string} userId - 확인할 사용자 ID
   * @returns {boolean} - 중복 여부
   */
    const userIdCheck = async(userId) => {
      try{
        const url = `${REST_API_URL}/idCheck`;
        const res = await axios.post(url, {userId});
        return res.data;
      }catch(err){
        console.log('에러발생', err);
      }
    };

  return { userRegist, userResign, userIdCheck };
});