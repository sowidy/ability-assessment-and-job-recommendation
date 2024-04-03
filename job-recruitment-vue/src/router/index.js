import VueRouter from "vue-router";
import Vue from "vue";
import { Notification } from 'element-ui'
import store from "@/store";
import { getToken } from '@/utils/auth' // get token from cookie
Vue.use(VueRouter);

const routers = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('@/view/Home'),
  },
  {
    path: '/recommend',
    name: 'recommend',
    component: () => import('@/view/Recommend')
  },
  {
    path: '/search',
    name: 'search',
    component: () => import('@/view/Search')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/view/Login')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/view/Register')
  },
  {
    path: '/help',
    name: 'help',
    redirect:'/help/student',
    component: () => import('@/view/Help'),
    children:[
      {
        path:'student',
        name:'Student',
        component:()=>import('@/view/Help/Student')
      },
      {
        path:'enterprise',
        name:'Enterprise',
        component:()=>import('@/view/Help/Enterprise')
      },
    ]
  },
  {
    path: '/evaluation-recommendations',
    name: 'evaluation-recommendations',
    component: () => import('@/view/Evaluation-Recommendations')
  },
  {
    path: '/userinfo',
    name: 'UserInfo',
    redirect: '/userinfo/detail',
    component: () => import('@/view/UserInfo'),
    children: [
      {
        path: 'detail',
        name: 'Detail',
        component: () => import('@/view/UserInfo/PersonalDetails')
      },
      {
        path: 'resume',
        name: 'Resume',
        component: () => import('@/view/UserInfo/ResumeEdit')
      },
      {
        path: 'setting',
        name: 'UserSetting',
        component: () => import('@/view/setting')
      }
    ]
  },
  {
    path: '/enterprise',
    redirect: '/enterprise/detail',
    name: 'EnterPrise',
    component: () => import('@/view/Enterprise'),
    children: [
      {
        path: 'detail',
        name: 'Detail',
        component: () => import('@/view//Enterprise/Detail')
      },
      {
        path: 'titles',
        name: 'Titles',
        component: () => import('@/view//Enterprise/Title')
      },
      {
        path: 'setting',
        name: 'EnSeeting',
        component: () => import('@/view/setting')
      }
    ]
  },
  {
    path: '/job',
    name: 'SingleJob',
    component: () => import("@/view/SingleJob"),
  },
  {
    path: '/student',
    name: 'SingleStudent',
    component: () => import("@/view/SingleStudent"),
  },
  {
    path: '/admin',
    name: 'admin',
    redirect: '/admin/dashboard',
    component: () => import("@/admin/layout"),
    children: [
      {
        path: 'manage',
        name: 'manage',
        component: () => import('@/admin/manage'),
        children: [
          {
            path: 'student',
            name: 'student',
            component: () => import('@/admin/manage/student.vue')
          },
          {
            path: 'enterprise',
            name: 'enterprise',
            component: () => import('@/admin/manage/enterprise.vue')
          }
        ]
      },
      {
        path: 'application',
        name: 'application',
        component: () => import('@/admin/application')
      },
      {
        path: 'remark',
        name: 'remark',
        component: () => import('@/admin/remark')
      },
      {
        path: 'dashboard',
        name: 'dashboard',
        component: () => import('@/admin/dashboard')
      }
    ]
  },
  {
    path: '/admin/login',
    name: 'adminLogin',
    component: () => import("@/admin/login"),
  }
]

const router = new VueRouter({
  routes: routers,
  scrollBehavior(to, from, savedPosition) {
    return new Promise((resolve, reject) => {
      if (savedPosition) {
        resolve(savedPosition);
      } else {
        resolve({ x: 0, y: 0 });
      }
    });
  },
});
// //先把VueRouter.prototype身上的push|replace方法进行保存一份
let originPush = VueRouter.prototype.push;
let originReplace = VueRouter.prototype.replace;
// //重写VueRouter.prototype身上的push方法了
VueRouter.prototype.push = function (location, resolve, reject) {
  //第一个形参：路由跳转的配置对象（query|params）
  //第二个参数：undefined|箭头函数（成功的回调）
  //第三个参数:undefined|箭头函数（失败的回调）
  if (resolve && reject) {
    //push方法传递第二个参数|第三个参数（箭头函数）
    //originPush：利用call修改上下文，变为(路由组件.$router)这个对象，第二参数：配置对象、第三、第四个参数：成功和失败回调函数
    originPush.call(this, location, resolve, reject);
  } else {
    //push方法没有产地第二个参数|第三个参数
    originPush.call(
      this,
      location,
      () => { },
      () => { }
    );
  }
};
//重写VueRouter.prototype身上的replace方法了
VueRouter.prototype.replace = function (location, resolve, reject) {
  if (resolve && reject) {
    originReplace.call(this, location, resolve, reject);
  } else {
    originReplace.call(
      this,
      location,
      () => { },
      () => { }
    );
  }
};
const whiteList = ['/admin/login', '/login', '/register', '/home', '/help/student','/help/enterprise'];//'/userinfo', '/enterprise', '/recommend'
//全局路由首位
router.beforeEach(async (to, from, next) => {
  //获取仓库中的token-----可以确定用户是登录了

  const hasToken = getToken()
  let token = localStorage.getItem('token');
  let Info = store.state.user.userInfo;
  const hasGetUserInfo = store.state.admin.name
  let identity = store.state.user.identity;
  if (token) {
    if (to.path == "/login" || to.path == '/register') {
      next('/');
    } else {
      if (Info) {
        next();
      } else {
        try {
          await store.dispatch('user/getUserInfo', { identity });
          next();
        } catch (error) {
          Notification.error('登录失效，请重新登录')
          store.commit('user/USER_LOGOUT');
          next('/login');
        }
      }
    }
  } else if (hasToken) {
    if (to.path == '/admin/login') {
      next('/admin')
    } else {
      if (hasGetUserInfo) {
        next()
      } else {
        try {
          await store.dispatch('admin/getInfo');
          next();
        } catch (error) {
          Notification.error('登录失效，请重新登录');
          store.dispatch('admin/resetToken');
          next('/admin/login');
        }
      }
    }
  } else {
    let toPath = to.path
    if (whiteList.includes(toPath)) next();
    else {
      Notification.error('请您先登录');
      next(toPath.includes('admin') ? '/admin/login' : '/login');
    }
  }
})
export default router;