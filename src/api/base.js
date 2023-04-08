import request from '@/utils/request'

export function requestGet(url, reqBody) {
  return request({
    url: url,
    method: 'get',
    data: reqBody
  })
}

export function requestPost(url, reqBody) {

  return request({
    url: url,
    method: 'post',
    data: reqBody
  })
}

export function requestPut(id, url, reqBody) {
  if (!reqBody) {
    reqBody = {
      restaurantId: id
    }
  } else if (!reqBody.restaurantId) {
    reqBody.restaurantId = id
  }

  return request({
    url: url,
    method: 'put',
    data: reqBody
  })
}

export function requestAdd(id, routeName, name, reqBody) {
  if (!reqBody) {
    reqBody = {
      restaurantId: id
    }
  } else if (!reqBody.restaurantId) {
    reqBody.restaurantId = id
  }

  return request({
    url: `/${routeName}/${name}/add`,
    method: 'post',
    data: reqBody
  })
}

export function requestDelete(id, url, reqBody) {
  if (!reqBody) {
    reqBody = {
      restaurantId: id
    }
  } else if (!reqBody.restaurantId) {
    reqBody.restaurantId = id
  }

  return request({
    url: url,
    method: 'delete',
    data: reqBody
  })
}

export function requestGetList(id, page, limit, routeName, name, reqBody) {
  let url = `/${routeName}/`
  if (name) {
    url += `${name}/`
  }

  return request({
    url: `${url}list`,
    method: 'post',
    data: !reqBody ? {
      num: page,
      size: limit,
      restaurantId: id
    } : reqBody
  })
}
