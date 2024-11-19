import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/feedback`
export const useFeedbackStore = defineStore('feedback', () => {
  
  const getFeedback = async(questId) => {
    try{
      const url = `${REST_API_URL}/${questId}`
      // console.log('피드백피니아url', url);
      const res = await axios.get(url)
      // console.log('피드백피니아', res.data);
      return res.data;
    }catch(err){
      console.log('피드백가져오기실패', err)
      throw err;
    }
  }

  return { getFeedback }
})
