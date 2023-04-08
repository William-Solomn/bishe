<template>
  <div class="main wrapper">
    <img :src="'https://bfjdkm.com:9002/file/download/id/'+restaurantInfo.avatar" alt="" class="avator">
    <el-upload action="https://bfjdkm.com:9002/file/upload/single" :on-success="changeAvator" class="upload">
      <el-button type="primary" plain>更换餐厅照片</el-button>
    </el-upload>
    <div class="info">
      <div class="name">
        <span class="key">餐厅名称：</span>{{ restaurantInfo.name }}
      </div>
      <div class="phone"><span class="key">订餐电话：</span>{{ restaurantInfo.phone }}</div>
      <div class="location"><span class="key">餐厅地址：</span>{{ restaurantInfo.location }}</div>
    </div>
    <div class="buttons">
      <el-button type="primary" plain @click="emitInfo1">编辑信息</el-button>
      <el-button type="primary" plain @click="changePassword">修改密码</el-button>
    </div>
    <el-dialog title="编辑餐厅信息" :visible.sync="emitInfoVisible">
      <el-form :model="restaurantInfo">
        <el-form-item label="餐厅名称">
          <el-input v-model="restaurantInfo.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="订餐电话">
          <el-input v-model="restaurantInfo.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="餐厅地址">
          <el-input v-model="restaurantInfo.location" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="emitInfoVisible = false">取 消</el-button>
        <el-button type="primary" @click="subEmitInfo">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改密码" :visible.sync="changePasswordVisible">
      <el-form :model="passwords">
        <el-form-item label="旧密码">
          <el-input v-model="passwords.oldPassword" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="passwords.password" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="再次输入">
          <el-input v-model="passwords.passwordAgain" type="password" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="changePasswordVisible = false">取 消</el-button>
        <el-button type="primary" @click="subChangePassword">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
import { getSystemInfo, emitInfo, subChangePassword } from '@/api/systemInfo'

export default {
  name: 'Qiantai',
  data() {
    return {
      restaurantInfo: {},
      emitInfoVisible: false,
      changePasswordVisible: false,
      passwords: {
        oldPassword: '',
        password: '',
        passwordAgain: ''
      }
    }
  },
  watch: {
    $route: {
      handler: function handler(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getSystemInfo()
  },
  methods: {
    subEmitInfo() {
      this.emitInfoVisible = false
      emitInfo(this.restaurantInfo)
    },
    getSystemInfo() {
      getSystemInfo().then(res => {
        console.log('getSystem', res)
        this.restaurantInfo = res.data.data[0]
      })
    },
    changeAvator(res) {
      console.log(res.data, 'changeavator')
      emitInfo({ avatar: res.data }).then(() => {
        getSystemInfo().then(res => {
          this.restaurantInfo = res.data.data[0]
        })
      })
    },
    emitInfo1() {
      this.emitInfoVisible = true
    },
    changePassword() {
      this.changePasswordVisible = true
    },
    subChangePassword() {
      if (this.passwords.password !== this.passwords.passwordAgain) {
        this.$alert('两次输入的新密码不同，请重试', '输入有误', {
          confirmButtonText: '确定'
        })
      } else {
        subChangePassword(this.passwords).then((res) => {
          if (res.data.message === '密码不匹配') {
            this.$alert('旧密码错误', '输入有误', {
              confirmButtonText: '确定'
            })
          } else {
            this.$message({
              message: '修改成功',
              type: 'success'
            })
          }
        })
      }
    }
  }
}
</script>

<style scoped>
.wrapper {
  margin-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.upload {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  margin: 30px 0;
}

.avator {
  margin: 20px;
  width: 150px;
  height: 150px;
  border-radius: 50%;
}

.info {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100px;
  justify-content: space-around;
  margin-bottom: 30px;
}

.key {
  font-weight: 600;
}
</style>
