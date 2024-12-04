<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ post?.id }}. {{ post?.title }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                  class="avatar me-2 float-md-start bg-light p-2"
                  src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">{{post?.writer}}</span> <br />
                <span class="text-secondary fw-light">
                  {{ post?.createdAt }} 조회 : {{ post?.viewCnt }}
                </span>
              </p>
            </div>
          </div>
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ post?.content }}
          </div>
          <div v-if="post?.file" class="mt-3">
            <h4>첨부파일</h4>
            <div v-if="isImageFile(post?.file)">
              <img :src="`http://localhost:8080/files/${encodeURIComponent(getFileName(post.file))}`" alt="첨부 이미지" style="width: 50%; height: 50%;"/>
            </div>
            <div v-else>
              <a :href="`http://localhost:8080/download/${encodeURIComponent(getFileName(post.file))}`" download>
                첨부파일 다운로드
              </a>
            </div>
          </div>

          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
              글목록
            </button>
            <button v-if="post?.writer === userInfo?.username" type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
              글수정
            </button>
            <button v-if="post?.writer === userInfo?.username" type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteArticle">
              글삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useMemberStore } from "@/stores/member";
import {deleteArticle, detailArticle, listArticle} from "@/api/board.js";
import router from "@/router/index.js";

const post = ref(null);
const route = useRoute();
const memberStore = useMemberStore();
const { userInfo } = memberStore;

// 파일 타입 확인 (이미지인지 판별)
const isImageFile = (filePath) => {
  const imageExtensions = [".png", ".jpg", ".jpeg", ".gif"];
  return imageExtensions.some((ext) => filePath?.toLowerCase()?.endsWith(ext));
};

const getFileName = (filePath) => {
  return filePath.split("/").pop();
};

// 게시글 상세 데이터 로드
const loadArticle = () => {
  const boardId = route.params.id; // URL의 id 파라미터 사용
  detailArticle(
      boardId,
      (response) => {
        console.log("게시글 데이터 로드 성공:", response);
        post.value = response.data;
      },
      (error) => {
        console.error("게시글 데이터 로드 실패:", error);
        alert("게시글 정보를 가져오는 데 실패했습니다.");
        router.push({ name: "board-list" }); // 실패 시 목록으로 이동
      }
  );
};

// 글목록으로 이동
const moveList = () => {
  router.push({ name: "board-list" });
};

// 글수정 페이지로 이동
const moveModify = () => {
  console.log(route.params.id);
  router.push({ name: "board-modify", params: { id: route.params.id } });
};

// 글삭제
const onDeleteArticle = () => {
  const confirmDelete = confirm("정말로 이 글을 삭제하시겠습니까?");
  if (confirmDelete) {
    deleteArticle(
        route.params.id,
        (response) => {
          console.log("글 삭제 성공:", response);
          alert("글이 삭제되었습니다.");
          moveList(); // 삭제 후 목록으로 이동
        },
        (error) => {
          console.error("글 삭제 실패:", error);
          alert("글 삭제에 실패했습니다.");
        }
    );
  }
};


onMounted(async () => {
  loadArticle();
});
</script>
