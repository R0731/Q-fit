<template>
  <div class="container date-container">
    <div class="text-center mb-3">
      <h5>{{ currentMonth }}월</h5>
    </div>

    <div
      ref="dateListWrapperRef"
      class="date-list-wrapper"
      @mousedown="startDrag"
      @mousemove="onDrag"
      @mouseup="endDrag"
      @mouseleave="endDrag"
    >
      <div ref="dateListRef" class="date-list">
        <div
          v-for="(date, index) in visibleDates"
          :key="index"
          class="date-item"
          @click="onDateSelect(date)"
        >
          <div class="day-name">{{ getDayName(date) }}</div>
          <div
            class="date"
            :class="{ 'text-primary': isToday(date), 'selected-date': isSelected(date) }"
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

// Pinia 스토어
const viewStore = useViewStore();

// 현재 날짜와 선택된 날짜
const today = new Date();
const selectedDate = ref(today);

// 표시할 날짜 목록
const visibleDates = ref(generateDates(today, -7, 14));

// 날짜 리스트 DOM 참조
const dateListRef = ref(null);
const dateListWrapperRef = ref(null);

// 드래그 상태 관리
const scrollState = ref({ isDragging: false, startX: 0, scrollOffset: 0 });

// 현재 월 계산 (선택된 날짜 기준)
const currentMonth = computed(() => selectedDate.value.getMonth() + 1);

// 요일 배열 및 날짜 아이템 너비 상수화
const DAYS = ['일', '월', '화', '수', '목', '금', '토'];
const DATE_ITEM_WIDTH = 96;

/**
 * 날짜 배열 생성
 * @param {Date} baseDate 기준 날짜
 * @param {number} startOffset 시작 오프셋 (일 단위)
 * @param {number} endOffset 끝 오프셋 (일 단위)
 * @returns {Date[]} 생성된 날짜 배열
 */
function generateDates(baseDate, startOffset, endOffset) {
  return Array.from({ length: endOffset - startOffset + 1 }, (_, i) => {
    const date = new Date(baseDate);
    date.setDate(baseDate.getDate() + startOffset + i);
    return date;
  });
}

/**
 * 요일 이름 반환
 * @param {Date} date 요일을 얻고자 하는 날짜
 * @returns {string} 요일 이름
 */
function getDayName(date) {
  return DAYS[date.getDay()];
}

// 선택한 날짜 변경을 상위 컴포넌트에 알림
const emit = defineEmits(['update:selectedDate']);

/**
 * 날짜 선택 핸들러
 * @param {Date} date 선택된 날짜
 */
function onDateSelect(date) {
  selectedDate.value = date;
  viewStore.setSelectedDate(date);
  centerSelectedDate();
  emit('update:selectedDate', date);
}

/**
 * 선택된 날짜를 화면 중앙으로 이동
 */
function centerSelectedDate() {
  const dateList = dateListRef.value;
  const dateListWrapper = dateListWrapperRef.value;

  if (!dateList || !dateListWrapper) return;

  const containerWidth = dateListWrapper.offsetWidth;
  const targetIndex = visibleDates.value.findIndex(
    (d) => d.toDateString() === selectedDate.value.toDateString()
  );

  if (targetIndex === -1) return;

  const offset =
    -(targetIndex * DATE_ITEM_WIDTH) + containerWidth / 2 - DATE_ITEM_WIDTH / 2;

  dateList.style.transition = 'transform 0.5s ease';
  dateList.style.transform = `translateX(${offset}px)`;

  dateList.addEventListener(
    'transitionend',
    () => {
      dateList.style.transition = ''; // 애니메이션 완료 후 transition 제거
    },
    { once: true }
  );

  scrollState.value.scrollOffset = offset; // 스크롤 상태 업데이트
}

/**
 * 오늘 날짜인지 확인
 * @param {Date} date 비교할 날짜
 * @returns {boolean} 오늘인지 여부
 */
function isToday(date) {
  return date.toDateString() === today.toDateString();
}

/**
 * 선택된 날짜인지 확인
 * @param {Date} date 비교할 날짜
 * @returns {boolean} 선택 여부
 */
function isSelected(date) {
  return date.toDateString() === selectedDate.value.toDateString();
}

/**
 * 드래그 시작 핸들러
 * @param {MouseEvent} event 마우스 이벤트
 */
function startDrag(event) {
  scrollState.value.isDragging = true;
  scrollState.value.startX = event.clientX;
}

/**
 * 드래그 중 핸들러
 * @param {MouseEvent} event 마우스 이벤트
 */
function onDrag(event) {
  if (!scrollState.value.isDragging) return;

  const dragSpeed = 0.2;
  const deltaX = (event.clientX - scrollState.value.startX) * dragSpeed;
  scrollState.value.scrollOffset += deltaX;
  scrollState.value.startX = event.clientX;

  const dateList = dateListRef.value;
  if (dateList) {
    dateList.style.transform = `translateX(${scrollState.value.scrollOffset}px)`;
  }
}

/**
 * 드래그 종료 핸들러
 */
function endDrag() {
  scrollState.value.isDragging = false;
}

/**
 * 가시적인 날짜 목록 업데이트
 * @param {string} direction 날짜 추가 방향 ('previous' | 'next')
 */
function updateVisibleDates(direction) {
  const newDates =
    direction === 'previous'
      ? generateDates(visibleDates.value[0], -7, -1)
      : generateDates(visibleDates.value.at(-1), 1, 7);

  visibleDates.value =
    direction === 'previous'
      ? [...newDates, ...visibleDates.value].slice(0, 14)
      : [...visibleDates.value, ...newDates].slice(-14);
}

// 컴포넌트가 마운트되면 선택된 날짜를 화면 중앙으로 이동
onMounted(centerSelectedDate);
</script>

<style scoped>
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
  gap: 18px;
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
