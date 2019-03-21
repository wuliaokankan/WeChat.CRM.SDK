<template>
  <div class="lastChat">
    <div v-if="!messages[0]" class="gray">没有聊天消息...</div>
    <div
      v-else
      class="chats pointer"
      v-for="(message, index) in messages"
      :key="index"
      :class="[{selected:index===current},{hover:index!==current}]"
      @click="select(index,message)"
    >
      <img v-if="message.avatar!=='undefined'" class="img" :src="message.avatar" alt="未加载">
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
          v-if="messagesNumber[message.friendId]>0"
          class="isRead flex-center"
          v-text="messagesNumber[message.friendId]"
        ></div>
        <div v-else class="read flex-center" v-text></div>
        <div>{{ message.time | transform }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
// import emojis from '@/utils/emoji.js'

export default {
  data () {
    return {
      current: 0
    }
  },
  computed: {
    ...mapGetters({
      messages: 'messages',
      messagesNumber: 'messagesNumber',
      currenWechat: 'currentWechat'
    })
  },
  methods: {
    ...mapActions({
      SetPersonalInfo: 'SetPersonalInfo'
    }),
    select (index, message) {
      this.current = index
      let info = {
        friendId: message.friendId,
        wechatId: this.currentWechat.WeChatId
      }
      this.SetPersonalInfo(info)
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
      // console.log('emoji')
      let reg = /%uE[\w]{3}/gi
      let content = escape(val)
      let result = content.match(reg)
      if (result) {
        for (const e of result) {
          // let pngName = emojis.emojiArr[e.replace('%u', '').toLowerCase()]
          // content = content.replace(e, '&#x' + pngName + ';')
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