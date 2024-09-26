<template>
  <div class="parent">
    这是首页？
    <SuxchHeader :title="myTitle" @replaceTitle="receiveData"></SuxchHeader>
    <SuxchMain :suxch="sendData" @headerData="receiveHeaderData"></SuxchMain>
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
  },
  data () {
    return {
      myTitle: "我是父级传过去的消息",
      sendData: {
        id: 1,
        name: "suxch",
        age: 23
      }
    }
  },
  methods: {
    receiveData (data) {
      this.myTitle = data;
    },
    receiveHeaderData (data) {
      console.log(data)
    }
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