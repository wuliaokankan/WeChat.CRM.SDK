<template>
  <form class="login" @submit.prevent="submit" novalidate="true">
    <div id="welcome">欢迎使用聚播微信客服系统</div>
    <!-- 用户名 -->
    <div class="username">
      <icon-font height="24" width="24" icon-color="white">
        <icon-user></icon-user>
      </icon-font>
      <input
        type="text"
        name
        id="username"
        placeholder="用户名"
        maxlength="20"
        v-model="username"
        autofocus
        required
      >
    </div>
    <!-- 密码 -->
    <div class="password">
      <icon-font height="24" width="24" icon-color="white">
        <icon-password></icon-password>
      </icon-font>
      <input
        type="password"
        name
        id="password"
        placeholder="密码"
        maxlength="20"
        v-model="password"
        required
      >
    </div>
    <!-- errorMsg -->
    <div style="height:20px;">
      <div v-if="errors" v-text="errors" id="errorMsg"></div>
    </div>
    <!-- 记住密码 -->
    <div class="remember flex-center">
      <div class="rememberPassword">
        <input type="checkbox" name id="rememberPassword" v-model="checked">
        <label class="labe1" for="rememberPassword" id="label1">记住密码</label>
      </div>
      <div class="forgetPassword pointer hover">忘记密码</div>
    </div>
    <!-- 登陆 -->
    <input class="submit" id="submitInput" type="submit" value="登陆">
  </form>
</template>

<script>
import { validName, validPassword } from '@/utils/valid.js'
import { mapGetters, mapActions } from 'vuex'
import { setTimeout } from 'timers'
import base64 from '@/utils/base64.js'

export default {
  data () {
    return {
      username: '',
      password: '',
      checked: false
    }
  },
  computed: {
    ...mapGetters({
      errors: 'error',
      isLogin: 'isLogin'
    })
  },
  created () {
    let userinfo = localStorage.getItem('userinfo')
    if (userinfo) {
      userinfo = JSON.parse(localStorage.getItem('userinfo'))
      this.username = userinfo.username
      this.password = userinfo.password
      this.checked = true
    }
  },
  methods: {
    ...mapActions({
      setError: 'SetError'
    }),
    submit () {
      this.setError('')
      var _this = this
      if (!validName(_this.username) || !validPassword(_this.password)) {
        this.setError('用户名和密码不能以_开头和结尾，不能含有特殊符号，密码为6-20位')
      } else {
        let content = base64.b64EncodeUnicode(this.username + ':' + this.password)
        let msg = { 'MsgType': 'DeviceAuthReq', 'Content': { 'AuthType': 2, 'Credential': content } }
        console.log('发出登陆请求：DeviceAuthReq')
        console.log(msg)
        this.socketApi.sendSock(msg)
        setTimeout(() => {
          if (_this.isLogin) {
            if (document.getElementById('rememberPassword').checked) {
              let userinfo = { username: _this.username, password: _this.password, checked: true }
              localStorage.setItem('userinfo', JSON.stringify(userinfo))
            } else {
              localStorage.removeItem('userinfo')
              localStorage.removeItem('rememberPassword')
            }
            console.log('用户名密码正确，跳转到选择微信页面')
            this.$router.push({ path: '/wechat/status' }) // 登录成功之后重定向到选择微信登陆页面
          } else {
            console.log('登陆失败')
          }
        }, 1000)
      }
    }
  }
}
</script>


<style lang="scss" scoped>
@import "../../styles/globals.scss";
.login {
  width: 500px;
  height: 320px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
#welcome {
  height: 40px;
  width: 480px;
  color: white;
  font-size: 20px;
  text-align: center;
  line-height: 40px;
  border-bottom: 2px solid white;
}

.username,
.password {
  width: 250px;
  height: 40px;
  margin-top: 25px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

input {
  font-size: 18px;
  padding: 2px;
}

.remember {
  width: 250px;
  height: 40px;
  justify-content: space-around;
}

.rememberPassword {
  height: 30px;
  text-align: center;
  line-height: 30px;
}

#errorMsg {
  color: red;
  font-size: 16px;
  text-align: center;
}

#label1 {
  color: white;
  cursor: pointer;
  font-size: 14px;
}

#label1:hover {
  color: gray;
}

.forgetPassword {
  color: white;
  font-size: 14px;
}
.forgetPassword:hover {
  color: gray;
}

.submit {
  height: 32px;
  width: 200px;
  font-size: 16px;
  margin-top: 20px;
  background-color: #09bffe;
  border-radius: 5px;
  color: white;
  cursor: pointer;
}
.submit:hover {
  color: gray;
}
</style>