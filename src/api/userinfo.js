
import { requestGet, requestPost } from '@/api/base'

export function getAllUser(){
    return requestGet(`/userinfo/getAllUserInfo`)
}

export function postAddUser(userinfo){
    return requestPost(`/userinfo/addUser`,userinfo)
}