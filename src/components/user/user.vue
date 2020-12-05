<template>
<div>
    <el-row gutter="2">
        <el-col span="4">
            <el-input placeholder="请输入工号" v-model="userData.id" clearable class="input-with-select"></el-input>
        </el-col>
        <el-col span="4">
            <el-input placeholder="请输入姓名" v-model="userData.username" clearable class="input-with-select"></el-input>
        </el-col>
        <el-col span="4">
            <el-input placeholder="请输入类型" v-model="userData.user_flag" clearable class="input-with-select"></el-input>
        </el-col>
        <el-button @click="searchByKeyword()" icon="el-icon-search" type="primary">查找</el-button>
    </el-row>

    <el-table :data="userData" border style="width: 100%">
        <el-table-column prop="id" label="工号"></el-table-column>
        <el-table-column prop="username" label="姓名"></el-table-column>
        <el-table-column prop="gender" label="性别"></el-table-column>
        <el-table-column prop="position" label="职位"></el-table-column>
        <el-table-column prop="age" label="年龄"></el-table-column>
        <el-table-column prop="id_card" label="身份证号"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="phone" label="电话"></el-table-column>
        <el-table-column prop="department.departName" label="部门"></el-table-column>
        <el-table-column prop="work_year" label="工龄"></el-table-column>
        <el-table-column prop="user_flag" label="用户类型"></el-table-column>
        <el-table-column label="操作" width="200">
            <template slot-scope="scope">
            <el-button type="primary" size="small" @click="userEdit(scope.$index, scope.row)">修改</el-button>
            <el-button type="danger" size="small" @click="userDelete(scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <div class="block" style="height:70px;">
    <el-pagination
        @size-change="sizeChange"
        @current-change="currentChange"
        :page-sizes="[10,20,30,40]"
        :page-size="page.pageSize"
        layout="total, sizes, prev, pager, next"
        :total="page.totalRecords">
    </el-pagination>
    </div>

    <!-- 修改员工信息模态框 -->
    <el-dialog title="编辑员工信息" :visible="edituserForm" size="tiny" :modal-append-to-body='false' @close='closeDialog'>
    <el-form ref="editsForm" :model="editsForm" label-width="80px">
        <el-row gutter="8">
            <el-col span="12">
                <el-form-item label="工号" prop="id">
                    <el-input  v-model="editsForm.id" max-length="10" disabled="disabled"></el-input>
                </el-form-item>
            </el-col>
            <el-col span="12">
                <el-form-item label="姓名">
                    <el-input v-model="editsForm.username"></el-input>
                </el-form-item>
            </el-col>
            <el-col span="12">
                <el-form-item label="年龄">
                    <el-input v-model="editsForm.age"></el-input>
                </el-form-item>
            </el-col>
        </el-row>

        <el-row gutter="10">
            <el-col span="12">
                <el-form-item label="邮箱">
                    <el-input v-model="editsForm.email"></el-input>
                </el-form-item>
            </el-col>
            <el-col span="12">
                <el-form-item label="电话">
                    <el-input v-model="editsForm.phone"></el-input>
                </el-form-item>
            </el-col>
            <el-col span="12">
                <el-form-item label="身份证号">
                    <el-input v-model="editsForm.id_card"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row gutter="10">
            <el-col span="8">
                <el-form-item label="性别">
                    <el-radio-group v-model="editsForm.gender">
                    <el-radio label="男" value="m"></el-radio>
                    <el-radio label="女" value="f"></el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-col>
            <el-col span="9">
                <el-form-item label="职位" prop="position">
                    <el-select v-model="editsForm.position" placeholder="请选择职位">
                    <el-option label="教授" value="教授"></el-option>
                    <el-option label="副教授" value="副教授"></el-option>
                    <el-option label="院长" value="院长"></el-option>
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col span="7">
                <el-form-item label="工龄">
                    <el-input v-model="editsForm.work_day"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-form-item label="密码">
            <el-input v-model="editsForm.password" disabled="disabled"></el-input>
        </el-form-item>
        <el-row gutter="10">
            <el-col span="8">
                <el-form-item label="部门">
                    <el-select v-model="editsForm.department" value-key="id">
                        <el-option v-for="departName in department"  :key="departName" :label="department.departName" :value="department.id"></el-option>
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col span="8">
                <el-form-item label="用户类型">
                    <el-select v-model="editsForm.user_flag" placeholder="请选择用户类型">
                    <el-option label="职工" value="1"></el-option>
                    <el-option label="部门管理员" value="2"></el-option>
                    <el-option label="财务管理员" value="3"></el-option>
                    </el-select>
                </el-form-item>
            </el-col>
        </el-row>

        <el-form-item>
            <el-button type="primary" @click="usercEdit()">确定</el-button>
            <el-button @click="edituserForm = false">取消</el-button>
        </el-form-item>
    </el-form>
    </el-dialog>
</div>
</template>

<script>
export default{
  data () {
    return {
      userData: [],
      rules: {
        email: [
          {
            required: true,
            validator: (rule, value, callback) => {
              if (!value) {
                return callback(new Error('请输入邮箱号'))
              }
              const reg = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/ // 邮箱正则
              if (!reg.test(value)) {
                return callback(new Error('邮箱不合法'))
              }
              callback()
            },
            trigger: 'blur'
          }
        ],
        phone: [
          {
            required: true,
            validator: (rule, value, callback) => {
              if (!value) {
                return callback(new Error('请输入手机号'))
              }
              const reg = /^1[3456789][0-9]{9}$/
              if (!reg.test(value)) {
                return callback(new Error('手机号不合法'))
              }
              callback()
            },
            trigger: 'blur'
          }
        ]
      },
      page: {
        pageSize: 10, // 每页条数,  默认10条
        totalRecords: 0, // 总条数
        totalPages: 0, // 总页数
        pageNum: 0
      },
      editsForm: {
        id: '',
        id_card: '',
        age: '',
        username: '',
        department: {
          'departName': '',
          'id': ''
        },
        gender: '',
        email: '',
        position: '',
        phone: '',
        psd: '',
        work_year: '',
        user_flag: 0
      }
    }
  },
  created () {
    this.init()
    this.getUser()
  },
  methods: {
    async getUser () {
      const { data: res } = await this.$axios.get('user/info')
      console.log(res)
      if (res.code === 200) {
        this.userData = res.data
        this.$message.success(res.msg)
      } else if (res.code === 403) {
        return this.$message.waring(res.msg)
      } else {
        return this.$message.error(res.msg)
      }
    },
    init () {
      this.userData = []
      // eslint-disable-next-line no-unused-vars
      let {pageNum, pageSize} = this.page
      // eslint-disable-next-line no-undef
      this.$axios({}).then(res => {
        let {errCode, errMsg} = res.data
        this.page.totalRecords = res.data.totalRecords
        if (errCode === 0) {
          this.userData = res.data.dataList
        } else {
          alert(errMsg)
        }
      }, res => {
      })
    },

    // 每页显示多少条数据
    sizeChange (val) {
      this.page.pageSize = val
      this.init()
    },

    // 翻页
    currentChange (val) {
      this.page.pageNum = val - 1
      console.log(this.page.pageNum)
      this.init()
    },

    closeDialog () {
      this.edituserForm = false
    },

    userEdit (index, row) {
      this.edituserForm = true // 编辑信息模态框显示
      this.editsForm = Object.assign({}, row) // 获得行内数据
    },

    // 点击编辑信息弹框的确定按钮
    usercEdit () {
      let usereList = this.editsForm
      console.log(usereList)
      // eslint-disable-next-line camelcase
      let {userid, username, gender, email, department, psd, work_year, user_flag} = usereList
      // eslint-disable-next-line camelcase
      if (userid === '' || username === '' || gender === '' || email === '' || department === '' || psd === '' || work_year === '' || user_flag === '') {
        this.$message.error('修改内容除了不可编辑的每一项都不准为空')
      } else {
        this.$axios({
          method: 'POST',
          url: 'http://salary.free.idcfengye.com/salary/user/update',
          data: this.editsForm
        }).then(res => {
          let {errCode, errMsg} = res.data
          if (!errCode === 1) {
            this.init()
            this.edituserForm = false
          } else {
            this.$message.error(errMsg)
          }
        })
      }
    },

    // 删除当前对应行数据
    // 后台传 id进行删除
    userDelete (user) {
      this.$confirm('此操作将永久删除员工是否继续?', '提示', { type: 'warning' })
        .then(() => {
          this.$axios({
            method: 'POST',
            url: 'http://salary.free.idcfengye.com/salary/user/delete',
            data: {
              id: this.userData.id
            }
          }).then((res) => {
            this.$message.success(res.data.msg)
            this.init()
          })
            .catch((res) => {
              console.log(res)
            })
        }).catch(() => {
          this.$message.info('已取消操作!')
        })
    },
    searchByKeyword () {
      if (this.keyword.length < 1) {
        // this.userData
        return 0
      }
      this.$axios({
        url: '',
        data: {},
        method: 'GET'
      }).then(
        res => {
          console.log(res)
          if (res.data.length < 1) {
            this.$message('查找失败~')
            return 0
          } else {
            this.$message('查找成功~')
          }
          // 搜索时不分页
          this.total = 1
          this.userData = res.data
        },
        error => {
          console.log('error :', error)
        }
      )
    }

  }
}
</script>
<style>
  .el-row {
    margin-bottom: 20px;
  }
  .el-col {
    border-radius: 4px;
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
</style>
