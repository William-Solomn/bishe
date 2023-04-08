<template>
  <div>
    <el-button type="primary" @click="showAddDialog">添加数据</el-button>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="parking_id" label="停车场ID"></el-table-column>
      <el-table-column prop="parking_name" label="停车场名称"></el-table-column>
      <el-table-column prop="parking_position" label="停车场位置"></el-table-column>
      <el-table-column prop="parking_is_useful" label="是否可用"></el-table-column>
      <el-table-column prop="parking_is_bought" label="是否购买"></el-table-column>
      <el-table-column prop="parking_user_id" label="用户ID"></el-table-column>
      <el-table-column label="操作">
        <template #default="{row}">
          <el-button type="text" @click="showEditDialog(row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" title="编辑停车场信息" width="50%">
      <el-form :model="form" label-width="100px">
        <el-form-item label="停车场名称">
          <el-input v-model="form.parking_name"></el-input>
        </el-form-item>
        <el-form-item label="停车场位置">
          <el-input v-model="form.parking_position"></el-input>
        </el-form-item>
        <el-form-item label="是否可用">
          <el-switch v-model="form.parking_is_useful"></el-switch>
        </el-form-item>
        <el-form-item label="是否购买">
          <el-switch v-model="form.parking_is_bought"></el-switch>
        </el-form-item>
        <el-form-item label="用户ID">
          <el-input v-model="form.parking_user_id"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAllParkingInfo, postParkingInfo } from '@/api/parkinginfo'


export default {
  setup() {
  },
    data:() =>({
        tableData:[],
        dialogVisible:false,
        form:{}
    }),
    methods: {
        handleAllParkingInfo(){
            getAllParkingInfo().then(resp => {
                this.tableData = resp.data
            })
        },
        showAddDialog(){
            this.form = {}
            this.dialogVisible= true
        },
        showEditDialog(row){
            this.form = Object.assign({}, row)
            this.dialogVisible = true
        },
        submitForm() {
            postParkingInfo(this.form).then(resp => {
                this.dialogVisible = false
                this.handleAllParkingInfo()
            })
        }

    },
    mounted() {
        this.handleAllParkingInfo()
    }
}
</script>
