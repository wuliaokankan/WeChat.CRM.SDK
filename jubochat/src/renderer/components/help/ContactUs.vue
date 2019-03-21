<template>
  <div class>
    <div class="title"></div>
    <div class="help-content">
      <p>
        <strong>微信二次开发SDK特色功能</strong>
      </p>
      <p>1、无需扫码登陆即可多账号微信聊天
        <br>2、支持发朋友圈、朋友圈评论互动
        <br>4、为合作伙伴提供完整的二次开发sdk，无缝集成任何scrm软件系统
        <br>3、安全稳定20000+设备
        <br>
      </p>
      <br>
      <p>
        <strong>技术支持及联系方式</strong>
      </p>
      <p>官网地址：
        <!-- <a href="http://www.yunlauncher.com" target="_blank">http://www.yunlauncher.com</a> -->
        <span class="address-url" @click="openUrl">http://www.yunlauncher.com</span>
        <br>咨询电话：13456850448
        <br>客服微信：Uta2012(微信号)
        <br>技术咨询：tangjinjinwx（微信号）
      </p>
      <br>
      <p>总公司地址：浙江省杭州市拱墅区金通国际大厦B座1405
        <br>我们专注微信营销工具的研发多年，拥有全面的微信二次开发sdk和完整的api文档可适用于微信营销软件研发、微信营销手机研发、微信手机群控云控系统研发、微信客服系统研发、微信营销系统微商营销工具研发等，无论是代理加盟还是合作定制开发，我们都是您不二的选择！！！
      </p>
    </div>
    <div class="bottom"></div>
  </div>
</template>

<script>
import { shell } from 'electron'
export default {
  data () {
    return {

    }
  },
  methods: {
    openUrl () {
      shell.openExternal('http://www.yunlauncher.com')
    }
  }

}
</script>

<style lang="scss" scoped>
.help-content {
  height: 90%;
  width: 98%;
  margin-left: 10px;
  p {
    font-size: 18px;
  }
  .address-url {
    cursor: pointer;
    &:hover {
      color: #4bbcfb;
    }
  }
}
</style>