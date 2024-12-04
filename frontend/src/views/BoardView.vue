<template>
  <div class="container text-center mt-3">
    <router-view></router-view>
  </div>
</template>

<script setup>
import BoardList from '@/components/boards/BoardList.vue';
</script>
<style>
mark.sky {
  background: linear-gradient(to top, #54fff9 20%, transparent 30%);
}
</style>
