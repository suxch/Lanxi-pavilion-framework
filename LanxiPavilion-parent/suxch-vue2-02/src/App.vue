<template>
  <div>
    <!-- 快捷链接 -->
    <XtxShortCut></XtxShortCut>
    <!-- 顶部导航 -->
    <XtxHeaderNav></XtxHeaderNav>
    <!-- 轮播区域 -->
    <XtxBanner></XtxBanner>
    <!-- 新鲜好物 -->
    <XtxNewGoods></XtxNewGoods>
    <!-- 热门品牌 -->
    <XtxHotBrand></XtxHotBrand>
    <!-- 最新专题 -->
    <XtxTopic></XtxTopic>
    <!-- 版权底部 -->
    <XtxFooter></XtxFooter>
  </div>
</template>

<script>

import XtxShortCut from "@/components/shortCut/XtxShortCut.vue";
import XtxHeaderNav from "@/components/HeaderNav/XtxHeaderNav.vue";
import XtxBanner from "@/components/banner/XtxBanner.vue";
import XtxNewGoods from "@/components/newGoods/XtxNewGoods.vue";
import XtxHotBrand from "@/components/hotBrand/XtxHotBrand.vue";
import XtxTopic from "@/components/topic/XtxTopic.vue";
import XtxFooter from "@/components/footer/XtxFooter.vue";

export default {
  components: {
    XtxShortCut,
    XtxHeaderNav,
    XtxBanner,
    XtxNewGoods,
    XtxHotBrand,
    XtxTopic,
    XtxFooter
  }
}
</script>

<style lang="less" scoped>

</style>