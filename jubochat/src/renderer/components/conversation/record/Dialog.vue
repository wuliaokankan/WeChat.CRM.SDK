<template>
  <div class="dialog-bg">
    <div class="dialog">
      <div class="check-box">
        <button :class="{selected:current===0}" @click="toggle(0)">文字</button>
        <button :class="{selected:current===1}" @click="toggle(1)">图片</button>
        <button :class="{selected:current===2}" @click="toggle(2)">视频</button>
      </div>

      <div class="content">
        <input class="remark" type="text" v-model="remark" placeholder="添加备注">
        <textarea v-if="current===0" cols="30" rows="10" placeholder="输入常用语" v-model="text"></textarea>
        <div class="media" v-if="current===1">
          <label for="addImages">点击添加图片</label>
          <input
            ref="picture"
            v-show="false"
            type="file"
            id="addImages"
            accept=".jpg, .png, .gif, .jpeg"
            @change="showPicture"
          >
          <img v-if="picture" :src="picture" alt>
        </div>
        <div class="media" v-if="current===2">
          <label for="addVideo">点击添加视频</label>
          <input
            ref="video"
            v-show="false"
            type="file"
            id="addVideo"
            accept=".mp4"
            @change="showVideo"
          >
          <video class="video" v-if="video" :src="video" controls></video>
        </div>
      </div>

      <div class="submit-box">
        <button class="cancle" @click="cancle">取消</button>
        <button class="submit" @click="submit">提交</button>
      </div>
    </div>
  </div>
</template>

<script>
import { validPicture, validVideo } from '@/utils/valid.js'
import websql from '../../../utils/websql'
export default {
  data () {
    return {
      current: 0,
      text: '',
      pictures: [],
      videos: [],
      picture: '',
      video: '',
      remark: ''
    }
  },
  methods: {
    cancle () {
      this.$router.push({ path: '/main' })
    },
    toggle (val) {
      if (this.current !== val) {
        this.current = val
      }
    },
    showPicture () {
      // console.log('showPicture')
      let picture = this.$refs.picture.files[0]
      if (picture && validPicture(picture.path)) {
        console.log('图片存在并符合要求')
        let formdata = new FormData()
        formdata.append('myfile', picture)
        this.$store.dispatch('UploadFile', formdata).then(res => {
          this.picture = res.url
          this.$refs.picture.value = ''
        }).catch(
          err => {
            console.log(err)
          })
      } else {
        this.$refs.picture.value = ''
        alert('图片格式只能是jpg|jpeg|png|gif')
      }
    },
    showVideo () {
      console.log('showVideo')
      let video = this.$refs.video.files[0]
      if (video && validVideo(video.path)) {
        console.log('视频存在并符合要求')
        let formdata = new FormData()
        formdata.append('myfile', video)
        this.$store.dispatch('UploadFile', formdata).then(res => {
          this.video = res.url
          this.$refs.video.value = ''
        }).catch(
          err => {
            console.log(err)
            alert('上传视频失败，请检查网络！')
            this.$refs.video.value = ''
          })
      } else {
        this.$refs.video.value = ''
        alert('视频格式只能是MP4')
      }
    },
    submit () {
      console.log('添加')
      let replay = this.$route.params.replay
      replay['remark'] = this.remark
      replay['contentType'] = this.current
      switch (this.current) {
        case 0:
          replay['content'] = this.text
          break
        case 1:
          replay['content'] = this.picture
          break
        case 2:
          replay['content'] = this.video
          break
        default:
          replay['content'] = this.text
          break
      }
      console.log(replay)
      if (replay.content) {
        let db = this.$store.getters.db
        websql.replaceReplays(db, replay)
        this.$store.dispatch('SetReplays')
        this.$router.push({ path: '/main' })
      } else {
        alert('添加的内容不能为空')
      }
    }
  }

}
</script>

<style lang="scss" scoped>
.dialog-bg {
  z-index: 7;
  width: 100%;
  height: 100%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  position: absolute;
  background: rgba(0, 0, 0, 0.5);
}

.dialog {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 50%;
  height: 50%;
  background: white;
  opacity: 1;
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex-direction: column;

  .check-box {
    width: 300px;
    display: flex;
    justify-content: space-around;
    align-items: center;
    button {
      width: 80px;
      height: 20px;
      line-height: 20px;
      cursor: pointer;
      border-radius: 10px;
      font-size: 14px;
      // font-weight: 400;
      background: #dddddd;
      &:hover {
        color: #4bbcfb;
      }
    }
    .selected {
      background: black;
      color: white;
    }
  }

  .content {
    width: 80%;
    height: 70%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-direction: column;

    .remark {
      width: 100%;
      height: 8%;
      border: #4bbcfb 1px dashed;
      padding: 2px;
    }

    textarea,
    .media {
      width: 100%;
      height: 85%;
      border: #4bbcfb 1px dashed;
      padding: 2px;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;
      label {
        width: 120px;
        height: 20px;
        line-height: 20px;
        text-align: center;
        font-size: 16px;
        border-radius: 10px;
        font-weight: 500;
        cursor: pointer;
        background: #dddddd;
        &:hover {
          color: #4bbcfb;
        }
      }
      img,
      .video {
        width: auto;
        height: auto;
        max-width: 80%;
        max-height: 80%;
      }
    }
  }

  .submit-box {
    width: 180px;
    display: flex;
    justify-content: space-between;
    .cancle,
    .submit {
      width: 80px;
      background: black;
      color: white;
      font-size: 16px;
      border-radius: 5px;
      cursor: pointer;
      &:hover {
        color: #4bbcfb;
      }
    }
  }
}
</style>
