<template>
  <div class="container">
    <el-card  class="login-card">
      <el-row :span="24">
        <el-col :span="12">
          <img src="../../assets/images/img.png" width="100%" height="100%">
        </el-col>
        <el-col :span="12" class="login-wrap">
          <div class="el">
            <el-form
              class="login-form"
              ref="loginFormRef"
              :rules="loginFormRules"
              label-position="top"
              label-width="80px"
              :model="formdata"
              >
              <h2 class="h2">工资管理系统</h2>
              <el-form-item label="工号" prop="userid">
                <el-input
                  placeholder="请输入职工号"
                  prefix-icon="iconfont el-icon-login-yonghu"
                  v-model="formdata.userid"
                ></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input
                  placeholder="请输入密码"
                  prefix-icon="iconfont el-icon-login-mimaicon"
                  type="password"
                  v-model="formdata.password"
                ></el-input>
              </el-form-item>
              <el-button @click="doLogin" class="login-btn" type="primary" plain
                >登录</el-button
              >
              <div class="forget-password">
                <router-link to="forget" exact>忘记密码</router-link>
              </div>
              </el-form>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
// import axios from 'axios'
// import md5 from 'js-md5'
export default {
  data () {
    return {
      formdata: {
        userid: '',
        password: ''
      },
      loginFormRules: {
        userid: [
          { required: true, message: '输入职工号', trigger: 'blur' }
          // {
          //   min: 2,
          //   max: 10,
          //   message: '长度在 2 到 10 个字符',
          //   trigger: 'blur'
          // }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
          // { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    doLogin () {
      this.$refs.loginFormRef.validate(async (valid) => {
        if (!valid) return false
        this.$axios({
          url: 'login',
          method: 'post',
          data: {
            id: this.formdata.userid,
            psd: this.formdata.password
          },
          transformRequest: [
            function (data) {
              // Do whatever you want to transform the data
              let ret = ''
              for (let it in data) {
                ret +=
                  encodeURIComponent(it) +
                  '=' +
                  encodeURIComponent(data[it]) +
                  '&'
              }
              return ret
            }
          ]
        })
          .then((res) => {
            console.log(res)
            if (res.data.code === 200) {
              this.$router.push({name: 'index'})
              this.$message.success(res.data.msg)
              this.userData = res.data.data.user
              this.photo = res.data.data.user.name[0]
              this.userToken = res.data.data.token
              this.menuList = res.data.data.menus
              this.code = res.data.code
              this.msg = res.data.msg
              // console.log(this.userToken)
              // console.log(this.userData)
              console.log(this.menuList)
              // console.log(this.code)
              // 将用户token保存到vuex中
              sessionStorage.setItem('userData', JSON.stringify(this.userData))
              sessionStorage.setItem('photo', this.photo)
              sessionStorage.setItem('Token', this.userToken)
              sessionStorage.setItem('Menu', JSON.stringify(this.menuList))
              sessionStorage.setItem('Code', this.code)
              sessionStorage.setItem('Msg', this.msg)
              // eslint-disable-next-line no-undef
            } else {
              this.$message.error(res.data.msg)
            }
          })
          .catch((error) => {
            console.log(error)
          })
      })
    }
  }
}
</script>

<style scope>
.container {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color:#4d5f88;
}
.login-card {
  width: 1000px;
  height: 550px;
  box-shadow:5px 5px 10px rgba(51, 51, 51, 0.459) !important;
}
.el-card__body {
  padding: 0;
}
.el-form--label-top .el-form-item__label {
  padding-top: 15px;
  padding-bottom: 0px;
  color: #000000;
}
.el{
  background-color: #ffff
}
.login-wrap .login-form {
  border-radius: 5px;
  padding: 100px;
  /* background-color:#75a3e7 */
}
.login-wrap .login-btn {
  margin-top: 40px;
  width: 100%;
}

.login-wrap .forget-password {
  text-align: right;
  size: 10px;
}

a {
  text-decoration: none;
  color: #0000009d;
  font-size: 14px;
}

.router-link-active {
  text-decoration: none;
}
.el-form-item {
  margin-bottom: 0;
}
.h2 {
  color: #000000;
}
</style>
