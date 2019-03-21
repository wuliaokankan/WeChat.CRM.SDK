<template>
  <div class="dialog-bg">
    <div class="dialog">
      <textarea name id cols="30" rows="10" placeholder="输入常用语" v-model="content"></textarea>
      <div>
        <button class="cancle" @click="cancle">取消</button>
        <button class="submit" @click="submit">提交</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      content: ''
    }
  },
  methods: {
    cancle () {
      this.$router.push({ path: '/main' })
    },
    submit () {
      console.log('添加')
      let replay = this.$route.params.replay
      replay['content'] = this.content
      console.log(replay)
      this.$store.dispatch('ReplaceReplays', replay)
      this.$store.dispatch('SetReplays')
      this.$router.push({ path: '/main' })
    }
  }

}
</script>

<style lang="scss" scoped>
.dialog-bg {
  z-index: 7;
  width: 100%;
  height: 100%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  position: absolute;
  // background-color: black;
  // opacity: 0.8;
  background: rgba(0, 0, 0, 0.5);
}
.dialog {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 500px;
  height: 300px;
  background: white;
  opacity: 1;
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex-direction: column;
  textarea {
    width: 400px;
    height: 200px;
    border: solid 1px gray;
  }
}
.cancle,
.submit {
  width: 80px;
  background: #4bbcfb;
  font-size: 16px;
  border-radius: 5px;
  cursor: pointer;
}
</style>
