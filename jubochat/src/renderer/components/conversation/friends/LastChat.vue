<template>
  <div class="lastChat scroll">
    <div v-if="currentChats.length===0" class="gray">没有聊天消息...</div>
    <div
      v-else
      class="chats pointer"
      v-for="(message, index) in currentChats"
      :key="index"
      :class="[{selected:index===current},{hover:index!==current}]"
      @click="select(index,message)"
    >
      <!-- {{message}} -->
      <img v-if="message.avatar!=='undefined'" class="img" :src="message.avatar">
      <div v-else class="img flex-center">
        <i class="fa fa-spinner" aria-hidden="true"></i>
      </div>
      <div class="nickname">
        <div v-text="message.friendNick"></div>
        <p
          class="gray"
          v-if="message.contentType==='Text'"
          :title="message.content | emoji"
        >{{ message.content | emoji }}</p>
        <p class="gray" v-else v-text="message.contentType"></p>
      </div>
      <div class="time">
        <div
          v-if="message.messageNumber > 0"
          class="isRead flex-center"
          v-text="message.messageNumber"
        ></div>
        <div v-else class="read flex-center" v-text></div>
        <div>{{ message.time | transform }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import base64 from '@/utils/base64.js'

export default {
  data () {
    return {
      current: 0
    }
  },
  computed: {
    ...mapGetters({
      currentWechat: 'currentWechat',
      currentChats: 'chats/currentChats'
    })
  },
  methods: {
    ...mapActions({
      SetPersonalInfo: 'SetPersonalInfo',
      SetWechatIsRead: 'chats/SetWechatIsRead',
      SetCurrentChats: 'chats/SetCurrentChats',
      SetNotReadWechat: 'chats/SetNotReadWechat',
      SetAllChats: 'chats/SetAllChats',
      SetAllChatsLength: 'chats/SetAllChatsLength',
      SetCurrentCircles: 'circles/SetCurrentCircles'
    }),
    select (index, message) {
      let info = {
        friendId: message.friendId,
        wechatId: this.currentWechat.WeChatId
      }
      this.SetPersonalInfo(info)
      this.current = index
      this.SetWechatIsRead(info)
      this.SetNotReadWechat()
      this.SetCurrentChats(this.currentWechat.WeChatId)
      this.SetAllChats(info)
      this.SetAllChatsLength(info)
      this.SetCurrentCircles(info)
    }
  },
  filters: {
    transform (time) {
      let date = new Date(time)
      let h = date.getHours()
      let m = date.getMinutes()
      if (m < 10) {
        return (h + ':0' + m)
      } else {
        return (h + ':' + m)
      }
    },
    emoji (val) {
      let reg = /%uE[\w]{3}/gi
      let content = escape(base64.b64DecodeUnicode(val))
      let result = content.match(reg)
      if (result) {
        for (const e of result) {
          content = content.replace(e, '[emoji]')
        }
      }
      return unescape(content)
    }
  }
}
</script>

<style lang="scss" scoped>
@import "../../../styles/globals.scss";
.lastChat {
  display: flex;
  flex-direction: column;
  height: 88%;
  overflow-y: auto;
  margin-left: 5%;
  // border: solid red 1px;
}
.chats {
  display: flex;
  justify-content: space-between;
  width: 95%;
  height: 50px;
  align-items: center;
  position: relative;
  min-height: 50px;
}
.img {
  width: 36px;
  height: 36px;
  font-size: 20px;
}
.nickname {
  display: flex;
  flex-direction: column;
  width: 50%;
  div {
    display: inline-block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  p {
    display: inline-block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
.time {
  width: 25%;
  .isRead {
    position: absolute;
    right: 0;
    top: 0;
    width: 16px;
    height: 16px;
    border-radius: 8px;
    background: red;
    color: white;
  }
  .read {
    position: absolute;
    right: 0;
    top: 0;
    width: 16px;
    height: 16px;
  }
}

.hover:hover {
  background: #dedcdb;
}
.selected {
  background-color: #c8c6c6;
}
</style>