<template>
<div >
    <el-button type="primary" size="small" @click="addUser()">添加</el-button>
    <el-table :data="userData" border style="width: 100%">
        <el-table-column prop="userid" label="工号"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="gender" label="性别"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="phone" label="电话"></el-table-column>
        <el-table-column prop="department" label="部门"></el-table-column>
        <el-table-column prop="work_day" label="工龄"></el-table-column>
        <el-table-column label="操作">
            <template slot-scope="scope">
            <el-button type="primary" size="small" @click="userEdit(scope.$index, scope.row)">编辑</el-button>
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
    <!-- 新增员工信息模态框 -->
    <el-dialog title="新增员工信息" :visible="adduserForm" size="tiny" :modal-append-to-body='false' @close='closeDialog'>
    <el-form id="#addsForm" ref="addsForm" :model="addsForm" label-width="80px">
        <el-form-item label="工号" prop="userid">
            <el-input  v-model="addsForm.userid" max-length="10"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
            <el-input v-model="addsForm.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
            <el-radio-group v-model="addsForm.gender">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
            <el-input v-model="addsForm.email"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
            <el-input v-model="addsForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="department">
            <el-input v-model="addsForm.department"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input v-model="addsForm.password"></el-input>
        </el-form-item>
        <el-form-item label="工龄" prop="work_day">
            <el-input v-model="addsForm.work_day"></el-input>
        </el-form-item>
        <el-form-item label="用户类型" prop="user_flag">
            <el-select v-model="addsForm.user_flag" placeholder="请选择用户类型">
            <el-option label="职工" value="user"></el-option>
            <el-option label="部门管理员" value="depart"></el-option>
            <el-option label="财务管理员" value="financial"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="userAdd()">确定</el-button>
            <el-button @click="adduserForm = false;canceladdT('formt')">取消</el-button>
        </el-form-item>
    </el-form>
    </el-dialog>

    <!-- 编辑员工信息模态框 -->
    <el-dialog title="编辑员工信息" :visible="edituserForm" size="tiny" :modal-append-to-body='false' @close='closeDialog'>
    <el-form ref="editsForm" :model="editsForm" label-width="80px">
        <el-form-item label="工号">
        <el-input  v-model="editsForm.userid" max-length="10" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
        <el-input v-model="editsForm.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
        <el-input v-model="editsForm.gender"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
        <el-input v-model="editsForm.email"></el-input>
        </el-form-item>
        <el-form-item label="电话">
        <el-input v-model="editsForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="部门">
        <el-input v-model="editsForm.department"></el-input>
        </el-form-item>

        <!-- <el-form-item label="部门">
        <el-select v-model="editsForm.department" value-key="id">
            <el-option v-for="item in termArry"  :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
        </el-form-item> -->
        <el-form-item label="密码">
            <el-input v-model="editsForm.password" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="用户类型">
            <el-select v-model="editsForm.user_flag" placeholder="请选择用户类型">
            <el-option label="职工" value="user"></el-option>
            <el-option label="部门管理员" value="depart"></el-option>
            <el-option label="财务管理员" value="financial"></el-option>
            </el-select>
        </el-form-item>
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
      userData: [{
        userid: '1',
        name: 'q',
        gender: '男',
        email: '11',
        phone: '180',
        department: '1',
        password: '11',
        work_day: '1',
        user_flag: '职工'
      }, {
        userid: '2',
        name: 'w',
        gender: '女',
        email: '11',
        phone: '180',
        department: '1',
        password: '11',
        work_day: '1',
        user_flag: '职工'
      }, {
        userid: '1',
        name: 'q',
        gender: '男',
        email: '11',
        phone: '180',
        department: '1',
        password: '11',
        work_day: '1',
        user_flag: '财务管理员'
      }

      ], // 所有学生信息数组置空
      adduserForm: false, // 新增学生信息模态框
      page: {
        pageSize: 10, // 每页条数,  默认10条
        totalRecords: 0, // 总条数
        totalPages: 0, // 总页数
        pageNum: 0
      },
      addsForm: {
        userid: '',
        name: '',
        gender: '',
        email: '',
        phone: '',
        department: '',
        password: '',
        work_day: '',
        user_flag: ''
      },
      editsForm: {
        userid: '',
        name: '',
        gender: '',
        email: '',
        phone: '',
        department: '',
        password: '',
        work_day: '',
        user_flag: '0'
      }
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      this.studentData = []
      let {pageNum, pageSize} = this.page // es6写法
      // pageNum:页数从0开始
      // pageSize:每页显示10条

      // eslint-disable-next-line no-undef
      this.$axios.get(Main.getStudent(pageNum, pageSize)).then(res => {
        let {errCode, errMsg} = res.data
        this.page.totalRecords = res.data.totalRecords // 总条数
        if (errCode === 0) {
          const studentArray = res.data.dataList
          this.studentData = studentArray
        } else {
          alert(errMsg)
        }
      }, response => {
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

    // 点击模态框关闭按钮关闭模态框
    closeDialog () {
      this.adduserForm = false
      this.edituserForm = false
    },

    // 点击新增按钮
    addUser () {
      this.adduserForm = true // 原来隐藏的新增信息模态框显示
    },
    // 点击新增职工信息模态框的确定按钮
    userAdd () {
      let userList = this.addsForm
      // eslint-disable-next-line camelcase
      let {userid, name, gender, email, department, password, work_day, user_flag} = userList
      // 判断数据是否为空
      // eslint-disable-next-line camelcase
      if (userid === '' || name === '' || gender === '' || email === '' || department === '' || password === '' || work_day === '' || user_flag === '') {
        this.$message.error('新增内容每一项都不准为空')
      } else {
        // eslint-disable-next-line no-undef
        this.$axios.post(Main.adduserForm(), this.addsForm).then(res => {
          let {errCode, errMsg} = res.data
          if (!errCode === 1) {
            this.$set(this.addsForm, {})
            this.init() // 重新渲染数据列表
            this.adduserForm = false
          } else {
            this.$message.error(errMsg) // 弹出后台返回错误
          }
        }, response => {
        })
      }
    },

    // 点击编辑按钮
    userEdit (index, row) {
      this.edituserForm = true // 编辑信息模态框显示
      this.editsForm = Object.assign({}, row) // 获得所有数据显示在编辑信息模态框里面
    },
    // 点击编辑信息弹框的确定按钮
    usercEdit () {
      let usereList = this.editsForm
      console.log(usereList)
      // eslint-disable-next-line camelcase
      let {userid, name, gender, email, department, password, work_day, user_flag} = usereList
      // eslint-disable-next-line camelcase
      if (userid === '' || name === '' || gender === '' || email === '' || department === '' || password === '' || work_day === '' || user_flag === '') {
        this.$message.error('修改内容除了不可编辑的每一项都不准为空')
      } else {
        // eslint-disable-next-line no-undef
        this.$http.put(Main.changeUser(), this.editsForm).then(res => {
          let {errCode, errMsg} = res.data
          if (!errCode === 1) {
            this.init()
            this.edituserForm = false
          } else {
            this.$message.error(errMsg)
          }
        }, response => {
        })
      }
    },
    // 删除当前对应行数据
    // 后台传 id进行删除
    userDelete (user) {
      this.$confirm('此操作将永久删除员工 ' + user.name + ', 是否继续?', '提示', { type: 'warning' })
        .then(() => { // 向服务端请求删除
          // eslint-disable-next-line no-undef
          this.$http.delete(Main.deleteUser(user.userid, 0)).then((response) => {
            this.$message.success('成功删除了员工' + user.name + '!'); this.init()
          })
            .catch((response) => {
              this.$message.error('删除失败!')
            })
        }).catch(() => {
          this.$message.info('已取消操作!')
        })
    }

  }
}
</script>
