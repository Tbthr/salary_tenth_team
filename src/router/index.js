import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/login/login.vue'
import Index from '../components/home/index.vue'
import Home from '../components/home/home.vue'
import Forget from '../components/login/forget.vue'
import User from '../components/user/user.vue'
import Role from '../components/authority/role'
import Menu from '../components/authority/menu'
import Submit from '../components/bills/submit.vue'
import Log from '../components/log/log.vue'
import UserAdd from '../components/user/useradd'
import AccountInfo from '../components/account/accountInfo'
import AccountEdit from '../components/account/accountEdit'
import EditInfo from '../components/account/editInfo'
/// import Account from '../components/account/info'
import BillsInfo from '../components/bills/info'
import Check from '../components/bills/check'
Vue.use(Router)

const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
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
          name: 'useradd',
          path: 'user/add',
          component: UserAdd
        },
        {
          name: 'submit',
          path: 'bills/submit',
          component: Submit
        },
        {
          name: 'log',
          path: 'sys/log',
          component: Log
        },
        {
          name: 'accountInfo',
          path: 'account/info',
          component: AccountInfo
        },
        {
          name: 'accountEdit',
          path: 'account/edit/psd',
          component: AccountEdit
        },
        {
          name: 'editInfo',
          path: 'account/edit/info',
          component: EditInfo
        },
        // {
        //   name: 'account',
        //   path: 'account/info',
        //   component: Account
        // },
        {
          name: 'info',
          path: 'bills/info',
          component: BillsInfo
        },
        {
          name: 'check',
          path: 'bills/check',
          component: Check
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
