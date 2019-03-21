// 引入mockjs
const Mock = require('mockjs')
// 使用mockjs模拟数据
Mock.mock('/api/data', {
  'foods|10-50': [
    {
      name: '@ctitle(2,10)',
      img: "@image('600x600',#b7ef7c)",
      brief: '@csentence(1,50)',
      'price|0-20.0-2': 1,
      num: 0,
      minusFlag: true,
      time: '@time',
      'peisongfei|0-100.0-2': 1,
      'limit|0-50': 1
    }
  ]
})
Mock.setup({
  timeout: 2000 // 请求两秒后才加载数据
})
