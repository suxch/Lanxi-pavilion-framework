<template>
  <div id="header">
    头部 {{ title }}
    <SuxchButton></SuxchButton>
    <button @click="clickBtn">变更父级数据</button>
  </div>
</template>

<script>
export default {
  props: ['title'],
  methods: {
    clickBtn () {
      this.$emit('replaceTitle', "这是子级变更的数据")
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