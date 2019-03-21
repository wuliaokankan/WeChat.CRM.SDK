<template>
    <div class="flex-container">
        <form @submit.prevent="submit">
            <div id="welcome">欢迎使用聚播微信客服系统</div>
            <div class="username">
                <i class="fa fa-user-circle-o " aria-hidden="true"> 用 户 名
                    <input class="input1" type="text" name="" id="" placeholder="用户名" maxlength="20" v-model="user.name" autofocus><br />
                </i>
            </div>
            <div class="password">
                <i class="fa fa-key" aria-hidden="true"> 密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码
                    <input class="input1" type="password" name="" id="" placeholder="密码" maxlength="20" v-model="user.password"><br />
                </i>
            </div>
            <div class="moblieNumber">
                <i class="fa fa-mobile fa-2x" aria-hidden="true"> 手机号码
                    <input class="input1" type="tel" name="" id="" placeholder="手机号码" v-model="user.moblieNumber" @click="showCode" maxlength="11"><br />
                </i>
                <div v-show="isFoucus" id="vcd">
                    <input id="verificationCode" type="tel" minlength="4" maxlength="4" placeholder="验证码" v-model="verificationCode">
                    <span id="sendCode">发送短信验证码</span>
                </div>
            </div>
            <input class="submit" id="submitInput" type="submit" value="立即注册" @mouseover="addClass('submitInput')" @mouseout="deleteClass('submitInput')">
        </form>
    </div>
</template>

<script>
export default {
  data () {
    return {
      isFoucus: false,
      verificationCode: '',
      user: {
        name: '',
        password: '',
        moblieNumber: ''
      }
    }
  },
  methods: {
    addClass (val) {
      document.getElementById(val).style.color = 'gray'
    },
    deleteClass (val) {
      document.getElementById(val).style.color = ''
    },
    showCode () {
      this.isFoucus = true
    },
    submit () {
      if (this.user.namename === '' || this.user.password === '') {
        alert('请填写完整')
      } else {
        var formData = JSON.stringify(this.user)
        this.$http.post('/api/alluser', formData).then(function (data) {
          if (data.json().state === 'success') { console.log('success') }
        }).catch(function () {
          console.log('服务器异常')
          alert('用户名或密码错误')
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../styles/globals.scss';
.flex-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
form {
  margin: auto;
  width: 300px;
  height: 250px;
}
#welcome {
  height: 40px;
  width: 280px;
  margin-left: 10px;
  color: white;
  font-size: 18px;
  text-align: center;
  padding-top: 11px;

  border-bottom: 2px solid white;
}
.username,
.password,
.moblieNumber {
  width: 260px;
  margin-top: 20px;
  margin-left: 20px;
}

#vcd {
  margin-top: 10px;
}
#sendCode {
  color: white;
  background-color: #46b6ef;
}

i {
  width: 260px;
  font-size: 16px;
  //   text-align: center;
//   vertical-align: auto;
  color: white;
  border: #09bffe 1px solid;
}
.input1 {
  width: 160px;
  height: 20px;
}

.submit {
  height: 32px;
  width: 200px;
  font-size: 16px;
  margin-left: 50px;
  margin-top: 20px;
  background-color: #09bffe;
  border-radius: 5px;
  color: white;
}
</style>

