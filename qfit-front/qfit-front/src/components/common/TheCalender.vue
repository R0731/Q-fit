<template>
  <div class="container date-container">
    <!-- 현재 월 표시 -->
    <div class="text-center mb-3">
      <h5>{{ currentMonth }}월</h5>
    </div>

    <!-- 날짜 선택 및 화살표 버튼 -->
    <div class="date-navigation">
      <!-- 이전 날짜로 이동 -->
      <i class="bi bi-arrow-left arrow-icon" @click="prevDay"></i>

      <!-- 날짜 목록 표시 -->
      <div class="date-list">
        <div
          v-for="(date, index) in visibleDates"
          :key="index"
          class="date-item"
          @click="selectDate(date)"
        >
          <!-- 요일 표시 (선택 효과 없음) -->
          <span class="day">{{ getDayName(date) }}</span>

          <!-- 날짜 표시 (선택 효과 적용) -->
          <span
            class="date"
            :class="{
              'bg-primary text-white': isToday(date),
              'selected-date': isSelected(date)
            }"
          >
            {{ date.getDate() }}
          </span>
        </div>
      </div>

      <!-- 다음 날짜로 이동 -->
      <i class="bi bi-arrow-right arrow-icon" @click="nextDay"></i>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const today = new Date();
const selectedDate = ref(today);
const visibleDates = ref(generateDates(today));

const currentMonth = computed(() => selectedDate.value.getMonth() + 1);

function generateDates(baseDate) {
  const dates = [];
  for (let i = -2; i <= 2; i++) {
    const newDate = new Date(baseDate);
    newDate.setDate(baseDate.getDate() + i);
    dates.push(newDate);
  }
  return dates;
}

function selectDate(date) {
  selectedDate.value = date;
  visibleDates.value = generateDates(date);
}

function isToday(date) {
  return (
    date.getDate() === today.getDate() &&
    date.getMonth() === today.getMonth() &&
    date.getFullYear() === today.getFullYear()
  );
}

function isSelected(date) {
  return (
    date.getDate() === selectedDate.value.getDate() &&
    date.getMonth() === selectedDate.value.getMonth() &&
    date.getFullYear() === selectedDate.value.getFullYear()
  );
}

function getDayName(date) {
  const days = ['일', '월', '화', '수', '목', '금', '토'];
  return days[date.getDay()];
}

function prevDay() {
  const newDate = new Date(selectedDate.value);
  newDate.setDate(selectedDate.value.getDate() - 1);
  selectDate(newDate);
}

function nextDay() {
  const newDate = new Date(selectedDate.value);
  newDate.setDate(selectedDate.value.getDate() + 1);
  selectDate(newDate);
}
</script>

<style scoped>
/* 반응형을 고려한 date-container 스타일 */
.date-container {
  width: 100%;
  max-width: 480px; /* 👉 최대 너비 제한 */
  margin: auto;
  padding: 0 16px;
  box-sizing: border-box;
  overflow-x: hidden; /* 가로 스크롤 방지 */
}

/* 날짜 및 화살표 정렬 컨테이너 */
.date-navigation {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
}

/* 날짜 목록 가로 정렬 */
.date-list {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px; /* 👉 간격 조정 */
}

/* 요일 스타일 */
.day {
  font-size: 14px;
  color: #6c757d;
  text-align: center;
  margin-bottom: 4px;
}

/* 날짜 항목 스타일 */
.date-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  width: 60px; /* 기본 너비 설정 */
  height: 80px; /* 기본 높이 설정 */
  box-sizing: border-box;
}

/* 날짜 스타일 (선택 효과는 날짜에만 적용) */
.date {
  font-size: 16px;
  padding: 8px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px; /* 👉 선택된 날짜의 크기 고정 (여기서 크기 조정 가능) */
  height: 40px; /* 👉 선택된 날짜의 크기 고정 */
}

/* 선택된 날짜 스타일 */
.selected-date {
  background-color: #8504e8;
  color: white;
}

/* 오늘 날짜 스타일 */
.bg-primary {
  background-color: #ffc107 !important;
}

/* 반응형 스타일 적용 */
@media (max-width: 768px) {
  /* 태블릿 이하 크기 */
  .date-item {
    width: 50px; /* 화면이 작아지면 너비 축소 */
    height: 70px;
  }
  .date {
    width: 35px;
    height: 35px;
  }
  .arrow-icon {
    font-size: 28px; /* 화살표 크기 줄이기 */
  }
}

@media (max-width: 480px) {
  /* 모바일 크기 */
  .date-item {
    width: 40px;
    height: 60px;
  }
  .date {
    width: 30px;
    height: 30px;
  }
  .arrow-icon {
    font-size: 24px; /* 모바일에서는 더 작은 화살표 */
  }
}
</style>
