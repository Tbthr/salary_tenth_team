<template>
  <el-container>
    <el-header>
      <div>
        <img src="../../assets/images/logo1.png" width="33%" height="33%">
        <span style="margin-left:10px; font-size:30px; ">工资管理系统</span>
      </div>
      <div class="user">
        <el-dropdown @command="handleCommand">
          <el-avatar>{{userName}}</el-avatar>
          <el-dropdown-menu>
            <el-dropdown-item command="/account/info">个人信息</el-dropdown-item>
            <el-dropdown-item command="login">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <!-- <el-button type="info" @click="logout" class="logout" size="small">退出登录</el-button> -->
      </div>
    </el-header>
    <el-container>
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle-button" @click="togleCollapse">|||</div>
        <el-menu unique-opened :collapse="isCollapse" :collapse-transition="false" router :default-active="activePath" background-color="#4d5f88" text-color="#fff" active-text-color="#fff">
          <el-menu-item @click="go()">
            <i class="el-icon-s-home"></i>
            <span slot="title">首页</span>
         </el-menu-item>
          <el-submenu :index="item.id+1+''" v-for="item in menuList" :key="item.id" >
            <template slot="title">
              <i :class="iconObj[item.id]"></i>
              <span>{{item.name}}</span>
            </template>
            <el-menu-item :index="subItem.url" v-for="subItem in item.children" :key="subItem.id" @click="saveNavState('/' + subItem.path)">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>{{ subItem.name}}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
        </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
  data () {
    return {
      // 左侧菜单数据
      menuList: [],
      iconObj: {
        '3': 'iconfont el-icon-login-geren',
        '4': 'iconfont el-icon-login-bills',
        '5': 'iconfont el-icon-login-yuangong',
        '6': 'iconfont el-icon-login-permission',
        '17': 'iconfont el-icon-login-rizhi'
      },
      // 默认不折叠
      isCollapse: false,
      // 被激活导航地址
      activePath: '',
      userName: ''
    }
  },
  created () {
    this.getMenuList()
    this.activePath = window.sessionStorage.getItem('Token')
  },
  methods: {
    handleCommand (command) {
      // this.$message('click on item ' + command)
      if (command === 'login') {
        window.sessionStorage.clear()
        this.$message.success('退出成功！')
        this.$router.push({name: 'login'})
      } else {
        this.$router.push(command)
      }
    },
    getMenuList () {
      if (JSON.parse(sessionStorage.getItem('userData')) !== null) {
        // 自定义代码
        console.log(sessionStorage.getItem('Code'))
        if (sessionStorage.getItem('Code') === '200') {
          this.menuList = JSON.parse(sessionStorage.getItem('Menu'))
          this.$message.success(sessionStorage.getItem('Msg'))
          this.userName = sessionStorage.getItem('photo')
        } else if (sessionStorage.getItem('Code') === '401') {
          this.$message.warning(sessionStorage.getItem('Msg'))
          this.$router.push({name: 'login'})
          window.localStorage.clear()
        } else {
          this.$message.error(sessionStorage.getItem('Msg'))
        }
      } else {
        this.$router.push({name: 'login'})
      }
      // this.$axios.get('http://salary1.free.idcfengye.com/salary/index')
      //   .then((res) => {
      //     console.log(res)
      //     if (res.data.meta.code !== 200) {
      //       this.$message.error(res.data.meta.msg)
      //       console.log(res.data)
      //     } else {
      //       this.menuList = res.data.data
      //     }
      //   })
      //   .catch(function (error) {
      //     console.log(error)
      //   })
    },
    // 菜单的折叠与展开
    togleCollapse () {
      this.isCollapse = !this.isCollapse
    },
    saveNavState (activePath) {
      window.sessionStorage.setItem('activePath', activePath)
      this.activePath = window.sessionStorage.getItem('activePath')
    },
    go () {
      this.$router.push({name: 'index'})
    }
  }
}
</script>
<style scoped>
.el-container {
  height: 100%;
}
.el-header {
  background-color: #4d5f88;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-left: -10px;
  color: #fff;
  font-size: 20px;
}
.user{
  display: flex;
}
.logout{
  margin-left: 20px;
}
.el-header .toggle-button{
    display: flex;
    align-items: center;
}
.el-aside {
  background-color: #4d5f88;
}
.el-aside .el-menu {
    border: none;
  }
.el-main {
  background-color: #eaedf1;
}
.iconfont{
  margin-right: 10px;
}
.toggle-button {
  background-color: #232b3d;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  /* 鼠标放上去变成小手 */
  cursor: pointer;
}
</style>
