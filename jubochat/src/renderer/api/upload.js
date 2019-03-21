import fetch from '@/utils/fetch'

export function uploadFile (formData) {
  // console.log('这里是api_>upload.js')
  return fetch.fileUpload('http://www.jjldkj.com:10089/fileUpload', formData)
}

// export function logout () {
//   return fetch.post('/api/logout')
// }

// export function getUserInfo (token) {
//   console.log('这里是api_>getUserInfo')
//   return fetch.get('/api/info', { token: token })
// }

// export function updataUserInfo (info) {
//   console.log('这里是api_>updataUserInfo')
//   return fetch.post('/api/user/updata', info)
// }
