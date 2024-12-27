import axios from "axios";

const backendURL = process.env.VUE_APP_BACKEND_URL;
const api = axios.create({
  baseURL: backendURL,
});

export default api;
