<template>
  <el-container>
    <el-header />
    <el-main>
      <el-row type="flex" justify="center">
        <el-col :span="12">
          <el-card>
            <div style="margin: 64px">
              <!-- Title -->
              <div slot="header" class="clearfix">
                <h3 style="text-align: center">餐饮管理后台</h3>
              </div>
              <!-- Main form -->
              <el-row type="flex" justify="center">
                <el-col :span="16">
                  <el-form ref="loginForm" :model="loginForm" auto-complete="on" label-position="left">
                    <el-form-item>
                      <el-radio-group v-model="rule" @change="changeRole">
                        <el-radio-button label="服务员" />
                        <el-radio-button label="管理员" />
                        <el-radio-button label="老板" />
                      </el-radio-group>
                    </el-form-item>

                    <el-form-item prop="username">
                      <span class="svg-container">
                        <svg-icon icon-class="user" />
                      </span>
                      <el-input ref="username" v-model="loginForm.username" style="width: auto" placeholder="用户名" name="username" type="text" tabindex="1" auto-complete="on" />
                    </el-form-item>

                    <el-form-item prop="password">
                      <span class="svg-container">
                        <svg-icon icon-class="password" />
                      </span>
                      <el-input :key="passwordType" ref="password" v-model="loginForm.password" style="width: auto" :type="passwordType" placeholder="密码" name="password" tabindex="2" auto-complete="on" @keyup.enter.native="handleLogin" />
                      <span class="show-pwd" @click="showPwd">
                        <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
                      </span>
                    </el-form-item>

                    <el-button :loading="loading" type="primary" @click.native.prevent="handleLogin">登录</el-button>
                  </el-form>
                </el-col>
              </el-row>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
  </el-container>

</template>

<script>
import { setRole } from '@/utils/auth'

export default {

  name: 'Login',
  data: () => (
    {
      loginForm: {
        account: '',
        password: ''
      },
      role: null,
      rule: '',
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }),
  watch: {
    $route: {
      handler: function handler(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    changeRole(role) {
      if (role === '服务员') {
        this.role = 2
      } else if (role === '管理员') {
        this.role = 1
      } else if (role === '老板') {
        this.role = 0
      }
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          const that = this
          this.loginForm.role = this.role
          this.loginForm.account = this.loginForm.username
          this.loading = true

          this.$store.dispatch('user/login', this.loginForm).then(() => {
            setRole(that.role)
            this.$router.push({ path: '/main/main' })
            this.loading = false
          }).catch(() => {
            this.$message.error('信息错误，请检查身份、账号、密码！')
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>
