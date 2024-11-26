<template>
  <div class="calendar">
    <div class="calendar-header">
      <!-- 현재 월 표시 및 이전/다음 버튼 -->
      <div class="d-flex justify-content-between align-items-center mb-3">
        <!-- 이전 달로 이동 -->
        <button class="nav-btn" @click="goToPreviousMonth">◀</button>
        <!-- 현재 월 표시 -->
        <h2 class="text-center m-0">{{ getCurrentMonthText }}</h2>
        <!-- 다음 달로 이동 -->
        <button class="nav-btn" @click="goToNextMonth">▶</button>
      </div>
    </div>

    <!-- 요일 헤더 -->
    <div class="row text-center fw-bold">
      <!-- 요일 컬럼 (일~토) -->
      <div class="col text-danger">SUN</div>
      <div class="col">MON</div>
      <div class="col">TUE</div>
      <div class="col">WED</div>
      <div class="col">THU</div>
      <div class="col">FRI</div>
      <div class="col text-primary">SAT</div>
    </div>

    <!-- 날짜 칸 -->
    <div v-for="week in calendar" :key="week" class="row">
      <div
        v-for="day in week"
        :key="day.date ? day.date.toISOString() : Math.random()" 
        class="col border p-2 text-start position-relative calendar-cell"
        :class="{
          'text-muted': !day.inCurrentMonth, /* 이전/다음 달 날짜는 흐리게 표시 */
          'bg-light': isToday(day.date), /* 오늘 날짜 강조 */
        }"
        @click="day.date && selectDate(day.date)" 
      >
        <!-- 날짜 숫자 -->
        <span
          :class="{
            'text-danger': isSunday(day.date), /* 일요일은 빨간색 */
            'text-primary': isSaturday(day.date), /* 토요일은 파란색 */
            'font-weight-bold': isToday(day.date), /* 오늘 날짜는 굵게 표시 */
          }"
        >
          {{ day.date.getDate() }} <!-- 날짜 숫자 -->
        </span>
        
        <!-- 퀘스트 있으면 동그라미 -->
        <span
          v-if="day.questStatus"
          class="event-dot position-absolute"
        ></span>

        <!-- 퀘스트 달성률, 리뷰, 피드백 표시 -->
        <div v-if="day.questStatus" class="quest-details">
          <div>달성률  {{ day.questStatus.questCompletionRate }}</div>
          <div v-if="day.questStatus.review">리뷰  <span v-html="getReviewEmoji(day.questStatus.review)"></span></div>
          <div v-if="day.questStatus.feedback">피드백  <i class="bi bi-chat-left"></i></div>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import { useQuestStore } from "@/stores/quest";
import { useTraineeStore } from "@/stores/trainee";
import { useViewStore } from "@/stores/viewStore";
import { ref, computed } from "vue";
import { useRouter } from "vue-router";

const currentMonth = ref(new Date()); // 현재 날짜 기준 초기화
const calendar = ref([]); // 달력 데이터 저장
const traineeStore = useTraineeStore();
const traineeId = traineeStore.selectedTrainee.id;
const viewStore = useViewStore();
const router = useRouter();

// 현재 월 텍스트 (YYYY년 MM월)
const getCurrentMonthText = computed(() => {
  const year = currentMonth.value.getFullYear();
  const month = currentMonth.value.getMonth() + 1; // 월은 0부터 시작하므로 +1
  return `${year}년 ${month}월`;
});

// 현재 월 데이터를 기반으로 달력 배열 생성
const generateCalendar = async () => {
  const start = new Date(
    currentMonth.value.getFullYear(),
    currentMonth.value.getMonth(),
    1
  );
  const end = new Date(
    currentMonth.value.getFullYear(),
    currentMonth.value.getMonth() + 1,
    0
  );

  const firstDayIndex = start.getDay(); // 첫 번째 날의 요일 (0: 일요일, 6: 토요일)
  const lastDay = end.getDate(); // 현재 월의 마지막 날짜
  const lastDayIndex = end.getDay(); // 마지막 날의 요일
  const daysInPrevMonth = new Date(
    currentMonth.value.getFullYear(),
    currentMonth.value.getMonth(),
    0
  ).getDate();

  let calendarDays = [];

  // 이전 달 날짜 추가
  for (let i = firstDayIndex; i > 0; i--) {
    calendarDays.push({
      date: new Date(
        currentMonth.value.getFullYear(),
        currentMonth.value.getMonth() - 1,
        daysInPrevMonth - i + 1
      ),
      inCurrentMonth: false,
    });
  }

  // 현재 달 날짜 추가
  for (let i = 1; i <= lastDay; i++) {
    calendarDays.push({
      date: new Date(
        currentMonth.value.getFullYear(),
        currentMonth.value.getMonth(),
        i
      ),
      inCurrentMonth: true,
    });
  }

  // 다음 달 날짜 추가
  for (let i = 1; i < 7 - lastDayIndex; i++) {
    calendarDays.push({
      date: new Date(
        currentMonth.value.getFullYear(),
        currentMonth.value.getMonth() + 1,
        i
      ),
      inCurrentMonth: false,
    });
  }

  // 달력을 주 단위로 나누기
  const weeks = [];
  while (calendarDays.length) {
    weeks.push(calendarDays.splice(0, 7));
  }
  calendar.value = weeks;


  // 퀘스트 상태 데이터 로드
  const startDate = `${currentMonth.value.getFullYear()}-${currentMonth.value.getMonth() + 1}-01`;
  // 현재 달의 마지막 날짜 계산
  const lastDayOfCurrentMonth = new Date(currentMonth.value.getFullYear(), currentMonth.value.getMonth() + 1, 0);
  const endDate = `${lastDayOfCurrentMonth.getFullYear()}-${lastDayOfCurrentMonth.getMonth() + 1}-${lastDayOfCurrentMonth.getDate()}`;

  await useQuestStore().getTraineeQuestCompletionRate(traineeId, startDate, endDate);
  updateCalendarWithQuestData();
};

// 퀘스트 상태 데이터로 달력 업데이트
const updateCalendarWithQuestData = () => {
  calendar.value.forEach((week) => {
    week.forEach((day) => {
      const status = useQuestStore().questCompletionRates.find((q) => {
        // q.start_at을 Date 객체로 변환한 후, 연도, 월, 일만 비교
        const qDate = new Date(q.start_at);
        const dayDate = day.date;

        return (
          qDate.getFullYear() === dayDate.getFullYear() &&
          qDate.getMonth() === dayDate.getMonth() &&
          qDate.getDate() === dayDate.getDate()
        );
      });
      if (status) {
        day.questStatus = status;
      }
    });
  });
};

// 리뷰를 Bootstrap Icons로 변환하는 메서드
 const getReviewEmoji = (review) => {
  if (!review) return '';
  switch (review) {
    case 'EASY':
      return '<i class="bi bi-emoji-smile" style="color: green;"></i>'; // 쉬운 리뷰
    case 'MEDIUM':
      return '<i class="bi bi-emoji-neutral" style="color: orange;"></i>'; // 보통 리뷰
    case 'HARD':
      return '<i class="bi bi-emoji-frown" style="color: red;"></i>'; // 어려운 리뷰
    default:
      return '❓'; // 알 수 없는 값
  }
};

/**
 * 오늘 날짜인지 확인
 * @param {Date} date - 확인할 날짜
 * @returns {boolean} 오늘 날짜 여부
 */
const isToday = (date) => {
  const today = new Date();
  return (
    today.getFullYear() === date.getFullYear() &&
    today.getMonth() === date.getMonth() &&
    today.getDate() === date.getDate()
  );
};

/**
 * 해당 날짜가 일요일인지 확인
 * @param {Date} date - 확인할 날짜
 * @returns {boolean} 일요일 여부
 */
const isSunday = (date) => date.getDay() === 0;

/**
 * 해당 날짜가 토요일인지 확인
 * @param {Date} date - 확인할 날짜
 * @returns {boolean} 토요일 여부
 */
const isSaturday = (date) => date.getDay() === 6;

/**
 * 날짜를 클릭했을 때 선택된 날짜를 반환
 * @param {Date} date - 클릭된 날짜
 */
const selectDate = (date) => {
  viewStore.setSelectedDate(date);
  router.push({ name: "quest" });
};

// 이전 달로 이동
const goToPreviousMonth = () => {
  currentMonth.value = new Date(
    currentMonth.value.getFullYear(),
    currentMonth.value.getMonth() - 1,
    1
  );
  generateCalendar();
};

// 다음 달로 이동
const goToNextMonth = () => {
  currentMonth.value = new Date(
    currentMonth.value.getFullYear(),
    currentMonth.value.getMonth() + 1,
    1
  );
  generateCalendar();
};

// 초기 달력 생성
generateCalendar();
</script>


<style scoped>
.calendar {
  width: 100%; /* 달력이 화면 전체 너비를 차지하도록 설정 */
  max-width: 800px; /* 달력의 최대 너비를 800px로 제한 */
  margin: auto; /* 달력을 화면 중앙에 정렬 */
}

/* 날짜 텍스트를 왼쪽 위에 배치 */
.calendar-cell {
  position: relative; /* 내부 요소를 절대 위치로 배치하기 위해 설정 */
  height: 14vh; /* 각 날짜 칸의 높이를 화면 높이의 14%로 설정 */
  /* 사용자가 달력의 칸 높이를 수정하고 싶다면 여기 값을 변경하면 됩니다. */
}

/* 오늘 날짜를 굵게 표시 */
.font-weight-bold {
  font-weight: bold;
}

/* 이벤트 1 동그라미 (날짜 오른쪽 위에 나타남) */
.event-dot {
  width: 8px; /* 동그라미의 너비 (픽셀 단위) */
  height: 8px; /* 동그라미의 높이 (픽셀 단위) */
  background-color: var(--theme-color); /* CSS 변수로 설정된 색상 사용 */
  border-radius: 50%; /* 동그라미 모양을 만들기 위한 설정 */
  position: absolute; /* 부모 요소(.calendar-cell)를 기준으로 절대 위치 지정 */
  top: 5px; /* 날짜 칸의 위쪽에서 5px 아래로 위치 */
  right: 5px; /* 날짜 칸의 오른쪽에서 5px 왼쪽으로 위치 */
  /* 동그라미의 색상이나 크기를 변경하려면 background-color, width, height를 수정하세요. */
}

/* 이벤트 목록 스타일 (날짜 아래에 표시) */
.events-list {
  margin-top: 15px; /* 날짜와 이벤트 목록 사이의 간격 */
  /* 이벤트 목록과 날짜 숫자 사이의 간격을 조정하려면 margin-top 값을 변경하세요. */
}

/* 이벤트 목록의 각 항목 스타일 */
.event-item {
  font-size: 0.8rem; /* 이벤트 텍스트의 크기 (rem 단위로 설정) */
  color: gray; /* 이벤트 텍스트의 색상 */
  /* 이벤트 텍스트의 색상을 변경하려면 color 값을 수정하세요. */
}

.nav-btn {
  background: none; /* 배경 제거 */
  border: none; /* 테두리 제거 */
  font-size: 1rem; /* 작고 간결한 글씨 크기 */
  font-weight: bold; /* 텍스트 강조 */
  color: #333; /* 기본 텍스트 색상 */
  cursor: pointer; /* 클릭 가능한 커서 */
  padding: 5px 10px; /* 작은 버튼 크기 */
  transition: color 0.3s ease, transform 0.2s ease; /* 부드러운 전환 효과 */
}

.nav-btn:hover {
  transform: scale(1.2); /* 살짝 확대 효과 */
}

.nav-btn:active {
  transform: scale(0.95); /* 클릭 시 작아지는 효과 */
}

/* quest-details 클래스에 스타일 추가 */
.quest-details {
  font-size: 0.7rem; /* 글씨 크기를 줄임 */
  line-height: 1.2;  /* 줄 간격 조정 */
}
</style>
