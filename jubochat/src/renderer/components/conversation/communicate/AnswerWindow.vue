<template>
  <div class="anwser">
    <textarea
      v-if="message.contentType === 1"
      @keyup.ctrl.enter="sendMessage"
      class="text scroll"
      cols="30"
      rows="10"
      maxlength="1000"
      autofocus
      required
      v-model="textContent"
    ></textarea>
    <div class="other-type" v-else>
      <!-- pic -->
      <img v-if="message.contentType ===2" :src="message.content" alt>
      <!-- video -->
      <video v-else-if="message.contentType ===4" :src="message.content" controls></video>
      <!-- files -->
      <div v-else-if="message.contentType === 8" class="file">
        <icon-font class="file-icon" height="64" width="64">
          <file-word v-if="message.fileType ==='doc'|| message.fileType==='docx'"/>
          <file-xls v-else-if="message.fileType==='xls'|| message.fileType==='xlsx'"/>
          <file-ppt v-else-if="message.fileType==='ppt'|| message.fileType==='pptx'"/>
          <file-zip v-else-if="message.fileType==='zip'"/>
          <file-rar v-else-if="message.fileType==='rar'"/>
          <file-text v-else-if="message.fileType==='text'|| message.fileType==='txt'"/>
          <file-mp3 v-else-if="message.fileType==='mp3'"/>
          <file-unknow v-else/>
        </icon-font>
        <div class="name-size">
          <span v-text="message.name" :title="message.name"></span>
          <span v-text="message.size+' B'"></span>
        </div>
      </div>
      <i class="fa fa-times-circle-o fa-2x close-media" aria-hidden="true" @click="closeWindow"></i>
    </div>
    <div v-show="errorMessages" v-text="errorMessages" class="errorMessages"></div>
    <button class="send" @click="sendMessage">发送</button>
  </div>
</template>

<script>
import { validMessages } from '@/utils/valid.js'
import websql from '@/utils/websql.js'
import base64 from '@/utils/base64.js'
import { mapGetters } from 'vuex'
export default {
  data () {
    return {
      errorMessages: '',
      textContent: '',
      message: {
        contentType: 1
      }
    }
  },
  props: {
    toolsContent: Object,
    personalInfo: {}
  },
  watch: {
    toolsContent (val) {
      if (val.contentType === 1) {
        this.textContent = this.textContent + val.content
      }
      this.message = val
    },
    commonReplay (cp) {
      if (cp) {
        if (cp.content.indexOf('http://') === -1) {
          // console.log('文字')
          this.textContent = cp.content
          this.message = {
            contentType: 1
          }
        } else if (cp.content.indexOf('.mp4') === -1) {
          // console.log('图片')
          this.message = {
            content: cp.content,
            contentType: 2
          }
        } else {
          // console.log('视频')
          this.message = {
            content: cp.content,
            contentType: 4
          }
        }
      }
    }
  },
  computed: {
    ...mapGetters({
      commonReplay: 'chats/commonReplay',
      sh: 'chats/sh',
      ch: 'chats/ch'
    })
  },
  methods: {
    // 发送消息
    sendMessage () {
      console.log('sendMessage')
      let _this = this
      let currentContent = base64.b64EncodeUnicode(this.textContent)
      let currentContentLocal = ''
      let flag = false
      if (this.message.contentType === 1) {
        flag = validMessages(this.textContent)
      } else {
        flag = validMessages(this.message.content)
      }
      switch (this.message.contentType) {
        case 1:
          // console.log('这是文本')
          currentContentLocal = currentContent
          break
        case 2:
          // console.log('这是图片')
          currentContent = base64.b64EncodeUnicode(this.message.content)
          currentContentLocal = currentContent
          break
        case 3:
          // console.log('这是语音')
          break
        case 4:
          // console.log('这是视频')
          currentContent = base64.b64EncodeUnicode(this.message.content)
          currentContentLocal = currentContent
          break
        case 8:
          // console.log('这是文件')
          currentContent = base64.b64EncodeUnicode(this.message.content)
          currentContentLocal = base64.b64EncodeUnicode(JSON.stringify(this.message))
          break
        default:
          // console.log('这是默认')
          break
      }
      if (this.personalInfo) {
        let content = {
          WeChatId: this.personalInfo.wechatId,
          FriendId: this.personalInfo.friendId,
          ContentType: this.message.contentType,
          Content: currentContent,
          SendTime: 0,
          IsResend: false,
          ResendTime: 0,
          Remark: '',
          MsgId: 0
        }
        let msg = { 'MsgType': 'TalkToFriendTask', 'Content': content }

        // if (validMessages(this.textContent) || validMessages(this.message.content)) {
        if (flag) {
          // console.log('发送给微信')
          // console.log(msg)
          this.socketApi.sendSock(msg)
          let message = {
            Content: currentContentLocal,
            ContentType: this.message.contentType,
            FriendId: this.personalInfo.friendId,
            MsgId: '',
            WeChatId: this.personalInfo.wechatId,
            MsgSvrId: '',
            time: new Date().getTime(),
            isRead: 1,
            chatType: 1
          }
          // console.log('要保存的数据')
          // console.log(message)
          let db = this.$store.getters.db
          websql.InsertReceiveChats(db, message)
          this.$store.dispatch('chats/SetAllChats', this.personalInfo)
          this.textContent = ''
          this.message = {
            contentType: 1,
            content: ''
          }
          this.$store.dispatch('chats/SetCommonReplay', '')
          this.$store.dispatch('chats/SetST', this.sh - this.ch + 40)
        } else {
          _this.errorMessages = '不能发送空白消息'
          setTimeout(function () {
            _this.errorMessages = ''
          }, 1500)
        }
      } else {
        _this.errorMessages = '未选择聊天对象'
        setTimeout(function () {
          _this.errorMessages = ''
        }, 1500)
      }
    },
    // 清空message
    closeWindow () {
      this.message = {
        contentType: 1
      }
    }
  }
}
</script>


<style lang="scss" scoped>
.anwser {
  display: flex;
  flex-direction: column;
  width: 98%;
  height: 30%;
  background: white;
  position: relative;
  overflow-x: hidden;
  overflow-y: auto;

  .text {
    width: 100%;
    height: 90%;
  }
  .other-type {
    width: 100%;
    height: 90%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    video,
    img {
      width: auto;
      height: auto;
      max-height: 150px;
    }
    .file {
      border: solid #d0d0d0 1px;
      width: 300px;
      height: 80px;
      display: flex;
      align-items: center;
      justify-content: space-around;
      .file-icon {
        max-width: 64px;
        max-height: 64px;
      }
      .name-size {
        display: flex;
        flex-direction: column;
        span {
          width: 200px;
          text-align: left;
          overflow: hidden;
          text-overflow: ellipsis;
          word-break: break-all;
          word-wrap: break-word;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2;
        }
      }
    }
    .close-media {
      position: absolute;
      top: 0;
      right: 0;
      color: gray;
      cursor: pointer;
      &:hover {
        color: #4bbcfb;
      }
    }
  }

  .send {
    position: absolute;
    bottom: 0;
    right: 0;
    height: 32px;
    width: 80px;
    font-size: 16px;
    background-color: #f5f5f5;
    cursor: pointer;
    border: 3px solid white;
    &:hover {
      background-color: #129611;
      color: white;
    }
  }

  .errorMessages {
    width: 150px;
    color: red;
    border: #bdbdbd solid 1px;
    font-size: 14px;
    border-radius: 5px;
    text-align: center;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
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



