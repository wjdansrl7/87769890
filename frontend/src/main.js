import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap';
import { createApp } from 'vue'
import App from './App.vue'
import {createPinia} from "pinia";
import router from "@/router/index.js";

const app = createApp(App);
app.use(router); // 라우터 등록
app.use(createPinia()); // Pinia 등록
app.mount('#app');