<template>
  <div id="footer">
    @ 2016-2019
    <span @click="openUrl()">聚播科技</span>版权所有 v1.0 beta版
  </div>
</template>

<script>
import { shell } from 'electron'

export default {
  methods: {
    openUrl () {
      shell.openExternal('http://www.yunlauncher.com')
    }
  }
}
</script>

<style lang="scss" scoped>
#footer {
  background-color: black;
  color: white;
  text-align: center;
  height: 20px;
  span {
    color: white;
    cursor: pointer;
  }
}
</style>