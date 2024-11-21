import { defineStore } from 'pinia';
import { ref } from 'vue';
import axios from 'axios';

const REST_API_URL = `http://localhost:8080/notifications`;

export const useNotificationStore = defineStore('notification', () => {
  const notifications = ref([]); // 알림 목록
  const loading = ref(false); // 로딩 상태
  const error = ref(null); // 오류 메시지

  // 읽지 않은 알림 가져오기
  const fetchUnreadNotifications = async (userId) => {
    loading.value = true;
    error.value = null;
    try {
      const response = await axios.get(`${REST_API_URL}/unread`, {
        params: { userId },
      });
      notifications.value = response.data; // 서버에서 알림 목록 가져오기
    } catch (err) {
      error.value = err.message || 'Failed to fetch notifications';
    } finally {
      loading.value = false;
    }
  };

  // 개별 알림 읽음 처리
  const markAsRead = async (notificationId) => {
    try {
      console.log('알람아이디안넘어왓나:', notificationId);
  
      if (!notificationId) throw new Error('notificationId가 비어있음');
  
      await axios.put(`${REST_API_URL}/read`, null, {
        params: { notificationId },
      });
  
      console.log('알람 읽음 성공');
    } catch (err) {
      console.error('알림 중 오류 발생:', err);
    }
  };
  

  return {
    notifications,
    loading,
    error,
    fetchUnreadNotifications,
    markAsRead,
  };
});
