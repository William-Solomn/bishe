import { requestGet, requestPost } from '@/api/base'

export function getAllParkingInfo(){
    return requestGet(`/parkinginfo/getAllParking`)
}

export function postParkingInfo(info){
    return requestPost(`/parkinginfo/addParking`,info)
}