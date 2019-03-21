<template>
  <div class="flex-container" id="homepage">
    <v-header class="header"></v-header>
    <div id="maindiv" :style="{height:(screenHeight-80)+'px'}">
      <keep-alive>
        <component :is="flags"></component>
      </keep-alive>
    </div>
    <v-footer class="footer"></v-footer>
    <router-view></router-view>
  </div>
</template>

<script>
import Header from '@/components/Header'
import Footer from '@/components/Footer'
import Conversation from './Conversation'
import FriendsManage from './FriendsManage'
import CustomerManagement from './CustomerManagement'
import FriendsCircle from './FriendsCircle'
import GroupSendAssistant from './GroupSendAssistant'
import HistoricalNews from './HistoricalNews'
import ReportManagement from './ReportManagement'
import Help from './Help'
import Tools from './Tools'

export default {
  name: 'homepage',
  data () {
    return {
      // screenWidth: document.documentElement.clientWidth, // 屏幕宽度
      screenHeight: document.documentElement.clientHeight // 屏幕高度
    }
  },
  watch: {
    // 'screenWidth': function (val) { // 监听屏幕宽度变化
    //   var oIframe = document.getElementById('maindiv')
    //   oIframe.style.width = (Number(val)) + 'px' // '120'是页面布局调整，可去除
    // },
    'screenHeight': function (val) { // 监听屏幕高度变化
      var oIframe = document.getElementById('maindiv')
      // alert(this.$store.getters.screenHeight)
      oIframe.style.height = (Number(val) - 40) + 'px'
      this.$store.dispatch('SetScrrentHeight', this.screenHeight)
    }
  },
  mounted () {
    var _this = this
    window.onresize = function () { // 定义窗口大小变更通知事件
      // _this.screenWidth = document.documentElement.clientWidth // 窗口宽度
      _this.screenHeight = document.documentElement.clientHeight // 窗口高度
    }
  },
  computed: {
    flags () {
      return this.$store.getters.flags
    }
  },
  components: {
    'v-header': Header,
    'v-footer': Footer,
    'conversation': Conversation,
    'friendsManage': FriendsManage,
    'customerManagement': CustomerManagement,
    'friendsCircle': FriendsCircle,
    'v-help': Help,
    'historicalNews': HistoricalNews,
    'groupSendAssistant': GroupSendAssistant,
    'reportManagement': ReportManagement,
    'v-tools': Tools
  },
  created () {
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>
$color: black;
.flex-container {
  display: flex;
}

#homepage {
  flex-direction: column;
  background-color: #f5f5f5;
}

#maindiv {
  display: inline-flex;
  justify-content: space-between;
}
</style>

