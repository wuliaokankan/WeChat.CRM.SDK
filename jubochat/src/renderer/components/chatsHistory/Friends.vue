<template>
  <div class="history-friends flex-center">
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
    <div id="show" class="show flex-center">测试版本，功能暂不开放！</div>
    <!-- 分页 -->
    <div class="bottom" v-if="current==='address'">
      <i class="fa fa-angle-left fa-2x" aria-hidden="true" @click="lessPage"></i>
      <span>{{currentPage}}/{{pageMax}}</span>
      <i class="fa fa-angle-right fa-2x" aria-hidden="true" @click="addPage"></i>
    </div>
    <div class="bottom" v-else></div>
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
      currentPage: 1
    }
  },
  computed: {
    ...mapGetters({
      currentWechat: 'currentWechat',
      friendsList: 'friendsList'
    }),
    scrollTop () {
      return this.$store.getters.currentFriend.friend.sn * 44
    },
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
    // 'v-newFriends': NewFriends,
    'v-goodFriends': GoodFriends,
    'v-groupChat': GroupChat,
    'v-lastChat': LastChat,
    'v-lastGroupChat': LastGroupChat
  },
  watch: {
    scrollTop: function () { // 监听屏幕高度变化
      let st = this.scrollTop
      let cst = document.getElementById('show').scrollTop
      if (st - cst > 440) {
        document.getElementById('show').scrollTop = st
      } else if (st - cst < 0) {
        document.getElementById('show').scrollTop = st
      }
    },
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
    }
  },
  methods: {
    ...mapActions({
      SetCommunicatePage: 'SetCommunicatePage'
    }),
    lessPage () {
      console.log('less page')
      if (this.currentPage === 1) {
        this.currentPage = 1
      } else {
        this.currentPage -= 1
      }
    },
    addPage () {
      console.log('add page')
      console.log(this.currentPage)
      console.log(this.pageMax)
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
}
</script>

<style lang="scss" scoped>
.history-friends {
  width: 20%;
  height: 98%;
  border-radius: 10px;
  background-color: #e4e7ea;
  flex-direction: column;
}
.title {
  div {
    cursor: pointer;
    font-size: 14px;
    background: inherit;
    width: 50%;
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
  height: 4%;
  width: 92%;
  border-bottom: gray solid 1px;
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
  margin-top: 1%;
  width: 92%;
  height: 85%;
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
  .showLastConversation {
    height: 88%;
    overflow-y: scroll;
  }

  .nav-address {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .nav-friends,
  .nav-groups {
    width: 50%;
    font-size: 12px;
    text-align: center;
    cursor: pointer;
    background-color: gray;
  }

  .address {
    width: 100%;
    height: 95%;
  }

  .selected {
    color: #3b7957;
    background-color: #e4e7ea;
  }
  .hover:hover {
    color: #41c0fc;
    // background-color: gray;
    background: rgba(0, 0, 0, 0.3);
    div {
      color: #41c0fc;
    }
    i {
      color: #41c0fc;
    }
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

