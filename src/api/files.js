import { requestGet } from '@/api/base'

export function getFileById(fileId) {
  return requestGet(`/file/download/id/${fileId}`)
}

export function getFileByName(fileName) {
  return requestGet(`/file/download/name/${fileName}`)
}

export function downloadFileById(fileId, mime, label) {
  getFileById(fileId)
    .then(resp => {
      const blob = new Blob([resp.data], { type: mime })
      const element = document.createElement('a')
      element.href = URL.createObjectURL(blob)
      element.download = label
      element.click()
      URL.revokeObjectURL(element.href)
    })
}

export function downloadFileByName(fileId, mime, label) {
  getFileByName(fileId)
    .then(resp => {
      const blob = new Blob([resp.data], { type: mime })
      const element = document.createElement('a')
      element.href = URL.createObjectURL(blob)
      element.download = label
      element.click()
      URL.revokeObjectURL(element.href)
    })
}
