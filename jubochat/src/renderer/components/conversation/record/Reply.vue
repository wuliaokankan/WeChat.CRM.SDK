<template>
  <div class="reply">
    <div class="group" v-for="(type, index) in types" :key="index">
      <!-- title -->
      <div class="group-title">
        <div class="group-name">
          <i
            v-show="type.show"
            class="fa fa-chevron-circle-down"
            aria-hidden="true"
            title="下拉"
            @click="toggle(index)"
          ></i>
          <i
            v-show="!type.show"
            class="fa fa-chevron-circle-right"
            aria-hidden="true"
            title="隐藏"
            @click="toggle(index)"
          ></i>
          <p class="pointer" v-text="type.title" @click="toggle(index)"></p>
        </div>
        <div class="add">
          <i class="fa fa-plus-circle" aria-hidden="true" title="添加常用语" @click="addReplay(index)"></i>
        </div>
      </div>
      <!-- content -->
      <div class="content" v-show="type.show">
        <div class="content-box" v-for="(item, index2) in replays2(index)" :key="index2">
          <!-- text -->
          <div v-if="item.contentType === 0">
            <span
              class="content-msg"
              v-if="item.remark"
              :title="item.content"
              @click="sendMsg(item)"
              v-text="item.remark"
            ></span>
            <span
              v-else
              class="content-msg"
              :title="item.content"
              @click="sendMsg(item)"
              v-text="item.content"
            ></span>
            <i class="fa fa-trash-o" aria-hidden="true" title="删除该信息" @click="deleteMsg(item)"></i>
          </div>
          <!-- picture -->
          <div class="media" v-else-if="item.contentType === 1">
            <div>
              <span
                v-if="item.remark"
                class="content-msg"
                :title="item.content"
                @click="sendMsg(item)"
                v-text="item.remark"
              ></span>
              <span v-else class="content-msg" :title="item.content" @click="sendMsg(item)">这是图片</span>
              <i class="fa fa-trash-o" aria-hidden="true" title="删除该信息" @click="deleteMsg(item)"></i>
            </div>
            <img v-if="item.content" :src="item.content" @click="sendMsg(item)" alt>
          </div>
          <!-- video -->
          <div class="media" v-else-if="item.contentType ===2">
            <div>
              <span
                class="content-msg"
                v-if="item.remark"
                @click="sendMsg(item)"
                v-text="item.remark"
              ></span>
              <span class="content-msg" v-else :title="item.content">这是视频</span>
              <i class="fa fa-trash-o" aria-hidden="true" title="删除该信息" @click="deleteMsg(item)"></i>
            </div>
            <video :src="item.content" @click="sendMsg(item)"></video>
          </div>
        </div>
        <div v-if="replays2(index).length===0">未添加消息</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import websql from '../../../utils/websql'

export default {
  data () {
    return {
      types: [
        { title: '欢迎语', show: true },
        { title: '结束语', show: false },
        { title: '引导语', show: false },
        { title: '沟通语', show: false },
        { title: '收藏', show: false }
      ]
    }
  },
  computed: {
    ...mapGetters({
      user: 'user',
      replays: 'replays',
      personalInfo: 'personalInfo',
      currentWechat: 'currentWechat'
    })

  },
  filters: {
    replayFilter (val, index) {
      console.log(index)
      if (val.groupId === index) {
        console.log(val)
        return val.content
      }
    }
  },
  methods: {
    ...mapActions({
      SetReplays: 'SetReplays',
      SetCommonReplay: 'chats/SetCommonReplay'
    }),
    replays2 (groupId) {
      let newReplays = []
      if (this.replays['0']) {
        for (const replay of this.replays) {
          if (replay.groupId === groupId) {
            newReplays.push(replay)
          }
        }
      }
      return newReplays
    },
    // 发送常用消息
    sendMsg (msg) {
      this.SetCommonReplay(msg)
    },
    // 删除常用消息
    deleteMsg (msg) {
      console.log('删除常用消息')
      console.log(msg)
      let db = this.$store.getters.db
      websql.deletedReplays(db, msg)
      this.SetReplays()
    },
    // 切换常用消息
    toggle (index) {
      let flag = this.types[index].show
      this.types[index].show = !flag
    },
    // 添加常用消息
    addReplay (groupId) {
      let replay = {
        groupId: groupId,
        groupName: '',
        unionId: this.user.UnionId,
        isDeleted: 0
      }
      this.$router.push({ name: 'replayDialog', params: { replay: replay } })
    }
  },
  mounted () {
    // console.log('加载之后 获取常用消息')
    this.SetReplays()
  }

}
</script>

<style lang="scss" scoped>
.replay {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
.group {
  width: 100%;
  height: 100%;
  margin-top: 5px;
  margin-left: 3%;
}
.group-title {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
  font-size: 14px;
  height: 20px;
  .group-name {
    display: flex;
    align-items: center;
    justify-content: flex;
    width: 100px;
    p {
      margin-left: 5px;
    }
  }
}
i {
  cursor: pointer;
  &:hover {
    color: #4bbcfb;
  }
}

.content {
  width: 90%;
  height: 100%;
  margin-left: 10px;
  .content-box {
    margin-bottom: 10px;
    background: white;
    // border: solid red 1px;
  }
  .content-bar {
    display: flex;
    align-items: center;
    justify-content: flex-start;
  }
  .content-msg {
    width: 80%;
    cursor: pointer;
    display: inline-block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    margin-left: 8px;
    margin-top: 6px;
    &:hover {
      background: gray;
    }
  }

  .media {
    img,
    video {
      width: auto;
      height: auto;
      max-width: 70%;
      max-height: 180px;
      margin-left: 8px;
      cursor: pointer;
    }
  }
}

.selected {
  color: #3b7957;
  background-color: #e4e7ea;
}
</style>

