import Cookies from 'js-cookie'

const TokenKey = 'token'
const RoleKey = 'role'
const UserInfoKey = 'info'
const RoutesKey = 'routes'
// const RestaurantKey = 'restaurant'

export function getToken() {
  const token = sessionStorage.getItem(TokenKey)
  return token
}

export function setToken(token) {
  const a = sessionStorage.setItem(TokenKey, token)
  return a
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getRole() {
  const role = sessionStorage.getItem(RoleKey)
  return role
}

export function setRole(role) {
  return sessionStorage.setItem(RoleKey, role)
}

export function getUserInfo() {
  const info = sessionStorage.getItem(UserInfoKey)
  return JSON.parse(info)
}

export function setUserInfo(info) {
  return sessionStorage.setItem(UserInfoKey, JSON.stringify(info))
}

export function getRoutes() {
  return sessionStorage.get(RoutesKey)
}

export function setRoutes(routes) {
  return sessionStorage.set(RoutesKey, routes)
}

export function setRestaurantInfo(info) {
  // return sessionStorage.setItem(RestaurantKey, id)
}

export function getRestaurantInfo() {
  // return sessionStorage.getItem(RestaurantKey)
  return {
    restaurantId: 1
  }
}

