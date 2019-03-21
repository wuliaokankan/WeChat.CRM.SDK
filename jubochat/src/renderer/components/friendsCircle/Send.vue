<template>
  <div class="friends-circle-send">
    <div class="title">
      请选择微信:
      <button @click="allCheck" v-text="word"></button>
      <button>设置不给谁看</button>
    </div>

    <div class="wechat-list scroll-x" v-if="wechatsLogin.length > 0">
      <div
        class="wechat flex-center"
        :class="{selected : wechatId.indexOf(wechat.WeChatId)>=0 }"
        v-for="(wechat, index) in wechatsLogin"
        :key="index"
        @click="choseWechat(wechat)"
      >
        <img :src="wechat.Avatar" alt>
        <div class="ellipsis" v-text="wechat.WeChatNick" :title="wechat.WeChatNick"></div>
      </div>
    </div>
    <div class="wechat-list-none" v-else>没有微信登陆</div>

    <div class="content">
      <div class="content-type">
        <div class="content-btn">
          <button :class="{ selected2 : type === 'text' }" @click="typeToggle(0)">纯文本</button>
          <button :class="{selected2:type === 'picture'}" @click="typeToggle(1)">图片</button>
          <button :class="{selected2:type === 'video'}" @click="typeToggle(2)">视频</button>
        </div>
        <div class="content-text">
          <textarea
            class="scroll"
            cols="30"
            rows="10"
            v-model="content"
            maxlength="500"
            placeholder="输入要发送的文字内容"
          ></textarea>
          <div>
            <i class="fa fa-smile-o" aria-hidden="true" title="表情"></i>
            <i class="fa fa-trash-o" aria-hidden="true" @click="deleteAllText" title="清空"></i>
          </div>
        </div>
        <div>
          <label style="visibility:hidden;" for="picture" v-if="type==='text'">上传图片</label>
          <label class="pointer" for="picture" v-if="type==='picture'">
            上传图片
            <i class="fa fa-picture-o" aria-hidden="true"></i>
          </label>
          <input
            v-show="false"
            id="picture"
            ref="picture"
            accept=".jpg, .png, .gif, .jpeg"
            type="file"
            @change="showPicture()"
          >
          <label class="pointer" for="video" v-if="type==='video'">
            上传视频
            <i class="fa fa-film" aria-hidden="true"></i>
          </label>
          <input
            v-show="false"
            id="video"
            ref="video"
            accept=".mp4"
            type="file"
            @change="showVideo()"
          >
        </div>
        <div class="comment">
          <textarea
            class="scroll"
            maxlength="300"
            cols="30"
            rows="5"
            placeholder="如果有需要，请输入评论"
            v-model="comment"
          ></textarea>
        </div>
      </div>
      <div class="content-preview">
        <p class="title-btn">效果预览</p>
        <p class="text-preview ellipsis2" :class="{ onlyText: type==='text'}" v-text="content">text</p>
        <div v-if="type==='picture'" class="attachment-preview">
          <div class="content-img" v-for="(img, index) in attachmentP" :key="index">
            <img :src="img.url" alt>
            <div class="click-div" @click="deleteImg(index)"></div>
          </div>
        </div>
        <div v-if="type==='video'" class="attachment-preview">
          <video
            class="video"
            :src="video.url"
            v-for="(video, index) in attachmentV"
            :key="index"
            controls
            autoplay
          ></video>
        </div>
        <p class="comment-preview ellipsis" v-text="comment"></p>
      </div>
    </div>

    <div class="btn">
      <button @click="submit">立即发送</button>
      <button style="background:gray;" @click="sendAfter">延时发送</button>
      <button style="background:gray;" @click="sendAfter">暂存</button>
    </div>

    <div class="bottom"></div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { validPicture, validVideo } from '@/utils/valid.js'
export default {
  data () {
    return {
      comment: '',
      word: '全选',
      wechatId: [],
      type: 'text',
      content: '',
      attachmentP: [],
      attachmentV: []
    }
  },
  computed: {
    ...mapGetters({
      wechatsLogin: 'wechatsLogin'
    })
  },
  methods: {
    choseWechat (wechat) {
      let id = this.wechatId.indexOf(wechat.WeChatId)
      if (id < 0) {
        this.wechatId.push(wechat.WeChatId)
      } else {
        this.wechatId.splice(id, 1)
      }
    },
    allCheck () {
      // console.log(this.word)
      if (this.word === '全选') {
        this.wechatId = []
        for (const wechat of this.wechatsLogin) {
          this.choseWechat(wechat)
        }
        this.word = '全不选'
      } else {
        this.wechatId = []
        this.word = '全选'
      }
    },
    showPicture () {
      console.log('showPicture')
      let picture = this.$refs.picture.files[0]
      console.log(picture)
      if (!validPicture(picture.path)) {
        this.$refs.picture.value = ''
        alert('图片格式只能是jpg|jpeg|png|gif')
      } else {
        if (picture) {
          let formdata = new FormData()
          formdata.append('myfile', picture)
          this.$store.dispatch('UploadFile', formdata).then(res => {
            console.log(res)
            this.$refs.picture.value = ''
            if (this.attachmentP.length >= 9) {
              alert('最多只能添加9张图片')
            } else {
              this.attachmentP.push(res)
            }
          }).catch(
            err => {
              console.log(err)
            })
        }
      }
      console.log(this.attachmentP)
    },
    showVideo () {
      console.log('上传视频')
      let video = this.$refs.video.files[0]
      console.log(video)
      if (!validVideo(video.path)) {
        this.$refs.picture.value = ''
        alert('图片格式只能是jpg|jpeg|png|gif')
      } else {
        if (video) {
          let formdata = new FormData()
          formdata.append('myfile', video)
          this.$store.dispatch('UploadFile', formdata).then(res => {
            console.log(res)
            this.$refs.video.value = ''
            this.attachmentV = []
            this.attachmentV.push(res)
          }).catch(
            err => {
              console.log(err)
              this.$refs.video.value = ''
            })
        }
      }
      console.log(this.attachmentV)
    },
    typeToggle (val) {
      switch (val) {
        case 0:
          this.type = 'text'
          console.log(this.type)
          break
        case 1:
          this.type = 'picture'
          break
        case 2:
          this.type = 'video'
          break
        default:
          this.type = 'text'
          break
      }
    },
    deleteImg (index) {
      this.attachmentP.splice(index, 1)
    },
    deleteAllText () {
      this.content = ''
    },
    submit () {
      console.log('立即发送朋友圈')
      if (!this.wechatId[0]) {
        console.log('未选择发送的微信')
      } else {
        if (this.type === 'text') {
          for (const id of this.wechatId) {
            let content = {
              WeChatId: id,
              Content: this.content,
              Comment: this.comment,
              Attachment: {
              },
              TaskId: 5
            }
            let msg = { 'MsgType': 'PostSNSNewsTask', 'Content': content }
            console.log(msg)
            this.socketApi.sendSock(msg)
          }
        } else if (this.type === 'picture') {
          for (const id of this.wechatId) {
            let pictureList = []
            for (const pic of this.attachmentP) {
              pictureList.push(pic.url)
            }
            let content = {
              WeChatId: id,
              Content: this.content,
              Comment: this.comment,
              Attachment: {
                Type: 2,
                Content: pictureList
              },
              TaskId: 5
            }
            let msg = { 'MsgType': 'PostSNSNewsTask', 'Content': content }
            console.log(msg)
            this.socketApi.sendSock(msg)
          }
        } else {
          let attVideo = []
          attVideo.push(this.attachmentV[0].url)
          for (const id of this.wechatId) {
            let content = {
              WeChatId: id,
              Content: this.content,
              Comment: this.comment,
              Attachment: {
                Type: 3,
                Content: attVideo
              },
              TaskId: 5
            }
            let msg = { 'MsgType': 'PostSNSNewsTask', 'Content': content }
            console.log(msg)
            this.socketApi.sendSock(msg)
          }
        }
      }
    },
    sendAfter () {
      alert('测试版，此功能暂不可用！')
    }
  }

}
</script>

<style lang="scss" scoped>
.friends-circle-send {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.title {
  color: white;
  button {
    background: #dddddd;
    color: black;
    margin-left: 10px;
    width: 50px;
    height: 18px;
    border-radius: 10px;
    cursor: pointer;
    &:hover {
      color: #4bbcfb;
    }
  }
  button:nth-child(2) {
    width: 100px;
  }
}

.wechat-list {
  width: 98%;
  height: auto;
  min-height: 70px;
  display: flex;
  flex-wrap: nowrap;
  overflow-y: hidden;
  overflow-x: auto;
  border: #4bbcfb 1px dashed;
  margin-left: 1%;
  .wechat {
    margin-top: 5px;
    width: 70px;
    min-width: 70px;
    height: 70px;
    font-size: 10px;
    margin-left: 2px;
    flex-direction: column;
    cursor: pointer;
    position: relative;
    div {
      width: 50px;
      text-align: center;
    }
    &:hover {
      &::before {
        content: "";
        width: 70px;
        height: 70px;
        background: gray;
        opacity: 0.6;
        position: absolute;
        top: 0px;
        left: 0px;
      }
    }
  }
}

.wechat-list-none {
  width: 98%;
  min-height: 70px;
  display: flex;
  border: #4bbcfb 1px dashed;
  margin-left: 1%;
  justify-content: center;
  align-items: center;
  font-size: 18px;
}

.selected {
  &::after {
    content: "✅";
    width: 70px;
    height: 70px;
    background: black;
    opacity: 0.6;
    position: absolute;
    top: 0px;
    left: 0px;
  }
}

.content {
  height: 60%;
  display: flex;
  justify-content: space-around;
  .content-type {
    width: 47%;
    display: flex;
    justify-content: space-around;
    flex-direction: column;
    align-items: center;
    background: white;
    .content-btn {
      width: 200px;
      height: 20px;
      display: flex;
      justify-content: space-around;
      button {
        width: 50px;
        border-radius: 10px;
        // background: black;
        background: #dddddd;
        color: black;
        &:hover {
          color: #4bbcfb;
          cursor: pointer;
        }
      }
      .selected2 {
        color: white;
        background: black;
      }
    }
    .content-text {
      width: 90%;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;
      position: relative;
      border: #4bbcfb dashed 1px;
      textarea {
        width: 100%;
        height: 200px;
      }
      div {
        width: 100%;
        display: flex;
        justify-content: flex-end;
        i {
          font-size: 18px;
          cursor: pointer;
          margin-right: 6px;
          &:hover {
            color: #4bbcfb;
          }
        }
      }
    }
    .comment {
      width: 90%;
      border: #4bbcfb dashed 1px;
      textarea {
        width: 100%;
      }
    }
  }
  .content-preview {
    width: 47%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
    background: white;
    .title-btn {
      height: 20px;
      width: 100px;
      line-height: 20px;
      background: black;
      text-align: center;
      color: white;
      border-radius: 10px;
    }
    .text-preview {
      height: 40px;
      width: 90%;
      border: #4bbcfb 1px dashed;
    }
    .attachment-preview {
      width: 90%;
      height: 60%;
      position: relative;
      border: #4bbcfb 1px dashed;
      display: flex;
      align-items: center;
      justify-content: space-around;
      flex-wrap: wrap;
      .content-img {
        width: 30%;
        height: 30%;
        position: relative;
        text-align: center;
        &:hover {
          cursor: pointer;
          &::before {
            content: "❌";
            position: absolute;
            background: rgba(0, 0, 0, 0.5);
            width: 100%;
            height: 100%;
            left: 0;
            top: 0;
            text-align: end;
          }
        }
        .click-div {
          width: 15px;
          height: 15px;
          position: absolute;
          right: 0;
          top: 0;
        }
        img {
          width: auto;
          height: auto;
          max-width: 100%;
          max-height: 100%;
        }
      }
    }
    .comment-preview {
      width: 90%;
      height: 18px;
      border: #4bbcfb 1px dashed;
    }
    .video {
      width: auto;
      height: auto;
      max-width: 100%;
      max-height: 100%;
    }
    .onlyText {
      height: 70%;
      overflow: hidden;
      text-overflow: ellipsis;
      word-break: break-all;
      word-wrap: break-word;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 10;
    }
  }
}

.btn {
  height: 2%;
  display: flex;
  justify-content: space-around;
  align-items: center;
  // border: #4bbcfb solid 1px;
  button {
    width: 80px;
    height: 20px;
    border-radius: 10px;
    background: black;
    color: white;
    font-size: 12px;
    line-height: 20px;
    cursor: pointer;
  }
}
img {
  width: 50px;
  height: 50px;
}
.bottom {
  height: 5%;
  width: 100%;
  background: #282b30;
  border-radius: 0 0 10px 10px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
</style>