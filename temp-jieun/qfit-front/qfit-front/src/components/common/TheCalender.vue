<template>
  <div class="container date-container">
    <!-- 현재 월 표시 -->
    <div class="text-center mb-3">
      <h5>{{ currentMonth }}월</h5>
    </div>

    <!-- 날짜 목록과 드래그 기능 -->
    <div
      class="date-list-wrapper"
      @mousedown="startDrag"
      @mousemove="onDrag"
      @mouseup="endDrag"
      @mouseleave="endDrag"
    >
      <div class="date-list">
        <!-- 요일과 날짜 표시 -->
        <div
          v-for="(date, index) in visibleDates"
          :key="index"
          class="date-item"
          @click="onDateSelect(date)"
        >
          <div class="day-name">{{ getDayName(date) }}</div>
          <div
            class="date"
            :class="{
              'text-primary': isToday(date),
              'selected-date': isSelected(date),
            }"
          >
            {{ date.getDate() }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useViewStore } from '@/stores/viewStore';

const viewStore = useViewStore();

// 오늘 날짜와 선택된 날짜 상태
const today = new Date();
const selectedDate = ref(today);

// 현재 보여질 날짜 목록 초기화 (7일 기준)
const visibleDates = ref(generateDates(today, -7, 14)); // -7부터 +14일까지 표시

// 현재 월 계산
const currentMonth = computed(() => selectedDate.value.getMonth() + 1);

// 날짜 생성 함수: 기준 날짜로부터 이전과 이후 날짜를 포함해 생성
function generateDates(baseDate, startOffset, endOffset) {
  const dates = [];
  for (let i = startOffset; i <= endOffset; i++) {
    const newDate = new Date(baseDate);
    newDate.setDate(baseDate.getDate() + i);
    dates.push(newDate);
  }
  return dates;
}

// 요일 이름 반환 함수
function getDayName(date) {
  const days = ['일', '월', '화', '수', '목', '금', '토'];
  return days[date.getDay()];
}

// 날짜 선택 함수
function onDateSelect(date) {
  selectedDate.value = date; // 선택된 날짜 업데이트
  viewStore.setSelectedDate(date); // Pinia 상태 업데이트
  centerSelectedDate(); // 선택한 날짜를 중앙으로 이동
}

// 선택된 날짜를 중앙으로 이동하는 함수
function centerSelectedDate() {
  const dateList = document.querySelector('.date-list');

  // viewStore에서 selectedDate를 가져옴
  const storeSelectedDate = viewStore.selectedDate
    ? new Date(viewStore.selectedDate)
    : today;

  const targetDate = visibleDates.value.find(
    (d) =>
      d.getDate() === storeSelectedDate.getDate() &&
      d.getMonth() === storeSelectedDate.getMonth() &&
      d.getFullYear() === storeSelectedDate.getFullYear()
  );

  const selectedIndex = visibleDates.value.indexOf(targetDate);

  if (dateList && selectedIndex !== -1) {
    const dateItemWidth = 96; // 날짜 아이템 폭(80px + gap 16px)
    const containerWidth = document.querySelector('.date-list-wrapper').offsetWidth;
    const offset = -(selectedIndex * dateItemWidth) + containerWidth / 2 - dateItemWidth / 2;

    // 부드러운 이동
    dateList.style.transition = 'transform 0.5s ease'; // 부드러운 애니메이션 추가
    dateList.style.transform = `translateX(${offset}px)`;

    // 스크롤 값을 업데이트해 드래그와 일관성 유지
    scrollAmount.value = offset;
  }
}

// 오늘 날짜인지 확인
function isToday(date) {
  return (
    date.getDate() === today.getDate() &&
    date.getMonth() === today.getMonth() &&
    date.getFullYear() === today.getFullYear()
  );
}

// 선택된 날짜인지 확인
function isSelected(date) {
  return (
    date.getDate() === selectedDate.value.getDate() &&
    date.getMonth() === selectedDate.value.getMonth() &&
    date.getFullYear() === selectedDate.value.getFullYear()
  );
}

// 드래그 상태 관리
const isDragging = ref(false);
const startX = ref(0);
const scrollAmount = ref(0);
const scrollThreshold = 100; // 드래그 임계값

// 드래그 시작
function startDrag(event) {
  isDragging.value = true;
  startX.value = event.clientX;
}

// 드래그 중: 스크롤 이동 계산
function onDrag(event) {
  if (!isDragging.value) return;

  const dragSpeed = 0.2; // 감속 비율 (0.1 ~ 1 사이 값, 작을수록 느림)
  const deltaX = (event.clientX - startX.value) * dragSpeed; // 이동 거리 조정
  scrollAmount.value += deltaX;
  startX.value = event.clientX;

  // 스크롤을 부드럽게 이동
  const dateList = document.querySelector('.date-list');
  if (dateList) {
    dateList.style.transform = `translateX(${scrollAmount.value}px)`;
  }

  // 드래그 임계값을 넘었을 때 날짜 이동
  if (Math.abs(scrollAmount.value) > scrollThreshold) {
    if (scrollAmount.value > 0) {
      loadPreviousDates();
    } else {
      loadNextDates();
    }
    scrollAmount.value = 0; // 스크롤 거리 초기화
  }
}

// 드래그 종료
function endDrag() {
  isDragging.value = false;
}

// 이전 주 로드
function loadPreviousDates() {
  const firstVisibleDate = visibleDates.value[0];
  const newDates = generateDates(firstVisibleDate, -7, -1);
  visibleDates.value = [...newDates, ...visibleDates.value].slice(0, 14); // 14일 유지
}

// 다음 주 로드
function loadNextDates() {
  const lastVisibleDate = visibleDates.value[visibleDates.value.length - 1];
  const newDates = generateDates(lastVisibleDate, 1, 7);
  visibleDates.value = [...visibleDates.value, ...newDates].slice(-14); // 14일 유지
}

// 컴포넌트 로딩 시 초기화
onMounted(() => {
  centerSelectedDate(); // 로딩 시 selectedDate 또는 오늘 날짜를 중앙에 위치
});
</script>

<style scoped>
/* 기존 스타일 유지 */
.date-container {
  width: 100%;
  max-width: 480px;
  margin: auto;
  padding: 16px;
  background-color: #ffffff;
  border-radius: 16px;
}

.text-center h5 {
  font-size: 20px;
  font-weight: bold;
  color: #333333;
}

.date-list-wrapper {
  display: flex;
  overflow: hidden;
  user-select: none;
  cursor: grab;
}

.date-list-wrapper:active {
  cursor: grabbing;
}

.date-list {
  display: flex;
  gap: 16px;
  transition: transform 0.3s ease-out;
}

.date-item {
  text-align: center;
  min-width: 80px;
  cursor: pointer;
}

.day-name {
  font-size: 14px;
  color: #666666;
  margin-bottom: 4px;
}

.date {
  font-size: 20px;
  color: #333333;
  font-weight: bold;
}

.text-primary {
  color: var(--theme-color);
}

.date-item:hover .date {
  color: var(--hover-color);
  transition: color 0.2s ease-in-out;
}

.selected-date {
  color: var(--theme-color);
}
</style>
