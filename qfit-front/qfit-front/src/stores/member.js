import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

const REST_API_URL = `http://localhost:8080/member`
export const useMemberStore = defineStore('member', () => {

  const userRegist = async(userType, user) => {
    try{
      if (!userType || !user) {
        throw new Error("유효한 유저 타입 또는 데이터가 없습니다.");
      }

    // console.log("전달된 유저 데이터:", user);

      const url = `${REST_API_URL}/${userType}/regist`;
      const res = await axios.post(url, user);
      console.log(`${userType}등록 성공`)

      return res.data;

    }catch(error){
      console.log('에러발생')
      throw error;
    }

  }

  const userResign = async(userType, numberId) => {
    try{
      const url = `${REST_API_URL}/${userType}/resign/${numberId}`
      console.log(url)
      const res = await axios.delete(url);
      console.log('삭제성공')
    }catch(err){
      console.log('삭제실패')
      throw err;
    }
  } 

  return { userRegist, userResign };
});