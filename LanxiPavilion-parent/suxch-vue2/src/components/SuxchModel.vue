<script>
export default {
  name: "SuxchModel",
  props: {
    showOrHide: Boolean
  },
  methods: {
    closeModel () {
      this.$emit("update:showOrHide", false);
    }
  }
}
</script>

<template>
  <div class="s-model" v-show="showOrHide">
    <div class="alertInfo">
      <div>
        <button>确定</button>
        <button @click="closeModel">取消</button>
      </div>
    </div>
  </div>
</template>

<style scoped lang="less">
.s-model{
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}
.alertInfo {
  position: absolute;
  width: 500px;
  height: 300px;
  top: 270px;
  left: 633px;
}
</style>