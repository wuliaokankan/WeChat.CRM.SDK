<template>
  <div class="common-tools">
    <div class="title"></div>
    <div class="content-tools">
      <!-- <div class="tool" v-for="(tool, index) in commonlyTools" :key="index" @click="showAlert">
        <i class="fa fa-wrench fa-3x" aria-hidden="true"></i>
        <span v-text="tool"></span>
      </div>-->
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools21></tools21>
        </icon-font>
        <span>拉好友进群</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools22></tools22>
        </icon-font>
        <span>群内加好友</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools23></tools23>
        </icon-font>
        <span>欢迎进群新人</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools24></tools24>
        </icon-font>
        <span>自动接受进群邀请</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools25></tools25>
        </icon-font>
        <span>修改群昵称</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools26></tools26>
        </icon-font>
        <span>退群</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools27></tools27>
        </icon-font>
        <span>保存到通讯录</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools28></tools28>
        </icon-font>
        <span>群免打扰</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools29></tools29>
        </icon-font>
        <span>群成员管理</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools20></tools20>
        </icon-font>
        <span>更多</span>
      </div>
    </div>
    <div style="height:45%;"></div>
    <div class="bottom"></div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      commonlyTools: [
        '拉好友进群',
        '群内加好友',
        '欢迎进群新人',
        '自动接受进群邀请',
        '修改群昵称',
        '退群',
        '保存到通讯录',
        '群免打扰',
        '群成员管理',
        '更多...'
      ]
    }
  },
  methods: {
    showAlert () {
      alert('测试版，该功能暂不可用！')
    }
  }

}
</script>

<style lang="scss" scoped>
.common-tools {
  width: 100%;
  height: 100%;
  overflow: hidden;
  .content-tools {
    width: 100%;
    height: 45%;
    display: flex;
    justify-content: space-around;
    align-items: center;
    flex-wrap: wrap;
    .tool {
      min-width: 15%;
      max-width: 15%;
      margin-top: 1%;
      cursor: pointer;
      padding: 10px;
      border-radius: 10px;
      background: #F0F0F0;
      border: #F0F0F0 1px solid;

      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      span {
        font-size: 14px;
        font-weight: 600;
        margin-top: 10px;
      }
      &:hover {
        i,
        span {
          color: #4bbcfb;
        }
      }
    }
  }
}
</style>