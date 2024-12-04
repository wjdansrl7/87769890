import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '../views/LoginView.vue';
import BoardView from '../views/BoardView.vue';
import BoardDetailView from '../views/BoardDetailView.vue';
import BoardList from "@/components/boards/BoardList.vue";
import BoardWrite from "@/components/boards/BoardWrite.vue";
import BoardModify from "@/components/boards/BoardModify.vue";
import TheMainView from "@/views/TheMainView.vue";
import UserLogin from "@/components/users/UserLogin.vue";
import {storeToRefs} from "pinia";
import {useMemberStore} from "@/stores/member.js";
import UserRegister from "@/components/users/UserRegister.vue";

const onlyAuthUser = async (to, from, next) => {
    const memberStore = useMemberStore();
    const { userInfo, isValidToken } = storeToRefs(memberStore);
    const { getUserInfo } = memberStore;

    let token = sessionStorage.getItem("accessToken");

    if (userInfo.value != null && token) {
        await getUserInfo(token);
    }
    if (!isValidToken.value || userInfo.value === null) {
        alert("로그인 이후에 이용해주세요.")
        next({ name: "user-login" });
    } else {
        next();
    }
};

const routes = [
    { path: '/', name: 'Main', component:  TheMainView},
    { path: '/login', name: 'Login', component: LoginView },
    {
        path: '/user',
        name: 'User',
        component: LoginView,
        children: [
            {
                path: "login",
                name: "user-login",
                component: UserLogin,
            },
            {
                path: "join",
                name: "user-join",
                component: UserRegister,
            },
        ]
    },
    {
        path: '/board',
        name: 'Board',
        component: BoardView,
        children: [
            {
                path: "list",
                name: "board-list",
                component: BoardList,
            },
            {
                path: "write",
                name: "board-write",
                beforeEnter: onlyAuthUser,
                component: BoardWrite,
            },
            {
                path: ":id/modify",
                name: "board-modify",
                beforeEnter: onlyAuthUser,
                component: BoardModify,
            },
            {
                path: ':id',
                name: 'board-detail',
                beforeEnter: onlyAuthUser,
                component: BoardDetailView,
                props: true
            },

        ]},

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
