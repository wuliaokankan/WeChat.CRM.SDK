<template>
  <div class="groupSend">
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
    <component class="customer-manager-content" :is="currentComponent"></component>
  </div>
</template>

<script>
import CustomerInquire from '@/components/customer/CustomerInquire'
import Labels from '@/components/customer/Labels'
import LabelsManagement from '@/components/customer/LabelsManagement'
import NicknameManagement from '@/components/customer/NicknameManagement'
import PersonalizedName from '@/components/customer/PersonalizedName'
import ServerHistory from '@/components/customer/ServerHistory'
export default {
  data () {
    return {
      current: 0,
      currentComponent: 'v-ci',
      tabs: ['客户查询', '服务记录', '批量标签', '标签管理', '昵称管理', '个性称呼']
    }
  },
  methods: {
    select (index) {
      this.current = index
      switch (index) {
        case 0:
          this.currentComponent = 'v-ci'
          break
        case 1:
          this.currentComponent = 'v-sh'
          break
        case 2:
          this.currentComponent = 'v-labels'
          break
        case 3:
          this.currentComponent = 'v-lm'
          break
        case 4:
          this.currentComponent = 'v-nm'
          break
        case 5:
          this.currentComponent = 'v-pn'
          break
        default:
          this.currentComponent = 'v-ci'
          break
      }
    }
  },
  components: {
    'v-ci': CustomerInquire,
    'v-labels': Labels,
    'v-lm': LabelsManagement,
    'v-nm': NicknameManagement,
    'v-pn': PersonalizedName,
    'v-sh': ServerHistory
  }

}
</script>

<style lang="scss" scoped>
@import "../styles/globals.scss";
.groupSend {
  display: flex;
  width: 100%;
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
}
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

.customer-manager-content {
  background-color: #e4e7ea;
  width: 82.5%;
  height: 98%;
  border-radius: 10px;
}
</style>
