import Vue from 'vue'
import App from './App.vue'
import axios from "axios";

Vue.prototype.$http = axios;

const request = axios.create({
  headers: {
    'Context-type': 'application/json;charset=UTF-8'
  }
});

Vue.prototype.$api = request;

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
