import axios from 'axios'
const MyHttpServer = {}

MyHttpServer.install = (Vue) => {
// 4. 添加实例方法
  axios.defaults.baseURL = 'http://47.92.165.44:8080/salary/'
  Vue.prototype.$axios = axios
}

export default MyHttpServer
// import axios from 'axios'

// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'

// 请求拦截器
axios.interceptors.request.use(function (config) {
  if (config.url !== 'login') {
    const Token = sessionStorage.getItem('Token')
    config.headers['Authorization'] = Token
    console.log(config)
  }
  return config
}, function (error) {
  return Promise.reject(error)
})
// 响应拦截器
axios.interceptors.response.use(function (response) {
  return response
}, function (error) {
  return Promise.reject(error)
})
