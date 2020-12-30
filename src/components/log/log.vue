<template>
    <div>
       <!-- 面包屑导航区 -->
      <my-bread level1='系统工具' level2='查看日志'></my-bread>
    <el-card>
      <el-row :gutter="20"  class="el-row">
        <el-col :span="12">
          <div>
            <span >快速查找：</span>
            <el-input v-model="search" clearable placeholder="请输入搜索内容" style="width: 180px"></el-input>
          </div>
        </el-col>
        <el-col :span="12">
          <download-excel
          :data = "json_data"
          :fields = "json_fields"
          type = "xls"
          worksheet = "My Worksheet"
          name = "日志.xls"
          >
            <div style="float: right">
              <el-button type="success" @click="importLogs()">导出日志</el-button>
              <el-button type="danger" @click="clearLogs()">删除日志</el-button>
            </div>
          </download-excel>
        </el-col>
      </el-row>
      <!-- 表格 -->
        <div>
          <el-table :header-cell-style= "{background:'#f3f6fc',color:'#000'}" ref="multipleTable" class="table" :data="tables" border stripe height="475" @selection-change= "selectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column type="index" label="#"></el-table-column>
            <el-table-column label="操作时间" prop="date"></el-table-column>
            <el-table-column label="操作者" prop="username"></el-table-column>
            <el-table-column label="ip" prop="ip"></el-table-column>
            <el-table-column label="操作" prop="module"></el-table-column>
            <el-table-column label="日志类型" prop="info">
                <template slot-scope="scope">
                <el-tag v-if="scope.row.info === 'LOGIN'">LOGIN</el-tag>
                <el-tag type="success" v-else-if="scope.row.info === 'SELECT'">SELECT</el-tag>
                <el-tag type="info" v-else-if="scope.row.info === 'DELETE'">DELETE</el-tag>
                <el-tag type="warning" v-else-if="scope.row.info === 'WARN'">WARN</el-tag>
                <el-tag type="danger" v-else>ERROR</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="日志描述" prop="content"></el-table-column>
            <!-- <el-table-column label="内容" prop="content"></el-table-column> -->
          </el-table>
        </div>
  <!-- 分页 -->
      <div>
        <el-pagination
          @size-change="sizeChange"
          @current-change="currentChange"
          :current-page="query.page"
          :page-sizes="[5, 10, 15]"
          :page-size="query.rows"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
      total: 0,
      logList: [],
      searchList: [],
      ids: [],
      query: {
        page: 1,
        rows: 10,
        other: ''
      },

      json_data: [],
      json_fields: {
        '操作时间': 'date',
        '操作者': 'username',
        'ip': 'ip',
        '操作': 'module',
        '日志类型': 'info',
        '日志描述': 'content'
      },
      multipleSelection: [],
      search: ''
    }
  },
  created () {
    this.getLogList()
  },
  methods: {
    async getLogList () {
      const { data: res } = await this.$axios.get('sys/log', {params: this.query})
      console.log(res)
      if (res.code === 200) {
        this.logList = res.data.list
        this.$message.success(res.msg)
      } else if (res.code === 403) {
        return this.$message.waring(res.msg)
      } else {
        return this.$message.error(res.msg)
      }
      this.total = res.data.total
      console.log(this.total)
    },
    // 监听pageSize
    sizeChange (newSize) {
      this.query.rows = newSize
      this.getLogList()
    },
    // 监听pagenum改变
    currentChange (newPage) {
      this.query.page = newPage
      this.getLogList()
    },
    selectionChange (selection) {
      this.multipleSelection = selection
      for (var i = 0; i < selection.length; i++) {
        this.ids[i] = selection[i].id
      }
      console.log(this.ids)
    },
    async clearLogs () {
      if (this.multipleSelection.length === 0) {
        this.$message.error('请选择要删除的日志')
      } else {
        this.$axios({
          url: 'sys/log/delete',
          method: 'POST',
          data: {
            ids: this.ids
          },
          transformRequest: [
            function (data) {
            // Do whatever you want to transform the data
              let ret = ''
              for (let it in data) {
                ret +=
                  encodeURIComponent(it) +
                  '=' +
                  encodeURIComponent(data[it]) +
                  '&'
              }
              return ret
            }
          ]
        }).then((res) => {
          console.log(res)
          if (res.data.code !== 200) {
            this.$message.error(res.data.msg)
          } else {
            this.$message.success(res.data.msg)
          }
          this.ids = []
          this.getLogList()
        })
      }
    },
    importLogs () { // 打印工资条
      if (this.multipleSelection.length === 0) {
        this.$message.error('请选择要打印的日志')
      } else {
        for (var i = 0; i < this.multipleSelection.length; i++) {
          var jdata = []
          jdata['date'] = this.multipleSelection[i].date
          jdata['username'] = this.multipleSelection[i].username
          jdata['ip'] = this.multipleSelection[i].ip
          jdata['module'] = this.multipleSelection[i].module
          jdata['info'] = this.multipleSelection[i].info
          jdata['content'] = this.multipleSelection[i].content
          this.json_data.push(jdata)
        }
        this.clearSelect()
        this.multipleSelection = []
      }
    },
    clearSelect () {
      this.$refs.multipleTable.clearSelection()
    }
  },
  computed: {
    // 模糊搜索
    tables () {
      const search = this.search
      if (search) {
        // filter() 方法创建一个新的数组，新数组中的元素是通过检查指定数组中符合条件的所有元素。
        return this.logList.filter(data => {
          // some() 方法用于检测数组中的元素是否满足指定条件;
          // some() 方法会依次执行数组的每个元素：
          return Object.keys(data).some(key => {
            // indexOf() 返回某个指定的字符在某个字符串中首次出现的位置，如果没有找到就返回-1；
            // toLowerCase() 方法将所有查询到内容变为小写。
            return String(data[key]).toLowerCase().indexOf(search) > -1
          })
        })
      }
      return this.logList
    }
  }

}

</script>
<style scoped>
.el-pagination{
  padding: 15px 0 0 0;
}

.el-row {
    margin-bottom: 5px;
    display: flex;
    flex-wrap: wrap
  }
</style>
