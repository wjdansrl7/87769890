import { ref } from "vue";
import { defineStore } from "pinia";

export const useMenuStore = defineStore("menuStore", () => {
  const menuList = ref([
    { name: "게시판", show: true, routeName: "board-list" },
    { name: "회원가입", show: true, routeName: "user-join" },
    { name: "로그인", show: true, routeName: "user-login" },
    { name: "로그아웃", show: false, routeName: "user-logout" },
  ]);

  const changeMenuState = () => {
    menuList.value = menuList.value.map((item) => {
      if (item.routeName === "board-list") {
        return item;
      }
      return { ...item, show: !item.show };
    });
  };
  return {
    menuList,
    changeMenuState,
  };
});
