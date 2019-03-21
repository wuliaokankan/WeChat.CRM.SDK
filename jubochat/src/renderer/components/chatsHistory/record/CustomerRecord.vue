<template>
  <div class="customerRecord" :style="{height: scrrentHeight+'px'}">
    <!-- remark -->
    <div class="remark">
      <!-- <div>姓名:</div> -->
      <label for="name">姓名:</label>
      <div class="flex-center">
        <input type="text" name id="name" maxlength="20">
      </div>
      <label for="alias">个性称呼:</label>
      <div class="flex-center">
        <input type="text" name id="alias" maxlength="20">
      </div>
      <label for="phone">联系方式:</label>
      <div class="flex-center">
        <input type="tel" name id="phone" maxlength="13">
      </div>
      <label for="remark">备注:</label>
      <div class="flex-center">
        <textarea name id="remark" cols="30" rows="10" maxlength="1000"></textarea>
      </div>
    </div>
    <!-- label -->
    <div class="label">
      <label for="addLable">添加标签:</label>
      <div class="flex-center">
        <input type="text" name id="addLable">
        <i class="fa fa-plus" aria-hidden="true"></i>
      </div>
      <!-- <div>公共标签</div>
      <div>部门标签</div>
      <div>我的标签</div> -->
    </div>
    <!-- server history -->
    <div class="server">
      <div>新增服务记录</div>
      <div>历史服务记录</div>
    </div>
    <!-- warn -->
    <div class="warn flex-center">服务记录提醒</div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  data () {
    return {

    }
  },
  computed: {
    ...mapGetters({
      scrrentHeight: 'recordHeight'
    })
    // height () {
    //   let height = (window.innerHeight - 125) * 0.9
    //   return height
    // }
  }

}
</script>

<style lang="scss" scoped>
@import "../../../styles/globals.scss";
.customerRecord {
  height: 90%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.remark,
.label,
.server,
.warn {
  width: 90%;
  // margin: auto;
  // margin-top: 10px;
}
.server {
  display: flex;
}
</style>

