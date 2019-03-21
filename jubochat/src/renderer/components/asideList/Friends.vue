<template>
  <div id="friendBox">
    <!-- 搜索 -->
    <div id="searchbox">
      <input type="search" name="" id="search" maxlength="20" placeholder="输入昵称">
      <i class="fa fa-search" aria-hidden="true"></i>
    </div>
    <!-- 微信列表 -->
    <div v-for="(wechat, index) in wechats" :key="'wechats'+index" class="wechatsBox" :class="{select:current===index}" @click="addClass(index,wechat)">
      <div class="wechats">
        <img :src="wechat.portrait" alt="" class="wechatImg">
        <p class="wechatsName" v-if="wechat.memoName!==''" v-text="wechat.nickname"></p>
        <p class="wechatsName" v-else v-text="wechat.nickname"></p>
        <i title="点击显示更多" v-if="show[index].show" @click="showFriends(index)">
          <icon-base height="20" width="20">
            <expand-more></expand-more>
          </icon-base>
        </i>
        <i title="点击收起" v-if="hidden[index].hidden" @click="hideFriends(index)">
          <icon-base height="20" width="20">
            <take-up></take-up>
          </icon-base>
        </i>
      </div>

      <ul v-show="hidden[index].hidden">
        <li v-for="(friend, index) in friends[wechat.imei].friends" :key="index" class="li" :class="{select2: currentFriend ===( wechat.imei+index) }" @click.stop="addClass2(wechat.imei+index,friend,wechat)">
          <img :src="friend.portrait" alt="" class="friendImg" />
          <p v-text="friend.nickname" class="friendNickname" :title="friend.nickname"></p>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      current: 0,
      currentFriend: -1,
      currentWechatFriends: [],
      show: [
        { show: true },
        { show: true },
        { show: true },
        { show: true },
        { show: true },
        { show: true },
        { show: true },
        { show: true },
        { show: true },
        { show: true }],
      hidden: [
        { hidden: false },
        { hidden: false },
        { hidden: false },
        { hidden: false },
        { hidden: false },
        { hidden: false },
        { hidden: false },
        { hidden: false },
        { hidden: false },
        { hidden: false }]
    }
  },
  computed: {
    friends () {
      console.log('friend.vue computed friends')
      let friends = this.$store.getters.friends
      return friends
    },
    wechats () {
      console.log('friends.vue wechats')
      let wechats = this.$store.getters.wechats
      let wechatActived = []
      for (const wechat of wechats) {
        // 如果微信已经登陆成功
        if (wechat.status === 1) {
          wechatActived.push(wechat)
        }
      }
      // console.log(wechatActived)
      return wechatActived
    }
  },
  // created () {
  //   console.log('钩子函数')
  //   let _this = this
  //   let wechats = JSON.parse(localStorage.getItem('wechats'))
  //   if (wechats) {
  //     console.log('本地有微信信息')
  //     _this.$store.dispatch('SetWechat', wechats)
  //   } else {
  //     console.log('本地没有微信信息')
  //     _this.$store.dispatch('QueryWechat').then().catch(error => console.log(error))
  //   }
  //   let friends = JSON.parse(localStorage.getItem('friends'))
  //   if (friends) {
  //     console.log('本地有好友信息')
  //     _this.$store.dispatch('SetFriends', friends)
  //   } else {
  //     console.log('本地没有好友信息')
  //     _this.$store.dispatch('QueryFriends').then().catch(error => console.log(error))
  //   }
  // },
  methods: {
    addClass (index, wechat) {
      console.log('addClass')
      this.current = index
      this.$store.dispatch('SetCurrentWechat', wechat)
      this.$store.dispatch('SetCurrentPageOfInfo', 'wechat')
    },
    addClass2 (index, friend, wechat) {
      console.log('addClass2')
      this.currentFriend = index
      this.$store.dispatch('SetCurrentFriend', { 'wechat': wechat, 'friend': friend })
      console.log(friend)
      this.$store.dispatch('SetCurrentPageOfInfo', 'friend')
    },
    showFriends (index) {
      console.log('Friends.vue showFriends')
      this.show[index].show = false
      this.hidden[index].hidden = true
    },
    hideFriends (index) {
      console.log('hide Friends')
      this.show[index].show = true
      this.hidden[index].hidden = false
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
// wechats css
.wechatBox {
  display: flex;
  flex-direction: column;
}
.wechats {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 50px;
  cursor: pointer;
}
.wechatImg {
  width: 40px;
  height: 40px;
  border-radius: 20px;
  // border: red solid 1px;
  margin-top: 1px;
}
.wechatsName {
  font-size: 10px;
  height: 12px;
  width: 70px;
  text-align: center;
  // 单行显示
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.wechats:hover {
  background-color: #d9d8d8;
}
// 好友css
.li {
  display: flex;
  align-items: center;
  justify-content: space-around;
  // margin-top: 1px;
  height: 40px;
  background-color: #656564;
  cursor: pointer;
}
.li:hover {
  background-color: aquamarine;
}
.friendNickname {
  width: 100px;
  font-size: 10px;
  color: black;

  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.friendImg {
  width: 35px;
  height: 35px;
  border-radius: 17px;
  margin-top: 1px;
}
.select {
  background-color: #cac8c6;
}
.select2 {
  background-color: #3b3632;
}
</style>
