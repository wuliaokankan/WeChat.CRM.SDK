import Vue from 'vue'
import Router from 'vue-router'
import store from '../store/'
import * as types from '../store/mutation-types'

import Main from '@/views/Main'
import Login from '@/views/Login'
import AddMobile from '@/components/conversation/wechats/AddMobile'
import RemoveMobile from '@/components/conversation/wechats/RemoveMobile'
import WechatStatus from '@/components/conversation/wechats/WechatStatus'

Vue.use(Router)

const routes = [
  {
    path: '/',
    name: '/',
    children: [
      { path: '/mobile/add', name: 'addMobile', component: AddMobile },
      { path: '/mobile/remove', name: 'removeMobile', component: RemoveMobile },
      { path: '/wechat/status', name: 'wechatStatus', component: WechatStatus }
    ],
    meta: {
      requireAuth: true
    },
    component: Main
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  }
]

// sessionStorage.clear()
// 页面刷新时，重新赋值token
if (window.sessionStorage.getItem('token')) {
  store.commit(types.SET_TOKEN, window.sessionStorage.getItem('token'))
}

const router = new Router({
  routes
})

router.beforeEach((to, from, next) => {
  // 判断要去的页面要不要鉴权
  if (to.matched.some(r => r.meta.requireAuth)) {
    if (store.state.token) {
      if (store.state.user.length === 0) {
        store.dispatch('GetloginForm').then(res => {
          next()
        })
      } else {
        next()
      }
    } else {
      console.log('没有token，返回登陆')
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    }
  } else {
    next()
  }
})

export default router
