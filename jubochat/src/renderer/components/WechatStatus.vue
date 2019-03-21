<template>
  <div class="wechatStatus">
    <form @submit.prevent="submit" novalidate="true">
      <p class="p1">
        请选择微信登陆
        <i class="fa fa-times-circle" aria-hidden="true" @click="close"></i>
      </p>
      <ul v-if="wechats.length > 0" class="content">
        <li class="m-title">
          <div class="width1">状态</div>
          <div class="width3">登陆设备</div>
          <div class="width">头像</div>
          <div class="width3">微信号</div>
          <div class="width2">昵称</div>
          <div class="width3">地区</div>
          <div class="width">性别</div>
          <div class="width2">选择</div>
        </li>
        <li
          class="m-title2"
          :class="{bc2:index%2===0,bc:index%2===1}"
          v-for="(wechat, index) in wechats"
          :key="index"
        >
          <div class="width1">
            <p v-if="wechat.IsLogined && wechat.IsOnline" style="color:blue;">已登陆</p>
            <p v-if="!wechat.IsLogined && wechat.IsOnline" style="color:green;">可登陆</p>
            <p v-if="!wechat.IsOnline" style="color:red;">wx下线</p>
          </div>
          <div class="width3" v-text="wechat.DeviceName"></div>
          <div class="width">
            <img style="width:20px;height:20px;" :src="wechat.Avatar" alt="头像">
          </div>
          <div class="width3" v-text="wechat.WeChatNo"></div>
          <div class="width2" v-text="wechat.WeChatNick"></div>
          <div class="width3">
            <span v-if="wechat.Country" v-text="wechat.Country"></span>
            <span v-if="wechat.Province" v-text="wechat.Province"></span>
            <span v-if="wechat.City" v-text="wechat.City"></span>
          </div>
          <div class="width">
            <span v-if="wechat.Gender==='Female'">女</span>
            <span v-else-if="wechat.Gender==='Male'">男</span>
            <span v-else>--</span>
          </div>
          <input v-if="wechat.IsOnline" class="pointer width2 pointer" type="checkbox" :id="index">
          <div v-else class="pointer width2 pointer" :id="index"></div>
        </li>
      </ul>
      <p v-else class="content flex-center">该账号没有绑定微信</p>
      <div class="tips">第一次使用时，加载好友列表要写入数据，需要较长时间，请耐心等候！</div>
      <div class="lastDiv">
        <div>
          <input
            v-show="!allChecked"
            type="button"
            class="allcheck"
            value="全选"
            @click="allCheck(true)"
          >
          <input
            v-show="allChecked"
            type="button"
            class="allcheck"
            value="全不选"
            @click="allCheck(false)"
          >
          <input type="submit" value="提交">
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import { setTimeout } from 'timers'
// import base64 from '@/utils/base64.js'
export default {
  data () {
    return {
      allChecked: false
    }
  },
  computed: {
    ...mapGetters({
      wechats: 'wechats',
      wechatsLogin: 'wechatsLogin',
      user: 'user',
      friendsNumber: 'friendsNumber'
    })
  },
  methods: {
    ...mapActions({
      SetFriendsNumber: 'SetFriendsNumber'
    }),
    close () {
      this.$router.replace({ path: '/main/' })
    },
    allCheck (flag) {
      for (let index = 0; index < this.wechats.length; index++) {
        console.log(this.wechats[index])
        if (this.wechats[index].IsOnline) {
          document.getElementById(index).checked = flag
        }
      }
      this.allChecked = flag
    },
    /** 选择微信登陆 */
    submit () {
      let wechatsList = []
      for (let index = 0; index < this.wechats.length; index++) {
        let flag = document.getElementById(index).checked
        if (flag) {
          wechatsList.push(this.wechats[index])
        }
      }
      let content = {
        SupplierId: this.user.SupplierId,
        UnionId: this.user.UnionId,
        AccountType: this.user.AccountType,
        WeChats: []
      }
      let wechatsInfo = []
      if (wechatsList.length > 0) {
        for (const wechat of wechatsList) {
          let wechatInfo = {
            WeChatId: wechat.WeChatId,
            IsLogin: true
          }
          wechatsInfo.push(wechatInfo)
        }
        content.WeChats = wechatsInfo
      } else {
        for (const wechat of this.wechats) {
          let wechatInfo = {
            WeChatId: wechat.WeChatId,
            IsLogin: false
          }
          wechatsInfo.push(wechatInfo)
        }
        content.WeChats = wechatsInfo
      }
      let msg = { 'MsgType': 'WeChatLoginNotice', 'Content': content }
      this.socketApi.sendSock(msg)
      // console.log('上传要登陆的微信：WeChatLoginNotice')
      // console.log(msg)
      this.getWechatsList()
      setTimeout(() => {
        this.$router.push('/main/')
      }, 100)
    },

    /** 获取微信列表 */
    getWechatsList () {
      let accountType = this.$store.getters.user.AccountType
      let unionId = Number(this.$store.getters.user.UnionId)
      let msg = { MsgType: 'GetWeChatsReq', Content: { AccountType: accountType, UnionId: unionId } }
      console.log('获取微信列表:GetWeChatsReq')
      this.socketApi.sendSock(msg)
    },

    /** 获取好友列表 */
    getFriendsList () {
      if (this.wechatsLogin.length > 0) {
        for (const wechat of this.wechatsLogin) {
          let wechatId = wechat.WeChatId
          this.SetFriendsNumber(wechatId)
          let msg = { MsgType: 'TriggerFriendPushTask', Content: { WeChatId: wechatId } }
          console.log('获取好友列表：TriggerFriendPushTask')
          this.socketApi.sendSock(msg)
        }
      }
    }
  },
  created () {
    this.getWechatsList()
  }
}
</script>

<style lang="scss" scoped>
.wechatStatus {
  position: absolute;
  z-index: 2;
  width: 100%;
  height: 100%;
  background: rgba($color: #000000, $alpha: 0.5);
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}
form {
  width: 800px;
  height: 400px;
  margin: auto;
  position: relative;
  background: #f0f0f0;
  border-radius: 10px;
}
// 设置手机
.p1 {
  position: relative;
  height: 30px;
  line-height: 30px;
  color: white;
  font-size: 18px;
  text-align: center;
  border-bottom: rgb(231, 222, 222) 2px solid;
  background-color: #282b30;
  border-radius: 10px 10px 0 0;
  i {
    color: white;
    position: absolute;
    top: 5px;
    right: 5px;
    &:hover {
      color: #09bffe;
    }
  }
}
// ul title
ul,
.content {
  height: 320px;
  font-size: 16px;
  overflow-y: auto;
  overflow-x: hidden;

  .m-title {
    width: 800px;
    border-bottom: black solid 1px;
    display: flex;
    justify-content: space-around;
    div {
      display: flex;
      justify-content: center;
      align-items: center;
      // border: 1px solid red;
    }
  }

  .m-title2 {
    display: flex;
    justify-content: space-around;
    align-items: center;
    div {
      display: flex;
      justify-content: center;
      align-items: center;
      // border: 1px solid red;
    }
  }
  .bc {
    background-color: white;
    text-align: center;
  }
  .bc2 {
    background-color: gray;
    text-align: center;
  }
  .width {
    width: 40px;
  }
  .width1 {
    width: 60px;
  }
  .width2 {
    width: 80px;
  }
  .width3 {
    width: 160px;
  }
}

.tips {
  color: red;
  font-weight: 600;
  height: 20px;
  line-height: 20px;
  font-size: 16px;
  text-align: center;
}

.lastDiv {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 30px;
  background: #282b30;
  border-radius: 0 0 10px 10px;
  div {
    width: 300px;
    height: 30px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    // border: red 1px solid;
    input {
      width: 100px;
      height: 20px;
      font-size: 14px;
      line-height: 20px;
      border-radius: 10px;
      color: white;
      cursor: pointer;
      background: gray;
      &:hover {
        color: #09bffe;
      }
    }
  }
}
</style>
