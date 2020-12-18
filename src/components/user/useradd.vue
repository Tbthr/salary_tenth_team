<template>
<div>
      <!-- 面包屑导航区 -->
    <my-bread level1='员工管理' level2='添加员工'></my-bread>
    <el-card>
        <!-- 添加按钮 -->
        <div slot="header" class="clearfix">
          <i class="el-icon-user-solid"></i><span>新增用户</span>
            <el-button  style="float: right" @click="resetForm('addsForm')">重置</el-button>
            <el-button type="primary" @click="addUser('addsForm')" style="float: right">确认添加</el-button>
            <el-select v-model="roleName" placeholder="请选择用户角色" style="float: right" >
              <el-option label="普通用户" value="ROLE_normal"></el-option>
              <el-option label="部门管理员" value="ROLE_departadmin" disabled></el-option>
              <el-option label="财务管理员" value="ROLE_salaryadmin" disabled></el-option>
              <el-option label="系统管理员" value="ROLE_sysadmin" disabled></el-option>
            </el-select>
        </div>
        <!-- 添加用户表单 -->
        <div>
          <el-form ref="addsForm" :model="addsForm" :rules="rules" label-width="80px" :inline="true">
            <el-form-item label="职工号"  prop="id">
                <el-input  v-model="addsForm.id" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item label="部门" prop="departId">
            <el-select v-model="addsForm.departId" placeholder="请选择部门">
                <el-option  v-for="item in Name"  :key="item.id" :label="item.departName" :value="item.id"></el-option>
            </el-select>
            </el-form-item>
            <el-form-item label="职位" prop="position">
                <el-select v-model="addsForm.position" placeholder="请选择职位">
                <el-option label="教师" value="教师"></el-option>
                <el-option label="教学秘书" value="教学秘书"></el-option>
                <el-option label="财务处长" value="财务处长"></el-option>
                <el-option label="人事财务总管" value="人事财务总管"></el-option>
                <el-option label="院长" value="院长"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
                <el-input v-model="addsForm.name" ></el-input>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
                <el-input v-model.number="addsForm.age"></el-input>
            </el-form-item>
            <el-form-item label="身份证号" prop="idCard">
                <el-input v-model="addsForm.idCard" minlength="16"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="addsForm.email"></el-input>
            </el-form-item>
            <el-form-item label="电话" prop="phone">
                <el-input v-model="addsForm.phone"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="psd">
                <el-input v-model="addsForm.psd"></el-input>
            </el-form-item>
            <el-form-item label="工龄" prop="workYear">
                <el-input v-model="addsForm.workYear"></el-input>
            </el-form-item>
            <el-form-item label="用户类型" prop="userFlag">
                <el-select v-model="addsForm.userFlag" placeholder="请选择用户类型">
                <el-option label="普通用户" value="1"></el-option>
                <el-option label="部门管理员" value="2" disabled></el-option>
                <el-option label="财务管理员" value="3" disabled></el-option>
                <el-option label="系统管理员" value="4" disabled></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="addsForm.gender">
                <el-radio label="m" value="m"></el-radio>
                <el-radio label="f" value="f"></el-radio>
                </el-radio-group>
            </el-form-item>
          </el-form>
        </div>
    </el-card>
</div>
</template>

<script>
import myBread from '../cuscom/myBread.vue'
export default {
  components: { myBread },
  data () {
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
    // 工龄验证
    var checkWorkYear = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('工龄不能为空'))
      }
      setTimeout(() => {
        if (!Number.isInteger(+value)) {
          callback(new Error('请输入数字值'))
        } else {
          if (value < 0) {
            callback(new Error('必须大于 0 '))
          } else {
            callback()
          }
        }
      }, 100)
    }

    return {
      roleName: '',
      addsForm: {},
      Name: {
        id: '',
        departName: ''
      },
      rules: {
        id: [
          { required: true, message: '职工号不能为空', trigger: 'blur' },
          { min: 10, max: 10, message: '长度为10个字符', trigger: 'blur' }
        ],
        departId: [
          { required: true, message: '部门不能为空', trigger: 'change' }],
        name: [
          { required: true, message: '姓名不能为空', trigger: 'blur' }],
        age: [],
        position: [
          { required: true, message: '职位不能为空', trigger: 'change' }],
        email: [
          {required: true, message: '邮箱不能为空'},
          { validator: checkEmail, trigger: 'blur' }
        ],
        phone: [
          {required: true, message: '手机号不能为空'},
          { validator: checkPhone, trigger: 'blur' }
        ],
        idCard: [
          { required: true, message: '身份证号不能为空', trigger: 'blur' },
          { validator: this.checkIdCard, trigger: 'blur' }
        ],
        psd: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 3, max: 10, message: '长度为 3 到 10 个字符', trigger: 'blur' }],
        workYear: [
          { required: true, message: '工龄不能为空', trigger: 'blur' },
          { validator: checkWorkYear, trigger: 'blur' }],
        userFlag: [
          { required: true, message: '用户类型不能为空', trigger: 'change' }],
        gender: []
      }
    }
  },
  created () {
    this.getdepartName()
  },
  methods: {
    // 获取部门信息
    async getdepartName () {
      const { data: res } = await this.$axios.get('user/add/departInfo')
      console.log(res)
      if (res.code === 200) {
        this.Name = res.data
      }
    },
    // 添加用户
    async addUser (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
        } else {
          console.log('error submit!!')
          return false
        }
      })
      this.$axios({ // post提交表单
        url: 'user/add',
        method: 'post',
        data: this.addsForm,
        params: {roleName: this.roleName}
      })
        .then((res) => {
          console.log(res)
          if (res.data.code === 200) {
            this.addsForm = res.data.data
            this.$message.success(res.data.msg)
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 身份证号码验证
    async checkIdCard (rule, value, callback) {
      // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
      let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
      if (reg.test(value)) {
        await this.go(value.length)
        callback()
      } else {
        callback(new Error('身份证号码不正确'))
      }
    },
    // 实现自动生成生日，性别，年龄
    go (val) {
      let iden = this.addsForm.idCard
      let gender = null
      let myDate = new Date()
      let month = myDate.getMonth() + 1
      let day = myDate.getDate()
      let age = 0
      // 十八位的身份证号年龄计算 和 性别位的取出
      if (val === 18) {
        age = myDate.getFullYear() - iden.substring(6, 10) - 1
        gender = iden.substring(16, 17)
        if (iden.substring(10, 12) < month | iden.substring(10, 12) === month & iden.substring(12, 14) <= day) age++
      }
      // 十五位的身份证号年龄计算 和 性别位的取出
      if (val === 15) {
        age = myDate.getFullYear() - iden.substring(6, 8) - 1901
        gender = iden.substring(13, 14)
        if (iden.substring(8, 10) < month | iden.substring(8, 10) === month & iden.substring(10, 12) <= day) age++
      }
      // 性别验证
      if (gender % 2 === 0) { gender = 'f' } else { gender = 'm' }
      this.addsForm.gender = gender
      this.addsForm.age = age
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
