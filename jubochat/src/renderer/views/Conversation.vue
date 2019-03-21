<template>
  <div class="conversation">
    <!-- wechat list -->
    <v-wechats :wechats="wechats" :wechatsLogin="wechatsLogin"></v-wechats>
    <!-- friends -->
    <v-friends></v-friends>
    <!-- communicate -->
    <v-communicate></v-communicate>
    <!-- recording and commonly used -->
    <v-record v-show="!showFriendCircle"></v-record>
    <v-fc v-show="showFriendCircle"></v-fc>
  </div>
</template>

<script>
import Wechats from '@/components/conversation/Wechats'
import Friends from '@/components/conversation/Friends'
import Communicate from '@/components/conversation/Communicate'
import Record from '@/components/conversation/Record'
import FriendCircle from '@/components/conversation/FriendCircle'
import { mapGetters } from 'vuex'
export default {
  computed: {
    ...mapGetters({
      wechats2: 'wechats',
      wechatsLogin: 'wechatsLogin',
      currentWechat: 'currentWechat',
      showFriendCircle: 'circles/showFriendCircle'
    }),
    wechats () {
      if (this.wechats2.length > 0) {
        return this.wechats2
      } else {
        return []
      }
    }
  },
  components: {
    'v-wechats': Wechats,
    'v-friends': Friends,
    'v-communicate': Communicate,
    'v-record': Record,
    'v-fc': FriendCircle
  }
}
</script>

<style lang="scss" scoped>
.conversation {
  display: flex;
  justify-content: space-around;
  width: 100%;
  align-items: center;
}
</style>

