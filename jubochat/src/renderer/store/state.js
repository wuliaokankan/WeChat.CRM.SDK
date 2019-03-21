// import { getToken } from '@/utils/auth'
const state = {
  scrrentHeight: window.innerHeight, // 窗口高度
  isLogin: false, // 登陆状态
  user: {}, // 当前登陆的用户信息
  wechats: [], // 当前账号绑定的微信列表
  wechatsLogin: [], // 已登陆微信列表
  currentWechat: {}, // 当前微信
  personalInfo: {}, // 当前选中的好友信息
  friendsList: {}, // 当前微信的好友列表
  replays: {}, // 常用消息
  replay: '', // 选中的快捷语
  flags: 'conversation', // 当前的tab页

  // ***************************河蟹的分割线********************

  // token: '',
  retMsg: {}, // websocket返回的信息
  error: '', // 错误信息
  md5: '', // 大图
  bigPicture: {},
  // newFriendNotice: false, // 有新的加好友请求通知
  // newChatNotice: false, // 有新的聊天消息通知
  // showFriendCircle: false, // 显示某个好友的朋友圈
  // noFriendCircle: false, // 该好友仅显示最近三天的朋友圈！
  // friendsNumber: 0, // 指定微信的好友数量
  customers: {}, // 客户录入

  currentFriends: [], // 当前选中的好友(0+)
  // newfriendsList: [], // 新的朋友
  addRequest: {} // 添加好友申请
}
export default state
