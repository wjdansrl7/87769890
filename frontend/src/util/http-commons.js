import axios from "axios";

const { VITE_VUE_API_URL} = import.meta.env;

function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    withCredentials: true,
  });

// 요청 인터셉터
  instance.interceptors.request.use(
      (config) => {
        const accessToken = sessionStorage.getItem("accessToken");
        if (accessToken) {
          config.headers["Authorization"] = `Bearer ${accessToken}`;
        }
        return config;
      },
      (error) => {
        console.error("get information from interceptors ", response)
        return Promise.reject(error);
      }
  );


// 응답 인터셉터
  instance.interceptors.response.use(
      (response) => {
        return response;
      },
      async (error) => {
        const originalRequest = error.config;
        if (error.response?.status === 401 && error.response?.data === 'AccessTokenExpired' && !originalRequest._retry) {
          originalRequest._retry = true;
          try {
            // 리프레시 토큰 다시 요청
            // 리프레시 토큰으로 검증하므로 accessToken 불필요, 이후 삭제 예상
            const refreshToken = sessionStorage.getItem("refreshToken");
            // TODO: axios 관리 필요
            const response = await axios.get(`${VITE_VUE_API_URL}/auth/refresh`, {
              withCredentials: true,
              headers: {
                Authorization: refreshToken ? `Bearer ${refreshToken}` : ''
              }
            });
            // response.data가 객체인지 확인
            const newAccessToken = response.headers['authorization'];
            if (newAccessToken && newAccessToken.startsWith('Bearer ')) {
              const token = newAccessToken.split(' ')[1];
              // 새 토큰 저장
              sessionStorage.setItem('accessToken', token);
              // Axios 인스턴스의 기본 헤더 업데이트
              instance.defaults.headers.common['Authorization'] = newAccessToken;
              // 원래 요청 재시도
              originalRequest.headers['Authorization'] = newAccessToken;
              return instance(originalRequest);
            } else {
              console.error("서버 응답이 예상된 형식이 아닙니다:", response.data);
            }

            throw new Error("유효한 새 액세스 토큰을 받지 못했습니다.");
          } catch (refreshError) {
            console.error("토큰 갱신 실패:", refreshError);
            // 로그아웃 처리
            // await store.dispatch("user/userLogout");
            // 로그인 페이지로 리다이렉트
            // router.push('/login');
            return Promise.reject(refreshError);
          }
        }
        // 다른 에러 처리
        return Promise.reject(error);
      }
  );
  return instance;
}

export { localAxios };

