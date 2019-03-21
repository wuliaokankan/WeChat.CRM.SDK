const getters = {
  scrrentHeight: state => state.scrrentHeight, // 窗口高度
  isLogin: state => state.isLogin, // 用户登陆状态
  user: state => state.user, // 用户信息
  // 当前的数据库
  db: state => {
    let user = state.user
    console.log(user)
    let dbname = user.NickName + user.SupplierId + user.UnionId
    let db = openDatabase(dbname, '1.0', user.NickName, 2 * 1024 * 1024)
    return db
  },
  wechats: state => state.wechats, // 当前账号绑定的微信列表
  wechatsLogin: state => state.wechatsLogin, // 获取登陆的微信
  currentWechat: state => state.currentWechat, // 当前选中的微信
  personalInfo: state => state.personalInfo, // 当前选中的好友信息
  friendsList: state => state.friendsList, // 当前微信的好友列表
  replays: state => state.replays, // 所有的常用消息
  replay: state => state.replay, // 选中的常用消息
  flags: state => state.flags,

  // ***************************河蟹的分割线********************

  token: state => state.token,
  error: state => state.error, // websocket 返回的错误信息
  retMsg: state => state.retMsg, // websocket 返回的message
  md5: state => state.md5,
  bigPicture: state => state.bigPicture,
  // newFriendNotice: state => state.newFriendNotice,
  // newChatNotice: state => state.newChatNotice,
  // showFriendCircle: state => state.showFriendCircle,
  // noFriendCircle: state => state.noFriendCircle,
  // friendsNumber: state => state.friendsNumber,
  // circles: state => state.circles, // 朋友圈内容
  // friendCircles: state => state.friendCircles,
  // friendCirclesNumber: state => state.friendCirclesNumber,
  // currentFriendCirclesPage: state => state.currentFriendCirclesPage,
  // messages: state => state.messages, // 个人消息
  // messagesNumber: state => state.messagesNumber, // 获取friendId的未读信息
  // messagesNumbers: state => state.messagesNumbers, // 获取微信的所有friendId的未读信息
  // chats: state => state.chats, // 收到的信息和发送的信息都保存在chats
  customers: state => state.customers, // 客户录入 客户信息

  addRequest: state => state.addRequest // 好友申请
  // currentFriends: state => state.currentFriends, // 通讯录当前选中的好友
  // currentFriend: state => {
  //   let wechatId = state.currentWechat.WeChatId
  //   let currentFriends = state.currentFriends
  //   let currentFriend = { friend: { sn: 0 } }
  //   for (const iterator of currentFriends) {
  //     if (iterator.wechatId === wechatId) {
  //       currentFriend = iterator
  //       return currentFriend
  //     }
  //   }
  //   return currentFriend
  // }, // 通讯录当前显示的好友
  // recordHeight: state => (state.scrrentHeight - 115) * 0.95
}
export default getters
