import { requestDelete, requestPost, requestPut } from '@/api/base'

export function getTableList(id) {
  const req = {
    num: 1,
    size: 9999,
    rid: id
  }

  return requestPost(-1, '/table/list', req)
}

export function addTable(id, tableItem) {
  if (!tableItem.rid) {
    tableItem.rid = id
  }

  return requestPost(id, '/table/add', tableItem)
}

export function deleteTable(id, tableId) {
  return requestDelete(id, '/table/delete', {
    rid: id,
    tableId: tableId
  })
}

export function updateTable(id, tableItem) {
  if (!tableItem.rid) {
    tableItem.rid = id
  }

  return requestPut(id, '/table/update', tableItem)
}

export function getAreaList(id) {
  return requestPost(id, '/table/type/query')
}

export function addArea(id, name) {
  return requestPut(id, '/table/type/add', { name: name })
}

export function deleteArea(id, areaId) {
  return requestDelete(id, '/table/type/delete', {
    id: areaId
  })
}
