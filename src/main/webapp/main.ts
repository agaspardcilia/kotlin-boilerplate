import Vue from 'vue';
import App from '@/App.vue';
import router from '@/common/router';
import store from '@/common/store';
import Notifications from 'vue-notification'

Vue.config.productionTip = false;
Vue.use(Notifications);
new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
