<template>
  <div class="communicate">
    <div class="title flex-around"></div>
    <div class="output flex-center">测试版本，功能暂不开放！</div>
    <div class="bottom"></div>
  </div>
</template>

<script>
import { validMessages } from '@/utils/valid.js'
import { mapGetters, mapActions } from 'vuex'
import phiz from '@/utils/phiz.js'
import emojis from '@/utils/emoji.js'

export default {
  data () {
    return {
      messages: '',
      errorMessages: '',
      dialog: false,
      phizs: false,
      linkUrl: '',
      detailNow: '',
      replayDialog: false,
      replayImgUrl: ''
    }
  },
  watch: {
    replay (val) {
      this.messages = val
    }
  },
  filters: {
    transform (time) {
      let date = new Date(time)
      let h = date.getHours()
      let m = date.getMinutes()
      let month = date.getMonth() + 1
      if (m < 10) {
        m = '0' + m
      }
      let timeStr = date.getFullYear() + '-' + month + '-' + date.getDate() + ' ' + h + ':' + m
      return timeStr
    },
    toJson (val) {
      // console.log(val)
      let content = JSON.parse(val)
      return content.Thumb
    },
    linkF (val) {
      val = val.replace('[链接]<标题><![CDATA[邀请你加入群聊', '').replace('<描述><![CDATA[', '')
      val = val.replace('<链接><![CDATA[', '').replace(']]>', '').replace(']]>', '').replace(']]>', '')
      let reg = /https:.+/
      // this.linkUrl = val.match(reg)
      val = val.replace(reg, '')
      return val
    }
  },
  computed: {
    ...mapGetters({
      personalInfo: 'personalInfo',
      currentWechat: 'currentWechat',
      chats: 'chats',
      bigPicture: 'bigPicture',
      replay: 'replay'
    }),
    phizList () {
      return phiz.generatePhiz()
    },
    chats2 () {
      let chats = this.chats
      let chats2 = {}
      let reg = /\[[\u4e00-\u9fa5]{1,3}\]|\[\w{1,3}\]/gi
      let reg2 = /\\uE[\w]{3}/gi
      let reg3 = /%ue|%uE/g
      let reg4 = /%0A/g
      let reg5 = /\[\u9080\u8bf7\u4f60\u52a0\u5165\u7fa4\u804a\]/
      let i = 0
      if (chats[0]) {
        for (const chat of chats) {
          let val = chat.content
          // console.log('chat.content')
          // console.log(val)
          if (chat.contentType === 'Text') {
            // 如果是邀请加群
            if (val.match(reg5)) {
              console.log('这是邀请加群')
              chat['contentType'] = 'Link'
            } else {
              // 匹配QQ表情 [微笑]
              let results = val.match(reg)
              if (results) {
                for (const res of results) {
                  let r = res.replace('[', '').replace(']', '')
                  val = val.replace(res, '<img src="' + this.phizList[r] + '" alt="" >')
                }
              }
              // 匹配emoji
              let val2 = escape(val).replace(reg3, '\\ue')
              // console.log(val2)
              val2 = val2.replace(reg4, '<br/>')
              let results2 = val2.match(reg2)
              if (results2) {
                for (const emoji of results2) {
                  console.log(emoji)
                  let pngName = emojis.emojiArr[emoji.replace('\\u', '').toLowerCase()]
                  // console.log(pngName)
                  // val2 = val2.replace(emoji, '&#x' + pngName + ';')
                  val2 = val2.replace(emoji, '<img style="width:18px;height:18px;margin-right:1px;display:inline;" src="https://twemoji.maxcdn.com/2/72x72/' + pngName + '.png" alt="" >')
                }
              }
              chat['content'] = unescape(val2)
            }
          }
          chats2[i] = chat
          i++
        }
      }
      // console.log(chats2)
      return chats2
    }
  },
  methods: {
    ...mapActions({
      SetMd5: 'SetMd5',
      SetBigPicture: 'SetBigPicture'
    }),
    showDetail (chat) {
      console.log('显示细节')
      let content = JSON.parse(chat.content)
      console.log(chat)
      let md5 = content.Md5
      this.SetMd5(md5)
      if (chat.contentType === 'Picture') {
        this.SetBigPicture(md5)
        setTimeout(() => {
          if (this.bigPicture[0]) {
            this.detailNow = 'picture'
            this.dialog = true
          } else {
            let msg = { reqApi: 'RequestBigPicture', data: { msgId: chat.msgId, wechatId: chat.wechatId, friendId: chat.friendId, msgSvrId: content.MsgSvrId, md5: content.Md5 } }
            this.socketApi.sendSock(msg)
          }
        }, 100)
      } else if (chat.contentType === 'Video') {
        console.log('this is video')
        this.SetBigPicture(md5)
        setTimeout(() => {
          if (this.bigPicture[0]) {
            this.dialog = true
            this.detailNow = 'video'
          } else {
            console.log('数据库没有数据')
            let msg = { reqApi: 'RequestBigPicture', data: { msgId: chat.msgId, wechatId: chat.wechatId, friendId: chat.friendId, msgSvrId: content.MsgSvrId, md5: content.Md5 } }
            console.log(msg)
            this.socketApi.sendSock(msg)
          }
        }, 100)
      }
    },
    replayBig (imgUrl) {
      this.replayDialog = true
      this.replayImgUrl = imgUrl
    },
    closeDialog () {
      this.dialog = false
    },
    closeReplayDialog () {
      this.replayDialog = false
    },
    showPhiz (val) {
      console.log('选着表情')
      this.phizs = !val
    },
    selectPhize (key) {
      console.log(key)
      this.messages = this.messages + '[' + key + ']'
      this.phizs = false
    },
    openFile () {
      console.log('选择文件')
      let formdata = new FormData()
      let fileObj = this.$refs.file.files
      // console.log(fileObj)
      if (fileObj[0]) {
        let myType = this.$refs.file.files[0].type.replace(/\/.+/, '')
        // console.log(myType)
        switch (myType) {
          case 'image':
            myType = 2
            break
          case 'video':
            myType = 4
            break
          default:
            myType = 3
            break
        }
        formdata.append('myfile', fileObj[0])
        this.$store.dispatch('UploadFile', formdata).then(
          res => {
            // console.log('这里是communicate.vue')
            let WeChatId = this.personalInfo.wechatId
            let FriendId = this.personalInfo.friendId
            let msg = { reqApi: 'SendChatMsg', data: { messages: res.url, wechatId: WeChatId, friendId: FriendId, msgType: myType } }
            // console.log(msg)
            this.socketApi.sendSock(msg)
            this.$refs.file.value = ''

            let message = {
              WeChatId: WeChatId,
              FriendId: FriendId,
              MsgId: 'NULL',
              ContentType: myType,
              Content: res.url,
              time: new Date().getTime(),
              isRead: 'NULL',
              chatType: 1
            }
            this.$store.dispatch('SetChats', message)
            let info = {
              wechatId: WeChatId,
              friendId: FriendId
            }
            this.$store.dispatch('GetChats', info)
          }
        ).catch(err => console.log(err))
      } else {
        console.log('未选择文件')
      }
    },
    submit () {
      console.log('提交表单')
      let _this = this
      let messages = this.messages
      let msgType = 1
      if (this.personalInfo) {
        let WeChatId = this.personalInfo.wechatId
        let FriendId = this.personalInfo.friendId
        let info = {
          wechatId: WeChatId,
          friendId: FriendId
        }
        let msg = { reqApi: 'SendChatMsg', data: { messages: messages, wechatId: WeChatId, friendId: FriendId, msgType: msgType } }
        console.log(msg)
        if (validMessages(this.messages)) {
          this.socketApi.sendSock(msg)
          let message = {
            WeChatId: WeChatId,
            FriendId: FriendId,
            MsgId: 'NULL',
            ContentType: 'Text',
            Content: messages,
            time: new Date().getTime(),
            isRead: 'NULL',
            chatType: 1
          }
          this.$store.dispatch('SetChats', message)
          this.$store.dispatch('GetChats', info)
          this.messages = ''
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
    }
  }
}
</script>

<style lang="scss" scoped>
.communicate {
  background-color: #e4e7ea;
  width: 40%;
  height: 98%;
  display: flex;
  flex-direction: column;
  border-radius: 10px;
}

.title {
  color: white;
}

.output {
  height: 90%;
  width: 100%;
  display: flex;
  flex-direction: column;
  background: #e4e7ea;
  overflow-x: hidden;
  overflow-y: auto;
  img {
    width: 40px;
    height: 40px;
  }
  .time {
    height: 20px;
    width: 180px;
    border-radius: 5px;
    color: white;
    background: #dadada;
    margin-top: 5px;
  }
  .content {
    display: flex;
    width: 99%;
    margin-top: 5px;
    .triangle {
      max-width: 50%;
      margin-left: 7px;
      padding: 5px;
      border-radius: 5px;
      background: white;
      border: 1px solid #e7e7e7;
      text-align: start;
      position: relative;

      &::before {
        content: "";
        border: 6px solid transparent;
        border-right: 6px solid #e7e7e7;
        position: absolute;
        top: 10px;
        left: -13px;
      }
      &::after {
        content: "";
        border: 6px solid transparent;
        border-right: 6px solid white;
        position: absolute;
        top: 10px;
        left: -12px;
      }
    }
    .triangle:hover {
      background-color: #f6f6f6;
      &::after {
        content: "";
        border: 6px solid transparent;
        border-right: 6px solid #f6f6f6;
        position: absolute;
        top: 10px;
        left: -12px;
      }
    }
    .play {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      color: white;
      cursor: pointer;
      &:hover {
        color: chartreuse;
      }
    }
  }
}
.replyContent {
  display: flex;
  width: 100%;
  margin-top: 5px;
  justify-content: flex-end;
  .triangle {
    max-width: 50%;
    margin-right: 7px;
    padding: 5px;
    border-radius: 5px;
    background: #9eea6a;
    border: 1px solid #e7e7e7;
    text-align: start;
    position: relative;
    &::before {
      content: "";
      border: 6px solid transparent;
      border-left: 6px solid #e7e7e7;
      position: absolute;
      top: 10px;
      right: -13px;
    }
    &::after {
      content: "";
      border: 6px solid transparent;
      border-left: 6px solid #9eea6a;
      position: absolute;
      top: 10px;
      right: -12px;
    }
  }
  .triangle:hover {
    background-color: #98e165;
    &::after {
      content: "";
      border: 6px solid transparent;
      border-left: 6px solid #98e165;
      position: absolute;
      top: 10px;
      right: -12px;
    }
  }
}

.text {
  font-size: 13px;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  word-wrap: break-word;
  word-break: break-all;
}
.dialog {
  z-index: 6;
  border: solid white 1px;
  img,
  video {
    width: auto;
    height: auto;
    max-width: 420px;
    max-height: 600px;
  }
  .closeDialog {
    display: flex;
    justify-content: flex-end;
    &:hover {
      color: #129611;
    }
    cursor: pointer;
    position: absolute;
    top: 0;
    right: 0;
    z-index: 6;
  }
}
.voice {
  width: 60px;
  height: 20px;
  display: flex;
  justify-content: space-between;
  align-content: center;
  cursor: pointer;
  img {
    width: 18px;
    height: 18px;
  }
}
.img {
  width: auto;
  height: auto;
  max-width: 130px;
  max-height: 200px;
}
.bottom {
  display: flex;
  justify-content: space-around;
  align-items: center;
}
</style>

