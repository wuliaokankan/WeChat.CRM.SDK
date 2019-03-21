<template>
  <div id="header">
    <!-- logo -->
    <div class="logo" @click="openUrl()">
      <img class="pointer" src="static/img/logo.png" alt="聚播科技">
      <div class="logo-title">
        <div class="p1">聚播科技</div>
        <div class="p2">沟通改变生活</div>
      </div>
    </div>
    <!-- tab -->
    <div class="tabPage">
      <div class="tab" @click="toggleTab(0)" :class="{selected:current===0}">
        <i class="fa fa-wechat" aria-hidden="true"></i>
        <div>会话</div>
        <div v-show="newChatNotice" class="conversation-number"></div>
      </div>
      <div class="tab" :class="{selected:current===1}" @click="toggleTab(1)">
        <i class="fa fa-chrome" aria-hidden="true"></i>
        <div>朋友圈</div>
      </div>

      <div class="tab" :class="{selected:current===3}" @click="toggleTab(3)">
        <i class="fa fa-paper-plane" aria-hidden="true"></i>
        <div>群发助手</div>
      </div>

      <div class="tab" :class="{selected:current===6}" @click="toggleTab(6)">
        <i class="fa fa-cog" aria-hidden="true"></i>
        <div>好友管理</div>
        <div v-show="newFriendRequestNotice" class="conversation-number"></div>
      </div>

      <div class="tab" :class="{selected:current===8}" @click="toggleTab(8)">
        <i class="fa fa-th" aria-hidden="true"></i>
        <div>功能大全</div>
      </div>

      <div class="tab" :class="{selected:current===4}" @click="toggleTab(4)">
        <i class="fa fa-address-card-o" aria-hidden="true"></i>
        <div>客户管理</div>
      </div>
      <div class="tab" :class="{selected:current===2}" @click="toggleTab(2)">
        <i class="fa fa-history" aria-hidden="true"></i>
        <div>历史消息</div>
      </div>
      <div class="tab" :class="{selected:current===5}" @click="toggleTab(5)">
        <i class="fa fa-bar-chart" aria-hidden="true"></i>
        <div>报表管理</div>
      </div>
      <div class="tab" :class="{selected:current===7}" @click="toggleTab(7)">
        <i class="fa fa-question-circle" aria-hidden="true"></i>
        <div>帮助中心</div>
      </div>
    </div>
    <!-- userinfo -->
    <div class="user-info">
      <div></div>
      <p @click="logout" class="pointer" title="logout">
        <i class="fa fa-sign-out" aria-hidden="true"></i>
      </p>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import date from '@/utils/date.js'
import { shell } from 'electron'

export default {
  data () {
    return {
      current: 0,
      startTime: date.getDayTime()
    }
  },
  computed: {
    ...mapGetters({
      user: 'user',
      currentWechat: 'currentWechat',
      wechatsLongin: 'wechatsLogin',
      personalInfo: 'personalInfo',
      newChatNotice: 'chats/newChatNotice',
      newFriendRequestNotice: 'addfriends/newFriendRequestNotice'
    })
  },
  methods: {
    ...mapActions({
      setIsLogin: 'SetIslogin',
      setCircles: 'SetCircles',
      SetNewChatNotice: 'chats/SetNewChatNotice'
    }),
    openUrl () {
      shell.openExternal('http://www.yunlauncher.com')
    },
    /**
     * 每次登陆后，第一次点击朋友圈按钮
     * 先从数据库获取当前微信的朋友圈内容
     * 然后所有已经登陆的微信的朋友圈内容更新
     */
    toFriendCircle () {
      console.log('从数据库读取当前微信的朋友圈信息')
      let info = {
        'wechatId': this.currentWechat.WeChatId
      }
      this.setCircles(info)
      // console.log('获取指定微信ID的最近三天的朋友圈：TriggerCirclePushTask')
      let time = Math.floor(new Date().getTime() / 1000)
      for (const wechat of this.wechatsLongin) {
        let content = {
          WeChatId: wechat.WeChatId,
          StartTime: time
        }
        let msg = { MsgType: 'TriggerCirclePushTask', Content: content }
        console.log(msg)
        this.socketApi.sendSock(msg)
      }
    },
    toggleTab (tag) {
      this.current = tag
      switch (tag) {
        case 0:
          this.$store.dispatch('SetFlags', 'conversation')
          this.$store.dispatch('SetFriendsList', this.currentWechat.WeChatId)
          this.SetNewChatNotice(false)
          break
        case 1:
          this.$store.dispatch('SetFlags', 'friendsCircle')
          break
        case 2:
          this.$store.dispatch('SetFlags', 'historicalNews')
          break
        case 3:
          this.$store.dispatch('SetFlags', 'groupSendAssistant')
          break
        case 4:
          this.$store.dispatch('SetFlags', 'customerManagement')
          break
        case 5:
          this.$store.dispatch('SetFlags', 'reportManagement')
          break
        case 6:
          this.$store.dispatch('SetFlags', 'friendsManage')
          this.$store.dispatch('addfriends/SetNewFriendRequestNotice', false)
          break
        case 7:
          this.$store.dispatch('SetFlags', 'v-help')
          break
        case 8:
          this.$store.dispatch('SetFlags', 'v-tools')
          break
        default:
          this.$store.dispatch('SetFlags', 'conversation')
          break
      }
    },
    logout () {
      let unionId = Number(this.user.UnionId)
      if (!unionId) {
        let userInfo = JSON.parse(localStorage.getItem('userInfo'))
        unionId = userInfo.UnionId
      }
      let msg = { MsgType: 'AccountLogoutNotice', Content: { UnionId: unionId, AccountType: 2 } }
      console.log('退出登陆：AccountLogoutNotice')
      console.log(msg)

      this.socketApi.sendSock(msg)
      localStorage.setItem('loginStatus', 'logout')
      localStorage.removeItem('userInfo')
      this.$router.replace('/')
    }
  }
}
</script>

<style lang="scss" scoped>
#header {
  background: black;
  display: inline-flex;
  justify-content: space-between;
  height: 60px;
}

.logo {
  height: 60px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-left: 1%;
  img {
    width: 40px;
    height: 40px;
  }
  .logo-title {
    height: 40px;
    display: flex;
    flex-direction: column;
    margin-left: 5px;
    cursor: pointer;
    .p1 {
      height: 20px;
      line-height: 20px;
      font-size: 16px;
      color: white;
    }
    .p2 {
      height: 20px;
      line-height: 20px;
      font-size: 12px;
      color: white;
    }
  }
}

.tabPage {
  display: inline-flex;
  width: 70%;
  align-items: center;

  .tab {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 10%;
    cursor: pointer;
    position: relative;
    .conversation-number {
      width: 12px;
      height: 12px;
      border-radius: 6px;
      text-align: center;
      position: absolute;
      top: 0;
      background: red;
      right: 10px;
    }
    div {
      color: white;
    }
    i {
      color: white;
      font-size: 26px;
    }
    &:hover {
      i,
      div {
        color: #4bbcfb;
      }
    }
  }

  .selected {
    i,
    div {
      color: #4bfb9a;
    }
  }
}

.user-info {
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 10%;
  i {
    font-size: 26px;
    color: white;
    cursor: pointer;
    &:hover {
      color: #4bbcfb;
    }
  }
  div {
    width: 30%;
  }
  .user {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 10px;
    cursor: pointer;
    .userImg {
      width: 30px;
      height: 30px;
      border-radius: 15px;
    }
    .userName {
      width: 50%;
      margin-left: 10px;
      margin-right: 10px;
      color: white;
    }
  }
}
</style>
