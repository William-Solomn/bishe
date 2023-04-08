import { requestDelete, requestGetList, requestPost, requestPut } from '@/api/base'

const routeName = 'meal'

export function addCat(id, catName) {
  return requestPost(id, '/meal/category/add', {
    name: catName
  })
}

export function deleteCat(id, catId) {
  return requestDelete(id, '/meal/category/delete', {
    id: catId
  })
}

export function getCatList(id) {
  return requestGetList(id, 1, 9999, routeName, 'category')
}

export function addFood(id, foodItem) {
  return requestPost(id, '/meal/meal/add', foodItem)
}

export function updateFood(id, foodItem) {
  return requestPut(id, '/meal/meal/update', foodItem)
}

export function deleteFood(id, foodId) {
  return requestDelete(id, '/meal/meal/delete', {
    id: foodId
  })
}

export function getFoodList(id, spell, name) {
  return requestPost(id, '/meal/meal/list', {
    spell: spell,
    name: name
  })
}

export function getFoodDetail(id, mealId) {
  return requestPost(id, '/meal/meal/info', {
    mealId: mealId
  })
}

export async function getFoodPicture(id, foodId) {
  let list
  await requestPost(id, '/meal/file/meal', {
    mealId: foodId
  }).then(resp => {
    list = resp.data
  })

  if (list.data.length === 0) {
    return []
  }

  const result = []

  if (list.data[0]) {
    result.push({
      name: list.data[0]['fileId'],
      id: list.data[0]['id'],
      url: `https://bfjdkm.com:9002/file/download/id/${list.data[0]['fileId']}`
    })
  }
  if (list.data[1]) {
    result.push({
      name: list.data[1]['fileId'],
      id: list.data[1]['id'],
      url: `https://bfjdkm.com:9002/file/download/id/${list.data[1]['fileId']}`
    })
  }
  if (list.data[2]) {
    result.push({
      name: list.data[2]['fileId'],
      id: list.data[2]['id'],
      url: `https://bfjdkm.com:9002/file/download/id/${list.data[2]['fileId']}`
    })
  }

  return result
}

export function addFoodPicture(id, mealId, picAddList, picRemoveList) {
  return requestPut(id, '/meal/file/meal', {
    mealId: mealId,
    addList: picAddList,
    removeList: picRemoveList
  })
}

export function addMaterial(id, name) {
  return requestPost(id, '/meal/material/add', {
    name: name
  })
}

export function getMaterial(id) {
  return requestPost(id, '/meal/material/list', {
    id: id,
    num: 1,
    size: 9999
  })
}

export function deleteMaterial(id, materialId) {
  return requestDelete(id, '/meal/material/delete', {
    id: materialId
  })
}

export async function getComboList(id) {
  return requestPost(id, '/meal/combo/list')
    .then(resp => {
      return resp.data
    })
}

export function addCombo(id, comboItem) {
  return requestPost(id, '/meal/combo/add', comboItem)
}

export function addComboPicture(id, comboId, picAddList, picRemoveList) {
  return requestPut(id, '/meal/file/combo', {
    comboId: comboId,
    addList: picAddList,
    removeList: picRemoveList
  })
}

export function deleteCombo(id, comboId) {
  return requestDelete(id, '/meal/combo/delete', {
    id: comboId
  })
}

export function updateCombo(id, comboItem) {
  return requestPut(id, '/meal/combo/update', comboItem)
}

export async function getComboPicture(id, comboId) {
  let list
  await requestPost(id, '/meal/file/combo', {
    comboId: comboId
  }).then(resp => {
    list = resp.data
  })

  if (list.data.length === 0) {
    return []
  }

  const result = []

  if (list.data[0]) {
    result.push({
      name: list.data[0]['fileId'],
      id: list.data[0]['id'],
      url: `https://bfjdkm.com:9002/file/download/id/${list.data[0]['fileId']}`
    })
  }
  if (list.data[1]) {
    result.push({
      name: list.data[1]['fileId'],
      id: list.data[1]['id'],
      url: `https://bfjdkm.com:9002/file/download/id/${list.data[1]['fileId']}`
    })
  }
  if (list.data[2]) {
    result.push({
      name: list.data[2]['fileId'],
      id: list.data[2]['id'],
      url: `https://bfjdkm.com:9002/file/download/id/${list.data[2]['fileId']}`
    })
  }

  return result
}
