<template>
  <div class="calendar">
    <!-- 현재 월 표시 및 이전/다음 버튼 -->
    <div class="d-flex justify-content-between align-items-center mb-3">
      <!-- 이전 달로 이동 -->
      <button class="btn btn-outline-primary" @click="goToPreviousMonth">◀ 이전</button>
      <!-- 현재 월 표시 -->
      <h2 class="text-center m-0">{{ getCurrentMonthText }}</h2>
      <!-- 다음 달로 이동 -->
      <button class="btn btn-outline-primary" @click="goToNextMonth">다음 ▶</button>
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

        <!-- 이벤트 1 동그라미 (event1 데이터 여부에 따라 표시) -->
        <span
          v-if="day.event1"
          class="event-dot position-absolute"
        ></span>

        <!-- 이벤트 2, 3, 4 목록 -->
        <div class="events-list">
          <!-- 임시 이벤트 데이터 표시 -->
          <div v-for="(event, index) in day.events" :key="index" class="event-item">
            {{ event }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Calendar", // 컴포넌트 이름
  data() {
    return {
      currentMonth: new Date(), // 현재 날짜 기준으로 초기화
      calendar: [], // 달력 데이터 저장 (2차원 배열)
    };
  },
  mounted() {
    // 컴포넌트가 로드되었을 때 달력 데이터를 생성
    this.generateCalendar();
  },
  computed: {
    /**
     * 현재 월을 "YYYY년 MM월" 형식으로 반환
     * - 예: 2024년 11월
     */
    getCurrentMonthText() {
      const year = this.currentMonth.getFullYear();
      const month = this.currentMonth.getMonth() + 1; // 월은 0부터 시작하므로 +1
      return `${year}년 ${month}월`;
    },
  },
  methods: {
    /**
     * 현재 월 데이터를 기반으로 달력 배열 생성
     * - 이벤트 1 (동그라미): 랜덤한 Boolean 값
     * - 이벤트 2, 3, 4: 임시 문자열 데이터로 초기화
     */
    generateCalendar() {
      const start = new Date(
        this.currentMonth.getFullYear(),
        this.currentMonth.getMonth(),
        1 // 현재 월의 첫째 날
      );
      const end = new Date(
        this.currentMonth.getFullYear(),
        this.currentMonth.getMonth() + 1,
        0 // 현재 월의 마지막 날
      );

      const firstDayIndex = start.getDay(); // 첫 번째 날의 요일 (0: 일요일, 6: 토요일)
      const lastDay = end.getDate(); // 현재 월의 마지막 날짜
      const lastDayIndex = end.getDay(); // 마지막 날의 요일
      const daysInPrevMonth = new Date(
        this.currentMonth.getFullYear(),
        this.currentMonth.getMonth(),
        0 // 이전 월의 마지막 날짜
      ).getDate();

      let calendarDays = [];

      // 이전 달 날짜 추가
      for (let i = firstDayIndex; i > 0; i--) {
        calendarDays.push({
          date: new Date(
            this.currentMonth.getFullYear(),
            this.currentMonth.getMonth() - 1,
            daysInPrevMonth - i + 1 // 이전 달의 마지막 며칠
          ),
          inCurrentMonth: false, // 이전 달 날짜
          event1: Math.random() > 0.5, // 이벤트 1 (랜덤)
          events: ["Event 2", "Event 3", "Event 4"], // 임시 이벤트 데이터
        });
      }

      // 현재 달 날짜 추가
      for (let i = 1; i <= lastDay; i++) {
        calendarDays.push({
          date: new Date(
            this.currentMonth.getFullYear(),
            this.currentMonth.getMonth(),
            i // 현재 월의 날짜
          ),
          inCurrentMonth: true, // 현재 달 날짜
          event1: Math.random() > 0.5, // 이벤트 1 (랜덤)
          events: ["Event 2", "Event 3", "Event 4"], // 임시 이벤트 데이터
        });
      }

      // 다음 달 날짜 추가
      for (let i = 1; i < 7 - lastDayIndex; i++) {
        calendarDays.push({
          date: new Date(
            this.currentMonth.getFullYear(),
            this.currentMonth.getMonth() + 1,
            i // 다음 달의 날짜
          ),
          inCurrentMonth: false, // 다음 달 날짜
          event1: Math.random() > 0.5, // 이벤트 1 (랜덤)
          events: ["Event 2", "Event 3", "Event 4"], // 임시 이벤트 데이터
        });
      }

      // 달력을 주(Week) 단위로 분할
      this.calendar = [];
      while (calendarDays.length) {
        this.calendar.push(calendarDays.splice(0, 7));
      }
    },

    /**
     * 오늘 날짜인지 확인
     * @param {Date} date - 확인할 날짜
     * @returns {boolean} 오늘 날짜 여부
     */
    isToday(date) {
      const today = new Date();
      return (
        today.getFullYear() === date.getFullYear() &&
        today.getMonth() === date.getMonth() &&
        today.getDate() === date.getDate()
      );
    },

    /**
     * 해당 날짜가 일요일인지 확인
     * @param {Date} date - 확인할 날짜
     * @returns {boolean} 일요일 여부
     */
    isSunday(date) {
      return date.getDay() === 0;
    },

    /**
     * 해당 날짜가 토요일인지 확인
     * @param {Date} date - 확인할 날짜
     * @returns {boolean} 토요일 여부
     */
    isSaturday(date) {
      return date.getDay() === 6;
    },

    /**
     * 날짜를 클릭했을 때 선택된 날짜를 반환
     * @param {Date} date - 클릭된 날짜
     */
    selectDate(date) {
      const formattedDate = date.toISOString().split("T")[0]; // yyyy-mm-dd 형식으로 변환
      alert(`선택된 날짜: ${formattedDate}`);
    },

    /**
     * 이전 달로 이동
     */
    goToPreviousMonth() {
      this.currentMonth = new Date(
        this.currentMonth.getFullYear(),
        this.currentMonth.getMonth() - 1,
        1 // 이전 달의 첫째 날
      );
      this.generateCalendar();
    },

    /**
     * 다음 달로 이동
     */
    goToNextMonth() {
      this.currentMonth = new Date(
        this.currentMonth.getFullYear(),
        this.currentMonth.getMonth() + 1,
        1 // 다음 달의 첫째 날
      );
      this.generateCalendar();
    },
  },
};
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

</style>
