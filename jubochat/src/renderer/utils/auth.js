// import Cookies from 'js-cookie'

// const tokenKey = 'initToken'
// sessionStorage.setItem('token', 'initToken')

export function getToken () {
  return sessionStorage.getItem('token')
}

export function setToken (token) {
  return sessionStorage.setItem('token', token)
}

export function removeToken () {
  return sessionStorage.removeItem('token')
  // return Cookies.remove(tokenKey)
}
