import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/login/login.vue'
import Index from '../components/home/index.vue'
import Home from '../components/home/home.vue'
import Forget from '../components/login/forget.vue'
import User from '../components/user/user.vue'
import Role from '../components/authority/role.vue'
import Menu from '../components/authority/menu.vue'
import Submit from '../components/bills/submit.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      name: 'login',
      path: '/login',
      component: Login
    },
    {
      name: 'home',
      path: '/',
      meta: {
        requireAuth: true // 添加该字段，表示进入这个路由是需要登录的
      },
      children: [
        {
          name: 'index',
          path: 'index',
          component: Index
        },
        {
          name: 'role',
          path: 'authority/role',
          component: Role
        },
        {
          name: 'menu',
          path: 'authority/menu',
          component: Menu
        },
        {
          name: 'user',
          path: 'user/info',
          component: User
        },
        {
          name: 'submit',
          path: 'bills/submit',
          component: Submit
        }
      ],
      component: Home
    },
    {
      name: 'forget',
      path: '/forget',
      component: Forget
    }
  ]
})
