import fetch from '@/utils/fetch'

export function loginByUsername (loginForm) {
  console.log('这里是api_>login.js')
  return fetch.post('/api/login', loginForm)
}

export function logout () {
  return fetch.post('/api/logout')
}

export function getUserInfo (token) {
  console.log('这里是api_>getUserInfo')
  return fetch.get('/api/info', { token: token })
}

export function updataUserInfo (info) {
  console.log('这里是api_>updataUserInfo')
  return fetch.post('/api/user/updata', info)
}
