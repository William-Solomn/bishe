import request from '@/utils/request'

export async function getQrCode(id, tableId) {
  const key = `QRCODE_${id}_${tableId}`
  return requestQrCode(id, tableId).then(res => {
    const stored = res
    localStorage.setItem(key, stored)
    return res
  })
}

export async function requestQrCode(id, tableId) {
  return request({
    url: '/open/wx/getwxacodeunlimit',
    method: 'get',
    params: {
      scene: `${id};${tableId}`,
      page: 'pages/diancan/diancan'
    }
  })
}
