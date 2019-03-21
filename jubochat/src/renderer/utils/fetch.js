// 引入axios
import axios from 'axios'
import qs from 'qs'
import store from '@/store'
// import { getToken } from '@/utils/auth'

// 创建axios实例
const httpService = axios.create({
  // url前缀-'https://some-domain.com/api/'
  //   baseURL: process.env.BASE_API, // 需自定义
  // 请求超时时间
  timeout: 10000 // 需自定义
})

// request拦截器
httpService.interceptors.request.use(
  config => {
    // 根据条件加入token-安全携带
    if (store.getters.token) {
      // 需自定义
      // 让每个请求携带token
      //   config.headers.Authorization = `token ${store.state.token}`;
      // console.log('有token')
      config.headers.Authorization = sessionStorage.getItem('token')
    }
    return config
  },
  error => {
    // 请求错误处理
    return Promise.reject(error)
  }
)

// respone拦截器
httpService.interceptors.response.use(
  response => {
    return response
    // 统一处理状态
    // const res = response.data
    // if (res.statuscode !== 1) {
    //   // 需自定义
    //   let error = {
    //     status: res.statuscode,
    //     message: res.message
    //   }
    //   // 返回异常
    //   return Promise.reject(error)
    // } else {
    //   return response.data
    // }
  },
  // 处理处理
  error => {
    if (error && error.response) {
      switch (error.response.status) {
        case 400:
          error.message = '错误请求'
          break
        case 401:
          error.message = '未授权，请重新登录'
          break
        case 403:
          error.message = '拒绝访问'
          break
        case 404:
          error.message = '请求错误,未找到该资源'
          break
        case 405:
          error.message = '请求方法未允许'
          break
        case 408:
          error.message = '请求超时'
          break
        case 500:
          error.message = '服务器端出错'
          break
        case 501:
          error.message = '网络未实现'
          break
        case 502:
          error.message = '网络错误'
          break
        case 503:
          error.message = '服务不可用'
          break
        case 504:
          error.message = '网络超时'
          break
        case 505:
          error.message = 'http版本不支持该请求'
          break
        default:
          error.message = `未知错误${error.response.status}`
      }
    } else {
      error.message = '网络链接到服务器失败 请检查网络'
    }
    return Promise.reject(error)
  }
)

/* 网络请求部分 */

/*
 *  get请求
 *  url:请求地址
 *  params:参数
 * */
export function get (url, params = {}) {
  return new Promise((resolve, reject) => {
    httpService({
      url: url,
      method: 'get',
      params: params
    })
      .then(response => {
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

/*
 *  post请求
 *  url:请求地址
 *  params:参数
 * */
export function post (url, params = {}) {
  return new Promise((resolve, reject) => {
    httpService({
      url: url,
      method: 'post',
      data: qs.stringify(params)
    })
      .then(response => {
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

/*
 *  文件上传
 *  url:请求地址
 *  params:参数
 * */
export function fileUpload (url, params = {}) {
  return new Promise((resolve, reject) => {
    httpService({
      url: url,
      method: 'post',
      data: params,
      headers: { 'Content-Type': 'multipart/form-data' }
    })
      .then(response => {
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

export default {
  get,
  post,
  fileUpload
}
