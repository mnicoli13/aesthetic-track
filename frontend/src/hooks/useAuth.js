import { create } from "zustand";
// utils
import axios from "../utils/axios";
import { isValidToken, setSession, getTokenData } from "../utils/jwt";
import { data } from "react-router-dom";

const token = localStorage.getItem("accessToken");

const initialState = {
  isAuthenticated: isValidToken(token),
  user: isValidToken(token) ? getTokenData(token) : {},
  method: "jwt",
};

const register = async (email, password) => {
  const res = await axios.post("/auth/register/medic", {
    email,
    password,
  });

  const { user, accessToken } = res.data;
  console.log("data", data);
  setSession(accessToken, user);

  return { user, accessToken };
};

const login = async (email, password) => {
  const res = await axios.post("/auth/login", {
    email,
    password,
  });

  const { user, accessToken } = res.data;
  setSession(accessToken, user);

  return { user, accessToken };
};

const logout = async () => {
  const response = await axios.post("/auth/logout");
  setSession(null);

  return response;
};

const profile = async () => {
  const res = await axios.post("/auth/profile");

  const { user } = res.data;

  return { user };
};

const useAuth = create((set) => ({
  ...initialState,
  register: async (email, password) => {
    const { accessToken } = await register(email, password);
    set({
      isAuthenticated: isValidToken(accessToken),
      user: getTokenData(accessToken),
    });
  },
  profile: async () => {
    const { user } = await profile();
    return { user };
  },
  login: async (email, password) => {
    const { accessToken } = await login(email, password);
    set({
      isAuthenticated: isValidToken(accessToken),
      user: getTokenData(accessToken),
    });
  },
  logout: async () => {
    await logout();
    set({
      isAuthenticated: false,
      user: null,
    });
  },
}));

export default useAuth;
