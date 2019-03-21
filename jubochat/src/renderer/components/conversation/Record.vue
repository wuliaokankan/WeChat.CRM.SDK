<template>
  <div class="record">
    <div class="title">
      <div :class="{selected:current==='reply'}" @click="select('reply')">快捷回复</div>
      <div :class="{selected:current==='customerRecord'}" @click="select('customerRecord')">客户录入</div>
    </div>
    <div class="show scroll">
      <v-reply class="replay" v-if="current==='reply'"></v-reply>
      <v-customerRecord v-else></v-customerRecord>
    </div>
    <div class="bottom"></div>
  </div>
</template>

<script>
import CustomerRecord from './record/CustomerRecord'
import Reply from './record/Reply'
export default {
  data () {
    return {
      current: 'reply'
    }
  },
  components: {
    'v-reply': Reply,
    'v-customerRecord': CustomerRecord
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
.record {
  width: 20%;
  height: 98%;
  background-color: #e4e7ea;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  border-radius: 10px;
}

.title {
  div {
    cursor: pointer;
    width: 100%;
    text-align: center;
    color: gray;
    font-size: 14px;
    &:hover {
      color: #41c0fc;
    }
  }
  .selected {
    color: #4bfb9a;
  }
}

.show {
  display: flex;
  align-items: center;
  width: 98%;
  height: 90%;
  overflow-y: scroll;
  overflow-x: hidden;
}

.hover:hover {
  color: #41c0fc;
  background-color: rgb(209, 211, 212);
}
</style>
