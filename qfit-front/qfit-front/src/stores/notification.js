import { defineStore } from 'pinia';
import { ref } from 'vue';
import axios from 'axios';

const REST_API_URL = `http://localhost:8080/notifications`;

export const useNotificationStore = defineStore('notification', () => {
  const notifications = ref([]); // 알림 목록
  const loading = ref(false); // 로딩 상태
  const error = ref(null); // 오류 메시지

  /**
   * 읽지 않은 알림 가져오기
   * @param {number} userId - 사용자 ID
   * @returns {void}
   */
  const fetchUnreadNotifications = async (id) => {
    loading.value = true;
    error.value = null;
    try {
      const response = await axios.get(`${REST_API_URL}/unread`, {
        params: { id },
      });
      notifications.value = response.data; // 서버에서 알림 목록 가져오기
    } catch (err) {
      error.value = err.message || 'Failed to fetch notifications';
    } finally {
      loading.value = false;
    }
  };

  /**
   * 개별 알림 읽음 처리
   * @param {number} notificationId - 알림 ID
   * @returns {void}
   */
  const markAsRead = async (notificationId) => {
    try {
      if (!notificationId) throw new Error('notificationId가 비어있음');
      await axios.put(`${REST_API_URL}/read`, null, {
        params: { notificationId },
      });
    } catch (err) {
      console.error('알림 중 오류 발생', err);
    }
  };
  
  /**
   * 알림 삭제
   * @param {number} notificationId - 알림 ID
   * @returns {void}
   */
  const removeNotification = (notificationId) => {
    const index = notifications.value.findIndex(
      (n) => n.notificationId === notificationId
    );
    if (index > -1) {
      notifications.value.splice(index, 1); // 알림 삭제
    }
  };

  /**
   * 알림 생성
   * @param {Object} notification - 생성할 알림 객체
   * @returns {Object} - 생성된 알림 데이터
   */
  const createNotification = async(notification) => {
    try{
      const url = `${REST_API_URL}`
      const res = await axios.post(url, notification);
    }catch(err){
      console.log('알림 생성 중 오류 발생', err);
      throw err;
    }
  }
  
  return {
    notifications,
    loading,
    error,
    fetchUnreadNotifications,
    markAsRead,
    removeNotification,
    createNotification,
  };
});
