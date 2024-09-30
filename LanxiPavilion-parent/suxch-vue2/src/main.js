import Vue from 'vue'
import App from './App.vue'
import SuxchButton from "@/components/SuxchButton.vue";
import axios from "axios";

Vue.prototype.$http = axios;

const request = axios.create({
  headers: {
    'Context-type': 'application/json;charset=UTF-8'
  }
});

Vue.prototype.$api = request;

Vue.config.productionTip = false
Vue.component("SuxchButton", SuxchButton)
new Vue({
  //render: h => h(App)
  // 下方是完整写法
  render: (createElement) => {
    return createElement(App);
  }
}).$mount('#app')
