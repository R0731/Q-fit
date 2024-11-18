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

    // 아이디 중복 체크 기능
    const userIdCheck = async(userId) => {
      try{
        const url = `${REST_API_URL}/idCheck`;
        const res = await axios.post(url, {userId});
        if(res){
          console.log('중복 아이디')
        }else{
          console.log('사용가능 아이디')
        }
      }catch(err){
        console.log('에러발생 :', err);
      }
    };
    
  return { userRegist, userResign };
});