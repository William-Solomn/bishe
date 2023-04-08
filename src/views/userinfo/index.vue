<template>
  <div class="container">
    <el-button type="primary" @click="showAddUserDialog">新增用户</el-button>
    <el-table :data="userList" border style="width: 100%">
      <el-table-column prop="user_id" label="ID"></el-table-column>
      <el-table-column prop="user_name" label="姓名"></el-table-column>
      <el-table-column prop="user_phone" label="手机号"></el-table-column>
      <el-table-column prop="user_car_number" label="车牌号"></el-table-column>
      <el-table-column prop="remain_date" label="剩余时间"></el-table-column>
    </el-table>
    <el-dialog :visible.sync="addUserDialogVisible" title="新增用户">
      <el-form :model="addUserForm">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="addUserForm.user_name"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="addUserForm.user_phone"></el-input>
        </el-form-item>
        <el-form-item label="车牌号" :label-width="formLabelWidth">
          <el-input v-model="addUserForm.user_car_number"></el-input>
        </el-form-item>
        <el-form-item label="剩余时间" :label-width="formLabelWidth">
          <el-date-picker type="datetime" v-model="addUserForm.remain_date"></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addUserDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAddUser()">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAllUser, postAddUser } from "@/api/userinfo";
import { ref } from "vue";

export default {
  name: "UserList",
  setup() {

  },
    data:() =>({
        userList:[],
        addUserDialogVisible:false,
        addUserForm:{
            user_name:'',
            user_phone: "",
            user_car_number: "",
            remain_date: null,
        }
    }),
    methods: {
        showAddUserDialog(){
            this.addUserDialogVisible = true;
    },

        handleAddUser(){
            postAddUser(this.addUserForm).then(() => {
                getAllUser().then((resp) => {
                    userList = resp.data;
                });
                this.addUserDialogVisible = false;
                this.$notify({
                  name: `操作成功`,
                  type:  'success',
                  message: '成功添加该用户'
            })
            });
            this.handleAllUser()
    },
        handleAllUser(){
            getAllUser().then((resp) => {
                this.userList = resp.data;
        });
        }

    },
    mounted() {
        const formLabelWidth = "120px";
        this.handleAllUser()
    }
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
}
</style>