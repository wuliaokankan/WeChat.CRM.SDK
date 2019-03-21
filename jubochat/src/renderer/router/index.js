import Vue from 'vue'
import Router from 'vue-router'
import store from '../store/'
import WechatStatus from '@/components/WechatStatus'
import ReplayDialog from '@/components/conversation/record/Dialog'
import * as types from '../store/mutation-types'

import Main from '@/views/Main'
import Login from '@/views/Login'

Vue.use(Router)

const routes = [
  {
    path: '/',
    name: '/',
    component: Login
  },
  {
    path: '/main',
    name: 'main',
    meta: {
      requireAuth: true
    },
    children: [
      { path: '/wechat/status', name: 'wechatStatus', component: WechatStatus },
      { path: '/record/dialog', name: 'replayDialog', component: ReplayDialog }
    ],
    component: Main
  }
]

// sessionStorage.clear()
// 页面刷新时，重新赋值token
if (window.localStorage.getItem('loginStatus') === 'login') {
  // store.commit(types.SET_TOKEN, window.sessionStorage.getItem('token'))
  store.commit(types.SET_ISLOGIN, true)
}

const router = new Router({
  routes
})

router.beforeEach((to, from, next) => {
  // 判断要去的页面要不要鉴权
  if (to.matched.some(r => r.meta.requireAuth)) {
    if (store.getters.isLogin) {
      next()
      //   if (store.state.user.length === 0) {
      //     store.dispatch('GetloginForm').then(res => {
      //       next()
      //     })
      //   } else {
      //     next()
      //   }
    } else {
      console.log('isLogin false')
      next({
        path: '/',
        query: { redirect: to.fullPath }
      })
    }
  } else {
    next()
  }
})

export default router
