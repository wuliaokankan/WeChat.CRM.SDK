<template>
  <div class="newFriends">
    <div>功能开发中，敬请期待</div>
    <!-- <div v-for="(friend, index) in newFriends" :key="index" class="friend">
      <div
        v-if="currentWechat.wechatNumber===friend.newFriends[0].pwechat"
        class="newFriend"
        v-for="(newFriend, index) in friend.newFriends"
        :key="index"
      >
        <img :src="newFriend.avator" :alt="newFriend.nickname">
        <div class="nd">
          <div class="nickname" v-text="newFriend.nickname"></div>
          <div class="discribe pointer" v-text="newFriend.discribe" :title="newFriend.discribe"></div>
        </div>
        <div style="width:20%;">
          <div class="pointer" v-if="newFriend.status===0">已添加</div>
          <div class="pointer accept" v-else-if="newFriend.status===1">接受</div>
        </div>
      </div>
    </div> -->
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  data () {
    return {
      // current: 0
    }
  },
  computed: {
    ...mapGetters({
      newFriends: 'wechats/getNewFriends',
      currentWechat: 'wechats/getCurrentWechat'
    })
  }
}
</script>

<style lang="scss" scoped>
@import "../../../styles/globals.scss";
.newFriends {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: #b6bbb4;
}

.friend {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  width: 98%;
}

.newFriend {
  display: flex;
  width: 100%;
  justify-content: space-around;
  align-items: center;
  // display: flex;
}

img {
  height: 40px;
  width: 40px;
}

.nd {
  width: 50%;
  // overflow: hidden;
}

.discribe {
  width: 100%;
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.accept {
  color: white;
  background: #129611;
  text-align: center;
}
.accept:hover {
  color: #41c0fc;
}
</style>
