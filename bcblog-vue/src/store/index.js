import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isLogin:false,
    user:{
      email:"",
      token:"",
    },
    filterPattern:""
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.user.token = token;
    },
    SET_EMAIL(state, email){
      state.user.email = email;
    },
    LOGIN(state){
      state.isLogin = true;
    },
    QUIT(state){
      state.isLogin = false;
      localStorage.clear();
    },
    SET_FILTER(state, filter) {
      localStorage.setItem("filterPattern", filter);
      state.filterPattern = filter;
    },
    DEFAULT_FILTER(state) {
      localStorage.setItem("filterPattern", "1");
      state.filterPattern = "1";
    }
  },
  actions: {
  },
  modules: {
  }
})
