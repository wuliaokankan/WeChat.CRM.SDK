import fetch from '@/utils/fetch'

export function queryWechat () {
  return fetch.get('/api/wechat/query')
}
export function modifyWechatStatus (wechat) {
  return fetch.post('/api/wechat/modifyWechatStatus', wechat)
}
export function queryNewFriends () {
  return fetch.post('/api/wechat/queryNewFriends')
}
// export function queryFriends () {
//   // return fetch.post('/api/wechat/friends', wechat)
//   return fetch.get('/api/wechat/friends')
// }
