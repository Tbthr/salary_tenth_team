<template>
    <div>
        <!-- 面包屑导航区 -->
    <my-bread level1='安全日志' level2='查看安全日志'></my-bread>
    <el-card>
      <el-row>
        <el-col>
          <el-button type="success" size="small" @click="importInfo()">导出信息</el-button>
          <el-button type="danger" size="small" @click="clearInfo()">删除日志</el-button>
        </el-col>
        <el-table class="table" :data="logList" border stripe>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="操作时间" prop="date"></el-table-column>
        <el-table-column label="操作者" prop="username"></el-table-column>
        <el-table-column label="ip" prop="ip"></el-table-column>
        <el-table-column label="操作" prop="module"></el-table-column>
        <!-- <el-table-column label="日志类型" prop="level">
            <template slot-scope="scope">
            <el-tag v-if="scope.row.level === '0'">INFO</el-tag>
            <el-tag type="success" v-else-if="scope.row.level === '1'">DEBUG</el-tag>
            <el-tag type="warning" v-else-if="scope.row.level === '2'">WARN</el-tag>
            <el-tag type="danger" v-else>ERROR</el-tag>
            </template>
        </el-table-column> -->
        <el-table-column label="日志描述" prop="content"></el-table-column>
        <!-- <el-table-column label="内容" prop="content"></el-table-column> -->
      </el-table>
      </el-row>
    </el-card>
    </div>
</template>

<script>
import myBread from '../cuscom/myBread.vue'
export default {
  components: { myBread },
  data () {
    return {
      logList: []
    }
  },
  created () {
    this.getLogList()
  },
  methods: {
    async getLogList () {
      const { data: res } = await this.$axios.get('sys/log')
      console.log(res)
      if (res.code === 200) {
        this.logList = res.data.list
        this.$message.success(res.msg)
      } else if (res.code === 403) {
        return this.$message.waring(res.msg)
      } else {
        return this.$message.error(res.msg)
      }
    }
  }
}

</script>
<style scoped>
.table{
  top: 10px;
}
</style>
