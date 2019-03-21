<template>
  <div class="friend-circle">
    <!-- tabs  -->
    <div class="aside">
      <div style="height:4%;"></div>
      <div
        class="tab"
        @click="select(0)"
        :class="[{ selected: current === 0},{hover: current === 0}]"
      >发朋友圈</div>
      <div
        class="tab"
        @click="select(1)"
        :class="[{ selected: current === 1},{hover: current === 1}]"
      >我的朋友圈</div>
    </div>
    <!-- show -->
    <div class="content" v-if="wechatsLogin.length > 0">
      <keep-alive>
        <v-send v-if="current===0"></v-send>
        <v-interactive
          :wechatsLogin="wechatsLogin"
          :personalInfo="personalInfo"
          v-else-if="current===1"
        ></v-interactive>
        <v-history v-else></v-history>
      </keep-alive>
    </div>
    <div class="content-no" v-else>
      <div class="title"></div>
      <div class="content">没有微信登陆</div>
      <div class="bottom"></div>
    </div>
  </div>
</template>

<script>
import Send from '@/components/friendsCircle/Send'
import Interactive from '@/components/friendsCircle/Interactive'
import HistoryRecord from '@/components/friendsCircle/History'
import { mapGetters } from 'vuex'
export default {
  data () {
    return {
      current: 0
    }
  },
  computed: {
    ...mapGetters({
      wechatsLogin: 'wechatsLogin',
      personalInfo: 'personalInfo'
    })
  },
  methods: {
    select (index) {
      this.current = index
    }
  },
  components: {
    'v-send': Send,
    'v-interactive': Interactive,
    'v-history': HistoryRecord
  }

}
</script>

<style lang="scss" scoped>
.friend-circle {
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 100%;
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
    cursor: pointer;
    &:hover {
      color: #41c0fc;
      background: #d1d3d4;
    }
  }
  .selected {
    color: #4bfb9a;
    background-color: black;
  }
  .hover {
    &:hover {
      color: #4bfb9a;
      background-color: black;
    }
  }
}

.content,
.content-no {
  background-color: #e4e7ea;
  width: 82.5%;
  height: 98%;
  border-radius: 10px;
}

.content-no {
  .content {
    height: 90%;
    width: 100%;
    font-size: 18px;
    color: black;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
