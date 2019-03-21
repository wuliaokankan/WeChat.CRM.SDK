<template>
  <div class="autoAccept">
    <div class="title"></div>
    <div class="content">
      <ul class="ul-title">
        <li>微信</li>
        <li>自动通过</li>
        <li>通过后发送消息</li>
        <li>发送内容</li>
      </ul>
      <ul class="ul-body scroll">
        <li
          class="content-wechats"
          :class="{odd:index%2===0}"
          v-for="(wechat, index) in wechatsLogin"
          :key="index"
        >
          <th>
            <img :src="wechat.Avatar" alt>
            <span v-text="wechat.WeChatNick"></span>
          </th>
          <th>
            <i
              class="fa fa-power-off fa-2x"
              :class="{opened:autoAcceptSet[wechat.WeChatId].accept === 1}"
              aria-hidden="true"
              @click="toggleAutoAccept(wechat)"
            ></i>
          </th>
          <th>
            <i
              class="fa fa-power-off fa-2x"
              :class="{opened:autoAcceptSet[wechat.WeChatId].reply === 1}"
              aria-hidden="true"
              @click="setAutoReply(wechat)"
            ></i>
          </th>
          <th>
            <button @click="addReply(wechat)">添加</button>
            <!-- <input type="text"> -->
            <!-- <textarea
              class="scroll"
              cols="30"
              rows="3"
              v-text="autoAcceptSet[wechat.WeChatId].content"
            ></textarea>-->
          </th>
        </li>
        {{autoAccept}}
      </ul>
    </div>
    <div class="bottom"></div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import websql from '../../utils/websql'
export default {
  data () {
    return {
      aaf: {
        wechatId: '',
        accept: 0,
        reply: 0,
        content: ''
      }
    }
  },
  computed: {
    ...mapGetters({
      wechatsLogin: 'wechatsLogin',
      autoAccept: 'addfriends/autoAccept',
      db: 'db'
    }),
    autoAcceptSet () {
      let autoAcceptSet = {}
      let aas = this.autoAccept
      if (JSON.stringify(aas) === '{}') {
        // console.log('没有配置信息')
        for (const wechat of this.wechatsLogin) {
          let aaf = {
            wechatId: wechat.WeChatId,
            accept: 0,
            reply: 0,
            content: ''
          }
          websql.replaceAutoAcceptFriends(this.db, aaf)
          this.SetAutoAccept()
        }
      } else {
        // console.log('有配置信息')
        for (const aa of aas) {
          autoAcceptSet[aa.wechatId] = aa
        }
      }
      // console.log('autoAcceptSet')
      // console.log(autoAcceptSet)
      return autoAcceptSet
    }
  },
  methods: {
    ...mapActions({
      SetAutoAccept: 'addfriends/SetAutoAccept'
    }),
    toggleAutoAccept (wechat) {
      // console.log('自动接收')
      let wechatId = wechat.WeChatId
      // console.log(wechatId)
      let res = this.autoAcceptSet[wechatId]
      // console.log(res)
      if (res.accept === 1) {
        res.accept = 0
      } else {
        res.accept = 1
      }
      websql.replaceAutoAcceptFriends(this.db, res)
      this.SetAutoAccept()
    },
    setAutoReply (wechat) {
      // console.log('自动回复')
      let wechatId = wechat.WeChatId
      // console.log(wechatId)
      let res = this.autoAcceptSet[wechatId]
      // console.log(res)
      if (res.reply === 1) {
        res.reply = 0
      } else {
        res.reply = 1
      }
      websql.replaceAutoAcceptFriends(this.db, res)
      this.SetAutoAccept()
    },
    addReply (wechat) {
      console.log('添加回复')
      console.log(wechat)
    }
  }
}
</script>

<style lang="scss" scoped>
.autoAccept {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  width: 100%;
}

$width1: 20%;
$width2: 40%;
.content {
  width: 80%;
  height: 90%;
  .ul-title {
    display: flex;
    height: 5%;
    justify-content: space-evenly;
    align-items: center;
    border-bottom: #282b30 solid 2px;
    font-size: 16px;
    background: rgba($color: gray, $alpha: 0.8);
    li:nth-child(1) {
      width: $width1;
    }
    li:nth-child(2) {
      width: $width1;
    }
    li:nth-child(3) {
      width: $width1;
    }
    li:nth-child(4) {
      width: $width2;
    }
    li {
      text-align: center;
      font-size: 16px;
    }
  }
  .ul-body {
    height: 95%;
    overflow-y: auto;
    li {
      border-bottom: solid #4bbcfb 1px;
      display: flex;
      justify-content: space-evenly;
      align-items: center;
      padding: 5px;
      // height: 50px;
      th:nth-child(1) {
        width: $width1;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        img {
          width: 50px;
          height: 50px;
        }
        span {
          font-size: 12px;
          width: 80%;
          text-align: center;
          display: inline-block;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }
      }
      th:nth-child(2),
      th:nth-child(3) {
        width: $width1;
        text-align: center;
        .opened {
          color: #4bfb9a;
        }
        i {
          cursor: pointer;
          color: red;
          &:hover {
            color: #4bbcfb;
          }
        }
      }
      th:nth-child(4) {
        width: $width2;
        text-align: center;
        button {
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
}
</style>