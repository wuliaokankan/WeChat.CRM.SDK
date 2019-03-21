<template>
  <div>
    <div class="title">
      <div class="sw">选择微信</div>
      <div class="ht">已添加号码</div>
    </div>
    <div class="content flex-center">
      <!-- 微信列表 -->
      <div class="content-wechats scroll">
        <div
          class="content-wechat"
          :class="{selected:wechatId===wechat.WeChatId}"
          v-for="(wechat, index) in wechatsLogin"
          :key="index"
          @click="getWechatId(wechat)"
        >
          <img :src="wechat.Avatar" alt>
          <span v-text="wechat.WeChatNick"></span>
        </div>
      </div>
      <!-- info -->
      <div class="content-info">
        <div class="error-message" v-if="errorMessage !==''">{{ errorMessage }}</div>
        <div class="form">
          <label for="pn">输入微信号/QQ号/手机号：</label>
          <input
            :class="{errorTip: errorMessage !==''}"
            id="pn"
            type="text"
            v-model="phone"
            maxlength="20"
            placeholder="必填"
          >
          
          <label for="mv">输入验证消息：</label>
          <textarea id="mv" cols="30" rows="10" v-model="message" maxlength="200" placeholder="选填"></textarea>
          <label for="rm">为好友设置备注：</label>
          <input id="rm" type="text" v-model="remark" maxlength="50" placeholder="选填">
          
          <button class="add-friend" @click="addFriendByNumber()">添加</button>
        </div>
      </div>
      <!-- 已经添加 -->
      <div class="content-history scroll">
        <div class="phone" v-for="(item, index) in history" :key="index">
          <img :src="wechatImg[item.wechatId]" alt>
          <span v-text="item.phone" v-if="item.result===0" title="手机未执行"></span>
          <span v-text="item.phone" v-if="item.result===1" title="已经发出申请"></span>
          <span v-text="item.phone" v-if="item.result===2" title="不存在"></span>
          <span v-text="item.phone" v-if="item.result===3" title="已经是好友"></span>
          <div class="not-add" v-if="item.result===0"></div>
          <div class="already-add" v-if="item.result===1"></div>
          <div class="not-found" v-if="item.result===2"></div>
          <div class="already-friend" v-if="item.result===3"></div>
        </div>
      </div>
    </div>
    <div class="bottom"></div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import websql from '../../utils/websql.js'

export default {
  data () {
    return {
      wechatId: '',
      phone: '',
      message: '',
      remark: '',
      errorMessage: '',
      phones: []
    }
  },
  computed: {
    ...mapGetters({
      wechatsLogin: 'wechatsLogin',
      wechats: 'wechats',
      history: 'addfriends/history',
      db: 'db'
    }),
    wechatImg () {
      let wechatImg = {}
      for (const wechat of this.wechats) {
        wechatImg[wechat.WeChatId] = wechat.Avatar
      }
      return wechatImg
    }
  },
  methods: {
    getWechatId (wechat) {
      this.wechatId = wechat.WeChatId
    },
    addFriendByNumber () {
      console.log('通过号码加好友')
      let flag = false
      if (this.wechatId) {
        if (this.phone) {
          flag = true
          this.phones.push(this.phone)
        } else {
          this.errorMessage = '请输入微信号/QQ号/电话号'
        }
      } else {
        this.errorMessage = '请选择要添加的微信'
      }
      if (flag) {
        this.errorMessage = ''
        let content = {
          WeChatId: this.wechatId, // 商家所属微信号
          Phones: this.phones, // 要添加的手机号码，批量
          message: this.message, // 发送的验证消息
          TaskId: 4, // 任务Id
          Remark: this.remark, // 备注名
          IMEI: '' // 该值与WeChatId二选一, IMEI优先
        }
        let msg = { 'MsgType': 'AddFriendsTask', 'Content': content }
        // console.log(msg)
        this.socketApi.sendSock(msg)
        let contentLocal = content
        contentLocal['result'] = 0
        contentLocal['addTime'] = new Date().getTime()
        // console.log(contentLocal)
        websql.replaceAddFriendsHistory(this.db, contentLocal)
        this.phone = ''
        this.phones = []
        this.$store.dispatch('addfriends/SetHistory')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.title {
  position: relative;
  .sw {
    height: 20px;
    width: 210px;
    font-size: 14px;
    text-align: center;
    color: white;
    position: absolute;
    left: 0px;
  }
  .ht {
    height: 20px;
    width: 210px;
    font-size: 14px;
    text-align: center;
    color: white;
    position: absolute;
    right: 0px;
  }
}

.content {
  width: 100%;
  height: 90%;
  display: flex;
  justify-content: flex-start;
}

.content-wechats {
  height: 100%;
  width: 210px;
  overflow-x: hidden;
  overflow-y: auto;
  background: rgba($color: black, $alpha: 0.3);
  .content-wechat {
    width: 200px;
    height: 40px;
    display: flex;
    align-items: center;
    padding: 5px;
    cursor: pointer;
    img {
      width: 40px;
      height: 40px;
    }
    span {
      width: 135px;
      margin-left: 5px;
      font-size: 14px;
      overflow: hidden;
      word-break: break-all;
      word-wrap: break-word;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
    }
    &:hover {
      background: #dddbda;
      span {
        color: #4bbcfb;
      }
    }
  }
  .selected {
    background: #c5c5c5;
    &:hover {
      background: #c5c5c5;
      span {
        color: black;
      }
    }
  }
}

.content-info {
  flex: 1 1 auto;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  .error-message {
    position: absolute;
    top: 30px;
    font-size: 14px;
    color: red;
  }
  .form {
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    flex-direction: column;
    width: 80%;
    .errorTip {
      border: solid red 1px;
    }
    label {
      width: 90%;
      font-size: 14px;
      margin-top: 5px;
    }
    textarea,
    input {
      // border: #4bbcfb solid 1px;
      width: 90%;
      font-size: 14px;
      margin-top: 5px;
    }
    .add-friend {
      font-size: 14px;
      width: 80px;
      border-radius: 10px;
      color: white;
      background: black;
      cursor: pointer;
      margin-top: 5px;
      margin-bottom: 5px;
      &:hover {
        color: #4bbcfb;
      }
    }
  }
}

.content-history {
  height: 100%;
  width: 210px;
  overflow-x: hidden;
  overflow-y: auto;
  background: rgba($color: black, $alpha: 0.3);
  .phone {
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    padding: 5px;
    font-size: 14px;
    color: white;
    img {
      width: 30px;
      height: 30px;
      border-radius: 15px;
    }
    span {
      width: 150px;
      font-size: 16px;
      display: inline-block;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    div {
      width: 10px;
      height: 10px;
      border-radius: 5px;
    }
    .already-add {
      background: yellow;
    }
    .not-add {
      background: gray;
    }
    .not-found {
      background: red;
    }
    .already-friend {
      background: green;
    }
  }
}

.scroll {
  &::-webkit-scrollbar {
    width: 6px;
    height: 6px;
    background-color: #bababa;
  }
  &::-webkit-scrollbar-thumb {
    border-radius: 3px;
    background-color: black;
  }
}
</style>