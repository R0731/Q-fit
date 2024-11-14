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
      console.log(user)
      console.log(user.value.birthDate)
      const url = `${REST_API_URL}/${userType}/regist`;
      const res = axios.post(url, user);
      console.log(`${userType}등록 성공`)
      return res.data;
    }catch(error){
      throw error;
    }

  }

  return { userRegist };
});