import QS from 'qs'
var Mock = require('mockjs')

let imeis = [
  { imei: 111111111111110, available: 0 },
  { imei: 111111111111111, available: 1 },
  { imei: 111111111111112, available: 1 },
  { imei: 111111111111113, available: 1 },
  { imei: 111111111111114, available: 1 },
  { imei: 111111111111115, available: 1 },
  { imei: 111111111111116, available: 1 },
  { imei: 111111111111117, available: 1 },
  { imei: 111111111111118, available: 1 },
  { imei: 111111111111119, available: 1 },
  { imei: 111111111111120, available: 1 }
] // 1表示可用

// 查询imei
function queryIMEI (imei) {
  for (const iterator of imeis) {
    if (Number(imei) === iterator.imei) {
      if (iterator.available === 1) {
        return 1 // imei可用
      } else {
        return 2 // imei已经使用
      }
    }
  }
  return 3 // imei不匹配
}

function generateMobiles () {
  let mobiles = Mock.mock({
    'mobiles|1-10': [
      {
        'id|+1': 1,
        imei: '@integer(100000000000000,999999999999999)', // 头像
        alias: '测试@name()', // 昵称
        user: '@cname()', // 备注名
        createdTime: '@now()',
        wechatNumber: '@string(6,20)',
        isDelete: 'false'
      }
    ]
  })
  localStorage.setItem('mobiles', JSON.stringify(mobiles.mobiles))
  return mobiles
}

export default {
  addMobile: options => {
    console.log('mobile.js--addMobile')
    let data = QS.parse(options.body)
    console.log(data)
    // 首先校验添加数是否已达到最大
    let mobiles = JSON.parse(localStorage.getItem('mobiles'))
    if (mobiles.length >= 10) {
      console.log('已经添加10个')
      options.statuscode = 20005
      options.message = '已经添加10个不能再添加'
      return options
    }
    // 查看imei是否 存在并且可用
    let result = queryIMEI(data.imei)
    // 查看imei是否使用过
    if (result === 1) {
      console.log('imei存在并且可用')
      let localMobile = JSON.parse(localStorage.getItem('mobiles'))
      localMobile.push(data)
      localStorage.setItem('mobiles', JSON.stringify(localMobile))
      options.statuscode = 1
      options.message = '添加成功'
      options.body = localMobile
      console.log(options)
      return options
    } else if (result === 2) {
      console.log('imei已经使用过')
      options.statuscode = 20003
      options.message = 'imei已经使用过'
      // console.log(options)
      return options
    } else {
      console.log('imei不匹配')
      options.statuscode = 20004
      options.message = 'imei不匹配'
      return options
    }
  },

  queryMobile: options => {
    console.log('mock/mobile/queryMobile')
    let mobiles = generateMobiles()
    // localStorage.setItem('mobiles', JSON.stringify(mobiles.mobiles))
    options.body = mobiles
    console.log(mobiles)
    return options
  },

  removeMobile: options => {
    console.log('mockjs removeMobile')
    let mobilesRemove = QS.parse(options.body) // 客服端传来的要删除的手机
    let mobilesNow = JSON.parse(localStorage.getItem('mobiles')) // 该用户已经绑定的手机
    console.log(mobilesRemove[0])
    for (const index in mobilesRemove) {
      // 遍历要删除的imei
      for (const key in mobilesNow) {
        // console.log(key)
        if (mobilesNow[key].imei === mobilesRemove[index].imei) {
          // delete mobilesNow[key] //删除后会返回null 占据位置
          mobilesNow.splice(key, 1)
          break
        }
      }
    }
    options.body = mobilesNow
    options.statuscode = 1
    options.message = '删除成功'
    return options
  }
}
