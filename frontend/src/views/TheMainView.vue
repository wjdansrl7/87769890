<script setup>
import { ref } from "vue";
import profilePhoto from "@/assets/profile_photo.jpg";

const basicInfo = ref({
  photoUrl: profilePhoto,
  details: [
    "안녕하세요, 성장판이 열려 있는 개발자, 정문기입니다.",
    "생년월일: 1997.09.10",
    "휴대폰 번호: 010-8776-9890",
    "이메일: c880910@naver.com",
    "이러한 성장통을 통해 얻은 경험과 성장한 역량을 바탕으로,",
    "SK 엠앤서비스의 목표를 달성하고 혁신에 기여하는 개발자가 되겠습니다.",
  ],
});

const features = ref([

  {
    title: "1.1 로그인",
    details: [
      "- Spring Security와 JWT를 활용한 로그인 구현",
      "- 로그아웃 기능",
      "- 회원가입: 비밀번호 해시화 저장",
    ],
  },
  {
    title: "1.2 게시판",
    details: [
      "- 게시판 목록: 제목, 작성자ID, 조회수, 첨부파일 여부, 등록일 노출",
      "- 등록일 역순 정렬 및 페이징 처리 (10개 기준)",
      "- 제목이나 작성자ID로 게시글 검색 가능",
      "- 게시판 보기: 조회 수 증가, 첨부파일 다운로드 및 표시",
      "- 게시판 작성: 로그인 사용자만 작성 가능",
      "- 게시판 수정/삭제: 본인의 글만 수정/삭제 가능",
    ],
  },
]);

const stack = ref([
  { category: "2.1 Frontend",
    details: "Vue 3, Composition API, Pinia, Bootstrap, Axios 등" },
  {
    category: "2.2 Backend",
    details: "Spring Boot, Spring Security, Spring Data JPA, QueryDSL, JWT, JUnit 등",
  },
]);
</script>

<template>
  <div>
    <div class="video-container">
      <video autoplay muted loop>
        <source src="https://www.skmnservice.com/resources/pc/video/video_main.mp4" type="video/mp4" />
        Your browser does not support the video tag.
      </video>
    </div>
    <div class="basic-info">
      <img :src="basicInfo.photoUrl" alt="Profile Photo" class="profile-photo" />
      <h1>기본 정보</h1>
      <ul>
        <li v-for="(detail, index) in basicInfo.details" :key="index">{{ detail }}</li>
      </ul>
    </div>

    <div class="content">
      <h1>프로젝트 소개</h1>
      <h2>1. 구현 사항</h2>
      <div v-for="(feature, index) in features" :key="index" class="feature-section">
        <h3>{{ feature.title }}</h3>
        <ul>
          <li v-for="(detail, idx) in feature.details" :key="idx">{{ detail }}</li>
        </ul>
      </div>
      <h2>2. 구현 스택</h2>
      <div v-for="(item, index) in stack" :key="index" class="stack-section">
        <h3>{{ item.category }}</h3>
        <p>{{ item.details }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 동영상 배치 */
.video-container {
  width: 100vw;
  height: 60vh; /* 동영상 높이를 60%로 줄임 */
  overflow: hidden;
}

video {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

/* 기본 정보 영역 */
.basic-info {
  background-color: white; /* 텍스트 영역 배경 */
  padding: 2rem;
  text-align: center; /* 가운데 정렬 */
}

.profile-photo {
  width: 150px; /* 사진 크기 */
  height: 150px;
  border-radius: 50%; /* 원형 사진 */
  margin-bottom: 1rem;
}

/* 텍스트 영역 */
.content {
  background-color: white; /* 텍스트 영역과 동영상 명확히 구분 */
  padding: 2rem;
  margin-top: -1px; /* 동영상과 텍스트 간 경계 제거 */
}

h1 {
  font-size: 2.5rem;
  margin-bottom: 2rem;
  text-align: center;
}

h2 {
  font-size: 2rem;
  margin-top: 3rem;
}

.feature-section,
.stack-section {
  margin-bottom: 2rem;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  font-size: 1rem;
  line-height: 1.8;
}
</style>
