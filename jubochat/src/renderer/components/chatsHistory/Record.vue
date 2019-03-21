<template>
  <div class="record">
    <div class="title">客户信息</div>
    <div class="history-record-content flex-center">测试版本，功能暂不开放！</div>
    <div class="bottom"></div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      current: 'reply'
    }
  },
  components: {
  },
  computed: {
  },
  methods: {
    select (page) {
      console.log(page)
      this.current = page
    }
  }
}
</script>

<style lang="scss" scoped>
@import "../../styles/globals.scss";
.record {
  width: 20%;
  height: 98%;
  background-color: #e4e7ea;
  display: flex;
  flex-direction: column;
  border-radius: 10px;
}
.title {
  color: white;
}
.history-record-content {
  height: 90%;
}
</style>
