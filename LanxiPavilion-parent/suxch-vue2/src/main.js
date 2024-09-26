import Vue from 'vue'
import App from './App.vue'
import SuxchButton from "@/components/SuxchButton.vue";

Vue.config.productionTip = false
Vue.component("SuxchButton", SuxchButton)
new Vue({
  //render: h => h(App)
  // 下方是完整写法
  render: (createElement) => {
    return createElement(App);
  }
}).$mount('#app')
