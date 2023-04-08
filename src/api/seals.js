import { requestDelete, requestPost, requestPut } from '@/api/base'

export async function getDiscountList(id) {
  return requestPost(id, `discount_event/restaurant/${id}`, {
    pagenum: 1,
    pagesize: 9999
  })
}

export async function updateDiscount(id, ruleId, ruleItem) {
  const reqBody = {
    consumption: ruleItem.consumption,
    discount: ruleItem.discount,
    active: ruleItem.active,
    restaurant_id: id,
    rate: 1
  }
  return requestPut(id, `discount_event/${ruleId}`, reqBody)
}

export async function deleteDiscount(id, ruleId) {
  return requestDelete(id, `discount_event/${ruleId}`)
}

export async function addDiscount(id, price, discount) {
  return requestPost(id, 'discount_event', {
    consumption: price,
    discount: discount,
    restaurant_id: id,
    rate: 1
  })
}

export async function getFreebiesList(id) {
  return requestPost(id, 'discount_event/give/list')
}

export async function deleteFreebies(id, fid) {
  return requestDelete(id, 'discount_event/give/delete', {
    id: fid
  })
}

export async function addFreebies(id, price, itemList) {
  const reqBody = {
    consumption: price,
    items: itemList
  }
  return requestPut(id, 'discount_event/give/add', reqBody)
}

export async function updateFreebiesActive(id, ruleId, status) {
  return requestPost(id, 'discount_event/give/active', {
    id: ruleId,
    active: status
  })
}
