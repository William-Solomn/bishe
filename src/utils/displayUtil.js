export function handleCurrent(num) {
  if (isNaN(num)) {
    num = 0
  }
  return num.toLocaleString('zh-CN', {
    style: 'currency',
    currency: 'CNY',
    maximumFractionDigits: 1
  })
}
