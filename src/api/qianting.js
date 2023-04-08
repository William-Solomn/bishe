import request from '@/utils/request'
import { getToken, getUserInfo } from '@/utils/auth'

const userInfo = getUserInfo()
const adminId = userInfo.id
const token = getToken()

export function getWaiters() {
  return request({
    url: '/user/admin/find/worker',
    method: 'post',
    header: {
      Authorization: `restaurant_${token}`
    },
    data: {
      restaurantId: '1',
      id: null,
      name: null
    }
  })
}
export function getManages() {
  return request({
    url: '/user/admin/find/manager',
    method: 'post',
    header: {
      Authorization: `restaurant_${token}`
    },
    data: {
      restaurantId: '1',
      adminId
    }
  })
}

export function addWorker(peopleInfo) {
  return request({
    url: '/user/admin/add/worker',
    method: 'put',
    header: {
      Authorization: `restaurant_${token}`
    },
    data: {
      restaurantId: '1',
      ...peopleInfo
    }
  })
}

export function addManager(peopleInfo) {
  return request({
    url: '/user/admin/add/manager',
    method: 'put',
    header: {
      Authorization: `restaurant_${token}`
    },
    data: {
      id: '1',
      adminId,
      ...peopleInfo
    }
  })
}

export function deleteWorker(id) {
  return request({
    url: '/user/admin/delete/worker',
    method: 'delete',
    header: {
      Authorization: `restaurant_${token}`
    },
    data: {
      restaurantId: '1',
      workerId: id
    }
  })
}

export function deleteManager(id) {
  return request({
    url: '/user/admin/delete/manager',
    method: 'delete',
    header: {
      Authorization: `restaurant_${token}`
    },
    data: {
      restaurantId: '1',
      adminId
    }
  })
}

export function emitWorker(info) {
  const userInfo = getUserInfo()
  const adminId = userInfo.id
  const { managerId, name, phone, password } = info
  const workerId = info.id
  return request({
    url: '/user/admin/modify/worker',
    method: 'post',
    header: {
      Authorization: `restaurant_${token}`
    },
    data: {
      restaurantId: '1',
      managerId: managerId || adminId,
      name,
      phone,
      password: password || null,
      workerId
    }
  })
}

export function emitManager(info) {
  const userInfo = getUserInfo()
  const adminId = userInfo.id
  const { name, phone, password } = info
  return request({
    url: '/user/admin/modify/manager',
    method: 'post',
    header: {
      Authorization: `restaurant_${token}`
    },
    data: {
      restaurantId: '1',
      adminId,
      name,
      phone,
      password: password || null
    }
  })
}
