import Vue from "vue";
import Vuex from "vuex";
import user from "./modules/user";
import admin from "./modules/admin";
Vue.use(Vuex);
const store = new Vuex.Store({
  modules: {
    user,
    admin,
  }
});

export default store;