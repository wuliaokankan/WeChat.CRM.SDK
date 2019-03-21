<template>
  <div class="history-news">
    <!-- wechat list -->
    <v-wechats :wechats="wechats" :wechatsLogin="wechatsLogin"></v-wechats>
    <!-- friends -->
    <v-friends></v-friends>
    <!-- communicate -->
    <v-communicate></v-communicate>
    <!-- recording and commonly used -->
    <v-record></v-record>
  </div>
</template>

<script>
import Wechats from '@/components/chatsHistory/Wechats'
import Friends from '@/components/chatsHistory/Friends'
import Communicate from '@/components/chatsHistory/Communicate'
import Record from '@/components/chatsHistory/Record'
import { mapGetters, mapActions } from 'vuex'
export default {
  data () {
    return {
    }
  },
  computed: {
    ...mapGetters({
      wechats2: 'wechats',
      wechatsLogin: 'wechatsLogin'
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
    'v-record': Record
  },
  methods: {
    ...mapActions({
      SetFriendsList: 'SetFriendsList'
    }),
    // 获取已经登陆的微信的好友
    getFriendsList () {
      console.log('查询好友')
      if (localStorage.getItem('friendsList')) {
        console.log('本地有数据')
        let friendsList = JSON.parse(localStorage.getItem('friendsList'))
        this.SetFriendsList(friendsList)
      } else {
        let msg = { reqApi: 'TriggleFriendPush', data: {} }
        this.socketApi.sendSock(msg)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.history-news {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
</style>