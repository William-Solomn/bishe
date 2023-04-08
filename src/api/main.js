import { requestGet, requestPost } from '@/api/base'

export function getAllParkingInfo(){
    return requestGet(`/parkinginfo/getAllParking`)
}

export function postParkingInfo(info){
    return requestPost(`/parkinginfo/updateParking`,info)
}

export function getUserInfoByName(user_name){
    return requestPost(`/userinfo/getUserByName`, {
        user_name: user_name
      })
}

export function getParkingCounts(){
    return requestGet(`/parkinginfo/getParkingCounts`)
}