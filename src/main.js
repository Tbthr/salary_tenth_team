// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import axios from 'axios'
import store from './store'
import Validator from 'vue-validator'
import MyBread from './components/cuscom/myBread.vue'
import MyHttpServer from './plugins/http'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/reset.css'
import './assets/font/iconfont.css'

Vue.use(ElementUI)
Vue.use(MyHttpServer)
Vue.component(MyBread.name, {MyBread})
// 全局配置axios
Vue.prototype.$axios = axios
// require('./mock/mock.js')
Vue.use(Validator)
// eslint-disable-next-line standard/computed-property-even-spacing

// axios.interceptors.request.use(config => {
//   let token = sessionStorage.getItem('Authorization')
//   if (token) {
//     config.headers['Authorization'] = token
//   }
//   return config
// }, error => {
//   // 对请求错误做些什么
//   return Promise.reject(error)
// })

// Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store: store,
  components: { App },
  template: '<App/>'
})

// router.beforeEach((to, from, next) => {
//   if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
//     // eslint-disable-next-line no-undef
//     if (store.state.token) { // 通过vuex state获取当前的token是否存在
//       next()
//     } else {
//       next({
//         path: '/login',
//         query: {redirect: to.fullPath} // 将跳转的路由path作为参数，登录成功后跳转到该路由
//       })
//     }
//   } else {
//     next()
//   }
// })
