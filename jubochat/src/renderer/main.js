import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'

import './assets/font-awesome-4.7.0/css/font-awesome.min.css'
// import './mock'
import '@/icons'
import * as socketApi from './api/socket'
import base64 from '@/utils/base64.js'

Vue.prototype.socketApi = socketApi

if (!process.env.IS_WEB) Vue.use(require('vue-electron'))
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  components: { App },
  router,
  store,
  base64,
  template: '<App/>'
}).$mount('#app')
