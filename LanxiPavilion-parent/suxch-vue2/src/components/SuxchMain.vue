<template>
  <div class="main">
    这是中心
    <SuxchButton></SuxchButton>
    <button @click="sendData">发送对象数据</button>
  </div>
</template>

<script>
export default {
  props: ['suxch'],
  created() {
    console.log(this.suxch)
    console.log(this.suxch.id)
    console.log(this.suxch.name)
    console.log(this.suxch.age)
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
      ]
    }
  },
  methods: {
    sendData () {
      this.$emit('headerData', this.headerData)
    }
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