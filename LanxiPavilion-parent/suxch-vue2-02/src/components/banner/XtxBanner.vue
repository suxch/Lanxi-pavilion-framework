<template>
  <!-- 轮播区域 -->
  <div class="banner">
    <div class="wrapper">
      <!-- 图 -->
      <ul class="pic">
        <li>
          <a href="#"><img src="@/assets/images/banner1.png" alt="" /></a>
        </li>
        <li>
          <a href="#"><img src="@/assets/images/banner1.png" alt="" /></a>
        </li>
      </ul>
      <XtxLeftBanner></XtxLeftBanner>
      <!-- 指示器 -->
      <ol>
        <li class="current"><i></i></li>
        <li><i></i></li>
        <li><i></i></li>
      </ol>
    </div>
  </div>
</template>

<script>

import XtxLeftBanner from "@/components/banner/XtxLeftBanner.vue";

export default {
  components: {
    XtxLeftBanner
  }
}
</script>

<style lang="less" scoped>
/* 轮播区域 */
.banner {
  height: 500px;
  background-color: #F5F5F5 ;
}
.banner .wrapper {
  position: relative;
  overflow: hidden;
}
.banner .pic {
  display: flex;
  width: 3720px;
  height: 500px;
}
.banner .pic li {
  width: 1240px;
  height: 500px;
}

.banner ol {
  position: absolute;
  right: 17px;
  bottom: 17px;
  display: flex;
}
.banner ol li {
  cursor: pointer;
  margin-left: 8px;
  padding: 4px;
  width: 22px;
  height: 22px;
  background-color: transparent;
  border-radius: 50%;
}
.banner ol li i {
  display: block;
  width: 14px;
  height: 14px;
  background-color: rgba(255,255,255,0.5);
  border-radius: 50%;
}
.banner ol .current {
  background-color: rgba(255,255,255,0.5);
}
.banner ol .current i {
  background-color: #fff;
}
</style>