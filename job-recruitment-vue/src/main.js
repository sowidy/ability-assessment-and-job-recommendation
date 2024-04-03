import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import API from '@/api';
import 'animate.css';
import "swiper/css/swiper.css";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Carsousel from "@/components/Carousel";
import Remark from "@/components/Remark";
import Header from '@/components/Header'
import JobCard from '@/components/JobCard'
import StuCard from '@/components/StuCard'
import Footer from '@/components/Footer'
import { formatDate } from "@/utils/utilities";
import PopupProgress from "@/components/popup-progress";
import * as echarts from 'echarts'
Vue.prototype.$echarts = echarts;
// import AOS from "aos";
// import 'aos/dist/aos.css'
// Vue.use(AOS)
Vue.use(PopupProgress);
Vue.component(Carsousel.name, Carsousel);
Vue.component(JobCard.name, JobCard);
Vue.component(StuCard.name, StuCard);
Vue.component(Remark.name, Remark);
Vue.component(Header.name, Header);
Vue.component(Footer.name, Footer);

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.filter("formatDate", formatDate);
Vue.prototype.$API = API;
new Vue({
  render: h => h(App),
  router,
  store,
  beforeCreate() {
    Vue.prototype.$bus = this //安装全局事件总线
  },
}).$mount('#app')