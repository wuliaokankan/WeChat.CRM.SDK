// import QS from 'qs'
var Mock = require('mockjs')

// let label = Mock.mock({
//   'label|1-10': [
//     {
//       label: '@cname'
//     }
//   ] // 标签
// })

// 根据手机号码 生成微信号
function generateWechats (mobiles = []) {
  console.log('generateWechats')
  if (!mobiles) {
    return mobiles
  }
  let wechats = []
  for (const mobile of mobiles) {
    let wechat = {
      imei: mobile.imei,
      alias: mobile.alias,
      portrait: Mock.Random.image('50x50', Mock.Random.color()),
      nickname: Mock.Random.cname(),
      wechatNumber: Mock.Random.string(6, 20),
      QRcode: Mock.Random.image('50x50', Mock.Random.color()),
      sex: Mock.Random.integer(0, 1),
      area: Mock.Random.cname(4),
      declaration: Mock.Random.paragraph(0, 50),
      phoneNumber: Mock.Random.integer(13000000000, 18888888888),
      status: Mock.Random.integer(0, 1)
      // memoName: Mock.Random.cname(),
      // describe: Mock.Random.paragraph(3),
      // additionalPicture: '',
      // source: Mock.Random.string('搜索群面对面', 0, 3),
      // label: label
    }
    wechats.push(wechat)
  }
  return wechats
}
// function generateFriends () {
//   console.log('给绑定的手机微信号，随机生成微信好友')
//   let friends = {
//     '111111111111111': Mock.mock({
//       'friends|1-100': [
//         {
//           'id|+1': 1,
//           portrait: '@image("50x50","@color()")', // 头像
//           nickname: '@name()', // 昵称
//           memoName: '@cname()', // 备注名
//           wechatNumber: '@string("abcdefghijklmnopqrstuvwxyz0123456789",6,20)', // 微信号
//           declaration: '@paragraph(0, 50)', // 个性签名
//           phoneNumber: '@integer(13000000000, 18888888888)', // 电话号码
//           describe: '@paragraph(3)', // 描述
//           // additionalPicture: '', // 附加图片
//           sex: '@integer(0, 1)', // 性别 0男 1女
//           area: '@cname(4)', // 地区
//           QRcoe: '@image("50x50", @color())', // 二维码 无法获取好友的二维码
//           source: '@string("搜索群面对面", 0, 3)', // 来源
//           label: label // 标签
//         }
//       ]
//     })
//   }
//   return friends
// }

// newFriends
function generateNewFriends (wechats) {
  let newFriends = []
  for (const wechat of wechats) {
    // console.log(wechat)
    let newFriends2 = Mock.mock({
      'newFriends|1-10': [
        {
          'id|+1': 1,
          pwechat: wechat.wechatNumber,
          imei: wechat.imei,
          avator: '@image("50x50","@color()")', // 头像
          nickname: '@cname', // 昵称
          discribe: '@paragraph(1)', // 描述
          status: '@integer(0, 1)' // 未添加 已添加
        }
      ]
    })
    newFriends.push(newFriends2)
  }
  // console.log(newFriends)
  return newFriends
}

export default {
  // 查询微信
  queryWechat: options => {
    console.log('mockjs wechat queryWechat')
    let mobiles = JSON.parse(localStorage.getItem('mobiles'))
    let wechats = {}
    wechats['wechats'] = generateWechats(mobiles)
    if (wechats) {
      options.body = wechats
      options.statuscode = 1
    } else {
      options.statuscode = 20010
      options.message = '没有绑定手机'
    }
    return options
  },
  // 查询新的朋友
  queryNewFriends: options => {
    console.log('查询新的朋友')
    let wechats = JSON.parse(localStorage.getItem('wechats'))
    let newFriends = {}
    newFriends['newFriends'] = generateNewFriends(wechats)
    options.body = newFriends
    return options
  }
}
