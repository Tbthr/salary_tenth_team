import axios from 'axios'
const MyHttpServer = {}

MyHttpServer.install = (Vue) => {
// 4. 添加实例方法
  axios.default.baseURL = 'http://salary1.free.idcfengye.com'
  Vue.prototype.$http = axios
}

// export default MyHttpServer
// import axios from 'axios'

// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'

// // 请求拦截器
// axios.interceptors.request.use(function (config) {
//   return config
// }, function (error) {
//   return Promise.reject(error)
// })
// // 响应拦截器
// axios.interceptors.response.use(function (response) {
//   return response
// }, function (error) {
//   return Promise.reject(error)
// })
