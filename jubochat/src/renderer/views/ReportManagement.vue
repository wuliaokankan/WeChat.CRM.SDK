<template>
  <div class="report-management">
    <!-- tabs  -->
    <div class="aside">
      <div style="height:4%;"></div>
      <div
        v-for="(tab, index) in tabs"
        :key="index"
        :class="[{hover:index!==current},{selected:index===current}]"
        @click="select(index)"
        class="tab pointer"
        v-text="tab"
      ></div>
    </div>
    <!-- show -->
    <component class="report-content" :is="currentComponent"></component>
  </div>
</template>

<script>
import WechatFriends from '@/components/report/WechatFriends'
import DeletedFriends from '@/components/report/DeletedFriends'
import NotFriends from '@/components/report/NotFriends'
import NewFriends from '@/components/report/NewFriends'
import ServerRecord from '@/components/report/ServerRecord'
import TransferRecord from '@/components/report/TransferRecord'
import RedEnvelop from '@/components/report/RedEnvelop'
import Conversation from '@/components/report/Conversation'
export default {
  data () {
    return {
      current: 0,
      currentComponent: 'v-wf',
      tabs: ['微信好友', '删除好友', '被删好友', '新增好友', '服务记录', '转账记录', '红包统计', '会话统计']

    }
  },
  components: {
    'v-wf': WechatFriends,
    'v-df': DeletedFriends,
    'v-nf': NotFriends,
    'v-naf': NewFriends,
    'v-sr': ServerRecord,
    'v-tr': TransferRecord,
    'v-re': RedEnvelop,
    'v-c': Conversation
  },
  methods: {
    select (index) {
      this.current = index
      switch (index) {
        case 0:
          this.currentComponent = 'v-wf'
          break
        case 1:
          this.currentComponent = 'v-df'
          break
        case 2:
          this.currentComponent = 'v-nf'
          break
        case 3:
          this.currentComponent = 'v-naf'
          break
        case 4:
          this.currentComponent = 'v-sr'
          break
        case 5:
          this.currentComponent = 'v-tr'
          break
        case 6:
          this.currentComponent = 'v-re'
          break
        case 7:
          this.currentComponent = 'v-c'
          break
        default:
          this.currentComponent = 'v-wf'
          break
      }
    }
  }

}
</script>

<style lang="scss" scoped>
.report-management {
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 100%;
}
.aside {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #282b30;
  height: 98%;
  width: 15%;
  border-radius: 10px;
}
.tab {
  font-size: 18px;
  color: rgb(219, 219, 219);
  background: rgb(71, 75, 83);
  width: 90%;
  text-align: center;
  margin-top: 10%;
  border-radius: 5px;
}
.hover:hover {
  color: #41c0fc;
  background-color: rgb(209, 211, 212);
}
.selected {
  color: #3b7957;
  background-color: black;
}
.report-content {
  background-color: #e4e7ea;
  width: 82.5%;
  height: 98%;
  border-radius: 10px;
}
</style>

