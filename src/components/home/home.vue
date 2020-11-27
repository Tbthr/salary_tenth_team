<template>
  <el-container>
    <el-header>
      <div>
        <!-- <img src="../../assets/logo.jpg"> -->
        <span>财务管理系统</span>
      </div>
      <el-button type="info" @click="logout">退出登录</el-button>
    </el-header>
    <el-container>
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle-button" @click="togleCollapse">|||</div>
        <el-menu unique-opened :collapse="isCollapse" :collapse-transition="false" router :default-active="activePath" background-color="#0f294f" text-color="#fff" active-text-color="#409FFF">
          <el-submenu :index="item.id+''" v-for="item in menuList" :key="item.id" >
            <template slot="title">
              <!-- <i :class="iconObj[item.id]"></i> -->
              <span>{{item.authName}}</span>
            </template>
            <el-menu-item :index="subItem.path" v-for="subItem in item.children" :key="subItem.id" @click="saveNavState('/' + subItem.path)">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>{{ subItem.authName}}</span>
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
      // iconObj: {
      //   '125': 'iconfont icon-user',
      //   '103': 'iconfont icon-tijikongjian',
      //   '101': 'iconfont icon-shangpin',
      //   '102': 'iconfont icon-danju',
      //   '145': 'iconfont icon-baobiao'
      // },
      // 默认不折叠
      isCollapse: false,
      // 被激活导航地址
      activePath: ''
    }
  },
  created () {
    this.getMenuList()
    this.activePath = window.sessionStorage.getItem('Token')
  },
  methods: {
    logout () {
      // 清空token
      window.sessionStorage.clear()
      this.$router.push('/login')
    },
    getMenuList () {
      if (JSON.parse(sessionStorage.getItem('userData')) !== null) {
        // 自定义代码
        console.log(sessionStorage.getItem('Code'))
        if (sessionStorage.getItem('Code') === '200') {
          this.menuList = JSON.parse(sessionStorage.getItem('Menu'))
          this.$message.success(sessionStorage.getItem('Msg'))
        } else if (sessionStorage.getItem('Code') === '403') {
          this.$message.warning(sessionStorage.getItem('Msg'))
          this.$router.push({name: 'login'})
          window.localStorage.clear()
        } else {
          this.$message.error(sessionStorage.getItem('Msg'))
        }
      } else {
        // this.$router.push({name: 'login'})
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
    }
  }
}
</script>
<style>
.el-container {
  height: 100%;
}
.el-header {
  background-color: #0f294f;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #fff;
  font-size: 20px;
}
.el-header .toggle-button{
    display: flex;
    align-items: center;
}
.el-aside {
  background-color: #0f294f;
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
  background-color: #010b1a91;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  /* 鼠标放上去变成小手 */
  cursor: pointer;
}
</style>
