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
          <tools1></tools1>
        </icon-font>
        <span>智能回复</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools2></tools2>
        </icon-font>
        <span>定时群发</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools3></tools3>
        </icon-font>
        <span>条件循环群发</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools4></tools4>
        </icon-font>
        <span>清理僵尸粉</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools5></tools5>
        </icon-font>
        <span>多群同步</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools6></tools6>
        </icon-font>
        <span>自动通过好友请求</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools7></tools7>
        </icon-font>
        <span>智能养号</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools8></tools8>
        </icon-font>
        <span>智能点赞</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools9></tools9>
        </icon-font>
        <span>智能抢红包</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools10></tools10>
        </icon-font>
        <span>自动收转账</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools11></tools11>
        </icon-font>
        <span>新好友自动打招呼</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools12></tools12>
        </icon-font>
        <span>导出朋友圈</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools13></tools13>
        </icon-font>
        <span>导出微信二维码</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools14></tools14>
        </icon-font>
        <span>导出群二维码</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools15></tools15>
        </icon-font>
        <span>导出微信联系人</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools16></tools16>
        </icon-font>
        <span>群发名片</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools17></tools17>
        </icon-font>
        <span>通讯录加好友</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools18></tools18>
        </icon-font>
        <span>电话号码加好友</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools19></tools19>
        </icon-font>
        <span>转发小程序</span>
      </div>
      <div class="tool" @click="showAlert">
        <icon-font height="64" width="64" icon-color="white">
          <tools20></tools20>
        </icon-font>
        <span>更多</span>
      </div>
    </div>
    <div class="bottom"></div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      commonlyTools: [
        '智能回复',
        '定时群发',
        '条件循环群发',
        '清理僵尸粉',
        '多群转发',
        '自动通过好友请求',
        '智能养号',
        '智能点赞',
        '智能抢红包',
        '自动收转账',
        '新好友自动打招呼',
        '导出朋友圈',
        '导出微信二维码',
        '导出群二维码',
        '导出微信联系人',
        '群发名片',
        '通讯录加好友',
        '加好友',
        '转发小程序',
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
    height: 90%;
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