import { requestPost, requestPut } from '@/api/base'

export async function getAbout(id, foodId) {
  let list
  await requestPost(id, '/meal/relate/query/meal', {
    mealId: foodId
  }).then(resp => {
    list = resp.data
  })
  return list
}

export async function setAbout(info) {
  return requestPut(1, '/meal/relate/modify', info)
}
