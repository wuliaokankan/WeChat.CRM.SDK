import * as types from '../mutation-types'

// initial state
const state = {
  currentChats: [],
  newChatNotice: false,
  notReadWechat: {},
  allChats: {},
  allChatsLength: 0,
  commonReplay: {},
  st: 0,
  sh: 0,
  ch: 0
}

// getters
const getters = {
  currentChats: state => state.currentChats,
  newChatNotice: state => state.newChatNotice,
  notReadWechat: state => state.notReadWechat,
  allChats: state => state.allChats,
  allChatsLength: state => state.allChatsLength,
  commonReplay: state => state.commonReplay,
  st: state => state.st,
  sh: state => state.sh,
  ch: state => state.ch
}

// actions
const actions = {
  // 设置当前的聊天
  SetCurrentChats ({ commit, rootGetters }, wechatId) {
    let db = rootGetters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM (SELECT * FROM CURRENTCHATS LEFT JOIN FRIENDS ON CURRENTCHATS.friendId = FRIENDS.friendId AND CURRENTCHATS.wechatId = FRIENDS.wechatId)  WHERE wechatId = ? ORDER BY time DESC',
        [wechatId],
        function (tx, results) {
          commit(types.SET_CURRENT_CHATS, results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  // 设置会话图标新消息提醒
  SetNewChatNotice ({ commit }, flag) {
    commit('SET_NEW_CHAT_NOTICE', flag)
  },
  // 设置没有读信息的微信
  SetNotReadWechat ({ commit, rootGetters }) {
    let db = rootGetters.db
    let wn = {}
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT *,count(friendId) FROM CURRENTCHATS WHERE isRead = ? GROUP BY wechatId ',
        [0],
        function (tx, results) {
          for (const friend of results.rows) {
            wn[friend.wechatId] = friend['count(friendId)']
          }
          commit('SET_NOT_READ_WECHAT', wn)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  // 设置好友的消息已读
  SetWechatIsRead ({ commit, rootGetters }, info) {
    let db = rootGetters.db
    let friendId = info.friendId
    let wechatId = info.wechatId
    db.transaction(function (tx) {
      tx.executeSql(
        'UPDATE CURRENTCHATS SET isRead = ?, messageNumber = ? WHERE friendId=? AND wechatId = ?',
        [1, 0, friendId, wechatId],
        function (tx, results) {},
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  // 设置所有的聊天
  SetAllChats ({ commit, rootGetters }, info) {
    let db = rootGetters.db
    let wechatId = info.wechatId
    let friendId = info.friendId
    let number = 20
    if (info.number) {
      number = info.number
    }
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM RECEIVECHATS  WHERE wechatId = ? AND friendId = ? ORDER BY time DESC LIMIT ?, ? ',
        [wechatId, friendId, 0, number],
        function (tx, results) {
          console.log('SetAllChats')
          console.log(results.rows)
          commit('SET_ALL_CHATS', results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  // 统计聊天消息的条数
  SetAllChatsLength ({ commit, rootGetters }, info) {
    let db = rootGetters.db
    let wechatId = info.wechatId
    let friendId = info.friendId
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT *,count(id) FROM RECEIVECHATS  WHERE wechatId = ? AND friendId = ?',
        [wechatId, friendId],
        function (tx, results) {
          console.log('SetAllChatsLength')
          // console.log(results.rows[0]['count(id)'])
          commit('SET_ALL_CHATS_LENGTH', results.rows[0]['count(id)'])
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  // 设置当前的快捷语
  SetCommonReplay ({ commit }, common) {
    console.log('SetCommonReplay')
    commit('SET_COMMON_REPLAY', common)
  },
  SetST ({ commit }, st) {
    commit('SET_ST', st)
  },
  SetSH ({ commit }, sh) {
    commit('SET_SH', sh)
  },
  SetCH ({ commit }, ch) {
    commit('SET_CH', ch)
  }
}

// mutations
const mutations = {
  [types.SET_CURRENT_CHATS]: (state, currentChats) => {
    state.currentChats = currentChats
  },
  SET_NEW_CHAT_NOTICE: (state, flag) => {
    state.newChatNotice = flag
  },
  SET_NOT_READ_WECHAT: (state, wn) => {
    // console.log('SET_NOT_READ_WECHAT')
    state.notReadWechat = wn
  },
  SET_NOT_READ_CHATS: (state, message) => {
    console.log('SET_NOT_READ_CHATS')
    console.log(message)
    if (message.type && message.type === 1) {
      state.notReadChats[message.wechatId][message.friendId] = 0
    } else {
      let wechatId = message.WeChatId
      let friendId = message.FriendId
      if (state.notReadChats[wechatId]) {
        if (state.notReadChats[wechatId][friendId]) {
          state.notReadChats[wechatId][friendId] += 1
        } else {
          state.notReadChats[wechatId][friendId] = 1
        }
      } else {
        console.log('第一次接收信息')
        state.notReadChats[wechatId] = {
          friendId: 1
        }
      }
    }
  },
  SET_ALL_CHATS: (state, allChats) => {
    state.allChats = allChats
  },
  SET_ALL_CHATS_LENGTH: (state, length) => {
    state.allChatsLength = length
  },
  SET_COMMON_REPLAY: (state, commonReplay) => {
    state.commonReplay = commonReplay
  },
  SET_ST: (state, st) => {
    state.st = st
  },
  SET_SH: (state, sh) => {
    state.sh = sh
  },
  SET_CH: (state, ch) => {
    state.ch = ch
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
