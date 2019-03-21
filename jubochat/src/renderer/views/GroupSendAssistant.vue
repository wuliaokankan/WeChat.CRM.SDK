<template>
  <div class="groupSend">
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
    <v-sendToFriends class="group-send-content" v-if="current===0"></v-sendToFriends>
    <v-sendToGroup class="group-send-content" v-else-if="current===1"></v-sendToGroup>
    <v-groupHistory class="group-send-content" v-else></v-groupHistory>
  </div>
</template>

<script>
import SendToFriends from '@/components/group/SendToFriends'
import SendToGroup from '@/components/group/SendToGroup'
import GroupHistory from '@/components/group/GroupHistory'
export default {
  data () {
    return {
      current: 0,
      tabs: ['群发好友', '群发群', '群发记录']
    }
  },
  methods: {
    select (index) {
      this.current = index
    }
  },
  components: {
    'v-sendToFriends': SendToFriends,
    'v-sendToGroup': SendToGroup,
    'v-groupHistory': GroupHistory
  }

}
</script>

<style lang="scss" scoped>
@import "../styles/globals.scss";
.groupSend {
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

.group-send-content {
  width: 82.5%;
  height: 98%;
  border-radius: 10px;
}
</style>
