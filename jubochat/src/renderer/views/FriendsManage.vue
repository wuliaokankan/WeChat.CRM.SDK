<template>
  <div class="accountManage">
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
    <div class="content common">
      <component class="component" :is="currentComponent"></component>
    </div>
  </div>
</template>

<script>
import AcceptRequest from '@/components/friendManage/AcceptRequest'
import AddFriend from '@/components/friendManage/AddFriend'
import AutoAccept from '@/components/friendManage/AutoAccept'
export default {
  data () {
    return {
      current: 0,
      currentComponent: 'v-ar',
      tabs: ['好友请求', '添加好友', '自动通过']
      // tabs: ['通过好友请求', '添加好友', '载线日志', '机器人', '小程序商城']

    }
  },
  components: {
    'v-ar': AcceptRequest,
    'v-af': AddFriend,
    'v-aa': AutoAccept
  },
  methods: {
    select (index) {
      this.current = index
      switch (index) {
        case 0:
          this.currentComponent = 'v-ar'
          break
        case 1:
          this.currentComponent = 'v-af'
          console.log('获取已经添加的号码')
          this.$store.dispatch('addfriends/SetHistory')
          break
        case 2:
          this.currentComponent = 'v-aa'
          console.log('获取自动通过好友请求的配置')
          this.$store.dispatch('addfriends/SetAutoAccept')
          break
        default:
          this.currentComponent = 'v-af'
          break
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.accountManage {
  display: flex;
  width: 100%;
  justify-content: space-around;
  align-items: center;
}
.aside {
  display: flex;
  background-color: #282b30;
  flex-direction: column;
  align-items: center;
  height: 98%;
  width: 15%;
  border-radius: 10px;
  .tab {
    font-size: 16px;
    color: gray;
    background: #474b53;
    width: 90%;
    text-align: center;
    margin-top: 10%;
    border-radius: 5px;
  }
  .hover:hover {
    color: #41c0fc;
    background-color: #d1d3d4;
  }
  .selected {
    color: #4bfb9a;
    background-color: black;
  }
}
$bgcolor: #e4e7ea;
.content {
  background-color: $bgcolor;
  width: 82.5%;
  position: relative;
}
.component {
  height: 100%;
  width: 100%;
}
</style>


