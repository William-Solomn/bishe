<template>
  <div class="main">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">服务员</el-menu-item>
      <el-menu-item index="2">管理员</el-menu-item>
    </el-menu>
    <el-button type="primary" class="newPeople" @click="openDialog">新增人员</el-button>
    <el-table :data="activeIndex==='1'?waiterData:managerData" stripe style="width: 100%">
      <el-table-column prop="name" label="姓名" width="180" />
      <el-table-column prop="phone" label="联系方式" width="180" />
      <el-table-column prop="account" label="用户名" />
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button class="emitButton" type="text" size="small" @click="emitPeople(scope.row)">编辑</el-button>
          <el-popconfirm
            confirm-button-text="确认删除"
            cancel-button-text="取消"
            title="确定该员工删除吗？"
            @confirm="deleteWorker(scope.row)"
          >
            <el-button slot="reference" type="text" size="small">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="人员信息" :visible.sync="dialogFormVisible">
      <el-form :model="peopleInfo">
        <el-form-item label="姓名">
          <el-input v-model="peopleInfo.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="peopleInfo.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="peopleInfo.account" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="peopleInfo.password" autocomplete="off" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog">取 消</el-button>
        <el-button type="primary" @click="changePeople">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
import {
  getWaiters,
  getManages,
  emitManager,
  addManager,
  deleteManager,
  addWorker,
  deleteWorker,
  emitWorker
} from '@/api/qianting'

export default {

  name: 'Qiantai',
  data: () => (
    {
      activeIndex: '1',
      dialogFormVisible: false,
      newPeople: false,
      tableData: [],
      waiterData: [],
      managerData: [],
      peopleInfo: { name: '', phone: '', account: '', password: '' }
    }),
  watch: {
    $route: {
      handler: function handler(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getWaiter()
  },
  methods: {
    cancelDialog() {
      this.peopleInfo = { name: '', phone: '', account: '', password: '' }
      this.dialogFormVisible = false
    },
    // 确定按钮回调
    changePeople() {
      const that = this
      // 员工
      if (this.activeIndex === '1') {
        // 新增
        if (this.newPeople) {
          addWorker(this.peopleInfo).then(() => {
            this.getWaiter()
          })
          // 编辑
        } else {
          emitWorker(this.peopleInfo).then(() => {
            this.getWaiter()
          })
        }
        // 管理员
      } else {
        if (this.newPeople) {
          addManager(this.peopleInfo).then(() => {
            this.getManage()
          })
          // 编辑
        } else {
          emitManager(that.peopleInfo).then(() => {
            that.getManage()
          })
        }
      }
      this.dialogFormVisible = false
    },
    deleteWorker(userInfo) {
      if (this.activeIndex === '1') {
        deleteWorker(userInfo.id).then(() => {
          this.getWaiter()
        })
      } else {
        console.log('deleteManager')
        deleteManager(userInfo.id).then(() => {
          this.getManage()
        })
      }
    },
    addWorker() {
      this.dialogFormVisible = false
      addWorker(this.peopleInfo).then(() => {
        this.getWaiter()
        this.peopleInfo = { name: '', phone: '', account: '', password: '' }
      })
    },
    handleSelect(key, keyPath) {
      this.activeIndex = keyPath[0]
      if (key === '1') {
        getWaiters().then((res) => {
          this.waiterData = res.data.data
        })
      } else if (key === '2') {
        getManages().then((res) => {
          this.managerData = res.data.data ? [res.data.data] : null
        })
      }
    },
    getWaiter() {
      getWaiters().then((res) => {
        this.waiterData = res.data.data
      })
    },
    getManage() {
      getManages().then((res) => {
        this.managerData = res.data.data ? [res.data.data] : null
      })
    },
    // 新增人员按钮回调
    openDialog() {
      this.dialogFormVisible = true
      console.log('新增')
      console.log(this.activeIndex)
      this.newPeople = true
      this.peopleInfo = { name: '', phone: '', account: '', password: '' }
    },
    // 编辑人员按钮回调
    emitPeople(info) {
      this.newPeople = false
      console.log('编辑')
      this.peopleInfo = info
      this.dialogFormVisible = true
    }
  }
}
</script>

<style scoped>
.newPeople {
  margin: 10px;
}

.emitButton {
  margin-right: 15px;
}
</style>
