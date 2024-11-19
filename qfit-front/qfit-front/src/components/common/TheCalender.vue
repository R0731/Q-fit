<template>
  <div class="container date-container">
    <!-- 현재 월 표시 (윗줄) -->
    <div class="text-center mb-3">
      <h5>{{ currentMonth }}월</h5>
    </div>

    <!-- 날짜 선택 및 화살표 버튼 (아래 줄) -->
    <div class="d-flex align-items-center justify-content-center date-navigation">
      <!-- 이전 날짜로 이동 -->
      <button class="btn btn-outline-secondary me-3 arrow-icon" @click="prevDay">⬅️</button>

      <!-- 날짜 목록 표시 -->
      <div class="d-flex gap-2 date-list">
        <span
          v-for="(date, index) in visibleDates"
          :key="index"
          @click="onDateSelect(date)"
          :class="{
            'date': true,
            'bg-primary text-white': isToday(date),
            'selected-date': isSelected(date)
          }"
        >
          {{ date.getDate() }}
        </span>
      </div>

      <!-- 다음 날짜로 이동 -->
      <button class="btn btn-outline-secondary ms-3 arrow-icon" @click="nextDay">➡️</button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useViewStore } from '@/stores/viewStore';

const viewStore = useViewStore();

// 오늘 날짜를 기준으로 날짜 상태 관리
const today = new Date();
const selectedDate = ref(today);
const visibleDates = ref(generateDates(today));

// 현재 월 계산
const currentMonth = computed(() => selectedDate.value.getMonth() + 1);

// 날짜 목록 생성 함수
function generateDates(baseDate) {
  const dates = [];
  for (let i = -2; i <= 2; i++) {
    const newDate = new Date(baseDate);
    newDate.setDate(baseDate.getDate() + i);
    dates.push(newDate);
  }
  return dates;
}

// 날짜 선택 함수
function onDateSelect(date) {
  selectedDate.value = date;
  visibleDates.value = generateDates(date);
  viewStore.setSelectedDate(date); // Pinia 상태 업데이트
}

// 오늘 날짜 확인 함수
function isToday(date) {
  return (
    date.getDate() === today.getDate() &&
    date.getMonth() === today.getMonth() &&
    date.getFullYear() === today.getFullYear()
  );
}

// 선택된 날짜 확인 함수
function isSelected(date) {
  return (
    date.getDate() === selectedDate.value.getDate() &&
    date.getMonth() === selectedDate.value.getMonth() &&
    date.getFullYear() === selectedDate.value.getFullYear()
  );
}

// 이전 날짜로 이동
function prevDay() {
  const newDate = new Date(selectedDate.value);
  newDate.setDate(selectedDate.value.getDate() - 1);
  onDateSelect(newDate); // 날짜 선택과 동일 로직 실행
}

// 다음 날짜로 이동
function nextDay() {
  const newDate = new Date(selectedDate.value);
  newDate.setDate(selectedDate.value.getDate() + 1);
  onDateSelect(newDate); // 날짜 선택과 동일 로직 실행
}
</script>

<style scoped>
/* 기존 스타일 유지 */
.date-container {
  width: 100%;
  max-width: 480px;
  margin: auto;
  padding: 0 16px;
  box-sizing: border-box;
  overflow-x: hidden;
}

.date-navigation {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
}

.date-list {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

.date {
  font-size: 16px;
  padding: 8px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
}

.selected-date {
  background-color: #8504e8;
  color: white;
}

.bg-primary {
  background-color: #ffc107 !important;
}

.arrow-icon {
  font-size: 24px;
}
</style>
