import * as types from './mutation-types'
import { uploadFile } from '@/api/upload'
import date from '../utils/date'
let day = date.getDayTime()

export default {
  // 设置窗口高度
  SetScrrentHeight ({ commit }, height) {
    commit(types.SET_SCRRENT_HEIGHT, height)
  },
  // 登陆状态
  SetIslogin ({ commit }, flag) {
    commit(types.SET_ISLOGIN, flag)
  },
  // 设置当前用户
  SetUser ({ commit }, user) {
    commit(types.SET_USER, user)
  },
  // 设置当前账号绑定的微信列表
  SetWechats ({ commit }, wechats) {
    commit(types.SET_WECHATS, wechats)
  },
  //  设置登陆的微信
  SetWechatsLogin ({ commit }, wechatsLogin) {
    commit(types.SET_WECHATS_LOGIN, wechatsLogin)
  },
  // 设置当前选中的微信
  SetCurrentWechat ({ commit }, wechat) {
    commit(types.SET_CUREENT_WECHAT, wechat)
  },
  // 设置当前选中的好友的信息
  SetPersonalInfo ({ commit, getters }, info) {
    let db = getters.db
    let friendId = info.friendId
    let wechatId = info.wechatId
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM FRIENDS WHERE friendId = ? AND wechatId = ?',
        [friendId, wechatId],
        function (tx, results) {
          console.log('SetPersonalInfo')
          console.log(results.rows)
          commit(types.SET_PERSONAL_INFO, results.rows[0])
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  // 当前微信的好友列表
  SetFriendsList ({ commit, getters }, info) {
    let db = getters.db
    // console.log(db)
    let mn = 50
    let wechatId = info
    if (typeof info === 'object') {
      mn = Number(info.number) * Number(50)
      wechatId = info.wechatId
    }
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM FRIENDS WHERE wechatId = ? AND deleted = ? ORDER BY friendNick LIMIT ?',
        [wechatId, 0, mn],
        function (tx, results) {
          console.log('设置当前微信的好友列表：SetFriendsList')
          console.log(results.rows)
          commit(types.SET_FRIENDS_LIST, results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  /**
   * 读取数据库中的所有常用语并整理
   */
  SetReplays ({ commit, state, getters }) {
    let unionId = state.user.UnionId
    let db = getters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM REPLAYS WHERE isDeleted = ? AND unionId = ? ',
        [0, unionId],
        function (tx, results) {
          console.log(results.rows)
          commit(types.SET_REPLAYS, results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  // 设置当前的tab页
  SetFlags ({ commit }, flags) {
    commit(types.SET_FLAGS, flags)
  },

  // ***************************河蟹的分割线********************

  // 设置websocket 返回的信息
  SetRetmsg ({ commit }, retMsg) {
    commit(types.SET_RETMSG, retMsg)
  },

  SetError ({ commit }, error) {
    commit(types.SET_ERROR, error)
  },

  /**
   * 是否有新的好友请求
   * @param {*} flag
   */
  SetNewFriendNotice ({ commit }, flag) {
    commit(types.SET_NEW_FRIEND_NOTICE, flag)
  },

  /**
   * 设置MD5
   */
  SetMd5 ({ commit }, md5) {
    commit(types.SET_MD5, md5)
  },

  SetBigPicture ({ commit, getters }, md5) {
    let db = getters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM BIGPICTURES WHERE md5 = ?',
        [md5],
        function (tx, results) {
          commit(types.SET_BIG_PICTURE, results.rows)
        },
        function (tx, error) {
          console.log(error)
          let bigPicture = {}
          commit(types.SET_BIG_PICTURE, bigPicture)
        }
      )
    })
  },

  /**
   * 查询数据库 获取好友数量
   * @param {*} wechatId 微信id
   */
  SetFriendsNumber ({ commit, getters }, wechatId) {
    let db = getters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT COUNT(wechatId) FROM FRIENDS WHERE wechatId = ?',
        [wechatId],
        function (tx, results) {
          commit(types.SET_FRIENDS_NUMBER, results.rows[0]['COUNT(wechatId)'])
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },

  /**
   * 根据wechatId 获取friendsList
   */
  SearchFriendsList ({ commit, getters }, info) {
    let db = getters.db
    let keyword = '%' + info.val + '%'
    let wechatId = info.wechatId
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM FRIENDS WHERE wechatId =? AND friendNick Like ? AND deleted = 0 ORDER BY friendNick',
        [wechatId, keyword],
        function (tx, results) {
          commit(types.SET_FRIENDS_LIST, results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },

  /**
   * 查询好友
   */
  SearchFriend ({ commit, getters }, info) {
    let friendId = info.friendId
    let wechatId = info.wechatId
    let db = getters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM FRIENDS WHERE wechatId =? AND friendId = ?',
        [wechatId, friendId],
        function (tx, results) {
          // console.log('SearchFriend')
          // console.log(results)
          commit(types.SET_RESULT_OF_ACCEPT_ADD_REQUEST, true)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },

  /**
   * 根据info 搜索会话
   */
  SearchMessages ({ commit, getters }, info) {
    let db = getters.db
    let wechatId = info.wechatId
    let keyword = '%' + info.val + '%'
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM (SELECT * FROM CHATS INNER JOIN FRIENDS ON CHATS.friendId = FRIENDS.friendId) WHERE wechatId=? AND time>? AND friendNick LIKE ? GROUP BY friendId ORDER BY time DESC',
        [wechatId, day, keyword],
        function (tx, results) {
          commit(types.SET_MESSAGES, results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },

  /**
   * 设置当前选中的常用语
   */
  SetReplay ({ commit }, replay) {
    console.log(replay)
    commit(types.SET_REPLAY, replay)
  },

  /**
   * 获取所有的好友申请
   */
  SetAddRequest ({ commit, getters }) {
    let db = getters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM ADDREQUEST ORDER BY receivingTime',
        [],
        function (tx, results) {
          // console.log('SetAddRequest')
          // console.log(results.rows)
          commit(types.SET_ADD_REQUEST, results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },

  /**
   * 通过state 获取好友申请的数据 并commit
   * @param {*} state 状态
   */
  SetAddRequestByState ({ commit, getters }, state) {
    let db = getters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM ADDREQUEST WHERE state = ?',
        [state],
        function (tx, results) {
          // console.log('SetAddRequest')
          // console.log(results.rows)
          commit(types.SET_ADD_REQUEST, results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },

  /**
   * 更新addRequest
   * @param {*} param0
   * @param {*} updata
   */
  UpdataAddRequest ({ commit, getters }, updata) {
    let db = getters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'UPDATE ADDREQUEST SET state = ?, result=? WHERE friendId = ?',
        [updata.state, updata.result, updata.friendId],
        function (tx, results) {
          // console.log('SetAddRequest')
          // console.log(results.rows)
          commit(types.SET_ADD_REQUEST, results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },

  /**
   * 文件上传
   * @param {*} data 上传的数据
   */
  UploadFile ({ commit }, formData) {
    return new Promise((resolve, reject) => {
      uploadFile(formData)
        .then(response => {
          // console.log(response)
          let data = response.data.data
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  }
}
