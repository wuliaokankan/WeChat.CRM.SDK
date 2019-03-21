<template>
  <div class="help">
    <!-- tabs  -->
    <div class="aside">
      <div style="height:4%;"></div>
      <div
        v-for="(tab, index) in tabs"
        :key="index"
        :class="[{hover:index!==current},{selected:index===current}]"
        @click="select(index)"
        class="tab pointer"
        v-text="tab"
      ></div>
    </div>
    <!-- show -->
    <v-cu class="content" v-if="current===0"></v-cu>
    <!-- <v-og v-else-if="current===1"></v-og> -->
  </div>
</template>

<script>
import ContactUs from '@/components/help/ContactUs'
// import OperationGuide from '@/components/help/OperationGuide'
export default {
  data () {
    return {
      current: 0,
      tabs: ['联系我们']
    }
  },
  methods: {
    select (index) {
      this.current = index
    }
  },
  components: {
    'v-cu': ContactUs
    // 'v-og': OperationGuide
  }

}
</script>

<style lang="scss" scoped>
@import "../styles/globals.scss";
.help {
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: space-around;
  align-items: center;
}

.aside {
  display: flex;
  background-color: #282b30;
  flex-direction: column;
  align-items: center;
  height: 98%;
  width: 15%;
  border-radius: 10px;
  .tab {
    font-size: 18px;
    color: rgb(219, 219, 219);
    background: rgb(71, 75, 83);
    width: 90%;
    text-align: center;
    margin-top: 10%;
    border-radius: 5px;
  }
  .hover:hover {
    color: #41c0fc;
    background-color: rgb(209, 211, 212);
  }
  .selected {
    color: #3b7957;
    background-color: black;
  }
}

.content {
  width: 82.5%;
  height: 98%;
  background-color: #e4e7ea;
  overflow: hidden;
  border-radius: 10px;
}
</style>
