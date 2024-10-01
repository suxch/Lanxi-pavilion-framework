import Vue from 'vue'
import App from './App.vue'
import LanxiPavilionApp from "@/LanxiPavilionApp.vue";
import axios from "axios";
import {BootstrapVue, IconsPlugin} from "bootstrap-vue";
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.prototype.$http = axios;

const request = axios.create({
  headers: {
    'Context-type': 'application/json;charset=UTF-8'
  }
});

Vue.prototype.$api = request;

Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

Vue.component(App)

Vue.config.productionTip = false

new Vue({
  render: h => h(LanxiPavilionApp),
}).$mount('#app')
