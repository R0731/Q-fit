import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
const REST_API_URL = `http://localhost:8080/feedback`
export const useFeedbackStore = defineStore('feedback', () => {
  
  /**
   * 퀘스트 ID로 피드백 가져오기
   * @param {number} questId - 퀘스트 ID
   * @returns {Object} - 피드백 데이터
   */
  const getFeedback = async(questId) => {
    try{
      const url = `${REST_API_URL}/${questId}`
      const res = await axios.get(url)
      return res.data;
    }catch(err){
      console.log('피드백가져오기실패', err)
      throw err;
    }
  }

   /**
   * 새 피드백 생성
   * @param {Object} newFeedback - 생성할 피드백 데이터
   * @returns {Object} - 생성된 피드백 데이터
   */
  const createFeedback = async(newFeedback) => {
    try{
      const url = `${REST_API_URL}`
      const res = await axios.post(url, newFeedback)
    }catch(err){
      console.log('피드백등록실패', err)
      throw err;
    }
  }

   /**
   * 피드백 수정
   * @param {number} questId - 퀘스트 ID
   * @param {Object} newFeedback - 수정할 피드백 데이터
   * @returns {Object} - 수정된 피드백 데이터
   */
  const updateFeedback = async(questId, newFeedback) => {
    try{
      const url = `${REST_API_URL}/${questId}`
      const res = await axios.put(url, newFeedback)
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
