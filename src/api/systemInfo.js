import request from '@/utils/request'
import { getToken, getUserInfo } from '@/utils/auth'

export function getSystemInfo(params) {
  const token = getToken()
  const userInfo = getUserInfo()
  return request({
    url: '/user/admin/find/restaurant',
    method: 'post',
    headers: {
      Authorization: `restaurant_${token}`
    },
    data: {
      adminId: userInfo.id
    }
  })
}
export function emitInfo(info) {
  const token = getToken()
  const userInfo = getUserInfo()
  return request({
    url: '/user/admin/modify/restaurant',
    method: 'post',
    headers: {
      Authorization: `restaurant_${token}`
    },
    data: {
      adminId: userInfo.id,
      id: '1',
      location: info.location || null,
      phone: info.phone || null,
      name: info.name || null,
      avatar: info.avatar || null
    }
  })
}

export function subChangePassword(passwords) {
  const token = getToken()
  const userInfo = getUserInfo()
  return request({
    url: '/user/admin/modify/info',
    method: 'post',
    headers: {
      Authorization: `restaurant_${token}`
    },
    data: {
      adminId: userInfo.id,
      id: '1',
      name: userInfo.name,
      phone: userInfo.phone,
      oldPassword: passwords.oldPassword,
      password: passwords.password
    }
  })
}
