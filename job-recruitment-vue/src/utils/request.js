import axios from "axios";
import nProgress from 'nprogress';
import { Notification } from "element-ui";
//引入进度条样式
import 'nprogress/nprogress.css'
import store from "@/store";
import router from "@/router";
// import { getToken } from "./token";
import { getToken } from "./auth";
const service = axios.create({
  baseURL: '/api',
  timeout: 5000,
});

//请求拦截器
service.interceptors.request.use(config => {
  //进度条开始
  nProgress.start();
  //以后加token
  if (store.state.user.token || getToken()) {
    config.headers.token = store.state.user.token || getToken();
  }
  return config
}, error => {
  Promise.reject(error)
})

//响应拦截器
service.interceptors.response.use(response => {
  if (response.status === 200) {
    nProgress.done();
    return Promise.resolve(response)
  } else {
    return Promise.reject(response)
  }
}, error => {
  if (error.response) {
    switch (error.response.status) {
      // 401: 未登录
      case 401:
        Notification.error('未授权，请重新登录');
        router.push("/login")
        break
      // 403： 登录过期
      case 403:
        Notification.error('登录过期，请重新登录');
        router.push("/login")
        break
      // 404: 请求不存在
      case 404:
        Notification.error('网络请求不存在');
        break
      // 500: 服务错误
      case 408:
        Notification.error = ('请求超时')
        break;
      case 500:
        Notification.error('网络请求有误');
        break
      case 501:
        Notification.error = ('网络未实现')
        break;
      case 502:
        Notification.error = ('网络错误')
        break;
      case 503:
        Notification.error = ('服务不可用')
        break;
      case 504:
        Notification.error = ('网络超时')
        break;
      case 505:
        Notification.error = ('http版本不支持该请求')
        break;
      default:
        Notification.error('服务错误');
    }
    //return Promise.reject('服务错误')
  } else {
    // 配置服务器没有返回结果情况
    if (!window.navigator.onLine) {
      // 断网情况，进行断网处理
      return
    }
    return Promise.reject(error)
  }
})

export default service;