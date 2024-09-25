import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

new Vue({
  //render: h => h(App)
  // 下方是完整写法
  render: (createElement) => {
    return createElement(App);
  }
}).$mount('#app')
