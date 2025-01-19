import axios from "axios";

const saveToken = (accessToken) => {
  if (accessToken) {
    localStorage.setItem("accessToken", accessToken);

    axios.defaults.headers.Authorization = `Bearer ${accessToken}`;
  } else {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");

    delete axios.defaults.headers.Authorization;
  }
};

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    "Content-Type": "application/json",
    Authorization: localStorage.getItem("accessToken")
      ? `Bearer ${localStorage.getItem("accessToken")}`
      : null,
  },
});

axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  async (err) => {
    const originalConfig = err.config;

    if (originalConfig.url !== "/auth/login" && err.response) {
      if (err.response.status === 401 && !originalConfig._retry) {
        originalConfig._retry = true;
        saveToken();

        window.location.href = "/auth/login";
      }
    }

    return Promise.reject(
      err.response || {
        status: 500,
        data: {
          status: "fail",
          message: "Something went wrong",
        },
      }
    );
  }
);

export default axiosInstance;
