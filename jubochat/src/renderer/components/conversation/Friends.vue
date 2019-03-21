<template>
  <div class="friends common">
    <!-- conversation -->
    <div class="title">
      <div :class="{selected:current=='conversation'}" @click="selectConversation()">会话</div>
      <div :class="{selected:current=='address'}" @click="selectAddress()">通讯录</div>
    </div>
    <!-- search -->
    <div class="search flex-center">
      <input style="width:80%" type="search" v-model="searchInfo" placeholder="输入昵称" maxlength="20">
      <i class="fa fa-paw pointer" aria-hidden="true"></i>
    </div>
    <!-- groupChat chats -->
    <div id="show" class="show">
      <!-- 会话 -->
      <div class="coversation" v-if="current==='conversation'">
        <!-- 群聊 -->
        <div class="group pointer hover" @click="show(1)">
          <div class="font14">
            <i class="fa fa-object-group" aria-hidden="true"></i>
            群聊
          </div>
          <i class="fa fa-sort-desc" aria-hidden="true"></i>
        </div>
        <v-lastGroupChat class="showGroup" v-show="showLastGroupChat"></v-lastGroupChat>
        <!-- 最近联系人 -->
        <div class="lastConversation pointer hover" @click="show(2)">
          <div class="font14">
            <i class="fa fa-comments-o" aria-hidden="true"></i>
            获取最近联系人
          </div>
          <i class="fa fa-sort-desc" aria-hidden="true"></i>
        </div>
        <v-lastChat v-show="showLastChat"></v-lastChat>
      </div>
      <!-- show 通讯录 -->
      <v-goodFriends v-else></v-goodFriends>
    </div>
    <!-- 分页 -->
    <!-- <div class="bottom" v-if="current==='address'"> -->
    <!-- <i class="fa fa-angle-left fa-2x" aria-hidden="true" @click="lessPage"></i>
      <span>{{currentPage}}/{{pageMax}}</span>
    <i class="fa fa-angle-right fa-2x" aria-hidden="true" @click="addPage"></i>-->
    <!-- </div> -->
    <div class="bottom"></div>
  </div>
</template>

<script>
import GoodFriends from './friends/GoodFriends'
import GroupChat from './friends/GroupChat'
import LastChat from './friends/LastChat'
import LastGroupChat from './friends/LastGroupChat'

import { mapGetters, mapActions } from 'vuex'

export default {
  data () {
    return {
      current: 'conversation',
      currentAddress: 'goodFriends',
      showLastGroupChat: false,
      showLastChat: true,
      searchInfo: '',
      currentPage: 1,
      allLoad: false,
      message: '好友加载中...'
    }
  },
  computed: {
    ...mapGetters({
      currentWechat: 'currentWechat',
      friendsList: 'friendsList'
    }),
    pageMax () {
      let i = 0
      for (const key in this.friendsList) {
        if (this.friendsList.hasOwnProperty(key)) {
          i = key
        }
      }
      i++
      return Math.ceil(i / 100)
    }
  },
  components: {
    'v-goodFriends': GoodFriends,
    'v-groupChat': GroupChat,
    'v-lastChat': LastChat,
    'v-lastGroupChat': LastGroupChat
  },
  watch: {
    searchInfo (val) {
      let wechatId = this.currentWechat.WeChatId
      if (val !== '') {
        this.currentPage = 1
        let info = { val: val, wechatId: wechatId }
        this.$store.dispatch('SearchFriendsList', info)
        this.$store.dispatch('SearchMessages', info)
      } else {
        this.$store.dispatch('SetFriendsList', wechatId)
      }
    },
    currentWechat () {
      this.searchInfo = ''
    },
    pageMax (val) {
      // console.log(val)
      let number = -1
      if (this.friendsList[0]) {
        number = Math.ceil(this.friendsList[0].allFriends / 100)
        // console.log(number)
      }
      if (number === val) {
        // console.log('好友加载完成')
        this.allLoad = true
      } else {
        this.allLoad = false
      }
    }

  },
  methods: {
    ...mapActions({
      SetCommunicatePage: 'SetCommunicatePage'
    }),
    getFriends (wechat) {
      let wechatId = wechat.WeChatId
      let msg = { MsgType: 'TriggerFriendPushTask', Content: { WeChatId: wechatId } }
      this.message = '好友列表加载中'

      console.log(msg)
      this.socketApi.sendSock(msg)
    },
    lessPage () {
      if (this.currentPage === 1) {
        this.currentPage = 1
      } else {
        this.currentPage -= 1
      }
    },
    addPage () {
      if (this.currentPage < this.pageMax) {
        this.currentPage += 1
      } else {
        this.currentPage = this.pageMax
      }
    },
    selectConversation () {
      console.log('切换到conversation')
      this.current = 'conversation'
    },
    selectAddress () {
      console.log('切换到address')
      this.current = 'address'
    },
    tab (current) {
      console.log(current)
      this.currentAddress = current
    },
    show (info) {
      switch (info) {
        case 1:
          if (this.showLastGroupChat) {
            this.showLastGroupChat = false
          } else {
            this.showLastGroupChat = true
          }
          break
        case 2:
          if (this.showLastChat) {
            this.showLastChat = false
          } else {
            this.showLastChat = true
          }
          break

        default:
          this.showLastGroupChat = false
          this.showLastChat = false
      }
    }
  }
  // mounted () {
  //   if (!this.friendsList[0]) {
  //     this.message = '点击刷新，获取好友信息'
  //   }
  // }
}
</script>

<style lang="scss" scoped>
$bgcolor: #e4e7ea;
.friends {
  background-color: $bgcolor;
  width: 20%;
  position: relative;
}
.title {
  div {
    cursor: pointer;
    font-size: 16px;
    background: inherit;
    width: 45%;
    text-align: center;
    color: gray;
    &:hover {
      color: #41c0fc;
    }
  }
  .selected {
    color: #4bfb9a;
  }
}

.search {
  height: 5%;
  width: 92%;
  font-size: 16px;
  // border-bottom: gray solid 1px;
  .fa-paw {
    color: black;
    margin-left: 5px;
    font-size: 16px;
  }
  .fa-paw:hover {
    color: #41c0fc;
  }
}

.show {
  display: flex;
  flex-direction: column;
  width: 92%;
  height: 85%;
  margin-top: 1px;
  overflow: hidden;
  // border: solid green 1px;
  .coversation {
    height: 100%;
  }
  .group,
  .lastConversation {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 5px;
    height: 30px;
  }

  .showLastConversation,
  .showGroup {
    margin-left: 5%;
  }

  .address {
    width: 100%;
    height: 100%;
  }
}

.bottom {
  font-size: 14px;
  i {
    cursor: pointer;
    color: white;
  }
  span {
    color: white;
  }
}
</style>

