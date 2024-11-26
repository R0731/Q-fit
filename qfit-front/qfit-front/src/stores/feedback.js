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
  
  /**
   * 피드백 남기지 않은 퀘스트 정보 불러오기
   * @param {number} trainerId - 피드백을 확인할 트레이너의 ID
   * @returns {void} - 퀘스트 데이터를 받아와 `feedbacks`에 저장
   */
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
