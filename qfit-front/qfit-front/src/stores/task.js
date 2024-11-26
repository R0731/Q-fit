import { defineStore } from 'pinia'
import axios from 'axios'

const REST_API_URL = `http://localhost:8080/task`
export const useTaskStore = defineStore('task', () => {
  /**
   * 테스크 수행 여부 업데이트
   * @param {number} taskId - 테스크 ID
   * @param {boolean} completed - 수행 여부
   * @returns {Object} - 업데이트된 테스크 데이터
   */
  const updateCompleted = async(taskId, completed) => {
    try {
      const url = `${REST_API_URL}/complete/${taskId}`;
      const res = await axios.put(url, { completed });
      return res.data;
    } catch (err) {
      console.error(`태스크 수행여부 업데이트 실패 (taskId: ${taskId}):`, error);
      throw error;
    }
  };

  /**
   * 태스크 생성
   * @param {Object} task - 생성할 테스크 데이터
   * @returns {Object} - 생성된 테스크 데이터
   */
  const createTask = async(task) => {
    try{
      task.taskId = 0;
      const res = await axios.post(REST_API_URL, task)
    }catch(err){
      console.error('테스크 생성 중 서버 오류', err);
      throw error;
    }
  }
  
  /**
   * 태스크 업데이트
   * @param {Object} task - 업데이트할 테스크 데이터
   * @returns {Object} - 업데이트된 테스크 데이터
   */
  const updateTask = async(task) =>{
    try{
      const taskId = task.taskId;
      const url = `${REST_API_URL}/${taskId}`
      const res = await axios.put(url, task)
    }catch(err){
      console.error('테스크 업데이트 중 오류', err);
      throw err;
    }
  }

   /**
   * 테스크 삭제
   * @param {number} taskId - 삭제할 테스크 ID
   * @returns {Object} - 삭제 확인
   */
  const deleteTask = async(taskId) => {
    try{
      const url = `${REST_API_URL}/${taskId}`
      const res = await axios.delete(url)
    }catch(err){
      console.error('테스크 삭제 중 오류', err);
      throw err;
    }
  }
  return { updateCompleted, createTask, updateTask, deleteTask };
});