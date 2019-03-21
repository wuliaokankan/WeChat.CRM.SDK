<template>
  <div class="wechats flex-center">
    <div class="all flex-center">
      <img class="allWechatImg" src="/static/img/wechat.png" alt="所有微信">
      <p>所有微信{{ wechatsLogin.length }}/{{ wechats.length }}</p>
    </div>
    <!-- list -->
    <div
      v-if="wechatsLogin.length===0"
      class="wechat-list flex-center"
      style="color:red; fontsize:20px"
    >请先选择微信登陆</div>
    <div v-else class="wechat-list flex-center">
      <div>测试版本，功能暂不开放！</div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
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
      messagesNumber: 'messagesNumber',
      messagesNumbers: 'messagesNumbers'
    })
  },
  watch: {
    currentWechat () {
      this.SetFriendsList(this.currentWechat.WeChatId)
    }
  },
  methods: {
    ...mapActions({
      SetCurrentWechat: 'SetCurrentWechat',
      SetFriendsList: 'SetFriendsList'
    }),
    select (index, wechat) {
      this.current = index
      this.SetCurrentWechat(wechat)
    }
  }
}
</script>

<style lang="scss" scoped>
@import "../../styles/globals.scss";
.wechats {
  width: 15%;
  height: 98%;
  background-color: #282b30;
  border-radius: 10px;
  flex-direction: column;
}
.search {
  height: 40px;
  width: 90%;
}
.all {
  display: flex;
  width: 90%;
  height: 40px;
  justify-content: space-around;
}
.all > p {
  color: white;
  font-size: 12px;
}
.allWechatImg {
  width: 36px;
  height: 36px;
}

.wechat-list {
  height: 85%;
  width: 90%;
  border-top: gray 1px solid;
  border-bottom: gray 1px solid;
  .chats {
    background: red;
    width: 16px;
    height: 16px;
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

.fa-search {
  color: white;
  margin-right: 5px;
  font-size: 16px;
}
</style>

