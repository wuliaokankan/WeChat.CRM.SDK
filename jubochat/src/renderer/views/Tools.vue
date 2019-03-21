<template>
  <div class="tools">
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
    <v-commonly class="content" v-if="current===0"></v-commonly>
    <v-gm class="content" v-else-if="current===1"></v-gm>
  </div>
</template>

<script>
import Commonly from '@/components/wechatTools/Commonly.vue'
import GroupManagement from '@/components/wechatTools/GroupManagement.vue'
export default {
  data () {
    return {
      current: 0,
      tabs: ['常用功能', '群管理功能']
    }
  },
  methods: {
    select (index) {
      this.current = index
    }
  },
  components: {
    'v-commonly': Commonly,
    'v-gm': GroupManagement
  }

}
</script>

<style lang="scss" scoped>
.tools {
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

.content {
  background-color: #e4e7ea;
  width: 82.5%;
  height: 98%;
  border-radius: 10px;
}
</style>
