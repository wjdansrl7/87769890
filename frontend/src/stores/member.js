import {ref} from "vue"
import {useRouter} from "vue-router"
import {defineStore} from "pinia"
import {jwtDecode} from "jwt-decode"

import {userConfirm, findByUsername, tokenRegeneration, logout, signup} from "@/api/user"
import {httpStatusCode} from "@/util/http-status"

export const useMemberStore = defineStore("memberStore", () => {
    const router = useRouter()

    const isLogin = ref(false)
    const isLoginError = ref(false)
    const userInfo = ref(null)
    const isValidToken = ref(false)

    // 초기화 함수
    const initializeStore = async () => {
        const storedToken = sessionStorage.getItem("accessToken");
        const storedUserInfo = sessionStorage.getItem("userInfo");

        if (storedToken && storedUserInfo) {
            isLogin.value = true;
            isValidToken.value = true;
            userInfo.value = JSON.parse(storedUserInfo);
        } else {
            isLogin.value = false;
            userInfo.value = null;
        }
    };

    const userLogin = async (loginUser) => {
        await userConfirm(
            loginUser,
            async (response) => {
                let accessToken = "";
                const authHeader = response.headers["authorization"];
                if (authHeader && authHeader.startsWith("Bearer ")) {
                    accessToken = authHeader.substring(7);
                    sessionStorage.setItem("accessToken", accessToken);
                } else {
                    console.warn("Authorization 헤더가 없거나 Bearer 토큰이 아닙니다.");
                }

                isLogin.value = true;
                isLoginError.value = false;
                isValidToken.value = true;
                const userResponse = await getUserInfo();
                sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
            },
            (error) => {
                console.log("로그인 실패!!!!")
                isLogin.value = false
                isLoginError.value = true
                isValidToken.value = false
                console.error(error)
            }
        )
    }

    // 유저 정보 가져오기
    const getUserInfo = async (token) => {
        await findByUsername(
            (response) => {
                console.log(response.data)
                if (response.status === httpStatusCode.OK) {
                    userInfo.value = response.data;
                    console.log("유저 정보 저장 완료", userInfo)
                } else {
                    console.log("유저 정보 없음!!!!")
                }
            },
            async (error) => {
                console.error(
                    "g[토큰 만료되어 사용 불가능.] : ",
                    error.response.status,
                    error.response.statusText
                )
                isValidToken.value = false

                await tokenRegenerate()
            }
        )
    }

    const tokenRegenerate = async () => {
        await tokenRegeneration(
            JSON.stringify(userInfo.value),
            (response) => {
                if (response.status === httpStatusCode.CREATE) {
                    let accessToken = response.data["access-token"]
                    sessionStorage.setItem("accessToken", accessToken)
                    isValidToken.value = true
                }
            },
            async (error) => {
                // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
                if (error.response.status === httpStatusCode.UNAUTHORIZED) {
                    // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
                    await logout(
                        userInfo.value.userid,
                        (response) => {
                            if (response.status === httpStatusCode.OK) {
                                console.log("리프레시 토큰 제거 성공")
                            } else {
                                console.log("리프레시 토큰 제거 실패")
                            }
                            alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.")
                            isLogin.value = false
                            userInfo.value = null
                            isValidToken.value = false
                            router.push({name: "user-login"})
                        },
                        (error) => {
                            console.error(error)
                            isLogin.value = false
                            userInfo.value = null
                        }
                    )
                }
            }
        )
    }

    const userLogout = async () => {
        console.log("로그아웃 함수 호출");
        await logout(
            (response) => {
                console.log("로그아웃 응답:", response);
                if (response.status === httpStatusCode.OK) {
                    sessionStorage.removeItem("accessToken")
                    sessionStorage.removeItem("userInfo")
                    console.log("accessToken 삭제")
                    isLogin.value = false
                    isValidToken.value = false
                    router.push({name: "login"});
                    userInfo.value = ''
                } else {
                    console.error("유저 정보 없음!!!!")
                }
            },
            (error) => {
                console.error("로그아웃 에러:", error);
                console.log(error)
            }
        )
    };

    // 유저 회원가입
    const userSignup = async (signupUser) => {
        console.log(signupUser.username)
        console.log(signupUser.password)
        await signup(
            signupUser,
            (response) => {
                if (response.status === httpStatusCode.CREATE) {
                    console.log("signup success")
                } else {
                    console.error("signup failed");
                }
            },
            (error) => {
                console.error(error);
            }
        );
    };

    return {
        isLogin,
        isLoginError,
        userInfo,
        isValidToken,
        initializeStore,
        userLogin,
        getUserInfo,
        tokenRegenerate,
        userLogout,
        userSignup
    }
})
