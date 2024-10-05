<template>
  <div class="main">
    这是中心
    <SuxchButton></SuxchButton>
    <button @click="sendData">发送对象数据</button>
    <div>{{fromHeaderMsg}}</div>
    <button @click="showModel">是否隐藏</button>
    <SuxchModel :showOrHide.sync="isShow"></SuxchModel>
    <VueNextTick></VueNextTick>
  </div>
</template>

<script>

import Bus from "@/utils/EventBus";
import SuxchModel from "@/components/SuxchModel.vue";
import VueNextTick from "@/components/VueNextTick.vue";

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
      userinfo: {},
      isShow: false
    }
  },
  methods: {
    sendData () {
      this.$emit('headerData', this.headerData)
    },
    showModel () {
      this.isShow = true;
    }
  },
  components: {
    SuxchModel,
    VueNextTick
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