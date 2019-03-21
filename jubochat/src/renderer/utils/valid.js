export function validName (username) {
  // 匹配规则：汉字、数字、字母、下划线，不能以下划线开头和结尾
  var re = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\-\u4e00-\u9fa5]{1,20}$/
  return re.test(username)
}

export function validPassword (password) {
  var re = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]{6,20}$/
  return re.test(password)
}

export function validMessages (messages) {
  // console.log(messages)
  // 匹配规则：任意字符 不能为空
  var re = /\S/
  // console.log(re.test(messages))
  return re.test(messages)
}

/**
 * 匹配图片
 * @param {*} path
 */
export function validPicture (path) {
  // console.log(path)
  let reg = /\.png$|\.jpg$|\.gif$|\.jpeg$/gi
  return reg.test(path.toLowerCase())
}

/**
 * 匹配视频
 * @param {*} path
 */
export function validVideo (path) {
  // console.log(path)
  let reg = /\.mp4$/gi
  return reg.test(path.toLowerCase())
}
