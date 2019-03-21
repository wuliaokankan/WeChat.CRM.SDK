import fetch from '@/utils/fetch'

export function addMobileByIMEI (mobile) {
  console.log('这里是api_>mobile.js_>addMobileByIMEI')
  console.log(mobile)
  return fetch.post('/api/mobile/add', mobile)
}

export function queryMobile () {
  console.log('api/mobile/queryMobile')
  return fetch.get('/api/mobile/query')
}

export function removeMobileByIMEI (mobile) {
  console.log('这里是api_>mobile.js_>removeMobileByIMEI')
  return fetch.post('/api/mobile/remove', mobile)
  // console.log('这里是api_>getUserInfo')
  // return fetch.get('/api/info', { token: token })
}
