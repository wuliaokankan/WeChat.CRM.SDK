import * as types from './mutation-types'

export default {
  // 设置token
  [types.SET_TOKEN]: (state, token) => {
    state.token = token
  },

  // 设置websocket 返回的内容
  [types.SET_RETMSG]: (state, retMsg) => {
    state.retMsg = retMsg
  },

  // 设置错误信息
  [types.SET_ERROR]: (state, error) => {
    state.error = error
  },

  [types.SET_NEW_FRIEND_NOTICE]: (state, flag) => {
    state.newFriendNotice = flag
  },

  [types.SET_NEW_CHAT_NOTICE]: (state, flag) => {
    state.newChatNotice = flag
  },

  [types.SET_NO_FRIEND_CIRCLE]: (state, flag) => {
    state.noFriendCircle = flag
  },

  [types.SET_SHOW_FRIEND_CIRCLE]: (state, flag) => {
    state.showFriendCircle = flag
  },

  // 设置height
  [types.SET_SCRRENT_HEIGHT]: (state, height) => {
    state.scrrentHeight = height
  },

  // 用户信息
  [types.SET_USER]: (state, user) => {
    state.user = user
  },
  // 用户登陆
  [types.SET_ISLOGIN]: (state, flag) => {
    state.isLogin = flag
  },

  // 微信号
  [types.SET_WECHATS]: (state, wechats) => {
    state.wechats = wechats
  },
  // 登陆的微信号
  [types.SET_WECHATS_LOGIN]: (state, wechatsLogin) => {
    state.wechatsLogin = wechatsLogin
  },

  // 当前选中的微信
  [types.SET_CUREENT_WECHAT]: (state, wechat) => {
    state.currentWechat = wechat
  },
  // =========================================================

  /**
   * 根据wechatId 获取friendsList
   */
  [types.SET_FRIENDS_LIST]: (state, friendsList) => {
    state.friendsList = friendsList
  },

  /**
   * 根据wechatId 获取friendsNumber
   */
  [types.SET_FRIENDS_NUMBER]: (state, friendsNumber) => {
    state.friendsNumber = friendsNumber
  },

  /**
   * 根据friendId获取信息
   */
  [types.SET_PERSONAL_INFO]: (state, personalInfo) => {
    state.personalInfo = personalInfo
  },

  /**
   * 根据wechatId查询并设置消息
   */
  [types.SET_MESSAGES]: (state, messages) => {
    state.messages = messages
  },

  /**
   * 获取friendId的未读信息
   */
  [types.SET_MESSAGES_NUMBER]: (state, messagesNumber) => {
    state.messagesNumber = messagesNumber
  },

  /**
   * 获取微信所有friendId的未读信息
   */
  [types.SET_MESSAGES_NUMBERS]: (state, messagesNumbers) => {
    state.messagesNumbers = messagesNumbers
  },

  /**
   * 收到的信息和发送的信息都保存在chats
   */
  [types.SET_CHATS]: (state, chats) => {
    state.chats = chats
  },

  /**
   * 设置md5
   */
  [types.SET_MD5]: (state, md5) => {
    state.md5 = md5
  },

  /**
   * 设置大图
   */
  [types.SET_BIG_PICTURE]: (state, bigPicture) => {
    state.bigPicture = bigPicture
  },

  /**
   * 设置常用消息
   */
  [types.SET_REPLAYS]: (state, replays) => {
    state.replays = replays
  },

  /**
   * 设置当前选中的常用消息
   */
  [types.SET_REPLAY]: (state, replay) => {
    state.replay = replay
  },

  /**
   * 设置请求好友
   */
  [types.SET_ADD_REQUEST]: (state, res) => {
    state.addRequest = res
  },

  /**
   * 设置朋友圈内容
   */
  [types.SET_CIRCLES]: (state, res) => {
    state.circles = res
  },

  [types.SET_FRIEND_CIRCLES]: (state, res) => {
    state.friendCircles = res
  },

  [types.SET_FRIEND_CIRCLES_NUMBER]: (state, res) => {
    state.friendCirclesNumber = res
  },

  [types.SET_CURRENT_FRIEND_CIRCLES_PAGE]: (state, currentPage) => {
    state.currentFriendCirclesPage = currentPage
  },

  // =========================================================

  // 当前选中的好友
  [types.SET_CUREENT_FRIENDS]: (state, friend) => {
    let flag = 0
    let currentFriend = {
      wechatId: state.currentWechat.WeChatId,
      friend: friend
    }
    if (state.currentFriends.length > 0) {
      for (const iterator of state.currentFriends) {
        if (iterator.wechatId === state.currentWechat.WeChatId) {
          iterator.friend = friend
          flag = 0
        } else {
          flag = 1
        }
      }
      // 如果没有保存过
      if (flag === 1) {
        state.currentFriends.push(currentFriend)
        console.log(state.currentFriends)
      }
      // 如果不存在
    } else {
      state.currentFriends.push(currentFriend)
      console.log(state.currentFriends)
    }
  },

  // 当前的聊天
  [types.SET_CHAT_LIST]: (state, chatList) => {
    state.chatList.push(chatList)
  },

  [types.SET_CHAT_LIST_READ]: (state, friendId) => {
    let chatList = state.chatList
    for (const chat of chatList) {
      if (chat.FriendId === friendId) {
        chat['isRead'] = true
      }
    }
  },

  [types.SET_CURRENT_CHATS]: (state, chat) => {
    console.log(chat)
    state.currentChats[chat.wechatId] = {
      friendInfo: chat.friendInfo,
      others: chat.others
    }
    console.log(state.currentChats)
  },

  // tab页切换
  [types.SET_FLAGS]: (state, flags) => {
    state.flags = flags
  }

  // [types.SET_COMMUNICATE_PAGE]: (state, page) => {
  //   state.communicatePage = page
  // }
}
