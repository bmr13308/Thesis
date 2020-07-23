import axios from 'axios';
import { API } from './index';
import { store } from '../store/store';

const instance = axios.create({
  baseURL: `http://${API}`,
});

instance.interceptors.request.use((config) => {
  const mutatedConfig = { ...config };
  const { token } = store.getState();
  if (token) {
    mutatedConfig.headers.Authorization = `Bearer ${token}`;
  }
  return mutatedConfig;
});

export default instance;
