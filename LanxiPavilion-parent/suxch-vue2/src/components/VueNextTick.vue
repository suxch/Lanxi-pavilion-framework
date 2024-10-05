<script>
export default {
  name: "VueNextTick",
  data () {
    return {
      editOrShow: true,
      showData: "可编辑数据"
    }
  },
  methods: {
    editData () {
      this.editOrShow = false;
      // this.$nextTick(() => {
      //   this.$refs.showDataInp.focus();
      // })
    },
    noEdit () {
      this.editOrShow = true;
    }
  }
}
</script>

<template>
  <div class="table-td">
    <div v-if="editOrShow">
      <span @click="editData">{{ showData }}</span>
    </div>
    <div v-else>
      <input v-model="showData" type="text" v-inp-focus/>
      <button @click="noEdit">确定</button>
    </div>
  </div>
</template>

<style scoped lang="less">
.table-td {
  border: #bd2c00 1px solid;
}
</style>