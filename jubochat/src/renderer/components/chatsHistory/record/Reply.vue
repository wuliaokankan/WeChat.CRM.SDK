<template>
  <div class="reply">
    <div class="group" v-for="(type, index) in types" :key="index">
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
      <div class="content" v-show="type.show">
        <!-- <div v-for="(item, index2) in  replays " :key="index2">{{item|replayFilter(index) }}</div> -->
        <div
          class="content-bar"
          v-if="replays2(index).length>0"
          v-for="(item, index2) in  replays2(index) "
          :key="index2"
        >
          <span
            class="content-msg"
            :title="item.content"
            @click="sendMsg(item)"
            v-text="item.content"
          ></span>
          <i class="fa fa-trash-o" aria-hidden="true" title="删除该信息" @click="deleteMsg(item)"></i>
        </div>
        <div v-if="replays2(index).length===0">未添加消息</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
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
      replays: 'replays'
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
      SetReplays: 'SetReplays'
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
    sendMsg (msg) {
      console.log(msg)
      this.$store.dispatch('SetReplay', msg.content)
    },
    deleteMsg (msg) {
      msg['isDeleted'] = 1
      console.log(msg)
      this.$store.dispatch('DeleteReplayById', msg.id)
      this.SetReplays()
    },
    toggle (index) {
      let flag = this.types[index].show
      this.types[index].show = !flag
    },
    addReplay (groupId) {
      let replay = {
        groupId: groupId,
        groupName: '',
        content: '',
        unionId: this.user.UnionId,
        isDeleted: 0
      }
      this.$router.push({ name: 'replayDialog', params: { replay: replay } })
    }
  },
  mounted () {
    console.log('加载之后 获取常用消息')
    this.SetReplays()
  }

}
</script>

<style lang="scss" scoped>
@import "../../../styles/globals.scss";
.replay {
  display: flex;
  flex-direction: column;
}
.group {
  margin-top: 5px;
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
  margin-left: 10px;
  .content-bar {
    display: flex;
    align-items: center;
    justify-content: flex-start;
  }
  .content-msg {
    width: 80%;
    // border: 1px red solid;
    cursor: pointer;
    display: inline-block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    &:hover {
      background: gray;
    }
  }
}

.selected {
  color: #3b7957;
  background-color: #e4e7ea;
}
</style>

