<script setup>
import { useRouter } from "vue-router";
const props = defineProps({ board: Object });
const router = useRouter();

const moveToDetail = () => {
  router.push({ name: "board-detail", params: { id: props.board.id } });
};
</script>

<template>
  <tr class="text-center" @click="moveToDetail" style="cursor: pointer">
    <th scope="row">{{ props.board.id }}</th>
    <td class="text-start">
      <router-link
          :to="{ name: 'board-detail', params: { id: props.board.id } }"
          class="article-title link-dark"
      >
        {{ props.board.title }}
      </router-link>
    </td>
    <td>{{ props.board.writer }}</td>
    <td>{{ props.board.viewCnt }}</td>
    <td>{{ props.board.hasFile }}</td>
    <td>{{ props.board.createdAt }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>