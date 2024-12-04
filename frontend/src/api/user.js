import {localAxios} from "@/util/http-commons.js";

const local = localAxios();

async function signup(param, success, fail) {
    const formData = new URLSearchParams();
    formData.append("username", param.username);
    formData.append("password", param.password);

    try {
        const response = await local.post(`/users/signup`, JSON.stringify(param), {
            headers: {
                "Content-Type": "application/json",
            },

            withCredentials: true,
        });
        success(response);
    } catch (error) {
        fail(error);
    }
}

async function userConfirm(param, success, fail) {
    const formData = new URLSearchParams();
    formData.append("username", param.username);
    formData.append("password", param.password);

    try {
        const response = await local.post(`/users/login`, formData, {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            withCredentials: true,
        });
        success(response);
    } catch (error) {
        fail(error);
    }
}

async function findByUsername(success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`/users/info`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
    local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
    await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(success, fail) {
    await local.get(`/users/logout`).then(success).catch(fail);
}

export { signup, userConfirm, findByUsername, tokenRegeneration, logout };
