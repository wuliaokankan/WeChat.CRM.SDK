<template>
  <div class="goodFriends scroll" id="goodFriends" @scroll="showGoodFriendsScroll()">
    <div
      class="friend flex-center pointer"
      :class="[{hover:index!==current},{selected:index===current}]"
      v-for="(friend, index) in friendsList"
      :key="index"
      @click="select(index, friend)"
    >
      <img v-if="friend.avatar!=='undefined'" class="img" :src="friend.avatar" alt="未加载">
      <div v-else class="img flex-center">
        <i class="fa fa-spinner fa-2x" aria-hidden="true"></i>
      </div>
      <div class="nickname pointer" :title="friend.friendNick">{{ friend.friendNick }}</div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  data () {
    return {
      current: 0,
      currentNumber: 2
    }
  },
  computed: {
    ...mapGetters({
      currentWechat: 'currentWechat',
      friendsList: 'friendsList'
    })
  },
  watch: {
    currentWechat (val) {
      console.log(val.WeChatId)
      this.currentNumber = 1
      let info = {
        wechatId: this.currentWechat.WeChatId,
        number: this.currentNumber
      }
      console.log(info)
      this.SetFriendsList(info)
    }
  },
  methods: {
    ...mapActions({
      SetFriendsList: 'SetFriendsList',
      SetPersonalInfo: 'SetPersonalInfo',
      SetAllChats: 'chats/SetAllChats'
    }),
    select (index, friend) {
      this.current = index
      let info = {
        friendId: friend.friendId,
        wechatId: this.currentWechat.WeChatId
      }
      this.SetPersonalInfo(info)
      this.SetAllChats(info)
    },
    showGoodFriendsScroll () {
      console.log('showGoodFriendsScroll')
      let st = document.getElementById('goodFriends').scrollTop
      let sh = document.getElementById('goodFriends').scrollHeight
      let ch = document.getElementById('goodFriends').clientHeight
      if (sh <= Math.ceil(st + ch)) {
        console.log('到底了')
        let info = {
          wechatId: this.currentWechat.WeChatId,
          number: this.currentNumber
        }
        this.SetFriendsList(info)
        this.currentNumber = this.currentNumber + 1
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.goodFriends {
  height: 99%;
  margin-top: 2px;
  overflow-y: auto;
}

.scroll {
  &::-webkit-scrollbar {
    width: 6px;
    height: 6px;
    background-color: #bababa;
  }
  &::-webkit-scrollbar-thumb {
    border-radius: 3px;
    background-color: black;
  }
}

.img {
  width: 34px;
  height: 34px;
  line-height: 34px;
  font-size: 10px;
}

.friend {
  display: flex;
  height: 40px;
  justify-content: space-between;
  margin-top: 2px;
  margin-bottom: 2px;
}

.nickname {
  width: 65%;
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.hover:hover {
  background-color: #dddbda;
}
.selected {
  background-color: #c8c6c6;
}
</style>

