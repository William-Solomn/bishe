import request from '@/utils/request'

export function login(data) {
  const { username, password } = data
  const role = data.role
  if (role === 0) {
    return request({
      url: '/user/admin/login',
      method: 'post',
      data: {
        password: password,
        account: username
      }
    })
  } else if (role === 1) {
    return request({
      url: '/user/manager/login',
      method: 'post',
      data: {
        password: password,
        account: username
      }
    })
  } else if (role === 2) {
    return request({
      url: '/user/worker/login',
      method: 'post',
      data: {
        password: password,
        account: username
      }
    })
  }
}

export function getInfo(token) {
  return request({
    url: '/vue-admin-template/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}
