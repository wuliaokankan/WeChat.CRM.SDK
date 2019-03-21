<template>
  <div class="autoAccept">
    <div class="title">
      <div>
        <!-- <input type="date" name id placeholder="起始日期">至
        <input type="date" name id placeholder="结束日期">
        <select ref="addRequestState" @change="showValue()">
          <option value="4" selected>全部</option>
          <option value="0">未处理</option>
          <option value="2">已接受</option>
          <option value="3">已拒绝</option>
          <option value="1">已忽略</option>
        </select>-->
      </div>
    </div>
    <div class="wechat-list">
      <ul class="ul-title">
        <li>所属微信</li>
        <li>好友头像</li>
        <li>好友昵称</li>
        <li>验证语</li>
        <li>操作</li>
      </ul>
      <ul class="ul-body scroll">
        <li
          v-show="showInfo"
          :class="{odd:index%2===0}"
          v-for="(res, index) in friendsRequestInfo"
          :key="index"
        >
          <th>{{ res.wechatId | showName(wechats) }}</th>
          <th>
            <img :src="res.avatar" alt>
          </th>
          <th v-text="res.friendNick" :title="res.friendNick"></th>
          <th v-text="res.reason" :title="res.reason"></th>
          <th v-if="res.state===0">
            <button @click="ignoreRequest(res)">忽略</button>
            <button @click="acceptRequest(res)">接受</button>
            <button @click="rejustRequest(res)">拒绝</button>
          </th>
          <th v-if="res.state===1">已忽略</th>
          <th v-if="res.state===2">已添加,且添加成功</th>
          <th v-if="res.state===3">已拒绝</th>
          <th v-if="res.state===4">已添加,但添加失败</th>
        </li>
        <li class="no-info" v-show="!showInfo">没有好友请求</li>
      </ul>
    </div>
    <div class="dialog" v-show="showDialog">
      <div>
        <span>为朋友设置备注</span>
        <textarea v-model="remark" cols="30" rows="10" maxlength="50" placeholder="添加备注，选填，最多50个汉字"></textarea>
        <div class="btns">
          <button @click="cancel">取消</button>
          <button @click="submit">确定</button>
        </div>
      </div>
    </div>
    <!-- <div class="dialog2" v-show="showDialog2" v-html="autoAcceptState"></div> -->
    <div class="bottom"></div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
// import { setTimeout } from 'timers'
import websql from '../../utils/websql'
export default {
  data () {
    return {
      showDialog: false,
      remark: '',
      message: {},
      friendInfo: {}
    }
  },
  computed: {
    ...mapGetters({
      friendsRequestInfo: 'addfriends/friendsRequestInfo',
      db: 'db',
      wechats: 'wechats',
      result: 'resultOfAcceptAddRequest'
    }),
    showInfo () {
      let flag = true
      if (JSON.stringify(this.friendsRequestInfo) === '{}') {
        flag = false
      }
      return flag
    }
  },
  filters: {
    showName (val, wechats) {
      for (const wechat of wechats) {
        if (wechat.WeChatId === val) {
          return wechat.WeChatNick
        } else {
          console.log('申请的好友的微信 不在wechats')
        }
      }
    }
  },
  methods: {
    ...mapActions({
      SetFriendsRequestInfo: 'addfriends/SetFriendsRequestInfo',
      UpdataAddRequest: 'UpdataAddRequest',
      SetAddRequest: 'SetAddRequest',
      SetAddRequestByState: 'SetAddRequestByState'
    }),
    // 忽略请求，仅本地处理
    ignoreRequest (res) {
      console.log('忽略')
      res.state = 1
      websql.replaceFriendsRequest(this.db, res)
      this.SetFriendsRequestInfo()
    },
    // 拒绝请求，仅本地处理
    rejustRequest (res) {
      console.log('拒绝')
      res.state = 3
      websql.replaceFriendsRequest(this.db, res)
      this.SetFriendsRequestInfo()
    },
    // 接收请求
    acceptRequest (res) {
      console.log('接收')
      res.state = 2
      console.log(res)
      this.friendInfo = res
      this.showDialog = true
      this.message = { MsgType: 'AcceptFriendAddRequestTask', Content: { WeChatId: res.wechatId, FriendId: res.friendId, Operation: 1, Remark: '', FriendNick: res.friendNick } }
    },
    // 取消接收请求
    cancel () {
      console.log('cancel')
      this.showDialog = false
    },
    submit () {
      console.log('提交')
      this.message.Content.Remark = this.remark
      console.log(this.message)
      this.socketApi.sendSock(this.message)
      let res = this.friendInfo
      res.state = 4
      websql.replaceFriendsRequest(this.db, res)
      this.showDialog = false
      this.SetFriendsRequestInfo()
    }
    // openAutoAccept () {
    //   this.opened = !this.opened
    //   this.showDialog2 = true
    //   if (this.opened) {
    //     this.autoAcceptState = '<span style="color:#4bfba9;font-size:22px;">自动通过好友请求，已开启！</span>'
    //     let autoAccept = []
    //     for (const wechat of this.wechats) {
    //       autoAccept.push(wechat.WeChatId)
    //     }
    //     localStorage.setItem('autoAccept', JSON.stringify(autoAccept))
    //   } else {
    //     this.autoAcceptState = '<span style="color:red;font-size:22px;">自动通过好友请求，已关闭！</span>'
    //     localStorage.removeItem('autoAccept')
    //   }
    //   setTimeout(() => {
    //     this.showDialog2 = false
    //   }, 1000)
    // },
    // showValue () {
    //   console.log('showValue')
    //   let index = parseInt(this.$refs.addRequestState.value)
    //   if (index === 4) {
    //     this.SetAddRequest()
    //   } else {
    //     this.SetAddRequestByState(index)
    //   }
    // },
  },
  mounted () {
    this.$store.dispatch('addfriends/SetFriendsRequestInfo')
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
  position: relative;
}

.wechat-list {
  width: 90%;
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
      width: 80px;
    }
    li:nth-child(2) {
      width: 80px;
    }
    li:nth-child(3) {
      width: 160px;
    }
    li:nth-child(4) {
      width: 160px;
    }
    li:nth-child(5) {
      width: 160px;
    }
    li {
      text-align: center;
      // color: white;
      // border: solid red 1px;
    }
  }
  .ul-body {
    height: 95%;
    overflow-y: auto;
    .odd {
      background: white;
      th {
        color: black;
      }
    }
    li {
      display: flex;
      justify-content: space-evenly;
      align-items: center;
      margin-top: 2px;
      th {
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 14px;
        cursor: pointer;
      }
      th:nth-child(1) {
        width: 80px;
      }
      th:nth-child(2) {
        width: 80px;
        img {
          width: 40px;
          height: 40px;
          transition: all 0.6s;
          &:hover {
            cursor: pointer;
            transform: scale(2);
          }
        }
      }
      th:nth-child(3) {
        width: 160px;
        display: inline-block;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        text-align: center;
      }
      th:nth-child(4) {
        width: 160px;
        display: inline-block;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        text-align: center;
      }
      th:nth-child(5) {
        width: 160px;
        display: flex;
        justify-content: space-around;
        align-items: center;
        // border: red solid 1px;
        button {
          cursor: pointer;
          width: 44px;
          font-size: 14px;
          border-radius: 10px;
          background: #282b30;
          color: white;
          &:hover {
            color: #4bbcfb;
          }
        }
      }
      &:hover {
        background: rgba($color: #000, $alpha: 0.2);
      }
    }
    .no-info {
      width: 160px;
      border-radius: 15px;
      font-size: 16px;
      background: black;
      color: white;
      text-align: center;
      margin: 30px auto;
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
.dialog {
  width: 100%;
  height: 100%;
  position: absolute;
  background: rgba($color: #000000, $alpha: 0.6);
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  div {
    width: 500px;
    height: 300px;
    background: white;
    display: flex;
    justify-content: space-around;
    align-items: center;
    flex-direction: column;
    position: absolute;
    span {
      font-size: 20px;
    }
    textarea {
      width: 90%;
      height: 50%;
      border: #4bbcfb dashed 1px;
    }
    .btns {
      width: 200px;
      height: 20px;
      display: flex;
      flex-direction: row;
      justify-content: space-around;
      position: relative;
      button {
        width: 80px;
        height: 20px;
        background: #000;
        border-radius: 10px;
        color: white;
        cursor: pointer;
        &:hover {
          color: #4bbcfb;
        }
      }
    }
  }
}
.dialog2 {
  position: absolute;
  background: rgba($color: #000000, $alpha: 0.2);
}
</style>