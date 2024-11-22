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

  const createFeedback = async(newFeedback) => {
    try{
      const url = `${REST_API_URL}`
      console.log('새 피드백', newFeedback)
      const res = await axios.post(url, newFeedback)
      console.log('피드백 등록 성공')
    }catch(err){
      console.log('피드백등록실패', err)
      throw err;
    }
  }

  const updateFeedback = async(questId, newFeedback) => {
    try{
      const url = `${REST_API_URL}/${questId}`
      console.log('새 피드백', newFeedback)
      const res = await axios.put(url, newFeedback)
      console.log('피드백 수정 성공')
    }catch(err){
      console.log('피드백수정실패', err)
      throw err;
    }
  }
  
  const feedbacks = ref([]);
  const getFeedbackPendingQuests = async(trainerId) => {
    try {
      const response = await axios.get(`${REST_API_URL}/pending/${trainerId}`);
      feedbacks.value = response.data
    } catch (err) {
      console.error('PendingFeedback Error: ', err)
    }
  }
  
  return { 
    getFeedback, 
    createFeedback, 
    updateFeedback, 
    getFeedbackPendingQuests,
    feedbacks,
  }
})
