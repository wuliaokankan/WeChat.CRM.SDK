// initial state
const state = {
  currentCircles: {},
  showFriendCircle: false,
  hasNoRight: false,
  circlesDetail: {}
}

// getters
const getters = {
  currentCircles: state => state.currentCircles,
  showFriendCircle: state => state.showFriendCircle,
  hasNoRight: state => state.hasNoRight,
  circlesDetail: state => {
    let circlesDetail = {}
    for (const key in state.circlesDetail) {
      if (state.circlesDetail.hasOwnProperty(key)) {
        const element = state.circlesDetail[key]
        circlesDetail[element.circleId] = JSON.parse(element.content)
      }
    }
    return circlesDetail
  }
}

// actions
// let db = getters.db

const actions = {
  // 设置当前的聊天
  SetCurrentCircles ({ commit, rootGetters }, info) {
    // console.log('SetCurrentCircles')
    let db = rootGetters.db
    let wechatId = info.wechatId
    let friendId = info.friendId
    let number = 5
    if (info.number) {
      number = number + info.number
    }
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM CIRCLES WHERE wechatId = ? AND friendId = ? ORDER BY publishTime DESC LIMIT ?',
        [wechatId, friendId, number],
        function (tx, results) {
          // console.log(results.rows)
          commit('SET_CURRENT_CIRCLES', results.rows)
        },
        function (tx, error) {
          console.log(error)
        }
      )
    })
  },
  SetShowFriendCircle ({ commit }, flag) {
    commit('SET_SHOW_FRIEND_CIRCLE', flag)
  },
  SetHasNoRight ({ commit }, flag) {
    commit('SET_HAS_NO_RIGHT', flag)
  },
  SetCirclesDetail ({ commit, rootGetters }) {
    let db = rootGetters.db
    db.transaction(function (tx) {
      tx.executeSql(
        'SELECT * FROM CIRCLESDETAIL',
        [],
        function (tx, results) {
          console.log('SetCirclesDetail')
          console.log(results.rows)
          commit('SET_CIRCLES_DETAIL', results.rows)
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
  SET_CURRENT_CIRCLES: (state, currentCircles) => {
    state.currentCircles = currentCircles
  },
  SET_SHOW_FRIEND_CIRCLE: (state, flag) => {
    state.showFriendCircle = flag
  },
  SET_HAS_NO_RIGHT: (state, flag) => {
    state.hasNoRight = flag
  },
  SET_CIRCLES_DETAIL: (state, detail) => {
    state.circlesDetail = detail
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
