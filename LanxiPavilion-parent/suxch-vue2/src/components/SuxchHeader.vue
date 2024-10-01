<template>
  <div id="header">
    头部 {{ title }}
    <SuxchButton></SuxchButton>
    <button @click="clickBtn">变更父级数据</button>
    <button @click="sendMsgToMain">变更中间盒子中的数据</button>
  </div>
</template>

<script>

import Bus from "@/utils/EventBus";

export default {
  props: ['title'],
  provide () {
    return {
      userinfo: {
        username: this.username,
        age: this.age,
        post: this.post
      }
    }
  },
  data () {
    return {
      username: "苏希辰",
      age: 33,
      post: "高级程序员"
    }
  },
  methods: {
    clickBtn () {
      this.$emit('replaceTitle', "这是子级变更的数据")
    },
    sendMsgToMain() {
      Bus.$emit('sendMsg', '这是给业务模块页面的消息')
    }
  }
}
</script>

<style lang="less">
  #header {
    width: 500px;
    height: 100px;
    margin: 10px auto;
    background-color: #42b983;
  }
</style>