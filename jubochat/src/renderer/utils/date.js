let date = new Date()
function getDate () {
  let date2 = {
    year: date.getFullYear(),
    month: date.getMonth() + 1,
    day: date.getDate(),
    hour: date.getHours(),
    minute: date.getMinutes(),
    second: date.getSeconds()
  }
  return date2
}

// 返回 1970 年 1 月 1 日至今的毫秒数。
function getTime () {
  return date.getTime()
}

/**
 * 获取当天0点的时间戳
 * @param {*} time
 */
function getDayTime () {
  let times = date.getTime()
  let hour = date.getHours()
  let minute = date.getMinutes()
  let second = date.getSeconds()
  let dayTime = times - hour * 3600 * 1000 - minute * 60 * 1000 - second * 1000
  return dayTime
}

/**
 * getTime()转化为2014-04-23 18:55:49
 * time 毫秒
 */
function transformTime (time) {
  // console.log(time)
  let date = new Date(Number(time))
  console.log(date)
  let Y = date.getFullYear() + '-'
  console.log(Y)
  let M =
    (date.getMonth() + 1 < 10
      ? '0' + (date.getMonth() + 1)
      : date.getMonth() + 1) + '-'
  let D = date.getDate() + ' '
  let h = date.getHours() + ':'
  let m = date.getMinutes() + ':'
  let s = date.getSeconds()
  console.log(Y + M + D + h + m + s)
  return Y + M + D + h + m + s
}

export default {
  getDate,
  getTime,
  transformTime,
  getDayTime
}
