import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/task`
export const useTaskStore = defineStore('task', () => {
  const updateCompleted = async (taskId, completed) => {
    try {
      const url = `${REST_API_URL}/complete/${taskId}`;
      const res = await axios.put(url, { completed });

      console.log('태스크 수행여부 업데이트', taskId, res.data);

      return res.data;
    } catch (error) {
      console.error(`태스크 수행여부 업데이트 실패 (taskId: ${taskId}):`, error);
      throw error;
    }
  };

  return { updateCompleted };
});