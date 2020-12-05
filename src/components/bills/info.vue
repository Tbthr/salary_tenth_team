/* eslint-disable standard/object-curly-even-spacing */
<template>
  <div class="divall" id="app">

    <my-bread level1='账单信息' level2='查看账单'></my-bread>

    <el-row class="demo-autocomplete">
        <el-col :span="5">
            <label>查找年月</label>
            <el-autocomplete
            class="inline-input"
            v-model="state[0]"
            :fetch-suggestions="querySearch"
            placeholder="请输入内容"

            @select="handleSelect">
            </el-autocomplete>
        </el-col>
        <el-col :span="5">
          <label>筛选部门</label>
          <el-autocomplete
          class="inline-input"
          v-model="state[1]"
          :fetch-suggestions="querySearch2"
          placeholder="请输入内容"
          @select="handleSelect">
          </el-autocomplete>
        </el-col>
        <el-col :span="5">
            <label>查找工号</label>
            <el-autocomplete
            class="inline-input"
            v-model="state1"
            :fetch-suggestions="querySearch3"
            placeholder="请输入内容"
            :trigger-on-focus="false"
            @select="handleSelect">
            </el-autocomplete>
        </el-col>
        <el-col :span="2">
          <el-button>查找</el-button>
        </el-col>

    </el-row>

    <el-row class="sbody">
            <el-card class="box-card">
              <el-table ref="multipleTable" tooltip-effect="dark" :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="50" ></el-table-column>
                <el-table-column type="index" width="50" ></el-table-column>
                <el-table-column prop="moth " label="年月" width="80"></el-table-column>
                <el-table-column prop="age" label="年龄" sortable width="80"></el-table-column>
                <el-table-column prop="class" label="部门" width="80"></el-table-column>
                <el-table-column prop="class1" label="职位" width="80"></el-table-column>
                <el-table-column prop="name" label="姓名" width="80"></el-table-column>
                <el-table-column prop="workid" label="职工号" width="110"></el-table-column>
                <el-table-column prop="basesal" label="基本工资" width="80"></el-table-column>
                <el-table-column prop="agesal" label="工龄工资" width="80"></el-table-column>
                <el-table-column prop="trasal" label="交通补贴" width="80"></el-table-column>
                <el-table-column prop="" label="加班工资" width="80"></el-table-column>
                <el-table-column prop="elsal" label="其他补贴" width="80"></el-table-column>
                <el-table-column prop="ins" label="五险一金" width="80"></el-table-column>
                <el-table-column prop="prstax" label="个人税" width="80"></el-table-column>
                <el-table-column prop="shdsal" label="应发工资" width="80"></el-table-column>
                <el-table-column prop="fine" label="扣款" width="80"></el-table-column>
                <el-table-column prop="tresal" label="实发工资" width="80"></el-table-column>

              </el-table>
            </el-card>
            <div class="div-b">
              <el-button @click="printgz()">打印工资条</el-button>
              <label>已选择行数：{{sle}}</label>
            </div>
    </el-row>

  </div>

</template>

<script>
import myBread from '../cuscom/myBread.vue'
export default{
  components: { myBread },
  data () {
    return {
      bumen: [{
        value: '选项1',
        label: '财务部'
      }, {
        value: '选项2',
        label: '教育部'
      }, {
        value: '选项3',
        label: '监督部'
      }, {
        value: '选项4',
        label: '不选择'
      }],
      xingbie: [{
        value: '选项1',
        label: '男'
      }, {
        value: '选项2',
        label: '女'
      }, {
        value: '选项3',
        label: '不选择'
      }],
      value1: [],
      value2: [],
      value3: [],
      value4: [],

      tableData: [
        {
          age: '22',
          name: 'wang',
          workid: '2018110345',
          basesal: '3000',
          agesal: '1000',
          trasal: '200',
          elsal: '100',
          ins: '1000',
          shdsal: '3000',
          fine: '200',
          prstax: '200',
          tresal: '2600',
          class: '教育部门',
          tag: '男'
        }, {
          age: '35',
          name: 'li',
          workid: '2018132344',
          basesal: '5000',
          agesal: '3000',
          trasal: '200',
          elsal: '100',
          ins: '1000',
          shdsal: '3000',
          fine: '200',
          prstax: '200',
          tresal: '2600',
          class: '人事部门',
          tag: '女'
        }],

      sle: 0,
      multipleSelection: [],

      restaurants: [],
      res: [],
      state: []

    }
  },
  methods: {
    formatter (row, column) {
      return row.address
    },
    filterClass (value, row) {
      return row.class === value
    },
    filterTag (value, row) {
      return row.tag === value
    },

    tzsy () {
      alert('sucs')
    },
    tzzhgl () {
      alert('sucs')
    },
    tzgzsp () {
      alert('sucs')
    },
    tzsjhz () {
      alert('sucs')
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
      console.log('选中的表格', this.multipleSelection)
      this.sle = val.length
    },
    toggleSelection (rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    printgz () {
      if (this.multipleSelection.length === 0) {
        alert('please select')
      } else {
        let multis = []
        for (var i = 0; i < this.multipleSelection.length; i++) {
          multis.push(this.multipleSelection[i].name)
        }
        alert('即将打印：' + multis)
      }
    },

    querySearch (queryString, cb) {
      var restaurants = this.restaurants
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants

      cb(results)
    },
    createFilter (queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    querySearch2 (queryString, cb) {
      var res = this.res
      var results = queryString ? res.filter(this.createFilter2(queryString)) : res

      cb(results)
    },
    createFilter2 (queryString) {
      return (res) => {
        return (res.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },

    loadAll () {
      return [
        // eslint-disable-next-line standard/object-curly-even-spacing
        { 'value': 'wang'},
        { 'value': 'li' }
      ]
    },
    loadAllres () {
      return [
        { 'value': 'w' },
        { 'value': 'l' }
      ]
    },
    handleSelect (item) {
      console.log(item)
    }
  },
  mounted () {
    this.restaurants = this.loadAll()
    this.res = this.loadAllres()
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .demo-autocomplete{
    margin:20px;
  }
  .div-b{
  margin-left: 600px;
  }
  .box-card {
    width: 100%;
    height: 90%;
  }
  .divbh{
    width: 40%;

    margin:10px 240px;
    font-size: 24px;
  }
  .divall{
    height: 100%;
    width: 100%;
    position: absolute;
  }
  .sbody{
    height: 74%;
  }
</style>
