import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

const REST_API_URL = `http://localhost:8080/review`
export const useReviewStore = defineStore('review', () => {

  const review = ref(null); // 리뷰 객체

  /**
   * 리뷰 가져오기
   * @param {number} questId - 퀘스트 ID
   * @returns {Object} - 리뷰 데이터
   */
  const getReview = async(questId) => {
    try{
      const url = `${REST_API_URL}/${questId}`
      const res = await axios.get(url)
      review.value = res.data;
    }catch(err){
      console.log('리뷰가져오기실패', err)
      throw err;
    }
  }

  /**
   * 리뷰 생성
   * @param {Object} newReview - 새 리뷰 데이터
   * @returns {Object} - 생성된 리뷰 데이터
   */
  const createReview = async(newReview) => {
    try{
      const url = `${REST_API_URL}`
      const res = await axios.post(url, newReview)
    }catch(err){
      console.log('리뷰업데이트실패')
      throw err;
    }
  }

  /**
   * 리뷰 업데이트
   * @param {number} questId - 퀘스트 ID
   * @param {Object} newReview - 업데이트할 리뷰 데이터
   * @returns {Object} - 업데이트된 리뷰 데이터
   */
  const updateReview = async(questId, newReview) => {
    try{
      const url = `${REST_API_URL}/${questId}`
      const res = await axios.put(url, newReview)
    }catch(err){
      console.log('리뷰업데이트실패')
      throw err;
    }
  }
  
  return { review, getReview, createReview, updateReview }
})
