<template>
  <div id="friendBox">
    <div id="searchbox">
      <input type="search" name="" id="search" maxlength="20" placeholder="输入昵称">
      <i class="fa fa-search" aria-hidden="true"></i>
    </div>
    <div v-for="(item, index) in unreadMessages" :key="index" class="friend" :class="{select:current===index}" @click="addClass(index)">
      <img :src="item.portrait" alt="" />
      <div class="wxname">
        <p  v-if="item.memoName!==''" v-text="item.memoName"></p>
        <p  v-else v-text="item.nickname"></p>
        <p class="contentText" v-text="item.content[0].text"></p>
      </div>
      <p class="messages" v-text="item.content.length"></p>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      current: 0
    }
  },
  computed: {
    unreadMessages () {
      return this.$store.getters.unreadMessages
    }
  },
  methods: {
    addClass (index) {
      this.current = index
    }
  }
}
</script>
<style lang="scss" scoped>
@import '../../styles/globals';
#friendBox {
  width: 100%;
}
#searchbox {
  margin-top: 2px;
  background-color: $fc;
  margin-left: 5%;
  width: 90%;
}
#search {
  width: 80%;
}
.friend {
  display: flex;
  height: 50px;
  padding-left: 10px;
  cursor: pointer;
  position: relative;
}
.friend:hover {
  background-color: #d9d8d8;
}
.wxname {
  margin-left: 2px;
  margin-top: 5px;
  display: inline-block;
  white-space: nowrap;
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  // border: red 1px solid;
}
.select {
  background-color: #cac8c6;
}
img {
  width: 40px;
  height: 40px;
  margin-top: 5px;
}
.messages {
  width: 26px;
  height: 20px;
  color: red;
  background-color: yellowgreen;
  // border: 1px yellowgreen solid;
  border-radius: 10px;
  text-align: center;
}
.contentText{
  font-size: 10px;
  color: gray;
}
</style>
