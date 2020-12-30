<template>
  <div>
    <my-bread level1='个人信息' level2='修改个人信息'></my-bread>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <i class="el-icon-s-grid"></i><span>修改其他个人信息</span>
      </div>
      <div >
        <center>
          <el-form label-width="85px" :inline="true" :model ="ManMsg" :rules= "rules" ref= "ManMsg">

            <el-form-item label="职工号:" style="margin-top:10px">
              <el-input v-model ="ManMsg.id" readonly="readonly"></el-input>
            </el-form-item>

            <el-form-item label = "姓名:" style="margin-top:10px">
              <el-input v-model ="ManMsg.name" readonly="readonly"></el-input>
            </el-form-item>

            <el-form-item label = "部门:" style="margin-top:10px">
              <el-input v-model ="ManMsg.department.departName" readonly="readonly"></el-input>
            </el-form-item>

            <el-form-item label = "职位:" style="margin-top:10px">
              <el-input v-model ="ManMsg.position" readonly="readonly"></el-input>
            </el-form-item>

            <el-form-item label = "手机号:"  prop="Phone" style="margin-top:10px">
              <el-input v-model ="ManMsg.Phone" placeholder="请输入新手机号" ></el-input>
            </el-form-item>

            <el-form-item label="验证码:" prop="confirm" style="margin-top:10px">
                  <el-input v-model="ManMsg.confirm" class="type1" placeholder="请输入验证码" style="width:100px"></el-input>
            </el-form-item>

            <el-from-item >
              <el-button class="confirm-btn" size="small" style="margin-top:10px" type="primary" :disabled="disable" :class="{ codeGeting:isGeting }" @click="getVerifyCode" >{{getCode}}</el-button>
            </el-from-item>
        </el-form>
        </center>

        <el-button  type="primary"  @click = " changeMsg()"  style="display:block;margin:0 auto">确认修改</el-button>
      </div>
    </el-card>
    <el-dialog title = "提示" :visible.sync= "dialogVisible2" width="30%" :before-close="handleClose">
      <span>修改成功</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click= "dialogVisible2 = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import myBread from '../cuscom/myBread.vue'
export default {
  components: { myBread },
  data () {
    // 验证邮箱的规则
    /* var checkEmail = (rule, value, callback) => {
      const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
      if (!value) {
        return callback(new Error('邮箱不能为空'))
      }
      setTimeout(() => {
        if (mailReg.test(value)) {
          callback()
        } else {
          callback(new Error('请输入正确的邮箱格式'))
        }
      }, 100)
    } */
    // 验证手机号的规则
    var checkPhone = (rule, value, callback) => {
      const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/
      if (!value) {
        return callback(new Error('手机号不能为空'))
      }
      setTimeout(() => {
      // Number.isInteger是es6验证数字是否为整数的方法,实际输入的数字总是识别成字符串
      // 所以在前面加了一个+实现隐式转换

        if (!Number.isInteger(+value)) {
          callback(new Error('请输入数字'))
        } else {
          if (phoneReg.test(value)) {
            callback()
          } else {
            callback(new Error('手机号格式不正确'))
          }
        }
      }, 100)
    }

    return {
      getCode: '获取验证码',
      isGeting: false,
      count: 20,
      disable: false,
      show: true,
      confirm: '',
      timer: null,

      ManMsg: {
        department: {
          departName: ''
        }
      },
      ruleForm: {

      },
      // 校验规则
      rules: {

        /* email: [
          {required: true, message: '请输入邮箱'},
          { validator: checkEmail, trigger: 'blur' }
        ], */
        Phone: [
          {required: true, message: '请输入手机号'},
          { validator: checkPhone, trigger: 'blur' }
        ],
        confirm: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      },
      dialogVisible2: false,
      dialogVisible1: false

    }
  },
  created () {
    this.getManMsg()
  },
  mounted () {
    // 1、数据首次加载完后 → 获取图片（或外层框）宽度，并设置其高度
    this.$nextTick(() => {
      // 获取图片（或外层框）
      let test = this.$refs.test
      // 获取其宽度
      let wtest = test[0].getBoundingClientRect().width
      // 设置其高度（以宽度的60%为例）
      this.test.height = 0.6 * wtest + 'px'
    })
    // 2、挂载 reisze 事件 → 屏幕缩放时监听宽度变化
    const that = this
    window.onresize = () => {
      return (() => {
        window.screenWidth = document.body.clientWidth
        that.screenWidth = window.screenWidth
        console.log(that.screenWidth)
        this.$nextTick(() => {
          let test = this.$refs.test
          let wtest = test[0].getBoundingClientRect().width
          this.test.height = 0.6 * wtest + 'px'
        })
      })()
    }
  },
  watch: {
    screenWidth (val) {
      // 为了避免频繁触发resize函数导致页面卡顿，使用定时器
      if (!this.timer) {
        // 一旦监听到的screenWidth值改变，就将其重新赋给data里的screenWidth
        this.screenWidth = val
        this.timer = true
        let that = this
        setTimeout(function () {
          // 打印screenWidth变化的值
          console.log(that.screenWidth)
          that.timer = false
        }, 400)
      }
    }
  },
  methods: {
    // 判断手机号是否为空

    // 发起请求
    getVerifyCode () {
      this.$refs.ManMsg.validate(async (valid) => {
        if (!valid) return false
        this.$axios({
          url: 'sendMail',
          method: 'post',
          data: {
            id: this.ManMsg.id
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
    // 获取信息
    getManMsg () {
      const AccountMsg = JSON.parse(sessionStorage.getItem('manMsg'))
      this.ManMsg = AccountMsg
      console.log(this.ManMsg)
    },
    // 发送修改信息请求
    async changeMsg () {
      this.$axios({
        url: 'account/edit/info',
        method: 'post',
        data: {
          id: this.ManMsg.id,
          code: this.ManMsg.confirm,
          phone: this.ManMsg.Phone
        }
      })
        .then((res) => {
          console.log(res)
          if (res.data.code === 200) {
            this.ManMsg = res.data.data
            this.$message.success(res.data.msg)
          } else {
            this.$message.error(res.data.msg)
          }
          if (res.data.phone === 200) {
            this.ManMsg = res.data.data
            this.$M.success(res.data.msg)
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .catch((error) => {
          console.log(error)
        })
    },
    dgVisible2 () {
      this.dialogVisible2 = true
    },
    handleClose (done) {
      this.$confirm('确定关闭吗').then(() => {
        // function(done)，done 用于关闭 Dialog
        done()

        console.info("点击右上角 'X' ，取消按钮或遮罩层时触发")
      }).catch(() => {
        console.log('点击确定时触发')
      })
    }
  }

}
</script>

<style scoped>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  margin: 20px auto;
  width: 50%;
  height: 100%;
}
.el-button {
  margin-top: 10px;
}
</style>
