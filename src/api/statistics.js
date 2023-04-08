import { requestPost } from '@/api/base'

const key = 'CACHE_DAY_DATA'
const interval = 600000
// const interval = 60

export const hoursArray = [
  '00:00', '01:00', '02:00', '03:00', '04:00', '05:00',
  '06:00', '07:00', '08:00', '09:00', '10:00', '11:00',
  '12:00', '13:00', '14:00', '15:00', '16:00', '17:00',
  '18:00', '19:00', '20:00', '21:00', '22:00', '23:00'
]

export async function getDayDataNoCache(id) {
  const dateTime = new Date()
  const dataResult = (await requestPost(id, '/order/statistics/daily', {
    time: dateTime.toLocaleDateString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit'
    }).replaceAll('/', '-')
  })).data

  if (dataResult['flag']) {
    const cacheDayData = {
      cacheTime: dateTime,
      data: dataResult
    }

    console.log('Refresh data:', JSON.stringify(cacheDayData))
    localStorage.setItem(key, JSON.stringify(cacheDayData))

    return cacheDayData
  } else {
    console.log('Refresh data error')
  }
}

export async function getDayData(id) {
  const dateTime = new Date()

  let cacheDayData = JSON.parse(localStorage.getItem(key))
  console.log('Reading cache:', JSON.stringify(cacheDayData))

  if (!cacheDayData || dateTime - new Date(cacheDayData.cacheTime) > interval) {
    cacheDayData = await getDayDataNoCache(id)
  }
  return cacheDayData.data
}

export async function getDayCustomerData(id) {
  return (await getDayData(id)).data['volume']
}

export async function getDaySealData(id) {
  return (await getDayData(id)).data['turnover']
}

export async function getDayFoodData(id) {
  const result = (await getDayData(id)).data

  const foodList = result['meal']
    .map(c => ({ name: c['meal'].name, sales: c.sales }))
    .concat(result['combo']
      .map(c => ({ name: c['combo'].name, sales: c.sales })))
    .sort((a, b) => b.sales - a.sales)

  return {
    nameList: foodList.map(ele => ele['name']),
    dataList: foodList.map(ele => ele['sales'])
  }
}
