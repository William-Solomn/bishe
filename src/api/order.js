import { requestPost } from '@/api/base'

export function GetBillingOrderList(id) {
  return requestPost(id, '/order/list', {
    num: 1,
    size: 9999,
    credit: false
  })
}

export function GetOrderDetail(id, orderNo) {
  return requestPost(id, '/order/order', {
    orderNo: orderNo
  })
}

export function UpdateTable(id, orderId, tableId) {
  return requestPost(id, '/order/table', {
    orderId: orderId,
    tableId: tableId
  })
}

export function UpdateMealOrComboFinish(id, orderId, orderNo, mealId, comboId) {
  const reqBody = {
    id: orderId,
    orderNo: orderNo,
    meals: [],
    combos: []
  }

  if (mealId) {
    reqBody['meals'].push({ id: mealId, count: 1 })
  } else if (comboId) {
    reqBody['combos'].push({ id: comboId, count: 1 })
  }

  return requestPost(id, '/order/submit', reqBody)
}

export function UpdateMealCancel(id, orderNo, mealId) {
  return requestPost(id, '/order/remove/direct', {
    orderNo: orderNo,
    meals: [{
      id: mealId,
      count: 1
    }]
  })
}

export function GetCreditOrderList(id) {
  return requestPost(id, '/order/list', {
    num: 1,
    size: 9999,
    credit: true
  })
}

export function GetScheduledOrderList(id) {
  return requestPost(id, '/order/reserve/list', {
    num: 1,
    size: 9999
  })
}

export function addFoodToOrder(id, orderId, orderNo, meals, combos) {
  const req = {
    id: orderId,
    orderNo: orderNo,
    meals: meals,
    combos: combos
  }

  return requestPost(id, '/order/add/direct', req)
}

export function deleteFoodOfOrder(id, orderId, orderNo, meals, combos) {
  const req = {
    id: orderId,
    orderNo: orderNo,
    meals: meals,
    combos: combos
  }

  return requestPost(id, '/order/remove/direct', req)
}

export function FinishCreditOrder(id, orderNo, creditNo) {
  const reqBody = {
    orderNo: orderNo,
    creditId: creditNo,
    state: 1
  }

  return requestPost(id, '/order/credit/finish', reqBody)
}
