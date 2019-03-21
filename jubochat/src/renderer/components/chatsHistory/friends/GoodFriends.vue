<template>
  <div class="goodFriends scroll">
    <div
      v-if="index<maxNumber && index>=minNumber"
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
      current: 0
    }
  },
  props: {
    currentPage: {
      type: Number,
      default: 1
    }
  },
  computed: {
    ...mapGetters({
      currentWechat: 'currentWechat',
      friendsList: 'friendsList'
    }),
    minNumber () {
      let min = (this.$props.currentPage - 1) * 100
      return min
    },
    maxNumber () {
      let max = (this.$props.currentPage) * 100
      return max
    }
  },
  methods: {
    ...mapActions({
      SetFriendsList: 'SetFriendsList',
      SetPersonalInfo: 'SetPersonalInfo'
    }),
    select (index, friend) {
      console.log(friend)
      this.current = index
      let info = {
        friendId: friend.friendId,
        wechatId: this.currentWechat.WeChatId
      }
      this.SetPersonalInfo(info)
    }
  },
  mounted () {
    this.SetFriendsList(this.currentWechat.WeChatId)
  }

}
</script>

<style lang="scss" scoped>
@import "../../../styles/globals.scss";
.goodFriends {
  height: 100%;
  overflow-y: scroll;
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

