<template>
  <div class="friends-circle-interactive">
    <!-- title -->
    <div class="title">
      <div class="current-wechat">
        <img :src="currentWechat.Avatar" alt>
        <span v-text="currentWechat.WeChatNick+' 的朋友圈'"></span>
      </div>
      <select id="cs" class="change-wechat" @change="changeWechat()">
        <option
          :value="wechat.WeChatId"
          v-for="(wechat, index) in wechatsLogin"
          :key="index"
          v-text="wechat.WeChatNick"
        ></option>
      </select>
    </div>
    <!-- content -->
    <!-- 本地有信息 -->
    <div v-if="currentCircles[0]" class="circle-content scroll" id="mycircle" @scroll="showScroll">
      <div class="content-box" v-for="(circle, index) in circlesFormated" :key="index">
        <div class="circles">
          <div class="circles-text" v-if="circle.content.Text" v-text="circle.content.Text"></div>
          <div class="circles-img" v-if="circle.content.Images">
            <div v-for="(img, index) in getImageUrl(circle.circleId)">
              <img
                class="right-img"
                v-if="img.ThumbImg.indexOf('http://www.jjldkj.com') === 0"
                :src="img.ThumbImg"
                :key="index"
              >
              <div class="wrong-img" v-else @click="regetImg(circle.circleId)">点击获取图片</div>
            </div>
          </div>
          <div class="circles-video" v-if="circle.content.Video">
            <video class="video" controls>
              <source :src="circle.content.Video.Url" type="video/mp4">
            </video>
          </div>
          <div class="public-time">
            <div class="show-time">
              <span>{{circle.publishTime | timeToString}}</span>
              <button @click="removeCircle(circle)">删除</button>
            </div>
            <div class="like-comment">
              <i class="fa fa-heart-o" aria-hidden="true" title="点赞"></i>
              <div class="btn-comment flex-center" title="评论">
                <span>..</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 本地没有信息 -->
    <div v-else class="circle-content-no flex-center" id="mycircle">
      <span>没有朋友圈信息</span>
    </div>
    <!-- bottom -->
    <div class="bottom">
      <span @click="getWechatCircle()">
        <i class="fa fa-spinner"></i> 获取最新朋友圈
      </span>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
// import websql from '@/utils/websql.js'

export default {
  name: 'interactive',
  data () {
    return {
      currentWechat: this.wechatsLogin[0],
      neetToGetUrl: [],
      hadGot: [],
      currentNumber: 1
    }
  },
  props: {
    wechatsLogin: {},
    personalInfo: {}
  },
  watch: {
    flags (val) {
      if (val === 'friendsCircle') {
        let info = {
          friendId: this.currentWechat.WeChatId,
          wechatId: this.currentWechat.WeChatId
        }
        this.SetCurrentCircles(info)
        console.log(this.$options.name)
        // setTimeout(() => {
        //   if (val === 'friendsCircle' && this.$options.name && this.$options.name === 'interactive') {
        //     let sh = document.getElementById('mycircle').scrollHeight
        //     let sch = (this.scrrentHeight - 60) * 0.98 * 0.9
        //     if (sh <= sch) {
        //       info['number'] = 2
        //     }
        //     this.SetCurrentCircles(info)
        //   }
        // }, 500)
      } else if (val === 'conversation') {
        if (this.personalInfo) {
          this.SetCurrentCircles(this.personalInfo)
        } else {
          // console.log('没有person')
          let info = {
            wechatId: '',
            friendId: ''
          }
          this.SetCurrentCircles(info)
        }
      }
    },
    neetToGetUrl (val) {
      if (val.length > 0) {
        for (const cid of val) {
          if (this.hadGot.indexOf(cid) === -1) {
            let msg = { 'MsgType': 'PullCircleDetailTask', 'Content': { WeChatId: this.currentWechat.WeChatId, CircleId: cid } }
            this.socketApi.sendSock(msg)
            this.hadGot.push(cid)
          }
        }
      }
    }
  },
  computed: {
    ...mapGetters({
      currentCircles: 'circles/currentCircles',
      hasNoRight: 'circles/hasNoRight',
      circlesDetail: 'circles/circlesDetail',
      flags: 'flags',
      scrrentHeight: 'scrrentHeight'
    }),
    circlesFormated () {
      let circlesFormated = []
      if (this.currentCircles[0]) {
        for (const circle of this.currentCircles) {
          let likes = ''
          let comments = ''
          if (circle.likes.indexOf('{') >= 0) {
            likes = JSON.parse(circle.likes)
          }
          if (circle.comments.indexOf('{') >= 0) {
            comments = JSON.parse(circle.comments)
          }
          let cc = {}
          cc['circleId'] = circle.circleId
          cc['publishTime'] = circle.publishTime
          cc['wechatId'] = circle.wechatId
          cc['friendId'] = circle.friendId
          cc['content'] = JSON.parse(circle.content)
          cc['likes'] = likes
          cc['comments'] = comments
          circlesFormated.push(cc)
        }
      }
      return circlesFormated
    }
  },
  filters: {
    timeToString (val) {
      return new Date(Number(val * 1000)).toLocaleString()
    }
  },
  methods: {
    ...mapActions({
      SetCurrentCircles: 'circles/SetCurrentCircles'
    }),
    // 切换朋友圈
    changeWechat () {
      console.log('切换微信')
      let id = document.getElementById('cs').value
      this.currentNumber = 1
      for (const wechat of this.wechatsLogin) {
        if (wechat.WeChatId === id) {
          this.currentWechat = wechat
          let info = {
            friendId: id,
            wechatId: id
          }
          this.SetCurrentCircles(info)
          // setTimeout(() => {
          //   let sh = document.getElementById('mycircle').scrollHeight
          //   let sch = (this.scrrentHeight - 60) * 0.98 * 0.9
          //   if (sh <= sch) {
          //     info['number'] = 2
          //   }
          //   this.SetCurrentCircles(info)
          // }, 500)
        }
      }
      console.log(this.currentWechat.WeChatId)
    },
    // 获取某个微信的朋友圈
    getWechatCircle () {
      let time = Math.floor(new Date().getTime() / 1000)
      let content = {
        WeChatId: this.currentWechat.WeChatId,
        StartTime: time
      }
      let msg = { MsgType: 'TriggerCirclePushTask', Content: content }
      this.socketApi.sendSock(msg)
      // let startTime = Math.floor(new Date().getTime() / 1000)
      // let content = { WeChatId: this.currentWechat.WeChatId, FriendId: this.currentWechat.WeChatId, StartTime: startTime, Count: 2 }
      // let msg = { 'MsgType': 'PullFriendCircleTask', 'Content': content }
      // console.log('获取自己的朋友圈')
      // console.log(msg)
      // this.socketApi.sendSock(msg)
    },
    // 获取图片详情
    getImageUrl (circleId) {
      if (this.circlesDetail && this.circlesDetail[circleId]) {
        // console.log('getImageUrl')
        // console.log(this.circlesDetail[circleId].Images)
        return this.circlesDetail[circleId].Images
      } else {
        if (this.neetToGetUrl.indexOf(circleId) >= 0) {
        } else {
          this.neetToGetUrl.push(circleId)
        }
      }
    },
    // 手动获取图片
    regetImg (circleId) {
      console.log('获取指定图片的详情')
      let msg = { 'MsgType': 'PullCircleDetailTask', 'Content': { WeChatId: this.personalInfo.wechatId, CircleId: circleId } }
      this.socketApi.sendSock(msg)
    },
    // 我的朋友圈触底刷新
    showScroll () {
      let st = document.getElementById('mycircle').scrollTop
      let sh = document.getElementById('mycircle').scrollHeight
      let ch = document.getElementById('mycircle').clientHeight
      if (sh <= Math.ceil(st + ch)) {
        console.log('到底了')
        // console.log(this.currentWechat)
        let info = {
          wechatId: this.currentWechat.WeChatId,
          friendId: this.currentWechat.WeChatId,
          number: this.currentNumber
        }
        this.currentNumber = this.currentNumber + 1
        this.SetCurrentCircles(info)
      }
    },
    // 点赞
    like (circle) {
      console.log('点赞')
      console.log(circle)
    },
    // 评论
    comment (circle) {
      console.log('评论')
      console.log(circle)
    },
    // 删除指定朋友圈
    removeCircle (circle) {
      console.log(circle)
      let content = { WeChatId: circle.wechatId, CircleId: circle.circleId, TaskId: 3 }
      let msg = { 'MsgType': 'DeleteSNSNewsTask', 'Content': content }
      console.log(msg)
      this.socketApi.sendSock(msg)
      // websql.deleteCircleById(circle.circleId)
      // let info = {
      //   wechatId: this.currentWechat.WeChatId,
      //   friendId: this.currentWechat.WeChatId,
      //   number: this.currentNumber
      // }
      // this.SetCurrentCircles(info)
    },
    // 回复评论
    replayComment (comment) {
      console.log(comment)
    }
  },
  mounted () {
    let info = {
      friendId: this.currentWechat.WeChatId,
      wechatId: this.currentWechat.WeChatId
    }
    console.log(info)
    this.SetCurrentCircles(info)
    // setTimeout(() => {
    //   if (this.flags === 'friendsCircle') {
    //     let sh = document.getElementById('mycircle').scrollHeight
    //     let sch = (this.scrrentHeight - 60) * 0.98 * 0.9
    //     if (sh <= sch) {
    //       info['number'] = this.currentNumber + 2
    //     }
    //     this.SetCurrentCircles(info)
    //   }
    // }, 500)
  }
}
</script>

<style lang="scss" scoped>
.friends-circle-interactive {
  width: 100%;
  height: 100%;
  .circle-content-no {
    height: 90%;
    span {
      background: black;
      color: white;
      width: 200px;
      border-radius: 10px;
      text-align: center;
      font-size: 16px;
    }
  }
}

.title {
  position: relative;
  .current-wechat {
    width: 90%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    span {
      color: white;
      margin-left: 10px;
    }
    img {
      width: 30px;
      height: 30px;
      border-radius: 15px;
    }
  }
  .change-wechat {
    width: 100px;
    max-width: 180px;
    position: absolute;
    top: 50%;
    right: 10px;
    transform: translate(-5px, -50%);
  }
}

.circle-content {
  height: 90%;
  flex-direction: column;
  overflow-x: hidden;
  overflow-y: auto;
  .content-box {
    width: 90%;
    margin-left: 5%;
    display: flex;
    flex-direction: column;
    align-items: center;
    background: #f8f8f8;
    border-bottom: 1px #dadada solid;

    .circles {
      width: 100%;
      margin-bottom: 10px;
      // border: solid red 1px;
      .circles-text {
        padding: 5px;
        font-size: 18px;
        width: 100%;
        // border: solid green 1px;
      }
      .circles-img {
        width: 90%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        div {
          width: 90%;
          margin: 5px auto;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
        }
        .right-img {
          width: 62%;
          height: auto;
        }
        .wrong-img {
          width: 100px;
          height: 100px;
          cursor: pointer;
          border: dashed red 1px;
        }
      }

      .circles-video {
        width: 100%;
        display: flex;
        justify-content: center;
        .video {
          width: auto;
          height: auto;
          min-width: 30%;
          max-width: 80%;
          max-height: 300px;
        }
      }

      .public-time {
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        // border: solid red 1px;
        .show-time {
          width: 300px;
          // border: solid red 1px;
          display: flex;
          align-items: center;
          margin-left: 5px;
          span {
            font-size: 16px;
            width: 200px;
            // border: solid red 1px;
          }
          button {
            margin-left: 10px;
            font-size: 16px;
            width: 80px;
            border-radius: 10px;
            cursor: pointer;
            &:hover {
              color: #4bbcfb;
              // background: black;
            }
          }
        }
        .like-comment {
          // border: solid red 1px;
          margin-right: 5px;
          display: flex;
          justify-content: space-around;
          align-items: center;
          width: 100px;
          i {
            cursor: pointer;
            font-size: 16px;
            &:hover {
              color: red;
            }
          }
          .btn-comment {
            height: 16px;
            width: 20px;
            background: #8593b0;
            position: relative;
            cursor: pointer;
            &::before {
              content: "";
              border: 5px solid transparent;
              border-right: 5px solid #8593b0;
              position: absolute;
              top: 3px;
              left: -8px;
            }
            span {
              color: #f8f8f8;
              font-size: 20px;
              line-height: 20px;
              position: absolute;
              top: -8px;
            }
            &:hover {
              background: #4bbcfb;
              &::before {
                border-right: 5px solid #4bbcfb;
              }
            }
          }
        }
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

.content-nocircle {
  width: 90%;
  margin-left: 5%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  span {
    width: 160px;
    height: 20px;
    line-height: 20px;
    border-radius: 10px;
    font-size: 12px;
    text-align: center;
    cursor: pointer;
    background: black;
    color: white;
  }
}

.bottom {
  i,
  span {
    color: white;
    cursor: pointer;
  }
  &:hover {
    i,
    span {
      color: #4bbcfb;
    }
  }
}
</style>