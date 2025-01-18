import { jwtDecode } from "jwt-decode";
// utils
import axios from "./axios";

const isValidToken = (accessToken) => {
  if (!accessToken) {
    return false;
  }

  const decoded = jwtDecode(accessToken);
  const currentTime = Date.now() / 1000;

  return decoded.exp > currentTime;
};

const getTokenData = (accessToken, key = null) => {
  if (!accessToken) {
    return {};
  }

  const data = jwtDecode(accessToken);
  if (key) {
    return data?.[key];
  }

  return data;
};

const setSession = async (accessToken, userId) => {
  if (accessToken) {
    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("userId", userId);
    // axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
    axios.defaults.headers.Authorization = `Bearer ${accessToken}`;
  } else {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("userId");
    // delete axios.defaults.headers.common.Authorization;
    delete axios.defaults.headers.Authorization;
  }
};

export { isValidToken, setSession, getTokenData };
