<template>
  <div>
    <!-- 面包屑导航区 -->
    <my-bread level1='权限管理' level2='权限列表'></my-bread>
    <!-- 卡片视图 -->
    <el-card>
      <el-table :data="menuList" border stripe height="500">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="权限名称" prop="name"></el-table-column>
        <el-table-column label="路径" prop="url"></el-table-column>
        <el-table-column label="权限等级" prop="level">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.level === 1 ">一级</el-tag>
            <el-tag type="success" v-else-if="scope.row.level === 2 ">二级</el-tag>
            <el-tag type="danger" v-else>三级</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 权限列表
      menuList: []
    }
  },
  created () {
    this.getMenuList()
  },
  methods: {
    async getMenuList () {
      const { data: res } = await this.$axios.get('authority/menu')
      console.log(res)
      if (res.code === 200) {
        this.menuList = res.data
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
.el-table{
  margin: 15px 0 0 0 ;
}
.el-tag {
  margin: 7px;
}
.el-breadcrumb{
  padding-bottom: 15px;
}
.el-button{
  float: right;
}
.bdtop {
  border-top: 1px solid #eee;
}
.bdbottom {
  border-bottom: 1px solid #eee;
}
.vcenter {
  display: flex;
  align-items: center;
}
</style>
