import { requestGet, requestPost } from '@/api/base'

export function getAllCarInfo() {
    return requestGet(`/carinfo/getAllCarInfo`)
  }

export function postCarInfo(carinfo) {
    return requestPost(`/carinfo/addCarInfo`,carinfo)
}