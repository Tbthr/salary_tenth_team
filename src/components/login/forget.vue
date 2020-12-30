<template>
    <div class="forget-wrap">
      <el-card class="forget-card">
        <el-row :span="24">
          <el-col :span="12">
            <img src="../../assets/images/3.png" width="100%" height="100%">
          </el-col>
          <el-col :span="12" class="text">
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
          </el-col>
        </el-row>
      </el-card>
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
          { min: 10, max: 10, message: '长度为 10 个字符', trigger: 'blur' }
        ],
        password: [
          { message: '请输入新密码', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
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
      })
      // this.$router.push({name: 'login'})
    }
  }
}
</script>

<style scoped>
/* .codeGeting{
    background: #cdcdcd;
    border-color: #cdcdcd;
  } */
.forget-wrap{
    height:100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color:#4d5f88;
    /* background:url("../../assets/images/4.jpg") no-repeat; */
    /* background-size: 100% 100%; */
}
.forget-card {
  width: 1000px;
  height: 550px;
  box-shadow:5px 5px 10px rgba(51, 51, 51, 0.459) !important;
}
.el-card__body {
  padding: 0;
}
.text .forget-form {
    border-radius: 5px;
    padding: 70px 60px 50px 40px;
}
.el-form--label-top .el-form-item__label {
  padding-top: 15px;
  padding-bottom: 0px;
  color: #000000;
}
.text .sure-btn {
    margin-top: 40px;
    width: 280px;
}

.text .back {
    text-align: right;
    size: 10px;
}

a {
  text-decoration: none;
  color: #00000075;
  font-size: 14px;
}

.router-link-active{
    text-decoration: none;
}
.el-form-item{
    margin-bottom: 0;
}
.h2{
    color: #000;
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
