<template>
    <div>
        <my-bread level1='账单信息' level2="提交表单"></my-bread>
        <el-card>
            <el-row class="cat_opt">
                <el-col>
                <span>选择表单类型：</span>
                <!-- 表单类型的级联选择框 -->
                <el-select v-model="selectedCateKeys"
                    :options="cateList"
                    :props="cateProps"
                    @change="handleChange" placeholder="请选择表单类型">
                  <el-option label="扣款清单" value="decuct"></el-option>
                  <el-option label="补贴清单" value="subsidy"></el-option>
                  <el-option label="出错信息表" value="financial"></el-option>
                  <el-option label="职工工资变动通知单" value="financial"></el-option>
                </el-select>
                </el-col>
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
      cateList: []
    }
  }
}
</script>
<style scoped>
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
