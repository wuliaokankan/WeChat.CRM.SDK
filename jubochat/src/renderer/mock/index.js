import Mock from 'mockjs'
import loginAPI from './login'
import mobileAPI from './mobile'
import wechatAPI from './wechat'

// Mock.setup({
//   timeout: '350-600'
// })

// 登录相关
Mock.mock('/api/login', 'post', loginAPI.loginByUsername)
Mock.mock(RegExp('/api/info' + '.*'), 'get', loginAPI.getUserInfo)
Mock.mock(/\/api\/logout/, 'post', loginAPI.logout)

// 手机相关
Mock.mock('/api/mobile/add', 'post', mobileAPI.addMobile)
Mock.mock('/api/mobile/remove', 'post', mobileAPI.removeMobile)
Mock.mock(RegExp('/api/mobile/query' + '*'), 'get', mobileAPI.queryMobile)

// 微信相关
Mock.mock(RegExp('/api/wechat/query' + '*'), 'get', wechatAPI.queryWechat)
Mock.mock(RegExp('/api/wechat/friends' + '*'), 'get', wechatAPI.queryFriends)
Mock.mock('/api/wechat/modifyWechatStatus', 'post', wechatAPI.modifyWechatStatus)
Mock.mock('/api/wechat/queryNewFriends', 'post', wechatAPI.queryNewFriends)
// Mock.mock('/api/wechat/friends', 'post', wechatAPI.queryFriends)
// Mock.mock(
//   RegExp('/api/wechat/unreadMessages' + '*'),
//   'get',
//   wechatAPI.queryUnreadMessages
// )
// Mock.mock(
//   RegExp('/api/wechat/receptions' + '*'),
//   'get',
//   wechatAPI.queryReceptionsToday
// )
// // 发送信息
// Mock.mock('/api/wechat/send', 'post', wechatAPI.sendMessages)

export default Mock
