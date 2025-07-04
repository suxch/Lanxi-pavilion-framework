import Vue from 'vue'
import App from './App.vue'
import './styles/base.css' // css 样式重置
import './styles/common.css' // 公共全局样式
import './assets/iconfont/iconfont.css' // 字体图标的样式
import axios from "axios";

Vue.prototype.$http = axios;

const request = axios.create({
  headers: {
    'Context-type': 'application/json;charset=UTF-8'
  }
});

Vue.prototype.$api = request;
Vue.config.productionTip = true

new Vue({
  render: h => h(App),
}).$mount('#app')
