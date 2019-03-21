<template>
  <div class="wechats flex-center">
    <!-- title -->
    <div class="title">
      <img src="static/img/wechat.png" alt="所有微信">
      <p>在线微信{{ wechatsLogin.length }}/{{ wechats.length }}</p>
    </div>

    <!-- content -->
    <div
      v-if="wechatsLogin.length===0"
      class="wechat-list flex-center"
      style="color:red; fontsize:20px"
    >请先选择微信登陆</div>

    <!-- wechatList -->
    <div v-else class="wechat-list">
      <div
        class="wechat flex-center"
        :class="[{selected:current===index},{hover:current!==index}]"
        v-for="(wechat, index) in wechatsLogin"
        :key="index"
        @click="select(index,wechat)"
      >
        <img :src="wechat.Avatar" alt="头像">
        <p class v-text="wechat.WeChatNick"></p>
        <!-- {{ notReadWechat[wechat.WeChatId] }} -->
        <div v-if="notReadWechat[wechat.WeChatId]>0" class="chats flex-center"></div>
        <div v-else class="chats flex-center" style="visibility:hidden"></div>
      </div>
    </div>

    <!-- manager -->
    <div class="management">
      <i class="fa fa-wechat" aria-hidden="true" title="选择微信登陆" @click="wechatStatus"></i>
      <i class="fa fa-trash-o" aria-hidden="true" title="清空本地信息" @click="deleteLocal()"></i>
      <i class="fa fa-refresh" aria-hidden="true" title="刷新当前微信" @click="fresh(currentWechat)"></i>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import websql from '../../utils/websql'
export default {
  data () {
    return {
      current: 0
    }
  },
  props: {
    wechats: Array,
    wechatsLogin: Array
  },
  computed: {
    ...mapGetters({
      currentWechat: 'currentWechat',
      notReadWechat: 'chats/notReadWechat',
      notReadChats: 'chats/notReadChats'
    })
  },
  watch: {
    currentWechat (val) {
      this.SetFriendsList(val.WeChatId)
      this.SetCurrentChats(val.WeChatId)
    }
  },
  methods: {
    ...mapActions({
      SetCurrentWechat: 'SetCurrentWechat',
      SetFriendsList: 'SetFriendsList',
      SetCurrentChats: 'chats/SetCurrentChats'
    }),
    /**
     * 选择微信
     */
    select (index, wechat) {
      this.current = index
      this.SetCurrentWechat(wechat)
      console.log(wechat)
    },
    /**
     * 删除数据库
     */
    deleteLocal () {
      let db = this.$store.getters.db
      websql.dropTables(db)
      // let db = websql.openDB()
      // db.transaction(function (tx) {
      //   tx.executeSql(
      //     'DELETE FROM CHATS WHERE friendId = ? AND wechatId = ?',
      //     ['wxid_291tq23onh5n22', 'wxid_kg9a5uxz94nl22'],
      //     function (tx, results) {
      //       console.log(results.rows)
      //       // commit(types.SET_MESSAGES, results.rows)
      //     },
      //     function (tx, error) {
      //       console.log(error)
      //     }
      //   )
      // })
    },
    /**
     * 刷新当前微信的好友列表
     */
    fresh (wechat) {
      let wechatId = wechat.WeChatId
      let msg = { MsgType: 'TriggerFriendPushTask', Content: { WeChatId: wechatId } }
      console.log(msg)
      this.socketApi.sendSock(msg)
    },
    /**
     * 选择微信登陆
     */
    wechatStatus () {
      this.$router.push({ path: '/wechat/status/' })
    }
  },
  mounted () {
    this.$store.dispatch('chats/SetNotReadWechat')
  }
}
</script>

<style lang="scss" scoped>
.wechats {
  width: 15%;
  height: 98%;
  flex-direction: column;
  background-color: #282b30;
  border-radius: 10px;
}

.title {
  justify-content: space-evenly;
  img {
    width: 30px;
    height: 30px;
  }
  p {
    color: white;
    font-size: 12px;
  }
}

.wechat-list {
  height: 90%;
  width: 90%;
  border-top: gray 1px solid;
  border-bottom: gray 1px solid;
  .chats {
    background: red;
    width: 12px;
    height: 12px;
    color: white;
    border-radius: 8px;
  }
  .number {
    width: 16px;
    height: 16px;
    color: white;
  }
}
.wechat {
  justify-content: space-around;
  margin-top: 2px;
  background: rgb(71, 75, 83);
  cursor: pointer;
  // background: #ebe9e8;
  img {
    height: 40px;
    width: 40px;
    border-radius: 20px;
  }
  p {
    color: rgb(219, 219, 219);
    width: 20%;
  }
}
.hover:hover {
  background-color: rgb(99, 103, 112);
  p {
    color: #4dbcfb;
  }
}
.selected {
  background-color: #c8c6c6;
  p {
    color: black;
  }
}

.management {
  display: flex;
  height: 5%;
  width: 90%;
  justify-content: space-around;
  align-items: center;
  i {
    color: white;
    font-size: 18px;
    cursor: pointer;
    &:hover {
      color: #4dbcfb;
    }
  }
}
.fa-search {
  color: white;
  margin-right: 5px;
  font-size: 16px;
}
</style>

