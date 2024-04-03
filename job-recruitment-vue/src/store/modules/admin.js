import { adminLogin, getAdminInfo } from '@/api/modules/admin'
import { getToken, setToken, removeToken } from '@/utils/auth'

const actions = {
  async adminlogin({ commit }, userInfo) {
    // const { phone, password } = userInfo
    console.log(userInfo, 'info')
    const result = await adminLogin(userInfo)
    if (result.data.code === 0) {
      console.log(result.data, 'result')
      commit('SET_TOKEN', result.data.data)
      setToken(result.data.data)
    } else return Promise.reject(new Error(result.data.message))
  },

  getInfo({ commit }) {
    return new Promise((resolve, reject) => {
      getAdminInfo().then(response => {
        const { data } = response
        if (!data) {
          return reject('Verification failed, please Login again.')
        }
        const { name, avatar } = data.data
        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  logout({ commit }) {
    removeToken() // must remove  token  first
    // resetRouter()
    commit('RESET_STATE')
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}



const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  }
}
const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: ''
  }
}

const state = {
  token: getToken(),
  name: '',
  avatar: ''
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}