/* eslint-disable no-unused-vars */
/* eslint-disable no-param-reassign */
import { createStore, persist, action } from 'easy-peasy';

const model = {
  token: null,
  setToken: action((state, token) => {
    state.token = token;
  }),
  reset: action((state) => {
    state.token = null;
  }),
};

export const store = createStore(
  persist(model, {
    whitelist: ['token'],
    storage: 'localStorage',
  })
);
