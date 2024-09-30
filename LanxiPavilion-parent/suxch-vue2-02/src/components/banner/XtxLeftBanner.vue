<template>
  <!-- 侧导航 -->
  <div class="subnav">
    <ul>
      <li v-for="(item) in leftNav" :key="item.mainTitle">
        <div>
          <span><a href="#">{{item.mainTitle}}</a></span>
          <span>
            <a href="#" v-for="(subItem) in item.subTitles" :key="subItem.subTitle">
              {{subItem.subTitle}}
            </a>
          </span>
        </div>
        <i class="iconfont icon-arrow-right-bold"></i>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "XtxLeftBanner",
  created() {
    this.leftNav = [
      {
        mainTitle: '生鲜',
        subTitles: [
          {subTitle: '水果'},
          {subTitle: '蔬菜'}
        ]
      },{
        mainTitle: '美食',
        subTitles: [
          {subTitle: '面点'},
          {subTitle: '干果'}
        ]
      },{
        mainTitle: '餐厨',
        subTitles: [
          {subTitle: '数码产品'}
        ]
      },{
        mainTitle: '电器',
        subTitles: [
          {subTitle: '床品'},
          {subTitle: '四件套'},
          {subTitle: '被枕'}
        ]
      },{
        mainTitle: '居家',
        subTitles: [
          {subTitle: '奶粉'},
          {subTitle: '玩具'},
          {subTitle: '辅食'}
        ]
      },{
        mainTitle: '洗护',
        subTitles: [
          {subTitle: '洗发'},
          {subTitle: '洗护'},
          {subTitle: '美妆'}
        ]
      },{
        mainTitle: '孕婴',
        subTitles: [
          {subTitle: '奶粉'},
          {subTitle: '玩具'}
        ]
      },{
        mainTitle: '服饰',
        subTitles: [
          {subTitle: '女装'},
          {subTitle: '男装'}
        ]
      },{
        mainTitle: '杂货',
        subTitles: [
          {subTitle: '户外'},
          {subTitle: '图书'}
        ]
      },{
        mainTitle: '品牌',
        subTitles: [
          {subTitle: '品牌制造'}
        ]
      }
    ]
  },
  data () {
    return {
      leftNav: []
    }
  }
}
</script>

<style scoped lang="less">
.banner .subnav {
  position: absolute;
  left: 0;
  top: 0;
  width: 250px;
  height: 500px;
  background-color: rgba(0,0,0,0.42);
}
.banner .subnav li {
  display: flex;
  justify-content: space-between;
  padding: 0 20px 0 30px;
  height: 50px;
  line-height: 50px;
}
.banner .subnav a,
.banner .subnav i {
  color: #fff;
}
.banner .subnav li span:nth-child(1) {
  margin-right: 14px;
}
.banner .subnav li span:nth-child(2) a {
  margin-right: 5px;
}
.banner .subnav li span:nth-child(2) a {
  font-size: 14px;
}
.banner .subnav li:hover {
  background-color: #00BE9A;
}
</style>