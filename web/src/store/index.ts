import { createStore } from 'vuex'
declare let SessionStorage:any;

const USER = 'USER';
const store  = createStore({
  state: {
    user:SessionStorage.get(USER) || {},
  },
  mutations: {
    SET_USER(state,user){
      state.user = user;
      SessionStorage.set(USER,user);
    }
  },
  actions: {
  },
  modules: {
  }
});
export default store;
