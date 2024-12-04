<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/layout/TheHeadingNavbar.vue";
import {useMemberStore} from "@/stores/member.js";

const memberStore = useMemberStore();
memberStore.initializeStore();

window.addEventListener("beforeunload", () => {
  sessionStorage.removeItem("userInfo");
  sessionStorage.removeItem("accessToken");
});

</script>

<template>
  <TheHeadingNavbar />
  <router-view />
</template>

<style scoped></style>
