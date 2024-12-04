<script setup>
import {ref} from "vue";
import router from "@/router/index.js";
import {useMemberStore} from "@/stores/member.js";

const signupUser = ref({
  username: "",
  password: "",
})

const memberStore = useMemberStore()
const { userSignup } = memberStore

const signup = async () => {
  await userSignup(signupUser.value)
  router.push("/")
}

const cancel = () => {
  router.push("/");
};

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">회원가입</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <form>
          <div class="mb-3">
            <label for="userid" class="form-label">아이디 : </label>
            <input type="text" class="form-control" v-model="signupUser.username" placeholder="아이디..." />
          </div>
          <div class="mb-3">
            <label for="userpwd" class="form-label">비밀번호 : </label>
            <input type="password" class="form-control" v-model="signupUser.password" placeholder="비밀번호..." />
          </div>
          <div class="mb-3">
            <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
            <input type="password" class="form-control" id="pwdcheck" placeholder="비밀번호확인..." />
          </div>
          <div class="col-auto text-center">
            <button type="button" class="btn btn-outline-primary mb-3" @click="signup">회원가입</button>
            <button type="button" class="btn btn-outline-success ms-1 mb-3" @click="cancel">취소</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
