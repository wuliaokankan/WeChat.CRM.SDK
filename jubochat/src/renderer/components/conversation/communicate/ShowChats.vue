<template>
  <div id="output" v-if="personalInfo" class="output scroll" @scroll="showScroll()">
    <div class="message" v-for="(chat, index) in reorganizationChats(chats)" :key="index">
      <!-- 消息发送/接收时间 -->
      <div class="time" v-if="chat.contentType!=='System'">{{ chat.time | transform }}</div>
      <!-- 收到的消息 -->
      <div v-if="chat.chatType===0" class="content">
        <!-- 好友的昵称头像 -->
        <img
          v-if="personalInfo.avatar!=='undefined' && chat.contentType!=='System'"
          class="avatar"
          :src="personalInfo.avatar"
        >
        <div v-else-if="personalInfo.avatar!=='undefined' && chat.contentType==='System'"></div>
        <div v-else class="avatar flex-center">
          <i class="fa fa-spinner fa-2x" aria-hidden="true"></i>
        </div>
        <!-- 文本 -->
        <div v-if="chat.contentType==='Text'" class="triangle text">{{chat.content | textFilter}}</div>
        <!-- 链接 -->
        <div v-else-if="chat.contentType==='Link'" class="triangle link">
          <span class="link-title">{{ linkJson(chat.content).Title }}</span>
          <div class="link-content">
            <span>{{ linkJson(chat.content).Des }}</span>
            <img :src="linkJson(chat.content).Thumb" alt>
          </div>
        </div>
        <!-- <div>{{ chat.content | decodeBase64 | linkFilter }}</div> -->
        <!-- 图片 -->
        <div v-else-if="chat.contentType==='Picture'" class="triangle picture" title="点击看大图">
          <img :src="chat.content | decodeBase64 | toJson" alt @click="showDetail(chat)">
        </div>
        <!-- 视频 -->
        <div
          v-else-if="chat.contentType==='Video'"
          class="triangle picture video"
          @click="showDetail(chat)"
          title="点击播放"
        >
          <img class="img pointer" :src="chat.content | decodeBase64 | toJson" :alt="chat.content">
          <i class="fa fa-play-circle-o fa-2x play" aria-hidden="true"></i>
        </div>
        <!-- 语音 -->
        <div v-else-if="chat.contentType==='Voice'" class="triangle voice" title="点击播放">
          <img src="static/img/voice.png" alt>2''
        </div>
        <!-- 系统通知 -->
        <div
          v-else-if="chat.contentType==='System'"
          class="content-system"
        >{{chat.content | textFilter}}</div>
        <!-- 文件 -->
        <div v-else-if="chat.contentType === 'File'" class="triangle file">
          <icon-font class="file-icon" height="64" width="64">
            <file-word
              v-if="decodeFile(chat.content).Des ==='doc'|| decodeFile(chat.content).Des==='docx'"
            />
            <file-xls
              v-else-if="decodeFile(chat.content).Des==='xls'|| decodeFile(chat.content).Des==='xlsx'"
            />
            <file-ppt
              v-else-if="decodeFile(chat.content).Des==='ppt'|| decodeFile(chat.content).Des==='pptx'"
            />
            <file-zip v-else-if="decodeFile(chat.content).Des==='zip'"/>
            <file-rar v-else-if="decodeFile(chat.content).Des==='rar'"/>
            <file-text
              v-else-if="decodeFile(chat.content).Des === 'text'|| decodeFile(chat.content).Des === 'txt'"
            />
            <file-mp3 v-else-if="decodeFile(chat.content).Des==='mp3'"/>
            <file-unknow v-else/>
          </icon-font>
          <div class="name-size">
            <span :title="decodeFile(chat.content).Title" v-text="decodeFile(chat.content).Title"></span>
            <span v-text="decodeFile(chat.content).Size+' B'"></span>
          </div>
        </div>
        <!-- 其他 -->
        <div v-else class="triangle text">{{ chat.contentType}}：{{chat.content}}</div>
      </div>
      <!-- 回复的消息 -->
      <div v-else-if="chat.chatType===1" class="replyContent">
        <!-- 文本 -->
        <div v-if="chat.contentType===1" class="triangle text">{{ chat.content | decodeBase64}}</div>
        <!-- 图片 -->
        <div
          v-else-if="chat.contentType===2"
          class="triangle"
          title="点击看大图"
          @click="replayBig(chat.content)"
        >
          <img class="replay-img pointer" :src="chat.content | decodeBase64">
        </div>
        <!-- 视频 -->
        <div v-else-if="chat.contentType===4" class="triangle" title="点击播放">
          <video class="video pointer" :src="chat.content | decodeBase64" type="video/mp4" controls></video>
        </div>
        <!-- 文件 -->
        <div v-else-if="chat.contentType === 8" class="triangle file">
          <!-- {{ decodeFileLocal(chat.content).fileType }} -->
          <icon-font class="file-icon" height="64" width="64" icon-color="white">
            <file-word
              v-if="decodeFileLocal(chat.content).fileType ==='doc'|| decodeFileLocal(chat.content).fileType==='docx'"
            />
            <file-xls
              v-else-if="decodeFileLocal(chat.content).fileType==='xls'|| decodeFileLocal(chat.content).fileType==='xlsx'"
            />
            <file-ppt
              v-else-if="decodeFileLocal(chat.content).fileType==='ppt'|| decodeFileLocal(chat.content).fileType==='pptx'"
            />
            <file-zip v-else-if="decodeFileLocal(chat.content).fileType==='zip'"/>
            <file-rar v-else-if="decodeFileLocal(chat.content).fileType==='rar'"/>
            <file-text
              v-else-if="decodeFileLocal(chat.content).fileType==='text'|| decodeFileLocal(chat.content).fileType==='txt'"
            />
            <file-mp3 v-else-if="decodeFileLocal(chat.content).fileType==='mp3'"/>
            <file-unknow v-else/>
          </icon-font>
          <div class="name-size">
            <span
              v-text="decodeFileLocal(chat.content).name"
              :title="decodeFileLocal(chat.content).name"
            ></span>
            <span v-text="decodeFileLocal(chat.content).size+' B'"></span>
          </div>
        </div>
        <!-- 其他 -->
        <div v-else class="triangle">{{chat.content}}-{{ chat.contentType}}</div>
        <!-- 微信头像 -->
        <img class="avatar pointer" :src="currentWechat.Avatar" alt>
      </div>
      <!-- 手机回复的消息 -->
      <div v-else-if="chat.chatType===2" class="replyContent">
        <!-- 文本 -->
        <div
          v-if="chat.contentType ==='Text'"
          class="triangle text"
        >{{ chat.content | decodeBase64}}</div>
        <!-- 图片 -->
        <div
          v-else-if="chat.contentType==='Picture'"
          class="triangle"
          title="点击看大图"
          @click="replayBig(chat.content)"
        >
          <img class="replay-img pointer" :src="chat.content | decodeBase64 | toJson">
        </div>
        <!-- 视频 -->
        <div
          v-else-if="chat.contentType===4 || chat.contentType === 'Video'"
          class="triangle"
          title="点击播放"
        >
          <video class="video pointer" :src="chat.content | decodeBase64" type="video/mp4" controls></video>
        </div>
        <!-- 文件 -->
        <div v-else-if="chat.contentType === 'File'" class="triangle file">
          <icon-font class="file-icon" height="64" width="64">
            <file-word
              v-if="decodeFile(chat.content).Des ==='doc'|| decodeFile(chat.content).Des==='docx'"
            />
            <file-xls
              v-else-if="decodeFile(chat.content).Des==='xls'|| decodeFile(chat.content).Des==='xlsx'"
            />
            <file-ppt
              v-else-if="decodeFile(chat.content).Des==='ppt'|| decodeFile(chat.content).Des==='pptx'"
            />
            <file-zip v-else-if="decodeFile(chat.content).Des==='zip'"/>
            <file-rar v-else-if="decodeFile(chat.content).Des==='rar'"/>
            <file-text
              v-else-if="decodeFile(chat.content).Des === 'text'|| decodeFile(chat.content).Des === 'txt'"
            />
            <file-mp3 v-else-if="decodeFile(chat.content).Des==='mp3'"/>
            <file-unknow v-else/>
          </icon-font>
          <div class="name-size">
            <span :title="decodeFile(chat.content).Title" v-text="decodeFile(chat.content).Title"></span>
            <span v-text="decodeFile(chat.content).Size+' B'"></span>
          </div>
        </div>
        <!-- 其他 -->
        <div v-else class="triangle">{{chat.content}}-{{ chat.contentType}}</div>
        <!-- 微信头像 -->
        <img class="avatar pointer" :src="currentWechat.Avatar" alt>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters, mapActions } from 'vuex'
import emojis from '@/utils/emoji.js'
import base64 from '@/utils/base64.js'
import { setTimeout } from 'timers'
export default {
  data () {
    return {
      detailNow: '',
      replayDialog: false,
      replayImgUrl: '',
      maxLength: 0,
      shl: 0
    }
  },
  props: {
    chats: {}
  },
  watch: {
    st (val) {
      document.getElementById('output').scrollTop = val
    },
    personalInfo (val) {
      setTimeout(() => {
        if (document.getElementById('output')) {
          let shl = document.getElementById('output').scrollHeight
          let chl = document.getElementById('output').clientHeight
          this.$store.dispatch('chats/SetST', shl - chl)
        }
      }, 500)
      this.shl = 0
    },
    shl (val) {
      if (val !== 0) {
        setTimeout(() => {
          let shc = document.getElementById('output').scrollHeight
          this.SetST(shc - val)
        }, 100)
      }
    }

  },
  filters: {
    //   时间戳转成localString
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
    decodeBase64 (val) {
      return base64.b64DecodeUnicode(val)
    },
    // 图片内容转换
    toJson (val) {
      // console.log(val)
      let reg = RegExp(/{"Thumb":"http:/)
      if (reg.test(val)) {
        // console.log(val)
        let content = JSON.parse(val)
        return content.Thumb
      }
      return val
    },
    textFilter (text) {
      let t = base64.b64DecodeUnicode(text)
      // let ct = escape(t)
      // console.log(ct)
      return t
    }
  },
  computed: {
    ...mapGetters({
      personalInfo: 'personalInfo',
      currentWechat: 'currentWechat',
      bigPicture: 'bigPicture',
      replay: 'replay',
      st: 'chats/st',
      allChatsLength: 'chats/allChatsLength'
    }),
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
          chat.content = base64.b64DecodeUnicode(chat.content)
          chats2[i] = chat
          i++
        }
      }
      for (const key in chats2) {
        if (chats2.hasOwnProperty(key)) {
          const element = chats2[key]
          let val = element.content
          if (element.contentType === 'Text') {
            if (val.match(reg5)) {
              // console.log('这是邀请加群')
              element['contentType'] = 'Link'
            } else {
              let results = val.match(reg)
              if (results) {
                for (const res of results) {
                  let r = res.replace('[', '').replace(']', '')
                  val = val.replace(res, '<img style="width:16px;height:16px;display:inline-block;" src="' + this.phizList[r] + '" alt="" >')
                }
              }
              let val2 = escape(val).replace(reg3, '\\ue')
              val2 = val2.replace(reg4, '<br/>')
              let results2 = val2.match(reg2)
              if (results2) {
                for (const emoji of results2) {
                  let pngName = emojis.emojiArr[emoji.replace('\\u', '').toLowerCase()]
                  // console.log(pngName)
                  // val2 = val2.replace(emoji, '&#x' + pngName + ';')
                  val2 = val2.replace(emoji, '<img style="width:16px;height:16px;margin-right:1px;display:inline;" src="https://twemoji.maxcdn.com/2/72x72/' + pngName + '.png" alt="" >')
                }
              }
              chats2[key].content = unescape(val2)
            }
          }
        }
      }
      // console.log(chats2)
      let chatsList = []
      let maxLength = 0
      for (const key in chats2) {
        if (chats2.hasOwnProperty(key)) {
          maxLength = key
        }
      }
      this.allChats = maxLength
      if (maxLength < 19) {
        this.theEnd = true
      } else {
        this.theEnd = false
      }
      console.log('maxLength' + maxLength)
      // console.log(maxLength)
      for (const key in chats2) {
        if (chats2.hasOwnProperty(key)) {
          chatsList.push(chats2[maxLength])
          maxLength--
        }
      }
      return chatsList
    }
  },
  methods: {
    ...mapActions({
      SetMd5: 'SetMd5',
      SetBigPicture: 'SetBigPicture',
      SetST: 'chats/SetST',
      SetSH: 'chats/SetSH',
      SetCH: 'chats/SetCH'
    }),
    // 滚动刷新
    showScroll () {
      let st = document.getElementById('output').scrollTop
      let sh = document.getElementById('output').scrollHeight
      let ch = document.getElementById('output').clientHeight
      // this.SetST(st)
      this.SetSH(sh)
      this.SetCH(ch)
      if (st === 0) {
        console.log('到顶了')
        if (this.maxLength < 20 || this.maxLength === this.allChatsLength) {
          console.log('没有更多消息')
        } else {
          let info = {
            wechatId: this.personalInfo.wechatId,
            friendId: this.personalInfo.friendId,
            number: this.maxLength + 20
          }
          this.shl = sh
          this.$store.dispatch('chats/SetAllChats', info)
          // this.SetST(sh - this.shl)
        }
      }
    },
    // 获取图片/视频详情
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
            let msg = { MsgType: 'RequestTalkDetailTask', Content: { MsgId: chat.msgId, WeChatId: chat.wechatId, FriendId: chat.friendId, MsgSvrId: content.MsgSvrId, Md5: content.Md5, GetOriginal: true } }
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
            let msg = { MsgType: 'RequestTalkDetailTask', Content: { WeChatId: chat.wechatId, FriendId: chat.friendId, MsgSvrId: content.MsgSvrId, Md5: content.Md5, GetOriginal: true } }
            console.log(msg)
            this.socketApi.sendSock(msg)
          }
        }, 100)
      }
    },
    // 显示大图
    replayBig (imgUrl) {
      this.replayDialog = true
      this.replayImgUrl = imgUrl
    },
    // 关闭大图
    closeDialog () {
      this.dialog = false
    },
    closeReplayDialog () {
      this.replayDialog = false
    },
    // 文件解码 转 JSON
    decodeFile (file) {
      let fs = JSON.parse(base64.b64DecodeUnicode(file))
      fs.Des = fs.Des.replace(', ', '')
      return fs
    },
    decodeFileLocal (file) {
      let fs = JSON.parse(base64.b64DecodeUnicode(file))
      return fs
    },
    // 图片转化
    decodePicture (content) {
      let fs = JSON.parse(base64.b64DecodeUnicode(content))
      return fs
    },
    // chats 重新排序
    reorganizationChats (chats) {
      // console.log('重组数据')
      let rec = {}
      let ml = 0
      for (const key in chats) {
        if (chats.hasOwnProperty(key)) {
          ml = key
        }
      }
      this.maxLength = Number(ml) + 1
      for (let i = 0; i <= ml; i++) {
        rec[i] = chats[ml - i]
      }
      return rec
    },
    // json link
    linkJson (link) {
      let val = base64.b64DecodeUnicode(link)
      let mv = val.replace('"Des":""', '"Des":"').replace(':"邀请你加入群聊"', '::邀请你加入群聊"').replace('"邀请你加入群聊', '邀请你加入群聊').replace('::邀请你加入群聊"', ':"邀请你加入群聊"')
      let jl = JSON.parse(mv)
      // console.log(jl)
      return jl
    }
  }
}
</script>

<style lang="scss" scoped>
.output {
  height: 50%;
  width: 98%;
  background: #f5f5f5;
  overflow-x: hidden;
  overflow-y: auto;
  .the-end {
    font-size: 16px;
    height: 20px;
    line-height: 20px;
    width: 100%;
    color: white;
    background: black;
    text-align: center;
    // border: 1px red solid;
  }
  .message {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;

    .time {
      font-size: 16px;
      height: 20px;
      line-height: 20px;
      width: 160px;
      border-radius: 5px;
      color: white;
      background: #dadada;
      margin-top: 5px;
    }

    .content {
      display: flex;
      width: 100%;
      margin-top: 5px;
      cursor: pointer;
      // border: solid red 1px;
      .avatar {
        height: 35px;
        width: 35px;
        cursor: pointer;
      }

      .triangle {
        max-width: 50%;
        margin-left: 7px;
        padding: 5px;
        border-radius: 5px;
        background: white;
        border: 1px solid #e7e7e7;
        text-align: start;
        position: relative;
        display: flex;
        align-items: center;
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
        &:hover {
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
      }

      .content-system {
        width: 70%;
        font-size: 14px;
        height: 20px;
        line-height: 20px;
        margin-left: 15%;
        color: white;
        background: #dadada;
      }

      .link {
        display: flex;
        flex-direction: column;
        .link-title {
          font-size: 16px;
          width: 100%;
        }
        .link-content {
          font-size: 12px;
          display: flex;
          justify-content: center;
          align-items: center;
          img {
            height: 50px;
            width: 50px;
          }
        }
      }

      .picture {
        img {
          width: auto;
          height: auto;
          max-width: 120px;
          // min-width: 100px;
        }
      }

      .text {
        font-size: 13px;
        flex-wrap: wrap;
        word-wrap: break-word;
        word-break: break-all;
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

    .replyContent {
      display: flex;
      width: 100%;
      margin-top: 5px;
      justify-content: flex-end;

      .avatar {
        height: 35px;
        width: 35px;
        cursor: pointer;
      }

      .replay-img {
        width: auto;
        height: auto;
        max-width: 120px;
        // max-height: 120px;
        // min-width: 120px;
      }

      .text {
        font-size: 13px;
        flex-wrap: wrap;
        word-wrap: break-word;
        word-break: break-all;
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

      .video {
        width: auto;
        height: auto;
        max-width: 120px;
        // min-height: 120px;
        cursor: pointer;
      }

      .triangle {
        max-width: 50%;
        margin-right: 7px;
        padding: 5px;
        border-radius: 5px;
        background: #9eea6a;
        border: 1px solid #e7e7e7;
        text-align: start;
        position: relative;
        display: flex;
        align-items: center;

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

        &:hover {
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
    }
  }
  .file {
    border: solid #d0d0d0 1px;
    max-width: 200px;
    height: 80px;
    display: flex;
    align-items: center;
    .file-icon {
      max-width: 48px;
      max-height: 48px;
    }
    .name-size {
      display: flex;
      flex-direction: column;
      span {
        width: 150px;
        margin-left: 10px;
        // border: solid red 1px;
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
</style>
