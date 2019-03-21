import { param2Obj } from '@/utils'
import QS from 'qs'

let user = [
  {
    userName: 'jubo', // 用户名
    password: 'jubo123456', // 密码
    token: 'jubo' // token
  }
]

let userInfo = [
  {
    id: 1, // 用户id
    userName: 'jubo', // 用户名
    // password: 'jubo123456', // 密码
    // token: 'jubo', // token
    status: 1, // 状态 1:激活,0是失效
    nickname: '', // 昵称
    avatar: '', // 头像
    introduction: '', // 介绍
    roles: 1, // 角色 1:是管理员 2:是操作员
    maxMobile: 10, // 可绑定的手机
    // mobiles: [], // 已经绑定的手机
    children: [], // 子账户
    parent: null // 父账号
  }
]

export default {
  loginByUsername: options => {
    // console.log('这里是mock_loginbyusername')
    let data = QS.parse(options.body)
    // console.log(data.username)
    // console.log(data.password)
    // console.log(user[0].userName)
    // console.log(user[0].password)
    // console.log(options)
    if (
      data.username === user[0].userName &&
      data.password === user[0].password
    ) {
      options.statuscode = 1 // 账号密码正确
      options.message = 'success'
      options.token = user[0].token
      // console.log('用户名密码正确')
      // console.log(options.token)
      return options
    } else {
      options.statuscode = 20000
      options.message = 'false'
      return options
    }
  },

  getUserInfo: options => {
    // console.log('mock_getUserInfo')
    const token = param2Obj(options.url)
    if (token.token === user[0].token) {
      // console.log('token 验证通过')
      return userInfo[0]
    } else {
      // console.log(token)
      options.statuscode = 20001
      options.message = 'token错误或失效'
      return options
    }
  },

  logout: () => 'success'
}
