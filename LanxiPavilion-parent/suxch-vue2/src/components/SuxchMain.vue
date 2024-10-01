<template>
  <div class="main">
    这是中心
    <SuxchButton></SuxchButton>
    <button @click="sendData">发送对象数据</button>
    <div>{{fromHeaderMsg}}</div>
    <button>是否隐藏</button>
    <SuxchModel></SuxchModel>
  </div>
</template>

<script>

import Bus from "@/utils/EventBus";
import SuxchModel from "@/components/SuxchModel.vue";

export default {
  props: ['suxch'],
  inject: ['userinfo'],
  created() {
    console.log(this.suxch)
    console.log(this.suxch.id)
    console.log(this.suxch.name)
    console.log(this.suxch.age)

    console.log(this.userinfo)

    Bus.$on('sendMsg', (res) => {
      this.fromHeaderMsg = res;
    })

  },
  data () {
    return {
      headerData: [
        {
          id: 1,
          name: 'suxch'
        },
        {
          id: 2,
          name: 'myMemory'
        }
      ],
      fromHeaderMsg: "",
      userinfo: {}
    }
  },
  methods: {
    sendData () {
      this.$emit('headerData', this.headerData)
    }
  },
  components: {
    SuxchModel
  }
}
</script>

<style scoped>
  .main{
    width: 500px;
    height: 600px;
    margin: 10px auto;
    background-color: aquamarine;
  }
</style>