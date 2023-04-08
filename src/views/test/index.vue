<template>
 <div>
    <el-button class="add-btn" @click="showAddDialog">新增</el-button>
    <el-table :data="carList" style="width: 100%">
      <el-table-column prop="car_id" label="流水号"></el-table-column>
      <el-table-column prop="car_number" label="车牌号"></el-table-column>
      <el-table-column prop="date_came_in" label="进入时间"></el-table-column>
      <el-table-column prop="date_leave_out" label="离开时间"></el-table-column>
      <el-table-column prop="park_fee" label="停车费用"></el-table-column>
      <el-table-column prop="is_pay" label="是否付款"></el-table-column>
      <el-table-column prop="is_internal" label="是否是内部人员"></el-table-column>
    </el-table>

         <el-dialog :visible.sync="dialogVisible" title="新增车辆信息">
      <el-form ref="carForm" :model="carForm" label-width="100px">
        <el-form-item label="车牌号" required>
          <el-input v-model="carForm.car_number"></el-input>
        </el-form-item>
        <el-form-item label="进入时间" required>
          <el-date-picker v-model="carForm.date_came_in" type="datetime"></el-date-picker>
        </el-form-item>
        <el-form-item label="离开时间">
          <el-date-picker v-model="carForm.date_leave_out" type="datetime"></el-date-picker>
        </el-form-item>
        <el-form-item label="是否付款" required>
          <el-radio-group v-model="carForm.is_pay">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否是内部人员" required>
          <el-radio-group v-model="carForm.is_internal">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCarInfo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>

import { getAllCarInfo, postCarInfo } from '@/api/carinfo'


export default({
    setup() {
        
    },
    data:() =>({
        carList: [],
        dialogVisible: false,
        carForm: {
            car_number: '',
            date_came_in: '',
            date_leave_out: '',
            is_pay: 0,
            is_internal: 0
        }
    }),
    methods: {
        handleAllCarInfo(){
            getAllCarInfo().then(resp=>{
                console.log('112312312')
                console.log(resp)
                this.carList = resp.data
            })
        },
        addCarInfo(){
            postCarInfo(this.carForm).then(resp=>{
                console.log("添加成功")
                console.log(resp)
            })
        },
        showAddDialog(){
            this.dialogVisible= true;
        }   
    },
    mounted() {
        this.handleAllCarInfo()
    }
    
})

</script>