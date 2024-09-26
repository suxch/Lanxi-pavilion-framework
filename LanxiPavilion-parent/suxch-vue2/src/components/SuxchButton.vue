<template>
  <div id="button">
    <button @click="suxchButton">这是公共按钮</button>
  </div>
</template>

<script>
export default {
  methods: {
    suxchButton () {
      alert("suxch");
    }
  }
}
</script>

<style lang="less">
  #button {
    button {
      height: 30px;
      /*width: 50px;*/
      background-color: pink;
    }
  }
</style>