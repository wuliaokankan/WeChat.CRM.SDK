<template>
  <div class="communicate">
    <!-- personalInfo -->
    <div class="personalInfo title">
      <div v-if="noPerson" style="color:white">未选择好友</div>
      <div v-else class="flex-around" style="width:100%">
        <img v-if="personalInfo.avatar!=='undefined'" class="avatar" :src="personalInfo.avatar">
        <div v-else class="avatar flex-center">
          <i class="fa fa-spinner" aria-hidden="true"></i>
        </div>
        <div style="width:10px;">
          <i v-if="personalInfo.gender==='Female'" class="fa fa-user female" aria-hidden="true"></i>
          <i v-else class="fa fa-user male" aria-hidden="true"></i>
        </div>
        <div class="nickname pointer" :title="personalInfo.friendNick">
          <span v-text="personalInfo.friendNick"></span>
        </div>
        <div style="width:100px;">
          <span v-if="personalInfo.province !=='undefined'" v-text="personalInfo.province"></span>
          <span v-if="personalInfo.city !=='undefined'" v-text="personalInfo.city"></span>
        </div>
      </div>
    </div>

    <!-- output -->
    <div class="output" v-if="noPerson || !allChats[0]"></div>
    <show-chats v-else :chats="allChats"></show-chats>

    <!-- dialog  -->
    <!-- <dialog class="dialog" :open="dialog" v-if="bigPicture[0]">
      <img v-if="detailNow==='picture'" :src="bigPicture[0].content" alt>
      <video v-if="detailNow==='video'" autoplay controls>
        <source :src="bigPicture[0].content" type="video/mp4">
      </video>
      <i class="fa fa-times-circle fa-2x closeDialog" aria-hidden="true" @click="closeDialog()"></i>
    </dialog>
    <dialog class="dialog" :open="replayDialog">
      <img :src="replayImgUrl">
      <i
        class="fa fa-times-circle fa-2x closeDialog"
        aria-hidden="true"
        @click="closeReplayDialog()"
      ></i>
    </dialog>-->
    <!-- toolbar -->
    <tools-bar @tools-content="listenTools"></tools-bar>

    <!-- input -->
    <answer-window :toolsContent="toolsContent" :personalInfo="personalInfo"></answer-window>

    <!-- 快捷语 -->
    <div class="tips bottom">按ctrl + enter 快速发送信息</div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

import ToolsBar from './communicate/ToolsBar'
import AnswerWindow from './communicate/AnswerWindow'
import ShowChats from './communicate/ShowChats'

export default {
  data () {
    return {
      toolsContent: {},
      dialog: false,
      replayDialog: false,
      noPerson: true
    }
  },
  components: {
    'tools-bar': ToolsBar,
    'answer-window': AnswerWindow,
    'show-chats': ShowChats
  },
  computed: {
    ...mapGetters({
      personalInfo: 'personalInfo',
      allChats: 'chats/allChats'
    })
  },
  watch: {
    personalInfo (val) {
      if (val && JSON.stringify(val) !== '{}') {
        this.noPerson = false
        this.SetCurrentCircles(val)
      }
      this.SetShowFriendCircle(false)
      this.$store.dispatch('circles/SetHasNoRight', false)
    }
  },
  methods: {
    ...mapActions({
      SetShowFriendCircle: 'circles/SetShowFriendCircle',
      SetCurrentCircles: 'circles/SetCurrentCircles',
      SetCirclesDetail: 'circles/SetCirclesDetail'
    }),
    // replayBig (imgUrl) {
    //   this.replayDialog = true
    //   this.replayImgUrl = imgUrl
    // },
    // closeDialog () {
    //   this.dialog = false
    // },
    // closeReplayDialog () {
    //   this.replayDialog = false
    // },
    // 监控tools返回的数据
    listenTools (tc) {
      console.log('tools返回的数据')
      console.log(tc)
      this.toolsContent = tc
    }
  },
  mounted () {
    this.SetCirclesDetail()
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
  justify-content: space-between;
  align-items: center;
  border-radius: 10px;
  .output {
    height: 50%;
    width: 98%;
    background: #f5f5f5;
  }
}

.personalInfo {
  justify-content: space-around;
  i {
    font-size: 20px;
  }
  span {
    color: white;
  }
  .nickname {
    width: 120px;
    display: inline-block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    color: white;
  }
  .avatar {
    height: 30px;
    width: 30px;
    border-radius: 15px;
  }
}

.dialog {
  z-index: 6;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: solid white 1px;
  img,
  video {
    width: auto;
    height: auto;
    max-width: 600px;
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

.tips {
  width: 100%;
  color: white;
}
</style>

