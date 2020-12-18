<template>
  <div style="width:100%;height:680px;">
      <!-- 面包屑导航区 -->
    <my-bread level1='员工管理' level2='查看员工'></my-bread>
    <el-card class="el-card">
      <!-- 快速查找 -->
      <div slot="header">
        <span>快速查找</span>
        <el-input placeholder="请输入..." size="small" v-model="search" clearable class="input-with-select"></el-input>
      </div>
      <!-- 员工表格 -->
      <div class="table">
        <el-table  
        ref="usertable" 
         :header-cell-style= "{background:'rgb(245,247,250)'}"
        :row-class-name="rowclass"
        border height="505" 
        style="width: 100%"
        :data="tables.slice((currentPage-1)*pagesize,currentPage*pagesize)" stripe tooltip-effect="dark">
          <el-table-column type="selection" width="40"></el-table-column>
          <el-table-column prop="id" width="115" label="工号" sortable align="center"></el-table-column>
          <el-table-column prop="name" width="90" label="姓名" sortable align="center"></el-table-column>
          <el-table-column prop="gender" label="性别" align="center">
            <template slot-scope="scope">
              {{ scope.row.gender === 'f' ? '女' : '男'}}
            </template>
          </el-table-column>
          <el-table-column prop="position" width="115" label="职位" align="center"></el-table-column>
          <el-table-column prop="age" label="年龄" sortable align="center"></el-table-column>
          <el-table-column prop="idCard" width="180" label="身份证号" align="center"></el-table-column>
          <el-table-column prop="email" width="180" label="邮箱" align="center"></el-table-column>
          <el-table-column prop="phone" width="130" label="电话" align="center"></el-table-column>
          <el-table-column prop="department.departName" width="150" label="部门" align="center"></el-table-column>
          <el-table-column prop="workYear" label="工龄" align="center"></el-table-column>
          <el-table-column prop="userFlag" width="90" label="用户类型" align="center"></el-table-column>
          <el-table-column label="操作" width="150" fixed="right" align="center">
              <template slot-scope="scope">
              <el-button type="primary" size="small" @click="userEdit(scope.$index, scope.row)">修改</el-button>
              <el-button type="danger" size="small" @click="userDelete(scope.$index)">删除</el-button>
              </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 分页 -->
      <div class="page">
        <el-pagination 
        @size-change="handleSizeChange" 
        @current-change="handleCurrentChange" 
        :current-page="currentPage"
        :page-sizes="[6, 12, 18]" 
        :page-size="pagesize" 
        layout="total, prev, pager, next, jumper" 
        :total="userData1.length">
        </el-pagination>
      </div>
    </el-card>
    <!-- 修改员工信息模态框 -->
    <el-dialog title="修改员工信息"  :visible="edituserForm" size="tiny" :modal-append-to-body='false' @close='closeDialog'>
      <el-form ref="editsForm" :rules="rules" :model="editsForm" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="工号" prop="id">
              <el-input  v-model="editsForm.id" max-length="10" disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="editsForm.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="身份证号" prop="idCard">
            <el-input v-model="editsForm.idCard"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
                <el-input v-model="editsForm.age"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="editsForm.gender">
              <el-radio label="男" value="m"></el-radio>
              <el-radio label="女" value="f"></el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="editsForm.email"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="phone">
                <el-input  v-model="editsForm.phone"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="职位" prop="position">
              <el-select v-model="editsForm.position">
                <el-option label="教师" value="教师"></el-option>
                <el-option label="教学秘书" value="教学秘书"></el-option>
                <el-option label="财务处长" value="财务处长"></el-option>
                <el-option label="人事财务总管" value="人事财务总管"></el-option>
                <el-option label="院长" value="院长"></el-option>
                <el-option label="管理" value="管理"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门" prop="departId">
              <el-select v-model="editsForm.departId" >
                  <el-option  v-for="item in Name"  :key="item.id" :label="item.departName" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="工龄">
              <el-input v-model="editsForm.workYear"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户类型" prop="userFlag">
              <el-select v-model="editsForm.userFlag">
                <el-option label="普通用户" value="1"></el-option>
                <el-option label="部门管理员" value="2" disabled></el-option>
                <el-option label="财务管理员" value="3" disabled></el-option>
                <el-option label="系统管理员" value="4" disabled></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item align="center">
            <el-button type="primary" @click="usercEdit()">确定</el-button>
            <el-button @click="edituserForm = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import myBread from '../cuscom/myBread.vue'
export default{
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
    // 验证工龄的规则
    var checkWorkYear = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('工龄不能为空'))
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
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
      tableHeight: null,
      search: '',
      edituserForm: false,
      userData1: [],
      currentIndex: '',
      currentPage: 1, // 初始页
      pagesize: 8, //    每页的数据
      Name: [{
        id: '',
        departName: ''
      }],
      editsForm: {},
      // 修改表单的验证规则对象
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

  computed: {
    // 模糊搜索
    tables () {
      const search = this.search
      if (search) {
        return this.userData1.filter(data => {
          return Object.keys(data).some(key => {
            return String(data[key]).toLowerCase().indexOf(search) > -1
          })
        })
      }
      return this.userData1
    }
  },

  created () {
    this.getUser()
    this.getdepartName()
  },
  methods: {

    rowclass({row, rowIndex}) {
        if (rowIndex === 0) {
          return 'success-row';
        } else if (rowIndex === 2) {
          return 'success-row';
        }else if (rowIndex === 4) {
          return 'success-row';
        }else if (rowIndex === 6) {
          return 'success-row';
        }
        return '';
      
      },
    // 获取部门
    async getdepartName () {
      const { data: res } = await this.$axios.get('user/add/departInfo')
      console.log(res)
      if (res.code === 200) {
        this.Name = res.data
      }
    },
    // 获取用户信息
    async getUser (val) {
      const { data: res } = await this.$axios.get('user/info')
      console.log(res)
      if (res.code === 200) {
        this.userData1 = res.data
        this.$message.success(res.msg)
      } else if (res.code === 403) {
        return this.$message.waring(res.msg)
      } else {
        return this.$message.error(res.msg)
      }
      sessionStorage.setItem('userData1', JSON.stringify(this.userData1))
    },
    // 分页
    handleSizeChange: function (size) {
      this.pagesize = size
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
    },
    // 关闭编辑信息模态框
    closeDialog () {
      this.edituserForm = false
    },

    userEdit (index, row) {
      this.edituserForm = true // 编辑信息模态框显示
      this.editsForm = Object.assign({}, row) // 获得行内数据
    },

    // 点击编辑信息弹框的确定按钮
    usercEdit () {
      this.$axios({
        method: 'POST',
        url: 'user/update',
        data: this.editsForm
      }).then(res => {
        console.log(this.userData1)
        if (res.data.code === 200) {
          this.userData1 = res.data.data
          this.$message.success(res.data.msg)
          this.edituserForm = false
        } else {
          this.$message.error(res.data.msg)
        }
      })
    },

    // 删除用户
    // 后台传id进行删除
    async userDelete (index) {
      // eslint-disable-next-line no-unused-vars
      const confirmResult = await this.$confirm('此操作将永久删除员工是否继续?', '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          this.$axios({
            method: 'POST',
            url: 'user/delete',
            data: {
              id: this.userData1[index].id
            }
          }).then((res) => {
            console.log(res)
            if (res.data.code === 200) {
              this.userData1 = res.data.data
              this.$message.success(res.data.msg)
              sessionStorage.removeItem('userData1')
              sessionStorage.setItem('userData1', JSON.stringify(this.userData1))
            } else {
              this.$message.error(res.data.msg)
            }
          }).catch((res) => {
            console.log(res)
          })
        }).catch(err => err)
      if (confirmResult !== 'confirm') {
      }
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
    // 实现自动生成性别，年龄
    go (val) {
      let iden = this.editsForm.idCard
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
      if (gender % 2 === 0) { gender = '女' } else { gender = '男' }
      this.editsForm.gender = gender
      this.editsForm.age = age
    }
  }
}
</script>
<style>
  .el-card{
    margin:20px auto;
    width: 100% auto;
    height: 650px;
  }
  .table{
    width: 100%;
    height: 508px;
  }
  .input-with-select{
    width: 160px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  
  .el-table .success-row {
    background: #f0f9eb;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }

</style>