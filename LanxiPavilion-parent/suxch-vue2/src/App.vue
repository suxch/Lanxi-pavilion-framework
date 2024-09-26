<template>
  <div class="parent">
    这是首页？
    <SuxchHeader></SuxchHeader>
    <SuxchMain></SuxchMain>
    <SuxchFooter></SuxchFooter>
  </div>
</template>

<script>
import SuxchHeader from "@/components/SuxchHeader.vue";
import SuxchMain from "@/components/SuxchMain.vue";
import SuxchFooter from "@/components/SuxchFooter.vue";

export default {
  components: {
    SuxchHeader,
    SuxchMain,
    SuxchFooter
  }
}
</script>

<style lang="less">
  .parent {
    width: 600px;
    height: 900px;
    margin: 0 auto;
    background-color: aqua;
  }
</style>