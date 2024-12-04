<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board.js";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/boards/item/BoardListItem.vue";
import VPageNavigation from "@/components/common/VPageNavigation.vue";

const router = useRouter();

const selectOption = ref([
  { text: "검색조건", value: "" },
  // { text: "글번호", value: "article_no" },
  { text: "제목", value: "title" },
  { text: "작성자아이디", value: "writer" },
]);

const boards = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);

const searchKey = ref(""); // 검색어 저장
const selectedKey = ref(""); // 선택된 검색 조건


const param = ref({
  page: currentPage.value - 1,
  size: 10,
  title: "",
  author: ""
});

onMounted(() => {
  getBoardList();
});

const changeKey = (val) => {
  selectedKey.value = val;
};

const getBoardList = () => {
  // 검색 조건 반영
  if (selectedKey.value === "title") {
    param.value.title = searchKey.value;
    param.value.author = "";
  } else if (selectedKey.value === "writer") {
    param.value.author = searchKey.value;
    param.value.title = "";
  }

  listArticle(
      param.value,
      (response) => {
        boards.value = response.data.content;
        currentPage.value = response.data.pageNumber + 1;
        totalPage.value = response.data.totalPages;
      },
      (error) => {
        console.error(error);
      }
  );
};

const onPageChange = (page) => {
  currentPage.value = page;
  param.value.page = page - 1;
  getBoardList();
};

const moveWrite = () => {
  router.push({ name: "board-write" });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글목록</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-outline-primary btn-sm" @click="moveWrite">
              글쓰기
            </button>
          </div>
          <div class="col-md-5 offset-5">
            <form class="d-flex">
              <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
              <div class="input-group input-group-sm ms-1">
                <input
                    type="text"
                    class="form-control"
                    v-model="searchKey"
                    placeholder="검색어..."
                />
                <button class="btn btn-dark" type="button" @click="getBoardList">검색</button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
          <tr class="text-center">
            <th scope="col">글번호</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">조회수</th>
            <th scope="col">첨부파일유무</th>
            <th scope="col">작성일</th>
          </tr>
          </thead>
          <tbody>
          <BoardListItem
              v-for="board in boards"
              :key="board.title"
              :board="board"
          ></BoardListItem>
          </tbody>
        </table>
      </div>
      <VPageNavigation
          :current-page="currentPage"
          :total-page="totalPage"
          @pageChange="onPageChange"
      ></VPageNavigation>
    </div>
  </div>
</template>

<style scoped></style>