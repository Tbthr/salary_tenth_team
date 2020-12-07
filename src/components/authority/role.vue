<template>
  <div>
    <!-- 面包屑导航区 -->
    <my-bread level1='权限管理' level2='角色列表'></my-bread>
    <el-card height="550">
        <!-- 添加角色按钮 -->
        <el-row>
            <el-col>
            <el-button class='sure' type="primary" @click="AddRoleDialogVisible=true" float-right>添加角色</el-button>
            <el-button type="danger" icon="el-icon-delete" @click="removeRoleById(scope.row.id)" float-right>删除</el-button>
            </el-col>
            <el-table :data="roleList" border stripe>
                <!-- 展开列 -->
                <el-table-column type="expand">
                <template slot-scope="scope">
                    <el-row
                    :class="['bdbottom', i1 === 0 ? 'bdtop' : '', 'vcenter']"
                    v-for="(item1, i1) in scope.row.menus"
                    :key="item1.id"
                    >
                      <!-- 一级权限 -->
                      <el-col :span="6">
                          <el-tag closable @close="removeRightById(scope.row, item1.id)">{{ item1.name}}</el-tag>
                          <i class="el-icon-arrow-right"></i>
                      </el-col>
                      <!-- 二级和三级 -->
                      <el-col :span="18">
                          <!-- 通过for循环 渲染二级权限 -->
                          <el-row
                          :class="[i2 === 0 ? '' : 'bdtop', 'vcenter']"
                          v-for="(item2, i2) in item1.children"
                          :key="item2.id"
                          >
                            <el-col :span="6">
                                <el-tag
                                type="success"
                                closable
                                @close="removeRightById(scope.row, item2.id)"
                                >{{ item2.name }}</el-tag>
                                <i class="el-icon-arrow-right"></i>
                            </el-col>
                            <el-col :span="18">
                                <el-tag
                                type="warning"
                                v-for="(item3) in item2.children"
                                :key="item3.id"
                                closable
                                @close="removeRightById(scope.row, item3.id)"
                                >{{ item3.name}}</el-tag>
                            </el-col>
                          </el-row>
                      </el-col>
                    </el-row>
                </template>
                </el-table-column>
                <!-- 索引列 -->
                <el-table-column type="index" label="#"></el-table-column>
                <el-table-column type="selection" width="55px"></el-table-column>
                <el-table-column label="角色名称" prop="name"></el-table-column>
                <el-table-column label="角色描述" prop="nameZh"></el-table-column>
                <el-table-column label="操作" width="300px">
                <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)">编辑</el-button>
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeRoleById(scope.row.id)">删除</el-button>
                    <el-button
                    type="warning"
                    icon="el-icon-setting"
                    size="mini"
                    @click="showSetRightDialog(scope.row)"
                    >分配权限</el-button>
                </template>
                </el-table-column>
            </el-table>
        </el-row>
    </el-card>
    <!-- 分配权限 -->
    <el-dialog
    title="分配权限"
    :visible.sync="setRightDialogVisible"
    width="50%"
    @close="setRightDialogClosed"
    >
    <el-tree
        :data="rightsList"
        :props="treeProps"
        ref="treeRef"
        show-checkbox
        node-key="id"
        default-expand-all
        :default-checked-keys="defKeys"
    ></el-tree>
    <span slot="footer" class="dialog-footer">
        <el-button @click="setRightDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="allotRights">确 定</el-button>
    </span>
    </el-dialog>
    <!-- 添加角色对话框 -->
    <el-dialog title="添加角色" :visible.sync="AddRoleDialogVisible" width="40%" @close="addRoleDialogClosed">
    <el-form
        :model="addRoleForm"
        ref="addRoleFormRef"
        :rules="addRoleFormRules"
        label-width="100px"
    >
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="addRoleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="nameZh">
          <el-input v-model="addRoleForm.nameZh"></el-input>
        </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="AddRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRoles">确 定</el-button>
    </span>
    </el-dialog>
    <!-- 编辑角色对话框 -->
    <el-dialog title="编辑角色" :visible.sync="editRoleDialogVisible" width="40%" @close="addRoleDialogClosed">
    <el-form
        :model="editRoleForm"
        ref="editRoleFormRef"
        :rules="editRoleFormRules"
        label-width="100px"
    >
        <el-form-item label="角色名称" prop="name">
        <el-input v-model="editRoleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="nameZh">
        <el-input v-model="editRoleForm.nameZh"></el-input>
        </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="editRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editRoles">确 定</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<script>
import myBread from '../cuscom/myBread.vue'
export default {
  components: { myBread },
  data () {
    return {
      idList: [],
      // 所有角色列表
      roleList: [],
      // 分配权限框
      setRightDialogVisible: false,
      // 权限列表
      rightsList: [],
      //  树形控件的属性绑定对象
      treeProps: {
        label: 'name',
        menus: 'menus'
      },
      //   默认选中节点ID值
      defKeys: [],
      //   添加用户对话框
      AddRoleDialogVisible: false,
      // 添加角色表单
      addRoleForm: {},
      //   添加角色表单验证
      addRoleFormRules: {
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ],
        nameZh: [
          { required: true, message: '请输入角色描述', trigger: 'blur' }
        ]
      },
      //   编辑角色信息
      editRoleForm: {},
      editRoleDialogVisible: false,
      editRoleFormRules: {
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ],
        namezh: [
          { required: true, message: '请输入角色描述', trigger: 'blur' }
        ]
      },
      //   当前即将分配权限的Id
      roleId: 0
    }
  },
  created () {
    this.getRoleList()
  },
  methods: {
    async getRoleList () {
      const { data: res } = await this.$axios.get('authority/role')
      console.log(res)
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      console.log('1')
      this.roleList = res.data
      console.log(this.roleList)
      sessionStorage.setItem('roleList', JSON.stringify(this.roleList))
    },
    // 根据ID删除对应的权限
    async removeRightById (roleId, rightId) {
      // 弹框提示 删除
      const confirmResult = await this.$confirm(
        '此操作将永久删除该权限, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)
      // 点击确定 返回值为：confirm
      // 点击取消 返回值为： cancel
      if (confirmResult !== 'confirm') {
        this.$message.info('已取消权限删除')
      }
      const { data: res } = await this.$axios.delete(
        `role/${roleId}/menu/${rightId}`
      )
      if (res.code !== 200) {
        return this.$message.error('删除权限失败！')
      }
      this.roleList = res.data
      //   不建议使用
      this.getRoleList()
    },
    // 分配权限
    async showSetRightDialog (role) {
      this.roleId = role.id
      console.log(this.roleId)
      // 获取角色的所有权限
      this.$axios({
        url: 'authority/role/add/menutree',
        method: 'post',
        data: {
          id: this.roleId
        }
      }).then((res) => {
        console.log(res)
        this.rightsList = res.data.data

        //   递归获取三级节点的id
        this.getLeafkeys(role, this.defKeys)

        this.setRightDialogVisible = true
      })
      // const { data: res } = await this.$axios.get('authority/role/add/menutree', this.roleId)
      // if (res.code !== 200) {
      //   console.log(res.code)
      //   console.log(res)
      //   return this.$message.error('获取权限数据失败！')
      // }
      //   获取权限树
      // this.rightsList = res.data
      //   console.log(res)
      // //   递归获取三级节点的id
      // this.getLeafkeys(role, this.defKeys)

      // this.setRightDialogVisible = true
    },
    // 通过递归 获取角色下三级权限的 id, 并保存到defKeys数组
    getLeafkeys (node, arr) {
      // 没有children属性，则是三级节点
      if (!node.children) {
        return arr.push(node.id)
      }
      node.children.forEach(item => this.getLeafkeys(item, arr))
    },
    // 权限对话框关闭事件
    setRightDialogClosed () {
      this.rightsList = []
    },
    // 添加角色对话框的关闭
    addRoleDialogClosed () {
      this.$refs.addRoleFormRef.resetFields()
    },
    // 添加角色
    addRoles () {
      this.$refs.addRoleFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$axios.post('authority/role/add/role', this.addRoleForm)
        if (res.code !== 200) {
          this.$message.error(res.msg)
        }
        this.$message.success(res.msg)
        this.AddRoleDialogVisible = false
        this.roleList = res.data
      })
    },
    // 删除角色
    async removeRoleById (id) {
      this.idList = id
      const confirmResult = await this.$confirm(
        '此操作将永久删除该角色, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      this.$axios({
        url: 'authority/role/delete/roles',
        method: 'POST',
        data: {
          id: [this.idList]
        }
      }).then((res) => {
        console.log(res)
        if (res.data.code !== 200) {
          this.$message.error(res.data.msg)
        }
        this.$message.success(res.data.msg)
        this.roleList = res.data.data
      })
    },
    // 编辑角色
    async showEditDialog (id) {
      console.log(id)
      this.id = id
      // this.editRoleForm = JSON.parse(sessionStorage.getItem('roleList'))
      // console.log(this.editRoleForm)
      this.editRoleDialogVisible = true
    },
    editRoles () {
      this.$refs.editRoleFormRef.validate(async valid => {
        // 表单预校验失败
        if (!valid) return
        this.$axios({
          url: 'authority/role/update',
          method: 'POST',
          data: {
            id: this.id,
            name: this.editRoleForm.name,
            nameZh: this.editRoleForm.nameZh
          }
        }).then((res) => {
          console.log(res)
          if (res.data.code !== 200) {
            this.$message.error(res.data.msg)
          }
          // 隐藏编辑角色对话框
          this.editRoleDialogVisible = false
          this.$message.success(res.data.msg)
          this.roleList = res.data.data
        })
      })
    },
    // 分配权限
    async allotRights (roleId) {
      // 获得当前选中和半选中的Id
      const keys = [
        ...this.$refs.treeRef.getCheckedKeys(),
        ...this.$refs.treeRef.getHalfCheckedKeys()
      ]
      // join() 方法用于把数组中的所有元素放入一个字符串
      const idStr = keys.join(',')
      const { data: res } = await this.$axios.post(`authority/role/rights`, { rids: idStr })
      if (res.meta.status !== 200) { return this.$message.error('分配权限失败！') }
      this.$message.success('分配权限成功!')
      this.getRolesList()
      this.setRightDialogVisible = false
    }
  }
}
</script>
<style scoped>
.el-tag {
  margin: 7px;
}
.sure{
  float: right;
  margin-right: 10px;
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
