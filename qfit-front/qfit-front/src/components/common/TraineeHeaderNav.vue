<template>
  <div class="header">
    <!-- 왼쪽 화살표 아이콘 -->
    <i class="header-bi bi-chevron-left" @click="goBack"></i>

    <!-- 가운데 로고 -->
    <img src="@/assets/logo.png" alt="logo" class="header-logo" />

    <!-- 오른쪽 벨 아이콘 -->
    <div class="icon-wrapper" @click="toggleNotification">
      <i class="header-bi bi-bell"></i>
      <span v-if="unreadCount > 0" class="badge">{{ unreadCount }}</span>
    </div>
  </div>

  <!-- 알림 팝업 -->
  <div v-if="isNotificationOpen" class="notification-popup-overlay" @click.self="toggleNotification">
    <div class="notification-popup">
      <div class="notification-header">
        <h3>알림</h3>
        <button class="close-btn" @click="toggleNotification">X</button>
      </div>
      <ul class="notification-list">
  <li
    v-for="notification in notifications"
    :key="notification.notificationId"
    :data-id="notification.notificationId"
    @click="() => handleNotificationClick(notification.notificationId)"
    class="notification-item"
  >
    {{ notification.message }}
  </li>
  <li v-if="notifications.length === 0" class="no-notifications">
    알림이 없습니다.
  </li>
</ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useNotificationStore } from '@/stores/notification';
import { useUserStore } from '@/stores/user';

const router = useRouter();
const userStore = useUserStore();
const notificationStore = useNotificationStore();

const isNotificationOpen = ref(false); // 알림 팝업 상태
const userId = userStore.loginUser?.numberId || 0; // 사용자 ID
const notifications = computed(() => notificationStore.notifications); // 알림 목록
const unreadCount = computed(() => notifications.value.length); // 읽지 않은 알림 개수

// 뒤로 가기
const goBack = () => {
  router.back();
};

// 알림 팝업 열기/닫기
const toggleNotification = () => {
  isNotificationOpen.value = !isNotificationOpen.value;
  if (isNotificationOpen.value) {
    fetchNotifications(); // 팝업 열릴 때 알림 가져오기
  }
};

// 알림 데이터 가져오기
const fetchNotifications = async () => {
  if (!userId) return; // 사용자 ID가 없으면 요청하지 않음
  try {
    await notificationStore.fetchUnreadNotifications(userId);
  } catch (err) {
    console.error('알림 데이터를 가져오는 중 오류 발생:', err);
  }
};

// 개별 알림 읽음 처리
const handleNotificationClick = async (notificationId) => {
  try {
    const item = document.querySelector(`[data-id="${notificationId}"]`); // 알림 DOM 찾기
    if (item) {
      item.classList.add('slide-out'); // 애니메이션 추가

      // 애니메이션 완료 후 실행
      setTimeout(() => {
        notificationStore.removeNotification(notificationId); // 로컬 상태 업데이트
      }, 300); // 애니메이션 시간과 일치
      
      // 서버에 읽음 요청
      await notificationStore.markAsRead(notificationId);
    }
  } catch (error) {
    console.error('알림 읽음 처리 중 오류 발생:', error);
  }
};

// 컴포넌트가 마운트되면 알림 데이터를 가져옴
onMounted(fetchNotifications);
</script>

<style scoped>
.icon-wrapper {
  position: relative;
  display: inline-flex;
  width: 40px;
  height: 40px;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

/* 알림 배지 */
.badge {
  position: absolute;
  top: 3px;
  right: 2px;
  background: red;
  color: white;
  font-size: 12px;
  padding: 2px 6px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 흔들림 애니메이션 */
@keyframes bell-shake {
  0% {
    transform: rotate(0);
  }
  25% {
    transform: rotate(-15deg);
  }
  50% {
    transform: rotate(15deg);
  }
  75% {
    transform: rotate(-10deg);
  }
  100% {
    transform: rotate(0);
  }
}

/* hover 상태에서 흔들림 효과 */
.icon-wrapper i:hover {
  animation: bell-shake 1s ease;
}

/* 알림 팝업 배경 */
.notification-popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* 반투명 배경 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1500; /* 헤더 위로 표시 */
}

/* 알림 팝업 */
.notification-popup {
  width: 90%;
  max-width: 400px;
  background-color: var(--background-color);
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  animation: popup-animation 0.3s ease;
}

/* 팝업 애니메이션 */
@keyframes popup-animation {
  from {
    transform: scale(0.8);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}

/* 팝업 헤더 */
.notification-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  background: linear-gradient(90deg, var(--theme-color), #9d47f4);
  color: white;
}

.notification-header h3 {
  margin: 0;
  font-size: 1.2rem;
}

.notification-header .close-btn {
  background: none;
  border: none;
  font-size: 1.2rem;
  color: white;
  cursor: pointer;
}

/* 알림 목록 */
.notification-list {
  list-style: none;
  padding: 16px;
  margin: 0;
  color: var(--text-color);
  transition: all 0.3s ease; /* 높이 변화를 부드럽게 처리 */
}

.notification-list li {
  padding: 12px;
  border-bottom: 1px solid #ddd;
}

.notification-list .no-notifications {
  text-align: center;
  color: #999;
  padding: 12px;
}

/* 알림 왼쪽으로 사라지는 애니메이션 */
@keyframes slide-out-left {
  from {
    transform: translateX(0);
    opacity: 1;
  }
  to {
    transform: translateX(-100%);
    opacity: 0;
  }
}

.notification-item {
  cursor: pointer;
  /* transition: transform 0.3s ease, opacity 0.3s ease; */
}

.notification-item.slide-out {
  animation: slide-out-left 0.3s forwards;
}
</style>
