<script setup>
import {onMounted, ref, watch} from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";
import {useMemberStore} from "@/stores/member.js";

const router = useRouter();
const route = useRoute();
const memberStore = useMemberStore();
const { userInfo } = memberStore; // 로그인한 유저 정보 가져오기

const props = defineProps({ type: String });

const isUseId = ref(false);

const article = ref({
  title: "",
  content: "",
  file: ""
});

onMounted(() => {
  // 로그인한 유저 ID 설정
  if (userInfo && userInfo.username) {
    article.value.username = userInfo.username;
  }


  if (props.type === "modify") {
    let {boardId} = route.params;
    console.log(boardId);
    getModifyArticle(
        boardId,
        ({data}) => {
          article.value = data;
        },
        (error) => {
          console.error(error);
        }
    );
  }
});

  const subjectErrMsg = ref("");
  const contentErrMsg = ref("");

// 파일 업로드 핸들러
const handleFileUpload = async (event) => {
  const file = event.target.files[0];
  if (file) {
    article.value.file = file;
  }
};

  watch(
      () => article.value.title,
      (value) => {
        let len = value.length;
        console.log(len)
        if (len == 0 || len > 30) {
          subjectErrMsg.value = "제목을 확인해 주세요!!!";
        } else subjectErrMsg.value = "";
      },
      {immediate: true}
  );
  watch(
      () => article.value.content,
      (value) => {
        let len = value.length;
        if (len == 0 || len > 500) {
          contentErrMsg.value = "내용을 확인해 주세요!!!";
        } else contentErrMsg.value = "";
      },
      {immediate: true}
  );

  function onSubmit() {
    if (subjectErrMsg.value) {
      alert(subjectErrMsg.value);
    } else if (contentErrMsg.value) {
      alert(contentErrMsg.value);
    } else {
      props.type === "regist" ? writeArticle() : updateArticle();
    }
  }

  function writeArticle() {
    console.log("글등록하자!!", article.value);

    // FormData 생성
    const formData = new FormData();
    formData.append("title", article.value.title);
    formData.append("content", article.value.content);
    if (article.value.file) {
      formData.append("file", article.value.file); // 파일 추가
    }
    registArticle(
        formData,
        (response) => {
          let msg = "글등록 처리시 문제 발생했습니다.";
          if (response.status == 201) msg = "글등록이 완료되었습니다.";
          route.params.id = response.data.id;
          alert(msg);
          moveList();
        },
        (error) => console.error(error)
    );
  }

  function updateArticle() {
    console.log(route.params.id + "번글 수정하자!!", article.value);
    // FormData 생성
    const formData = new FormData();
    formData.append("title", article.value.title);
    formData.append("content", article.value.content);
    if (article.value.file) {
      formData.append("file", article.value.file); // 파일 추가
    }
    modifyArticle(
        route.params.id,
        formData,
        (response) => {
          let msg = "글수정 처리시 문제 발생했습니다.";
          if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
          alert(msg);
          moveList();
        },
        (error) => console.log(error)
    );
  }

  function moveList() {
    router.push({name: "board-list"});
  }
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.username"
        readonly
        :disabled="isUseId"
        placeholder="작성자ID..."
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.title" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="file" class="form-label">파일 추가</label>
      <input id="file" type="file" class="form-control" @change="handleFileUpload" />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea class="form-control" v-model="article.content" rows="10"></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped>
</style>
