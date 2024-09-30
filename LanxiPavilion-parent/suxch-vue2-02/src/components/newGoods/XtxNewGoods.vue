<template>
  <!-- 新鲜好物 -->
  <div class="goods wrapper">
    <div class="title">
      <div class="left">
        <h3>新鲜好物</h3>
        <p>新鲜出炉 品质靠谱</p>
      </div>
      <div class="right">
        <a href="#" class="more"
        >查看全部<span class="iconfont icon-arrow-right-bold"></span
        ></a>
      </div>
    </div>
    <div class="bd">
      <ul>
        <li v-for="(item) in goods" :key="item.imgPath">
          <a href="#">
            <!--
                请注意：动态引用静态资源的时候，@/ 需要直接拼接到require中，不能由数据进行传递，否则不能被编译
                require使用时，其中的引用符号并非单引号，也需要格外注意
                第三注意点：文件后缀名也需要写死才可以。。。
             -->
            <div class="pic">
              <img :src="require(`@/${item.imgPath}.png`)" alt="图片走丢了" />
            </div>
            <div class="txt">
              <h4>{{item.goodName}}</h4>
              <p>¥ <span>{{item.goodPrice}}</span></p>
            </div>
          </a>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    this.goods = [
      {
        imgPath: '@/assets/images/goods1.png',
        goodName: 'KN95级莫兰迪色防护口罩',
        goodPrice: 79
      }, {
        imgPath: '@/assets/images/goods2.png',
        goodName: 'KN95级莫兰迪色防护口罩',
        goodPrice: 566
      }, {
        imgPath: '@/assets/images/goods3.png',
        goodName: '法拉蒙高颜值记事本可定制',
        goodPrice: 58
      }, {
        imgPath: '@/assets/images/goods4.png',
        goodName: '科技布布艺沙发',
        goodPrice: 3759
      }
    ];
    /*
    * 这段代码主要是为了配合动态加载静态图片时，修改路径所需要的
    * */
    for (let g_i = 0, g_len = this.goods.length; g_i < g_len; g_i++) {
      let oneGood = this.goods[g_i];
      let currImgPath = oneGood.imgPath;
      currImgPath = currImgPath.substring(2, currImgPath.indexOf('.'));
      oneGood.imgPath = currImgPath;
    }
  },
  data () {
    return {
      goods: []
    }
  }
}
</script>

<style lang="less" scoped>
/* 新鲜好物 */
.goods .bd ul {
  display: flex;
  justify-content: space-between;
}
.goods .bd li {
  width: 304px;
  height: 404px;
  background-color: #EEF9F4;
}
.goods .bd li {
  display: block;
}
.goods .bd li .pic {
  width: 304px;
  height: 304px;
}
.goods .bd li .txt {
  text-align: center;
}
.goods .bd li h4 {
  margin-top: 17px;
  margin-bottom: 8px;
  font-size: 20px;
}
.goods .bd li p {
  font-size: 18px;
  color: #AA2113;
}
.goods .bd li p span {
  font-size: 22px;
}
</style>