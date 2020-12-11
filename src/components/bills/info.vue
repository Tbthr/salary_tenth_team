<template>
  <div>
    <my-bread level1='账单信息' level2='查看账单'></my-bread>
    <div v-text="this.title" class="font_title"></div>
    <div class="demo_autocomplete">
      <div v-if="this.level == 1">
        <div class="div-ss">
          <label v-text="this.sname_1[0]" class="search_font"></label>
          <el-date-picker
            v-model="state[0]"
            align="left"
            type="year"
            placeholder="请选择"
            style="width:120px;">
          </el-date-picker>
        </div>
        <div class="div-ss">
          <label v-text="this.sname_1[1]" class="search_font"></label>

          <el-select v-model="state[1]" placeholder="请选择" style="width:90px;" clearable="true">
            <el-option
              v-for="item in this.opmoth"
              :key="item"
              :label="item.label"
              :value="item">
            </el-option>
          </el-select>

        </div>

        <el-button @click.native="find()" class="but">查找</el-button>
        <el-button @click.native="rereas()" class="but">重置</el-button>
      </div>

      <div v-if="this.level == 2">
        <div class="div-ss">
          <label v-text="this.sname_2[0]" class="search_font"></label>
          <el-date-picker
            v-model="state[0]"
            align="left"
            type="year"
            placeholder="请选择"
            style="width:120px;">
          </el-date-picker>
        </div>

        <div class="div-ss">
          <label v-text="this.sname_2[1]" class="search_font"></label>
          <el-select v-model="state[1]" placeholder="请选择" style="width:90px;" clearable="true">
            <el-option
              v-for="item in this.opmoth"
              :key="item"
              :label="item.label"
              clearable="true"
              :value="item">
            </el-option>
          </el-select>
        </div>

        <div class="div-ss">
          <label v-text="this.sname_3[2]" class="search_font"></label>
          <el-select v-model="state[2]" filterable placeholder="请选择" style="width:120px;" clearable="true">
            <el-option
              clearable="true"
              v-for="item in opworkid"
              :key="item"
              :label="item.label"
              :value="item"
              >
            </el-option>
          </el-select>
        </div>

        <div class="div-ss">
          <label v-text="this.sname_2[3]" class="search_font"></label>
          <el-select v-model="state[3]" filterable placeholder="请选择" style="width:120px;" clearable="true">
            <el-option
              clearable="true"
              v-for="item in opname"
              :key="item"
              :label="item.label"
              :value="item"
              >
            </el-option>
          </el-select>
        </div>

        <el-button @click.native="find()" class="but">查找</el-button>
        <el-button @click.native="rereas()" class="but">重置</el-button>
      </div>

      <div v-if="this.level == 3">
        <div class="div-ss">
          <label v-text="this.sname_3[0]" class="search_font"></label>
          <el-date-picker
            v-model="state[0]"
            align="left"
            type="year"
            placeholder="请选择"
            style="width:120px;">
          </el-date-picker>
        </div>

        <div class="div-ss">
          <label v-text="this.sname_3[1]" class="search_font"></label>
          <el-select v-model="state[1]" placeholder="请选择" style="width:90px;" clearable="true">
            <el-option
            width=50px
              v-for="item in this.opmoth"
              :key="item"
              :label="item.label"
              clearable="true"
              :value="item"
              >
            </el-option>
          </el-select>
        </div>

        <div class="div-ss">
          <label v-text="this.sname_3[4]" class="search_font"></label>
          <el-select v-model="state[4]" placeholder="请选择" style="width:100px;" clearable="true">
            <el-option
              v-for="item in this.opcl"
              :key="item"
              :label="item.label"
              clearable="true"
              :value="item">
            </el-option>
          </el-select>
        </div>

        <div class="div-ss">
          <label v-text="this.sname_3[2]" class="search_font"></label>
          <el-select v-model="state[2]" filterable placeholder="请选择" style="width:120px;" clearable="true">
            <el-option
              clearable="true"
              v-for="item in opworkid"
              :key="item"
              :label="item.label"
              :value="item"
              >
            </el-option>
          </el-select>

        </div>

        <div class="div-ss">
          <label v-text="this.sname_3[3]" class="search_font"></label>
          <el-select v-model="state[3]" filterable placeholder="请选择" style="width:120px;" clearable="true">
            <el-option
              clearable="true"
              v-for="item in opname"
              :key="item"
              :label="item.label"
              :value="item"
              >
            </el-option>
          </el-select>
        </div>

        <el-button @click.native="find()" class="but">查找</el-button>
        <el-button @click.native="rereas()" class="but">重置</el-button>
      </div>
    </div>
    <el-card class="card_p" v-if="this.level == 1">
      <div class="tabc">
        <el-table :data="tabledata1.slice(
                (currentPage - 1) * PageSize,
                currentPage * PageSize
                )"
                ref="multipleTable"
                :row-key="getRowKeys"
                tooltip-effect="dark"
                @selection-change="handleSelectionChange">
          <el-table-column label="基本信息" align="center">
            <el-table-column type="selection" ></el-table-column>
            <el-table-column label="#"  type="index"></el-table-column>
            <el-table-column label="年"  prop="b_year"></el-table-column>
            <el-table-column label="月"  prop="b_moth"></el-table-column>
            <el-table-column label="年龄"  prop="b_age"></el-table-column>
            <el-table-column label="部门" prop="b_class"></el-table-column>
            <el-table-column label="职位"  prop="b_work"></el-table-column>
            <el-table-column label="姓名"  prop="b_name"></el-table-column>
            <el-table-column label="职工号"  prop="b_workid"></el-table-column>
          </el-table-column>

          <el-table-column label="基本工资" align="center">
            <el-table-column label="底薪"  prop="b_basem"></el-table-column>
            <el-table-column label="工龄工资"  prop="b_agem"></el-table-column>
          </el-table-column>

          <el-table-column label="工资补贴" align="center">
            <el-table-column label="交通补贴"  prop="b_trasal"></el-table-column>
            <el-table-column label="加班补贴" prop="b_owsal"></el-table-column>
          </el-table-column>

          <el-table-column label="扣款" align="center">
            <el-table-column label="迟到"  prop="b_otime"></el-table-column>
            <el-table-column label="早退"  prop="b_mtime"></el-table-column>
          </el-table-column >

          <el-table-column label="实发工资" align="center" >
            <el-table-column label="总计" align="center" prop="b_realsal"></el-table-column>
          </el-table-column>
        </el-table>
      </div>
      <div class="tabListPage">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="PageSize"
            layout="total, prev, pager, next, jumper"
            :total="tabledata1.length"
          >
          </el-pagination>
      </div>
      <div class="div-b">
          <el-button @click="printgz()">打印工资条</el-button>
      </div>
    </el-card>

    <el-card class="card_p" v-if="this.level == 2">
      <div class="tabc">
        <el-table :data="tabledata2.slice(
                (currentPage - 1) * PageSize,
                currentPage * PageSize
                )"
                ref="multipleTable"
                :row-key="getRowKeys"
                tooltip-effect="dark"
                @selection-change="handleSelectionChange">
          <el-table-column label="基本信息" align="center">
            <el-table-column type="selection" ></el-table-column>
            <el-table-column label="#"  type="index"></el-table-column>
            <el-table-column label="年"  prop="b_year"></el-table-column>
            <el-table-column label="月"  prop="b_moth"></el-table-column>
            <el-table-column label="年龄"  prop="b_age"></el-table-column>
            <el-table-column label="部门"  prop="b_class"></el-table-column>
            <el-table-column label="职位"  prop="b_work"></el-table-column>
            <el-table-column label="姓名"  prop="b_name"></el-table-column>
            <el-table-column label="职工号"  prop="b_workid"></el-table-column>
          </el-table-column>

          <el-table-column label="基本工资" align="center">
            <el-table-column label="底薪"  prop="b_basem"></el-table-column>
            <el-table-column label="工龄工资"  prop="b_agem"></el-table-column>
          </el-table-column>

          <el-table-column label="工资补贴" align="center">
            <el-table-column label="交通补贴"  prop="b_trasal"></el-table-column>
            <el-table-column label="加班补贴"  prop="b_owsal"></el-table-column>
          </el-table-column>

          <el-table-column label="扣款" align="center">
            <el-table-column label="迟到"  prop="b_otime"></el-table-column>
            <el-table-column label="早退"  prop="b_mtime"></el-table-column>
          </el-table-column >

          <el-table-column label="实发工资" align="center" >
            <el-table-column label="总计" align="center" prop="b_realsal"></el-table-column>
          </el-table-column>
        </el-table>
      </div>
      <div class="tabListPage">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="PageSize"
          layout="total, prev, pager, next, jumper"
          :total="tabledata2.length"
        >
        </el-pagination>
      </div>
      <div class="div-b">
        <el-button @click="printgz()">打印工资条</el-button>
      </div>
    </el-card>

    <el-card class="card_p" v-if="this.level == 3">
      <div class="tabc">
        <el-table :data="tabledata3.slice(
                (currentPage - 1) * PageSize,
                currentPage * PageSize
                )"
                ref="multipleTable"
                :row-key="getRowKeys"
                tooltip-effect="dark"
                @selection-change="handleSelectionChange"
                >
        <el-table-column label="基本信息" align="center">
          <el-table-column type="selection"  :reserve-selection="true" ></el-table-column>
          <el-table-column label="#"  type="index"></el-table-column>
          <el-table-column label="年"  prop="b_year"></el-table-column>
          <el-table-column label="月"  prop="b_moth"></el-table-column>
          <el-table-column label="年龄"  prop="b_age"></el-table-column>
          <el-table-column label="部门"  prop="b_class"></el-table-column>
          <el-table-column label="职位"  prop="b_work"></el-table-column>
          <el-table-column label="姓名"  prop="b_name"></el-table-column>
          <el-table-column label="职工号"  prop="b_workid"></el-table-column>
          </el-table-column>

          <el-table-column label="基本工资" align="center">
            <el-table-column label="底薪"  prop="b_basem"></el-table-column>
            <el-table-column label="工龄工资"  prop="b_agem"></el-table-column>
          </el-table-column>

          <el-table-column label="工资补贴" align="center">
            <el-table-column label="交通补贴"  prop="b_trasal"></el-table-column>
            <el-table-column label="加班补贴"  prop="b_owsal"></el-table-column>
          </el-table-column>

          <el-table-column label="扣款" align="center">
            <el-table-column label="迟到"  prop="b_otime"></el-table-column>
            <el-table-column label="早退"  prop="b_mtime"></el-table-column>
          </el-table-column >

          <el-table-column label="实发工资" align="center" >
            <el-table-column label="总计" align="center" prop="b_realsal"></el-table-column>
          </el-table-column>
       </el-table>
      </div>
      <div class="tabListPage" style="width:700px;display:inline;">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="PageSize"
            layout="total, prev, pager, next, jumper"
            :total="tabledata3.length"
            style=""
          >
          </el-pagination>
      </div>
        <div class="div-b">
          <el-button @click="printgz()">打印工资条</el-button>
        </div>
    </el-card>
  </div>
</template>

<script>
import myBread from '../cuscom/myBread.vue'
import axios from 'axios'
export default {
  components: { myBread },
  data () {
    return {
      level: 3,
      title: '',

      tbd: [],

      sname_1: ['筛选年', '筛选月'],
      sname_2: ['筛选年', '筛选月', '查询职工号', '查询姓名'],
      sname_3: ['筛选年', '筛选月', '查询职工号', '查询姓名', '筛选部门'],
      sname: [],
      snum: null,

      opmoth: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      opcl: ['财务部门', '管理部门', '工程部门'],
      opname: [],
      opworkid: [],

      state: ['', '', '', '', ''],
      year: [],
      moth: [],
      workid: [],
      nam: [],
      clas: [],

      multipleSelection: [],
      tabledata1: [
        {
          b_year: '2018',
          b_moth: '2',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'wang',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '2',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'wang',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '2',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'wang',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '2',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'wang',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }
      ],
      tabledata2: [],
      tabledata3: [
        {
          b_year: '2018',
          b_moth: '2',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'g',
          b_workid: '200000',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '2',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'f',
          b_workid: '30000',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '2',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'w',
          b_workid: '500000',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '2',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'li',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '11',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'wang',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '12',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'f',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '9',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'wang',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '2',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'wang',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '7',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'wang',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '5',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'wang',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }, {
          b_year: '2018',
          b_moth: '3',
          b_age: '33',
          b_class: '工程部门',
          b_work: '普通员工',
          b_name: 'wang',
          b_workid: '342344',
          b_basem: '5000',
          b_agem: '1000)',
          b_realsal: '8000',
          b_trasal: '500',
          b_owsal: '1000',
          b_otime: '100',
          b_mtime: '200'
        }
      ],

      choose: '',
      tong: '',
      bumen: '',
      currentPage: 1,
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount: 1,
      // 个数选择器（可修改）
      pageSizes: [5, 10, 15, 20],
      // 默认每页显示的条数（可修改）
      PageSize: 6,

      reltabledata: []

    }
  },

  methods: {
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    getRowKeys (row) {
      return row.id
    },
    printgz () {
      if (this.multipleSelection.length === 0) {
        alert('please select')
      } else {
        let multis = []
        for (var i = 0; i < this.multipleSelection.length; i++) {
          multis.push(this.multipleSelection[i].b_name)
        }
        alert('即将打印：' + multis)
      }
    },

    find () {
      // eslint-disable-next-line no-unused-vars
      var tb = []
      var tab0 = []
      var tab1 = []
      var tab2 = []
      var tab3 = []
      var tab4 = []
      var table = []
      if (this.level === 1) table = this.tabledata1
      else if (this.level === 2) table = this.tabledata2
      else if (this.level === 3) table = this.tabledata3

      if (this.state[0] === undefined || this.state[0] == null) {

      } else {
        var str = this.state[0].toString()
        var st = []
        st = str.split(' ')
        this.state[0] = st[3]
      }

      if (this.state[0]) {
        table.map((item, index) => {
          if (this.state[0] === table[index].b_year) tab0.push(item)
        })
      } else { tab0 = table }

      if (this.state[1]) {
        tab0.map((item, index) => {
          if (this.state[1] === tab0[index].b_moth) tab1.push(item)
          else if (!this.state[0]) tab0 = table
        })
      } else { tab1 = tab0 }

      if (this.state[2]) {
        tab1.map((item, index) => {
          if (this.state[2] === tab1[index].b_workid) tab2.push(item)
        })
      } else { tab2 = tab1 }

      if (this.state[3]) {
        tab2.map((item, index) => {
          if (this.state[3] === tab2[index].b_name) tab3.push(item)
        })
      } else { tab3 = tab2 }

      if (this.state[4]) {
        tab3.map((item, index) => {
          if (this.state[4] === tab3[index].b_class) tab4.push(item)
        })
        table = tab4
      } else { table = tab3 }

      if (this.level === 1) this.tabledata1 = table
      else if (this.level === 2) this.tabledata2 = table
      else if (this.level === 3) this.tabledata3 = table
    },
    handleSelect (item) {
      console.log(item)
    },
    handleEdit (index, row) {
      console.log(index, row)
    },
    handleDelete (index, row) {
      console.log(index, row)
    },
    // 分页
    // 每页显示的条数
    handleSizeChange (val) {
      // 改变每页显示的条数
      this.PageSize = val
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.currentPage = 1
    },
    // 显示第几页
    handleCurrentChange (val) {
      // 改变默认的页数
      this.currentPage = val
    },
    rereas () {
      if (this.level === 1) this.tabledata1 = this.reltabledata
      else if (this.level === 2) this.tabledata2 = this.reltabledata
      else if (this.level === 3) this.tabledata3 = this.reltabledata
    },

    getlevel () {
      if (this.level === 1) {
        this.title = this.title_1
        this.sname = this.sname_1
        this.snum = this.sname.length

        this.reltabledata = this.tabledata1//

        for (var i = 0; i < this.reltabledata.length; i++) {
          this.opworkid[i] = this.reltabledata[i].b_workid
          this.opname[i] = this.reltabledata[i].b_name
        }
      } else if (this.level === 2) {
        this.title = this.title_2
        this.sname = this.sname_2
        this.snum = this.sname.length

        this.reltabledata = this.tabledata2//

        for (var j = 0; j < this.reltabledata.length; j++) {
          this.opworkid[j] = this.reltabledata[j].b_workid
          this.opname[j] = this.reltabledata[j].b_name
        }
      } else {
        this.title = this.title_3
        this.sname = this.sname_3
        this.snum = this.sname.length

        this.reltabledata = this.tabledata3//

        for (var k = 0; k < this.reltabledata.length; k++) {
          this.opworkid[k] = this.reltabledata[k].b_workid
          this.opname[k] = this.reltabledata[k].b_name
        }
      }

      return this.title
    }

  },
  mounted () {
    axios.get('/user/userinfo')
      .then(res => {
        this.tabledata1 = res.data.tabledata
        this.tabledata2 = res.data.tabledata
        this.tabledata3 = res.data.tabledata
        this.reltabledata = res.data.tabledata
      })
      .catch(err => {
        console.log(err)
      })

    this.getlevel()
  }

}
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style >

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both;
  }

  .box-card {
    width: 40%;
    height: 800px;
    margin: 20px auto;
  }

  .div-b{
    margin-left: 44%;
  }
  .div-ss{
    margin-left: 40px;
    display:inline;
  }
  .but{
    z-index:-2;
    margin-left:50px ;
  }
  .inline-input{
    width: 120px;
  }
  .search_font{
    font-size: 14px;
  }
  .card_p{
    margin: 50px auto;
    height: 469px;
    width: 100%;

  }
  .demo_autocomplete{
    margin-top:30px;
    margin-left:10px;
  }
  .font_title{
    font-size: 25px;
    margin-left:200px;
  }
  .border{
    border: solid;
  }
  .tabc{
    height: 370px;
  }
  body .el-table th.gutter{
    display: table-cell!important;
  }

</style>
