<template>
  <div>
    <my-bread level1='个人信息' level2='修改个人信息'></my-bread>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <i class="el-icon-s-grid"></i><span>修改其他个人信息</span>
      </div>
      <div>
        <el-form label-width="85px" :inline="true" :model ="ManMsg" :rules= "rules" ref= "ManMsg">
          <el-form-item label="职工号:" prop = "userid">
            <el-input v-model ="ManMsg.userid" :disabled = "true"></el-input>
          </el-form-item>

          <el-form-item label = "姓名:" prop = "name">
            <el-input v-model ="ManMsg.name" :disabled = "true"></el-input>
          </el-form-item>

          <el-form-item label = "部门:" prop = "department">
            <el-input v-model ="ManMsg.department" :disabled = "true"></el-input>
          </el-form-item>

          <el-form-item label = "职位:" prop = "position">
            <el-input v-model ="ManMsg.position" :disabled= "true"></el-input>
          </el-form-item>

          <el-form-item label = "手机号:"  prop = "phone" >
            <el-input v-model ="ManMsg.phone" placeholder="请输入新手机号" ></el-input>
          </el-form-item>

          <el-form-item label = "邮箱:"  prop = "email">

            <el-input type = "email" v-model="ManMsg.email" placeholder="请输入新邮箱"  ></el-input>

          </el-form-item>

          <!--获取验证码-->
          <el-form-item label="验证码:" prop ="textcode">
            <el-input v-model="ManMsg.testcode" style="width: 180px" placeholder="请输入验证码"></el-input>
            <el-button type="primary"  v-show = "show" @click="getCode" :disabled="disable" style="margin-left: 15px" :class="{ codeGeting:isGeting }">{{getCode}}</el-button>
          </el-form-item>
        </el-form>

        <el-button  type="primary"  @click = "dgVisible2()"  style="display:block;margin:0 auto">确认修改</el-button>
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
    var checkEmail = (rule, value, callback) => {
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
    }
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
      show: true,
      count: '',
      timer: null,
      ManMsg: { },
      // 校验规则
      rules: {
        userid: [],
        name: [],
        department: [],
        position: [],
        email: [
          {required: true, message: '请输入邮箱'},
          { validator: checkEmail, trigger: 'blur' }
        ],
        phone: [
          {required: true, message: '请输入手机号'},
          { validator: checkPhone, trigger: 'blur' }
        ],
        textcode: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      },
      dialogVisible2: false,
      dialogVisible1: false

    }
  },
  created () {
    this.editInfo()
  },
  methods: {
    // 发起请求
    getMag () {
      this.axios({
        url: 'account/edit/info',
        method: 'post',
        data: this.ManMsg
      })
    },
    dgVisible2 () {
      this.dialogVisible2 = true
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
  width: 100%;
}
</style>
