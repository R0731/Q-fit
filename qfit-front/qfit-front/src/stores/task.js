import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/task`
export const useTaskStore = defineStore('task', () => {
  // 수행여부 업데이트
  const updateCompleted = async(taskId, completed) => {
    try {
      const url = `${REST_API_URL}/complete/${taskId}`;
      const res = await axios.put(url, { completed });

      // console.log('태스크 수행여부 업데이트', taskId, res.data);

      return res.data;
    } catch (err) {
      console.error(`태스크 수행여부 업데이트 실패 (taskId: ${taskId}):`, error);
      throw error;
    }
  };

  // 테스크 생성
  const createTask = async(task) => {
    try{
      task.taskId = 0;
      // console.log('테스크아이디', task.taskId);
      console.log('생성되는 테스크', task);
      const res = await axios.post(REST_API_URL, task)
    }catch(err){
      console.error('테스크 생성 중 서버 오류', err);
      throw error;
    }
  }
  
  // 생성된 테스크 수정
  const updateTask = async(task) =>{
    try{
      const taskId = task.taskId;
      // console.log('수정 테스크Id조회', taskId);
      const url = `${REST_API_URL}/${taskId}`
      console.log('업데이트 되는 테스크', task)
      const res = await axios.put(url, task)
    }catch(err){
      console.error('테스크 업데이트 중 오류', err);
      throw err;
    }
  }

  // 테스크 삭제
  const deleteTask = async(taskId) => {
    // console.log('삭제 테스크Id조회', taskId);
    try{
      const url = `${REST_API_URL}/${taskId}`
      console.log('삭제url조회', url)
      const res = await axios.delete(url)
    }catch(err){
      console.error('테스크 삭제 중 오류', err);
      throw err;
    }
  }
  return { updateCompleted, createTask, updateTask, deleteTask };
});