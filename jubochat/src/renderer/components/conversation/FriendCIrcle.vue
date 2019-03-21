<template>
  <div class="friend-circle">
    <div class="title">
      <span
        v-if="personalInfo && personalInfo.friendNick"
        v-text="personalInfo.friendNick+'的朋友圈'"
        :title="personalInfo.friendNick"
      ></span>
      <span v-else>请先选择好友</span>
      <i v-show="false" class="fa fa-refresh" aria-hidden="true"></i>
    </div>
    <!-- 本地有朋友圈数据 -->
    <div class="circle-content scroll" id="circles" v-if="currentCircles[0]" @scroll="showScroll()">
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
            <span>{{circle.publishTime | timeToString}}</span>
            <i class="fa fa-heart-o" aria-hidden="true" title="点赞"></i>
            <div class="btn-comment flex-center" title="评论">
              <span>..</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 本地没有朋友圈数据 -->
    <div v-else class="content-nocircle">
      <!-- 正在获取朋友圈数据 -->
      <div v-show="!hasNoRight" class="sc">
        <icon-font2 height="24" width="24" icon-color="black">
          <spinning-circle/>
        </icon-font2>
        <span>正在获取该好友的朋友圈</span>
      </div>
      <!-- 获取不到朋友圈数据 -->
      <div v-show="hasNoRight" class="sc">
        <span>对方没有发朋友圈</span>
        <span>或者你没有权限</span>
      </div>
    </div>
    <div class="bottom">
      <span @click="getFriendCircle()">
        <i class="fa fa-spinner"></i> 获取最新朋友圈
      </span>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  data () {
    return {
      neetToGetUrl: [],
      hadGot: [],
      currentNumber: 1
    }
  },
  computed: {
    ...mapGetters({
      personalInfo: 'personalInfo',
      currentWechat: 'currentWechat',
      currentCircles: 'circles/currentCircles',
      hasNoRight: 'circles/hasNoRight',
      circlesDetail: 'circles/circlesDetail'
    }),
    circlesFormated () {
      // console.log('重组后的好友的朋友圈信息：friendCirclesFormated')
      let circlesFormated = []
      if (this.currentCircles[0]) {
        for (const circle of this.currentCircles) {
          let cc = {}
          cc['circleId'] = circle.circleId
          cc['publishTime'] = circle.publishTime
          cc['wechatId'] = circle.wechatId
          cc['friendId'] = circle.friendId
          cc['content'] = JSON.parse(circle.content)
          circlesFormated.push(cc)
        }
        // console.log(circlesFormated)
      }
      return circlesFormated
    }
  },
  filters: {
    timeToString (val) {
      return new Date(Number(val * 1000)).toLocaleString()
    }
  },
  watch: {
    neetToGetUrl (val) {
      if (val.length > 0) {
        for (const cid of val) {
          if (this.hadGot.indexOf(cid) === -1 && this.personalInfo) {
            let msg = { 'MsgType': 'PullCircleDetailTask', 'Content': { WeChatId: this.personalInfo.wechatId, CircleId: cid } }
            this.socketApi.sendSock(msg)
            this.hadGot.push(cid)
          }
        }
      }
    },
    personalInfo () {
      this.currentNumber = 1
    }
  },
  methods: {
    ...mapActions({
      SetCurrentCircles: 'circles/SetCurrentCircles'
    }),
    // 获取图片的详情
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
    regetImg (circleId) {
      console.log('获取指定图片的详情')
      let msg = { 'MsgType': 'PullCircleDetailTask', 'Content': { WeChatId: this.personalInfo.wechatId, CircleId: circleId } }
      this.socketApi.sendSock(msg)
    },
    // 获取最新的朋友圈
    getFriendCircle () {
      let startTime = Math.floor(new Date().getTime() / 1000)
      let content = { WeChatId: this.personalInfo.wechatId, FriendId: this.personalInfo.friendId, StartTime: startTime, Count: 2 }
      let msg = { 'MsgType': 'PullFriendCircleTask', 'Content': content }
      this.socketApi.sendSock(msg)
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
    showScroll () {
      let st = document.getElementById('circles').scrollTop
      let sh = document.getElementById('circles').scrollHeight
      let ch = document.getElementById('circles').clientHeight
      if (sh <= Math.ceil(st + ch)) {
        console.log('到底了')
        let info = {
          wechatId: this.personalInfo.wechatId,
          friendId: this.personalInfo.friendId,
          number: this.currentNumber
        }
        this.currentNumber = this.currentNumber + 1
        this.SetCurrentCircles(info)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.friend-circle {
  width: 20%;
  height: 98%;
  .no-person {
    color: red;
    height: 90%;
    font-size: 16px;
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

.title {
  color: white;
  text-align: center;
  span {
    color: white;
    width: 70%;
    display: inline-block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  i {
    color: white;
    cursor: pointer;
    &:hover {
      color: #4bbcfb;
    }
  }
}

.circle-content {
  height: 90%;
  flex-direction: column;
  overflow-x: hidden;
  overflow-y: auto;

  .content-box {
    // border: solid red 1px;
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
        font-size: 14px;
        width: 100%;
        // border: solid green 1px;
      }
      .circles-img {
        width: 100%;
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
          width: 90%;
          height: auto;
        }
        .wrong-img {
          width: 90%;
          height: 40px;
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
        i {
          cursor: pointer;
          &:hover {
            color: red;
          }
        }
        .btn-comment {
          height: 10px;
          width: 12px;
          background: #8593b0;
          position: relative;
          cursor: pointer;
          &::before {
            content: "";
            border: 2.5px solid transparent;
            border-right: 2.5px solid #8593b0;
            position: absolute;
            top: 2.5px;
            left: -5px;
          }
          span {
            color: #f8f8f8;
            font-size: 12px;
            line-height: 12px;
            position: absolute;
            top: -5px;
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

.content-nocircle {
  width: 90%;
  margin-left: 5%;
  height: 90%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;

  .sc {
    background: black;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 30px;
    width: 200px;
    border-radius: 10px;
    span {
      height: 20px;
      line-height: 20px;
      border-radius: 10px;
      font-size: 12px;
      text-align: center;
      // background: black;
      color: white;
    }
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