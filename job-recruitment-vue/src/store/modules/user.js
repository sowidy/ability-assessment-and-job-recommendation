import { getIdentity, getToken, removeToken, setToken, setIdentity, removeIdentity } from "@/utils/token";
import { reUserLogin, reGetUserInfo } from '@/api/modules/user'
const actions = {
  async userLogin(context, { userForm, identity }) {
    let result = await reUserLogin(userForm, identity);
    if (result.data.code == 0) {
      const { data } = result
      // console.log(data);
      context.commit('SET_USERIDENTITY', identity);
      context.commit('USERLOGIN', data);
    }
    else return Promise.reject(new Error(result.data.message))
  },
  async getUserInfo(context, { identity }) {
    // console.log(identity, 'identity');
    let result = await reGetUserInfo(identity);
    // console.log(result,'userinfo');
    if (result.data.code == 0) {
      const { data } = result
      // console.log(data.data, 'info');
      context.commit('SETUSERINFO', data.data);
    }
    else return Promise.reject(new Error(result.data.message))
  },
}
const mutations = {
  USERLOGIN(state, data) {
    state.token = data.data;
    console.log(state.token,'statetoken');
    setToken(data.data);
  },
  SETUSERINFO(state, userInfo) {
    state.userInfo = userInfo;
  },
  SET_USERIDENTITY(state, identity) {
    state.identity = identity
    setIdentity(identity);
  },
  USER_LOGOUT(state){
    removeToken();
    removeIdentity();
    state.identity = undefined;
    state.userInfo = '';
  }
}

const state = {
  userInfo: '',
  token: getToken() || '',
  identity: getIdentity() || '',
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
