import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

const REST_API_URL = `http://localhost:8080/review`
export const useReviewStore = defineStore('review', () => {
  const review = ref(null);

  const getReview = async(questId) => {
    try{
      const url = `${REST_API_URL}/${questId}`
      // console.log('리뷰아이디', questId)
      // console.log('리뷰주소', url)
      const res = await axios.get(url)
      review.value = res.data;
      // console.log('현재 리뷰상태', review.value)
    }catch(err){
      console.log('리뷰가져오기실패', err)
      throw err;
    }
  }
  const createReview = async(newReview) => {
    try{
      const url = `${REST_API_URL}`
      // console.log('리뷰업데이트', newReview)
      const res = await axios.post(url, newReview)
      // console.log('리뷰업데이트성공', res.data)
    }catch(err){
      console.log('리뷰업데이트실패')
      throw err;
    }
  }

  const updateReview = async(questId, newReview) => {
    try{
      const url = `${REST_API_URL}/${questId}`
      console.log('리뷰업데이트', newReview)
      const res = await axios.put(url, newReview)
      console.log('리뷰업데이트성공', res.data)
    }catch(err){
      console.log('리뷰업데이트실패')
      throw err;
    }
  }
  
  return { review, getReview, createReview, updateReview }
})
