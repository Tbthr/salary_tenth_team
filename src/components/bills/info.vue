/* eslint-disable standard/object-curly-even-spacing */

<template>
 <!-- cd C:\Users\Administrator\Desktop\mayun\test\3\salary
 npm run dev

-->
  <div>
    <!-- 标题 -->
    <my-bread level1='账单信息' level2='查看账单'></my-bread>
    <!-- 筛选框 -->
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

          <el-select v-model="state[1]" placeholder="请选择" style="width:90px;" clearable>
            <el-option
              v-for="item in this.opmoth"
              :key="item"
              :label="item.label"
              :value="item">
            </el-option>
          </el-select>

        </div>

        <el-button type="primary" @click.native="find()" class="but">查找</el-button>
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
          <el-select v-model="state[1]" placeholder="请选择" style="width:90px;" clearable>
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
          <el-select  v-model="state[2]" filterable placeholder="请选择" style="width:120px;" clearable>
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
          <el-select v-model="state[3]" filterable placeholder="请选择" style="width:120px;vertical-align: bottom !important;" clearable>
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

        <el-button type="primary" @click.native="find()" class="but">查找</el-button>
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
          <el-select v-model="state[1]" placeholder="请选择" style="width:90px;" clearable>
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
          <el-select v-model="state[4]" placeholder="请选择" style="width:100px;vertical-align: bottom !important;" clearable>
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
          <el-select type="number" v-model="state[2]" filterable placeholder="请选择" style="width:120px !important;" clearable>
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
          <el-select v-model="state[3]" filterable placeholder="请选择" style="width:120px;vertical-align: bottom !important;" clearable  >
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

        <el-button type="primary" @click.native="find()" class="but">查找</el-button>
        <el-button @click.native="rereas()" class="but">重置</el-button>
      </div>
    </div>

    <!-- 表格1 -->
    <el-card class="card_p" v-if="this.level == 1">
      <div class="tabc">
        <el-table :data="tabledata1.slice(
                (currentPage - 1) * PageSize,
                currentPage * PageSize
                )"
                ref="multipleTable"
                :row-key="getRowKeys"
                tooltip-effect="dark"
                :row-class-name="tableRowClassName"
                @selection-change="handleSelectionChange"
                @sort-change="changeSort">

          <el-table-column fixed type="selection" reserve-selection></el-table-column>
          <el-table-column fixed label="年"  sortable align="center" prop="b_year" width="70px"></el-table-column>
          <el-table-column fixed label="月"  sortable  align="center" prop="b_moth" width="60px"></el-table-column>
          <el-table-column label="职工号" sortable align="center" prop="b_workid" width="110px"></el-table-column>
          <el-table-column label="姓名"  align="center" prop="b_name" width="90px"></el-table-column>
          <el-table-column label="部门"  align="center" prop="b_class" width="130px" show-overflow-tooltip></el-table-column>
          <el-table-column label="职位"  align="center" prop="b_position" width="90px" show-overflow-tooltip></el-table-column>

          <el-table-column label="基本工资" align="center">
            <el-table-column label="基本工资"  prop="b_basem"></el-table-column>
            <el-table-column label="工龄工资"  prop="b_agem"></el-table-column>
            <el-table-column label="职位工资"  prop="b_cm"></el-table-column>
          </el-table-column>

          <el-table-column label="工资补贴" align="center">
            <el-table-column label="餐饮补贴"  prop="b_eat"></el-table-column>
            <el-table-column label="交通补贴"  prop="b_trasal"></el-table-column>
            <el-table-column label="出差补贴"  prop="b_out"></el-table-column>
            <el-table-column label="加班补贴"  prop="b_owsal"></el-table-column>
            <el-table-column label="其他补贴"  prop="b_esal"></el-table-column>
          </el-table-column>

          <el-table-column label="扣款" align="center">
            <el-table-column label="五险一金"  prop="b_x"></el-table-column>
            <el-table-column label="个人税"  prop="b_t"></el-table-column>
            <el-table-column label="缺勤扣款"  prop="b_on"></el-table-column>
            <el-table-column label="其他扣款"  prop="b_e"></el-table-column>
          </el-table-column >

          <el-table-column label="应发工资" align="center" prop="b_realsal"></el-table-column>
        </el-table>
      </div>
      <!-- 分页 -->
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
      <!-- 打印 -->
      <div class="div-b">
          <download-excel
             :data   = "json_data"
             :fields = "json_fields"
             type    = "xls"
             worksheet = "My Worksheet"
             name    = "工资条.xls"
            >
            <el-button type="success" @click="printg()"> 打印工资条  </el-button>
            <el-button @click="selectall()">选择全部数据</el-button>
          </download-excel>
        </div>
    </el-card>
<!-- 表格1 -->
    <el-card class="card_p" v-if="this.level == 2">
      <div class="tabc">
        <el-table :data="tabledata2.slice(
                (currentPage - 1) * PageSize,
                currentPage * PageSize
                )"
                ref="multipleTable"
                :row-key="getRowKeys"
                :row-class-name="tableRowClassName"
                tooltip-effect="dark"
                @selection-change="handleSelectionChange"
                @sort-change="changeSort">
          <el-table-column fixed type="selection" reserve-selection></el-table-column>
          <el-table-column fixed label="年"  sortable align="center" prop="b_year" width="70px"></el-table-column>
          <el-table-column fixed label="月"  sortable align="center" prop="b_moth" width="60px"></el-table-column>
          <el-table-column fixed label="职工号" sortable align="center" prop="b_workid" width="110px"></el-table-column>
          <el-table-column fixed label="姓名"  align="center" prop="b_name" width="90px"></el-table-column>
          <el-table-column label="部门"  align="center" prop="b_class" width="130px" show-overflow-tooltip></el-table-column>
          <el-table-column label="职位"  align="center" prop="b_position" width="90px" show-overflow-tooltip></el-table-column>

          <el-table-column label="基本工资" align="center">
            <el-table-column label="基本工资"  prop="b_basem"></el-table-column>
            <el-table-column label="工龄工资"  prop="b_agem"></el-table-column>
            <el-table-column label="职位工资"  prop="b_cm"></el-table-column>
          </el-table-column>

          <el-table-column label="工资补贴" align="center">
            <el-table-column label="餐饮补贴"  prop="b_eat"></el-table-column>
            <el-table-column label="交通补贴"  prop="b_trasal"></el-table-column>
            <el-table-column label="出差补贴"  prop="b_out"></el-table-column>
            <el-table-column label="加班补贴"  prop="b_owsal"></el-table-column>
            <el-table-column label="其他补贴"  prop="b_esal"></el-table-column>
          </el-table-column>

          <el-table-column label="扣款" align="center">
            <el-table-column label="五险一金"  prop="b_x"></el-table-column>
            <el-table-column label="个人税"  prop="b_t"></el-table-column>
            <el-table-column label="缺勤扣款"  prop="b_on"></el-table-column>
            <el-table-column label="其他扣款"  prop="b_e"></el-table-column>
          </el-table-column >

          <el-table-column label="应发工资" align="center" prop="b_realsal"></el-table-column>
        </el-table>
      </div>
      <!-- 分页 -->
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
      <!-- 打印 -->
      <div class="div-b">
          <download-excel
             :data   = "json_data"
             :fields = "json_fields"
             type    = "xls"
             worksheet = "My Worksheet"
             name    = "工资条.xls"
            >
            <el-button type="success" @click="printg()"> 打印工资条  </el-button>
            <el-button @click="selectall()">选择全部数据</el-button>
          </download-excel>
        </div>
    </el-card>
<!-- 表格1 -->
    <el-card class="card_p" v-if="this.level == 3">
      <div class="tabc">
        <el-table :data="tabledata3.slice(
                (currentPage - 1) * PageSize,
                currentPage * PageSize
                )"
                ref="multipleTable"
                :row-key="getRowKeys"
                :row-class-name="tableRowClassName"
                :header-cell-style="rowclass"
                tooltip-effect="dark"
                @selection-change="handleSelectionChange"
                style="width:100%;"
                @sort-change="changeSort">
          <el-table-column fixed type="selection" reserve-selection></el-table-column>
          <el-table-column fixed label="年"  sortable prop="b_year" align="center" width="70px" style="background-color:red;"></el-table-column>
          <el-table-column fixed label="月"  sortable prop="b_moth" align="center" width="60px"></el-table-column>
          <el-table-column fixed label="职工号" sortable align="center" prop="b_workid" width="110px"></el-table-column>
          <el-table-column fixed label="姓名"  align="center" prop="b_name" width="90px"></el-table-column>
          <el-table-column fixed label="部门"  align="center" prop="b_class" width="130px" show-overflow-tooltip></el-table-column>
          <el-table-column label="职位"  align="center" prop="b_position" width="90px" show-overflow-tooltip></el-table-column>

          <el-table-column label="基本工资" align="center">
            <el-table-column label="基本工资"  prop="b_basem"></el-table-column>
            <el-table-column label="工龄工资"  prop="b_agem"></el-table-column>
            <el-table-column label="职位工资"  prop="b_cm"></el-table-column>
          </el-table-column>

          <el-table-column label="工资补贴" align="center">
            <el-table-column label="餐饮补贴"  prop="b_eat"></el-table-column>
            <el-table-column label="交通补贴"  prop="b_trasal"></el-table-column>
            <el-table-column label="出差补贴"  prop="b_out"></el-table-column>
            <el-table-column label="加班补贴"  prop="b_owsal"></el-table-column>
            <el-table-column label="其他补贴"  prop="b_esal"></el-table-column>
          </el-table-column>

          <el-table-column label="扣款" align="center">
            <el-table-column label="五险一金"  prop="b_x"></el-table-column>
            <el-table-column label="个人税"  prop="b_t"></el-table-column>
            <el-table-column label="缺勤扣款"  prop="b_on"></el-table-column>
            <el-table-column label="其他扣款"  prop="b_e"></el-table-column>
          </el-table-column >

          <el-table-column label="应发工资" align="center" prop="b_realsal"></el-table-column>

       </el-table>
      </div>
      <!-- 分页 -->
      <div class="tabListPage" >
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="PageSize"
            layout="total, prev, pager, next, jumper"
            :total="tabledata3.length"
          >
          </el-pagination>
      </div>
      <!-- 打印 -->
        <div class="div-b">
          <download-excel
             :data   = "json_data"
             :fields = "json_fields"
             type    = "xls"
             worksheet = "My Worksheet"
             name    = "工资条.xls"
            >
            <el-button type="success" @click="printg()"> 打印工资条  </el-button>
            <el-button @click="selectall()">选择全部数据</el-button>
          </download-excel>
        </div>
    </el-card>

  </div>
</template>

<script>
import Vue from 'vue'
import JsonExcel from 'vue-json-excel'
import axios from 'axios'
import qs from 'qs'
import myBread from '../cuscom/myBread.vue'
Vue.component('downloadExcel', JsonExcel)
export default {
  components: { myBread },
  data () {
    return {
      level: 2,

      title_1: '查看账单',
      title_2: '部门账单',
      title_3: '数据汇总',
      title: '',

      tbd: [],

      sname_1: ['筛选年', '筛选月'],
      sname_2: ['筛选年', '筛选月', '查询职工号', '查询姓名'],
      sname_3: ['筛选年', '筛选月', '查询职工号', '查询姓名', '筛选部门'],
      sname: [],
      snum: null,

      opmoth: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      opcl: [],
      opname: [],
      opworkid: [],

      state: ['', '', '', '', ''],
      year: [],
      moth: [],
      workid: [],
      nam: [],
      clas: [],

      multipleSelection: [],
      checkds: [],

      tabledata1: [],
      tabledata2: [],
      tabledata3: [],

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

      reltabledata: [],
      json_fields: {
        '年': 'b_year',
        '月': 'b_moth',
        '职工号': 'b_workid',
        '姓名': 'b_name',
        '部门': 'b_class',
        '职位': 'b_position',
        '基本工资': 'b_basem',
        '工龄工资': 'b_agem',
        '职位工资': 'b_cm',
        '饮食补贴': 'b_eat',
        '交通补贴': 'b_trasal',
        '出差补贴': 'b_out',
        '加班补贴': 'b_owsal',
        '其他补贴': 'b_esal',
        '五险一金': 'b_x',
        '个人税': 'b_t',
        '缺勤扣款': 'b_on',
        '其他扣款': 'b_e',
        '应发工资': 'b_realsal'
      },

      json_data: []

    }
  },

  methods: {

    changeSort (column, prop, order) { // 对整个表格排序
      var table = []
      if (this.level == 1) table = this.tabledata1
      if (this.level == 2) table = this.tabledata2
      if (this.level == 3) table = this.tabledata3
      var n = table.length
      console.log(column.order)
      if (column.prop == 'b_year') {
        if (column.order == 'ascending') { // 升序排序
          var temp = null
          for (var i = 0; i < n; i++) {
            for (var j = 0; j < n - 1 - i; j++) {
              if (table[j + 1].b_year < table[j].b_year) {
                temp = table[j + 1]
                table[j + 1] = table[j]
                table[j] = temp
              }
            }
          }
        } else if (column.order == 'descending') { // 降序排序
          var temp = null
          for (var i = 0; i < n; i++) {
            for (var j = 0; j < n - 1 - i; j++) {
              if (table[j + 1].b_year > table[j].b_year) {
                temp = table[j + 1]
                table[j + 1] = table[j]
                table[j] = temp
              }
            }
          }
        }
      } else if (column.prop == 'b_moth') {
        if (column.order == 'ascending') { // 升序排序
          var temp = null
          var tp = null
          for (var i = 0; i < n; i++) {
            for (var j = 0; j < n - 1 - i; j++) {
              if (table[j + 1].b_moth < table[j].b_moth) {
                temp = table[j + 1]
                table[j + 1] = table[j]
                table[j] = temp
              }
            }
          }
        } else if (column.order == 'descending') { // 降序排序
          var temp = null
          for (var i = 0; i < n; i++) {
            for (var j = 0; j < n - 1 - i; j++) {
              if (table[j + 1].b_moth > table[j].b_moth) {
                temp = table[j + 1]
                table[j + 1] = table[j]
                table[j] = temp
              }
            }
          }
        }
      } else if (column.prop == 'b_workid') {
        if (column.order == 'ascending') { // 升序排序
          var temp = null
          var tp = null
          for (var i = 0; i < n; i++) {
            for (var j = 0; j < n - 1 - i; j++) {
              if (table[j + 1].b_workid < table[j].b_workid) {
                temp = table[j + 1]
                table[j + 1] = table[j]
                table[j] = temp
              }
            }
          }
        } else if (column.order == 'descending') { // 降序排序
          var temp = null
          for (var i = 0; i < n; i++) {
            for (var j = 0; j < n - 1 - i; j++) {
              if (table[j + 1].b_workid > table[j].b_workid) {
                temp = table[j + 1]
                table[j + 1] = table[j]
                table[j] = temp
              }
            }
          }
        }
      }
      if (this.level == 1) this.tabledata1 = table
      if (this.level == 2) this.tabledata2 = table
      if (this.level == 3) this.tabledata3 = table
    },
    rowclass ({row, rowIndex}) {
      if (rowIndex === 3) return 'background:red;color:#555'
    },
    clearJ () {
      this.json_data = []
    },
    printg () { // 打印工资条
      if (this.multipleSelection.length == 0) {
        alert('please select')
      } else {
        let multis = []

        for (var i = 0; i < this.multipleSelection.length; i++) {
          multis.push(this.multipleSelection[i].b_year + '-' + this.multipleSelection[i].b_moth)
          var jdata = []
          jdata['b_year'] = this.multipleSelection[i].b_year
          jdata['b_moth'] = this.multipleSelection[i].b_moth
          jdata['b_workid'] = this.multipleSelection[i].b_workid
          jdata['b_name'] = this.multipleSelection[i].b_name
          jdata['b_class'] = this.multipleSelection[i].b_class
          jdata['b_basem'] = this.multipleSelection[i].b_basem
          jdata['b_position'] = this.multipleSelection[i].b_position
          jdata['b_agem'] = this.multipleSelection[i].b_agem
          jdata['b_cm'] = this.multipleSelection[i].b_cm
          jdata['b_eat'] = this.multipleSelection[i].b_eat
          jdata['b_trasal'] = this.multipleSelection[i].b_trasal
          jdata['b_out'] = this.multipleSelection[i].b_out
          jdata['b_owsal'] = this.multipleSelection[i].b_owsal
          jdata['b_esal'] = this.multipleSelection[i].b_esal
          jdata['b_x'] = this.multipleSelection[i].b_x
          jdata['b_t'] = this.multipleSelection[i].b_t
          jdata['b_on'] = this.multipleSelection[i].b_on
          jdata['b_e'] = this.multipleSelection[i].b_e
          jdata['b_realsal'] = this.multipleSelection[i].b_realsal
          this.json_data.push(jdata)
        }
        this.clearSelect()
        this.multipleSelection = []
      }
    },

    tableRowClassName ({row, rowIndex}) {
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      } else if (rowIndex === 5) {
        return 'success-row'
      }
      return ''
    },
    selectall () { // 选择全部数据
      var table
      if (this.level == 1) table = this.tabledata1
      else if (this.level == 2) table = this.tabledata2
      else if (this.level == 3) table = this.tabledata3
      for (var i = 0; i < table.length; i++) {
        this.toggleSelection([table[i]])
      }
    },
    toggleSelection (rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      }
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    getRowKeys (row) {
      return row.b_workid + row.b_year + row.b_moth
    },

    clearSelect () {
      this.$refs.multipleTable.clearSelection()
    },

    find () { // 查找
      var tb = []
      var tab0 = []
      var tab1 = []
      var tab2 = []
      var tab3 = []
      var tab4 = []
      var table = this.reltabledata
      this.setnowpage()

      if (this.state[0] == undefined || this.state[0] == null) {

      } else {
        var str = this.state[0].toString()
        var st = []
        st = str.split(' ')
        this.state[0] = st[3]
      }

      if (this.state[0]) {
        table.map((item, index) => {
          if (this.state[0] == table[index].b_year) tab0.push(item)
        })
      } else { tab0 = table }

      if (this.state[1]) {
        tab0.map((item, index) => {
          for (var i; i < index; i++) {
            if (tab0[index].b_moth == '01') tab0[index].b_moth = 1
            if (tab0[index].b_moth == '02') tab0[index].b_moth = 2
            if (tab0[index].b_moth == '03') tab0[index].b_moth = 3
            if (tab0[index].b_moth == '04') tab0[index].b_moth = 4
            if (tab0[index].b_moth == '05') tab0[index].b_moth = 5
            if (tab0[index].b_moth == '06') tab0[index].b_moth = 6
            if (tab0[index].b_moth == '07') tab0[index].b_moth = 7
            if (tab0[index].b_moth == '08') tab0[index].b_moth = 8
            if (tab0[index].b_moth == '09') tab0[index].b_moth = 9
            if (tab0[index].b_moth == '10') tab0[index].b_moth = 10
            if (tab0[index].b_moth == '11') tab0[index].b_moth = 11
            if (tab0[index].b_moth == '12') tab0[index].b_moth = 12
          }
          if (this.state[1] == tab0[index].b_moth) tab1.push(item)
          else if (!this.state[0]) tab0 = table
        })
      } else { tab1 = tab0 }

      if (this.state[2]) {
        tab1.map((item, index) => {
          if (this.state[2] == tab1[index].b_workid) tab2.push(item)
        })
      } else { tab2 = tab1 }

      if (this.state[3]) {
        tab2.map((item, index) => {
          if (this.state[3] == tab2[index].b_name) tab3.push(item)
        })
      } else { tab3 = tab2 }

      if (this.state[4]) {
        tab3.map((item, index) => {
          if (this.state[4] == tab3[index].b_class) tab4.push(item)
        })
        table = tab4
      } else { table = tab3 }
      console.log(table)
      if (this.level == 1) this.tabledata1 = table
      else if (this.level == 2) this.tabledata2 = table
      else if (this.level == 3) this.tabledata3 = table
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
    setnowpage () {
      this.currentPage = 1
    },
    rereas () { // 重置
      if (this.level == 1) this.tabledata1 = this.reltabledata
      else if (this.level == 2) this.tabledata2 = this.reltabledata
      else if (this.level == 3) this.tabledata3 = this.reltabledata
      this.state = []
      this.clearSelect()
      this.multipleSelection = []
      this.clearJ()
    },

    getulevel () { // 从登录缓存中获取 用户级别 \ 用户id \用户部门id
      var lastname = sessionStorage.getItem('userData')
      this.level = lastname.split(',')[10].split(':')[1]
      if (this.level == 0) {
        this.level = 3
      }
    },

    putinfo () { // 将登录缓存的数据返给后端并获取该用户可查看的数据
      var lastname = sessionStorage.getItem('userData')
      var i = lastname.split(',')[0].split(':')[1].split('"')[1]
      var j = lastname.split(',')[12].split(':')[2].split('"')[1]
      var l = this.level
      if (l == 1) {
        this.getuserdata()
        axios.get('bills/info/origin', {
          params: {
            userId: i
          }
        }).then(res => {
          var that = this
          var dlength = res.data.data.length
          var rdata = res.data.data
          console.log(res)
          for (var i = 0; i < dlength; i++) {
            var tbl = []
            var date = []
            var num
            date = rdata[i].date.split('-')
            tbl['b_year'] = date[0]
            tbl['b_moth'] = date[1]
            if (tbl['b_moth'] == '01') tbl['b_moth'] = 1
            if (tbl['b_moth'] == '02') tbl['b_moth'] = 2
            if (tbl['b_moth'] == '03') tbl['b_moth'] = 3
            if (tbl['b_moth'] == '04') tbl['b_moth'] = 4
            if (tbl['b_moth'] == '05') tbl['b_moth'] = 5
            if (tbl['b_moth'] == '06') tbl['b_moth'] = 6
            if (tbl['b_moth'] == '07') tbl['b_moth'] = 7
            if (tbl['b_moth'] == '08') tbl['b_moth'] = 8
            if (tbl['b_moth'] == '09') tbl['b_moth'] = 9
            if (tbl['b_moth'] == '10') tbl['b_moth'] = 10
            if (tbl['b_moth'] == '11') tbl['b_moth'] = 11
            if (tbl['b_moth'] == '12') tbl['b_moth'] = 12
            tbl['b_basem'] = rdata[i].basePay
            tbl['b_agem'] = rdata[i].seniorityPay
            tbl['b_cm'] = rdata[i].postPay
            tbl['b_eat'] = rdata[i].foodAllow
            tbl['b_trasal'] = rdata[i].trafficAllow
            tbl['b_out'] = rdata[i].travelAllow
            tbl['b_owsal'] = rdata[i].overtimeAllow
            tbl['b_esal'] = rdata[i].otherAllow
            tbl['b_x'] = rdata[i].fiof
            tbl['b_t'] = rdata[i].incomeTax
            tbl['b_on'] = rdata[i].absenceCut
            tbl['b_e'] = rdata[i].otherCut
            tbl['b_realsal'] = rdata[i].shouldPay
            tbl['b_name'] = rdata[i].user.name
            tbl['b_workid'] = rdata[i].user.id
            tbl['b_class'] = rdata[i].department.departName
            tbl['b_position'] = rdata[i].user.position

            this.tabledata1.push(tbl)
            this.reltabledata = this.tabledata1
            console.log('ok')
          }
        }).catch(function (error) {
          console.log(error)
        })
      } else if (l == 2) {
        this.getuserdata()
        axios.get('bills/info/origin', {
          params: {
            departId: j
          }
        }).then(res => {
          var that = this
          var dlength = res.data.data.length
          var rdata = res.data.data
          for (var i = 0; i < dlength; i++) {
            var tbl = []
            var date = []
            tbl['b_name'] = rdata[i].user.name
            date = rdata[i].date.split('-')
            tbl['b_year'] = date[0]
            tbl['b_moth'] = date[1]
            if (tbl['b_moth'] == '01') tbl['b_moth'] = 1
            if (tbl['b_moth'] == '02') tbl['b_moth'] = 2
            if (tbl['b_moth'] == '03') tbl['b_moth'] = 3
            if (tbl['b_moth'] == '04') tbl['b_moth'] = 4
            if (tbl['b_moth'] == '05') tbl['b_moth'] = 5
            if (tbl['b_moth'] == '06') tbl['b_moth'] = 6
            if (tbl['b_moth'] == '07') tbl['b_moth'] = 7
            if (tbl['b_moth'] == '08') tbl['b_moth'] = 8
            if (tbl['b_moth'] == '09') tbl['b_moth'] = 9
            if (tbl['b_moth'] == '10') tbl['b_moth'] = 10
            if (tbl['b_moth'] == '11') tbl['b_moth'] = 11
            if (tbl['b_moth'] == '12') tbl['b_moth'] = 12
            tbl['b_position'] = rdata[i].user.position
            tbl['b_workid'] = rdata[i].user.id
            tbl['b_basem'] = rdata[i].basePay
            tbl['b_agem'] = rdata[i].seniorityPay
            tbl['b_cm'] = rdata[i].postPay
            tbl['b_eat'] = rdata[i].foodAllow
            tbl['b_trasal'] = rdata[i].trafficAllow
            tbl['b_out'] = rdata[i].travelAllow
            tbl['b_owsal'] = rdata[i].overtimeAllow
            tbl['b_esal'] = rdata[i].otherAllow
            tbl['b_x'] = rdata[i].fiof
            tbl['b_t'] = rdata[i].incomeTax
            tbl['b_on'] = rdata[i].absenceCut
            tbl['b_e'] = rdata[i].otherCut
            tbl['b_realsal'] = rdata[i].shouldPay
            tbl['b_class'] = rdata[i].department.departName

            this.tabledata2.push(tbl)
            this.reltabledata = this.tabledata2
          }
        }).catch(function (error) {
          console.log(error)
        })
      } else {
        this.getpartdata()
        this.getuserdata()
        axios.get('bills/info/origin')
          .then(res => {
            var that = this
            var dlength = res.data.data.length
            var rdata = res.data.data
            for (var i = 0; i < dlength; i++) {
              var tbl = []
              var date = []
              tbl['b_name'] = rdata[i].user.name
              date = rdata[i].date.split('-')
              tbl['b_year'] = date[0]
              tbl['b_moth'] = date[1]
              if (tbl['b_moth'] == '01') tbl['b_moth'] = 1
              if (tbl['b_moth'] == '02') tbl['b_moth'] = 2
              if (tbl['b_moth'] == '03') tbl['b_moth'] = 3
              if (tbl['b_moth'] == '04') tbl['b_moth'] = 4
              if (tbl['b_moth'] == '05') tbl['b_moth'] = 5
              if (tbl['b_moth'] == '06') tbl['b_moth'] = 6
              if (tbl['b_moth'] == '07') tbl['b_moth'] = 7
              if (tbl['b_moth'] == '08') tbl['b_moth'] = 8
              if (tbl['b_moth'] == '09') tbl['b_moth'] = 9
              if (tbl['b_moth'] == '10') tbl['b_moth'] = 10
              if (tbl['b_moth'] == '11') tbl['b_moth'] = 11
              if (tbl['b_moth'] == '12') tbl['b_moth'] = 12
              tbl['b_class'] = rdata[i].department.departName
              tbl['b_position'] = rdata[i].user.position
              tbl['b_workid'] = rdata[i].user.id
              tbl['b_basem'] = rdata[i].basePay
              tbl['b_agem'] = rdata[i].seniorityPay
              tbl['b_cm'] = rdata[i].postPay
              tbl['b_eat'] = rdata[i].foodAllow
              tbl['b_trasal'] = rdata[i].trafficAllow
              tbl['b_out'] = rdata[i].travelAllow
              tbl['b_owsal'] = rdata[i].overtimeAllow
              tbl['b_esal'] = rdata[i].otherAllow
              tbl['b_x'] = rdata[i].fiof
              tbl['b_t'] = rdata[i].incomeTax
              tbl['b_on'] = rdata[i].absenceCut
              tbl['b_e'] = rdata[i].otherCut
              tbl['b_realsal'] = rdata[i].shouldPay
              this.tabledata3.push(tbl)
              this.reltabledata = this.tabledata3
            }
          }).catch(function (error) {
            console.log(error)
          })
      }
    },

    getuserdata () { // 将缓存的用户数据返给后端 并从后端获取数据
      var lastname = sessionStorage.getItem('userData')
      var i = lastname.split(',')[0].split(':')[1].split('"')[1]
      axios.get('user/add/userInfo/available', {params: {userId: i}})
        .then(res => {
          var olength = res.data.data.length
          var odata = res.data.data
          for (var i = 0; i < olength; i++) {
            this.opname[i] = odata[i].name
            this.opworkid[i] = odata[i].id
          }
        }).catch(function (error) {
          console.log(error)
        })
    },

    getpartdata () { // 将缓存的用户数据返给后端 并从后端获取数据
      axios.get('user/add/departInfo')
        .then(res => {
          var olength = res.data.data.length
          var odata = res.data.data
          console.log(res)
          for (var i = 0; i < olength; i++) {
            this.opcl[i] = odata[i].departName
          }
        }).catch(function (error) {
          console.log(error)
        })
    }

  },
  mounted () {
    this.getulevel()// 从登录缓存中获取 用户级别 \ 用户id \用户部门id
    this.putinfo()// 将缓存的用户数据返给后端 并从后端获取数据
  }

}
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style >
  .el-table .warning-row {
    background: #f0f9eb;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
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
    margin-left: 40%;
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
    height: 494px;
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
  .tabListPage{
    margin-top:26px;
  }

</style>
