<template>
    <div class="forget-wrap">
        <el-form
        class="forget-form"
        ref="ruleForm"
        :rules="forgetFormRules"
        label-position="top"
        label-width="80px"
        :model="ruleForm">
        <h2 class="h2">忘记密码</h2>
            <el-form-item label="工号" prop="userid" class="type">
                <el-input v-model="ruleForm.userid"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="password" class="type">
                <el-input type="password" v-model="ruleForm.password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="resure" class="type">
                <el-input type="password" v-model="ruleForm.repassword"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="confirm">
                <el-input v-model="ruleForm.confirm" class="type1"></el-input>
                <el-button class="confirm-btn" size="small" type="primary" :disabled="disable" :class="{ codeGeting:isGeting }" @click="getVerifyCode">{{getCode}}</el-button>
            </el-form-item>
            <el-form-item>
              <el-button
              @click = "doSure"
              class="sure-btn" type="primary" plain >确定</el-button>
              <div class="back">
                <router-link to="login" exact>返回</router-link>
              </div>
            </el-form-item>
          </el-form>
    </div>
</template>

<script>
export default {
  data () {
    return {
      getCode: '获取验证码',
      isGeting: false,
      count: 20,
      disable: false,
      ruleForm: {
        userid: '',
        password: '',
        code: ''
      },
      forgetFormRules: {
        userid: [
          { message: '请输入职工号', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { message: '请输入新密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        resure: [
          { message: '请再次输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    getVerifyCode () {
      this.$refs.ruleForm.validate(async (valid) => {
        if (!valid) return false
        this.$axios({
          url: 'sendMail',
          method: 'post',
          data: {
            id: this.ruleForm.userid
          }
        })
          .then((res) => {
            console.log(res)
            if (res.data.code === 200) {
              this.$message.success(res.data.msg)
            } else {
              this.$message.error(res.data.msg)
            }
          })
          .catch((error) => {
            console.log(error)
          })
        var countDown = setInterval(() => {
          if (this.count < 1) {
            this.isGeting = false
            this.disable = false
            this.getCode = '获取验证码'
            this.count = 20
            clearInterval(countDown)
          } else {
            this.isGeting = true
            this.disable = true
            this.getCode = this.count-- + 's后重发'
          }
        }, 1000)
      })
    },
    doSure () {
      if (this.ruleForm.password !== this.ruleForm.repassword) {
        alert('两次密码不一致')
        return
      }
      this.$refs.ruleForm.validate(async valid => {
        if (!valid) return false
        this.$axios({
          url: 'forget',
          method: 'post',
          data: {
            id: this.ruleForm.userid,
            psd: this.ruleForm.password,
            code: this.ruleForm.confirm
          }
        })
          .then((res) => {
            console.log(res)
            if (res.data.code === 200) {
              this.$router.push({name: 'login'})
              this.$message.success(res.data.msg)
              // window.sessionStorage.setItem('token', res.data.token)
              // eslint-disable-next-line no-undef
            } else {
              this.$message.error(res.data.msg)
            }
          })
          .catch((error) => {
            console.log(error)
          })
        // eslint-disable-next-line no-unused-vars
        // this.$axios.post('/forget', this.formdata).then(res => {
        //   const {
        //     // eslint-disable-next-line no-unused-vars
        //     data,
        //     // eslint-disable-next-line no-unused-vars
        //     meta: {msg, code}
        //   } = res.data
        //   if (code === 200) {
        //     this.$router.push({name: 'login'})
        //     this.$message.success(msg)
        //     // eslint-disable-next-line no-undef
        //   } else {
        //     this.$message.error(msg)
        //   }
        // })
      })
      // this.$router.push({name: 'login'})
    }
  }
}
</script>

<style>
.codeGeting{
    background: #cdcdcd;
    border-color: #cdcdcd;
  }
.forget-wrap{
    height:100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color:#0f294f;
    /* url(../../assets/images/bg.png) no-repeat; */
    /* background-size: 100% 100%; */
}
.forget-wrap .forget-form {
    width:400px;
    background-color: none;
    border-radius: 5px;
    padding:20px
}
.el-form--label-top .el-form-item__label{
  padding-top: 15px;
  padding-bottom: 0px;
  color: #ffffff;
}
.forget-wrap .sure-btn {
    margin-top: 40px;
    width: 280px;
}

.forget-wrap .back {
    text-align: right;
    size: 10px;
}

a {
  text-decoration: none;
  color: #ffffff;
  font-size: 14px;
}

.router-link-active{
    text-decoration: none;
}
.el-form-item{
    margin-bottom: 0;
}
.h2{
    color: #ffffff;
    margin:0;
}
.type{
  width: 280px;
  height: 90px;
}
.type1{
  width:280px;
}
.back{
  width:280px;
}
</style>
