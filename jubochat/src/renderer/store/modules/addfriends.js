// initial state
const state = {
  newFriendRequestNotice: false, // 添加好友请求通知
  friendsRequestInfo: {}, // 请求信息
  history: {}, // 执行的历史记录
  autoAccept: {} // 自动通过好友请求列表
}

// getters
const getters = {
  newFriendRequestNotice: state => state.newFriendRequestNotice,
  friendsRequestInfo: state => state.friendsRequestInfo,
  history: state => state.history,
  autoAccept: state => state.autoAccept
}

// actions
// let db = getters.db
const actions = {
  SetNewFriendRequestNotice ({ commit }, flag) {
    commit('SET_NEW_FRIENDS_REQUEST_NOTICE', flag)
  },
  SetFriendsRequestInfo ({ commit, rootGetters }) {
    let db = rootGetters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM FRIENDSREQUEST ORDER BY receiveTime DESC',
        [],
        function (tx, results) {
          console.log(results.rows)
          commit('SET_FRIENDS_REQUEST_INFO', results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  SetHistory ({ commit, rootGetters }) {
    let db = rootGetters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM ADDFRIENDSHISTORY ORDER BY addTime DESC',
        [],
        function (tx, results) {
          console.log(results.rows)
          commit('SET_HISTORY', results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  UpdateHistory ({ commit, rootGetters }, info) {
    let db = rootGetters.db
    let result = 0 // 0未发出请求， 1已经发出添加好友的请求 2对象不存在 3对象已经是好友
    let phone = info.phone
    let wechatId = info.wechatId
    result = info.result
    db.transaction(function (tx) {
      tx.executeSql(
        'UPDATE ADDFRIENDSHISTORY SET result = ? WHERE phone = ? AND wechatId = ?',
        [result, phone, wechatId],
        function (tx, results) {
          console.log('UpdateHistory')
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  SetAutoAccept ({ commit, rootGetters }) {
    let db = rootGetters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM AUTOACCEPTFRIENDS',
        [],
        function (tx, results) {
          console.log('SetAutoAccept')
          console.log(results.rows)
          commit('SET_AUTO_ACCEPT', results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  }
}

// mutations
const mutations = {
  SET_NEW_FRIENDS_REQUEST_NOTICE: (state, flag) => {
    state.newFriendRequestNotice = flag
  },
  SET_FRIENDS_REQUEST_INFO: (state, fri) => {
    state.friendsRequestInfo = fri
  },
  SET_HISTORY: (state, history) => {
    state.history = history
  },
  SET_AUTO_ACCEPT: (state, aaf) => {
    state.autoAccept = aaf
  },
  SET_ALL_HISTORY: (state, allHistory) => {
    state.allHistory = allHistory
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
