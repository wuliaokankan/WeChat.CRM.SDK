import store from '../store'
// import base64 from '../utils/base64'
import websql from '../utils/websql'

var websock = null

function initWebSocket () {
  websock = new WebSocket('ws://192.168.0.102:11088')
  // websock = new WebSocket('ws://www.jjldkj.com:13088')
  websock.onmessage = function (e) {
    websocketonmessage(e)
  }
  websock.onclose = function () {
    websocketclose()
  }
  websock.onopen = function () {
    websocketOpen()
  }
  // 连接发生错误的回调方法
  websock.onerror = function () {
    console.log('WebSocket连接发生错误')
  }
}

// 实际调用的方法
function sendSock (agentData) {
  // globakCallback = callback
  if (websock.readyState === websock.OPEN) {
    // 若是ws开启状态
    websocketsend(agentData)
  } else if (websock.readyState === websock.CONNECTING) {
    // 若是 正在开启状态，则等待1s后重新调用
    setTimeout(function () {
      sendSock(agentData)
    }, 1000)
  } else {
    store.dispatch('SetError', '通讯已断开')
    // websocketclose()
    // // 若未开启 ，则等待1s后重新调用
    // setTimeout(function () {
    //   sendSock(agentData)
    // }, 1000)
  }
}

// 数据接收
function websocketonmessage (e) {
  // console.log('收到消息')
  let msg = JSON.parse(e.data)
  let msgType = msg.msgType
  let currentWechat = store.getters.currentWechat
  switch (msgType) {
    // 返回登陆结果
    case 'DeviceAuthRsp':
      {
        let userInfo = JSON.parse(msg.message).Extra
        console.log('返回登陆结果：DeviceAuthRsp')
        console.log(userInfo)
        store.dispatch('SetIslogin', true)
        store.dispatch('SetUser', userInfo)
        localStorage.setItem('loginStatus', 'login')
        let userInfoString = JSON.stringify(userInfo)
        localStorage.setItem('userInfo', userInfoString)
        let db = store.getters.db
        console.log(db)
        websql.createTables(db) // 创建表格
      }
      break
    // 返回微信列表
    case 'GetWeChatsRsp':
      {
        let wechats = []
        let wechatsLogin = []
        let message = JSON.parse(msg.message)
        if (message && message.WeChats) {
          wechats = message.WeChats
        }
        if (wechats.length > 0) {
          store.dispatch('SetWechats', wechats)
          for (const wechat of wechats) {
            if (wechat.IsLogined && wechat.IsOnline) {
              wechatsLogin.push(wechat)
            } else {
              wechat['IsLogined'] = false
            }
            let db = store.getters.db
            websql.replaceWechats(db, wechat)
          }
        } else {
          console.log('没有微信上线')
        }
        store.dispatch('SetWechatsLogin', wechatsLogin)
        if (wechatsLogin.length > 0) {
          store.dispatch('SetCurrentWechat', wechatsLogin[0])
        } else {
          let wechat = {}
          store.dispatch('SetCurrentWechat', wechat)
          store.dispatch('SetPersonalInfo', wechat)
        }
        store.dispatch('addfriends/SetFriendsRequestInfo')
        store.dispatch('addfriends/SetAutoAccept')
      }
      break
    // 好友列表推送通知
    case 'FriendPushNotice':
      {
        let db = store.getters.db
        let friendsList = JSON.parse(msg.message)
        console.log('返回好友列表：FriendPushNotice')
        console.log(friendsList)
        let wechatId = friendsList.WeChatId
        let allFriends = friendsList.Count
        let memo = ''
        let addTime = null
        let deleted = 0
        let delTime = null
        for (const friend of friendsList.Friends) {
          let friendId = friend.FriendId
          let friendNo = friend.FriendNo
          let friendNick = friend.FriendNick
          let avatar = friend.Avatar
          let gender = friend.Gender
          let province = friend.Province
          let city = friend.City
          let type = friend.Type
          if (friend.Memo) {
            memo = friend.Memo
          }
          websql.replaceFriends(
            db,
            friendId,
            wechatId,
            allFriends,
            friendNo,
            friendNick,
            avatar,
            gender,
            province,
            city,
            type,
            memo,
            addTime,
            deleted,
            delTime
          )
        }
        let id = store.getters.currentWechat.WeChatId
        store.dispatch('SetFriendsList', id)
      }
      break
    // 好友消息通知
    case 'FriendTalkNotice':
      {
        let message = JSON.parse(msg.message)
        console.log('FriendTalkNotice')
        console.log(message)
        message['time'] = new Date().getTime()
        message['isRead'] = 0 // 0表示未读，1表示已读
        message['chatType'] = 0 // 0表示收到的消息，1表示发送的消息
        message['messageNumber'] = 1 // 未读消息数量
        let cws = store.getters['chats/currentCnphats']
        if (cws && cws.length > 0) {
          console.log('当前会话表有数据')
          for (const chat of cws) {
            if (
              chat.wechatId === message.WeChatId &&
              chat.friendId === message.FriendId
            ) {
              let number = chat.messageNumber
              message['messageNumber'] = number + 1
            }
          }
        }

        if (message.FriendId.indexOf('@chatroom') !== -1) {
          console.log('聊天室消息')
        } else {
          console.log('好友发来的信息')
          let db = store.getters.db
          websql.InsertReceiveChats(db, message)
          websql.InsertOrReplaceCurrentChats(db, message)
          // 新消息通知
          store.dispatch('chats/SetNewChatNotice', true)
          store.dispatch('chats/SetNotReadWechat')
          // store.dispatch('chats/SetNotReadChats', message)
        }

        if (
          store.getters.personalInfo &&
          message.WeChatId === store.getters.currentWechat.WeChatId &&
          message.FriendId === store.getters.personalInfo.friendId
        ) {
          console.log('是当前聊天')
          store.dispatch('chats/SetCurrentChats', message.WeChatId)
          store.dispatch('chats/SetAllChats', store.getters.personalInfo)
        } else {
          console.log('新消息不是当前的聊天')
          store.dispatch('chats/SetCurrentChats', currentWechat.WeChatId)
        }
      }
      break
    // 手机消息通知pc
    case 'WeChatTalkToFriendNotice':
      {
        let message = JSON.parse(msg.message)
        console.log('手机微信发信息通知电脑：WeChatTalkToFriendNotice')
        console.log(message)
        message['time'] = new Date().getTime()
        message['isRead'] = 1 // 0表示未读，1表示已读
        message['chatType'] = 2 // 0表示收到的消息，1表示发送的消息, 2表示手机发送的消息
        let db = store.getters.db
        websql.InsertReceiveChats(db, message)
        store.dispatch('chats/SetNewChatNotice', true)
        store.dispatch('chats/SetNotReadWechat')
        if (
          store.getters.personalInfo &&
          message.WeChatId === store.getters.currentWechat.WeChatId &&
          message.FriendId === store.getters.personalInfo.friendId
        ) {
          console.log('是当前聊天')
          store.dispatch('chats/SetCurrentChats', message.WeChatId)
          store.dispatch('chats/SetAllChats', store.getters.personalInfo)
        } else {
          console.log('新消息不是当前的聊天')
          store.dispatch('chats/SetCurrentChats', currentWechat.WeChatId)
        }
      }
      break
    // 获取聊天详情
    // case 'RequestTalkDetailTaskResultNotice':
    //   {
    //     console.log('收到大图的URL：RequestTalkDetailTaskResultNotice')
    //     let bigPicture = JSON.parse(msg.message)
    //     let bpContent = base64.b64DecodeUnicode(bigPicture.Content)
    //     bigPicture['Content'] = bpContent
    //     bigPicture['Md5'] = store.getters.md5
    //     websql.replaceBigPictures(bigPicture)
    //   }
    //   break
    // 添加好友请求通知
    case 'FriendAddReqeustNotice':
      {
        let message = JSON.parse(msg.message)
        console.log('添加好友请求：FriendAddReqeustNotice')
        console.log(message)
        let res = {}
        res['wechatId'] = message.WeChatId
        res['friendId'] = message.FriendId
        res['friendNick'] = message.FriendNick
        res['reason'] = message.Reason
        res['avatar'] = message.Avatar
        res['state'] = 0
        res['receiveTime'] = new Date().getTime()
        res['agreeTime'] = 0
        console.log(res)
        let db = store.getters.db
        websql.replaceFriendsRequest(db, res)
        // if (localStorage.getItem('autoAccept')) {
        //   res['state'] = 2
        //   res['result'] = 2
        //   message = {
        //     MsgType: 'AcceptFriendAddRequestTask',
        //     Content: {
        //       WeChatId: res.wechatId,
        //       FriendId: res.friendId,
        //       Operation: 1,
        //       Remark: '',
        //       FriendNick: res.friendNick
        //     }
        //   }
        //   // websock.send()
        //   sendSock(message)
        // }
        // store.dispatch('SetAddRequest')
        store.dispatch('addfriends/SetNewFriendRequestNotice', true)
        store.dispatch('addfriends/SetFriendsRequestInfo')
      }
      break
    // 好友信息变更通知
    case 'FriendAddNotice':
      {
        let message = JSON.parse(msg.message)
        console.log('好友信息变更通知：FriendAddNotice')
        console.log(message)
        let db = store.getters.db
        // 更新数据库
        websql.updateFriends(db, message)
        // 更新页面
        let wechatId = store.getters.currentWechat.WeChatId
        store.dispatch('SetFriendsList', wechatId)
        // 获取申请添加好友的列表
        let newfiends = store.getters['addfriends/friendsRequestInfo']
        let msgfriend = message.Friend
        for (const friend of newfiends) {
          if (
            (friend.state === 0 || friend.state === 4) &&
            friend.friendId === msgfriend.FriendId &&
            friend.wechatId === message.WeChatId
          ) {
            friend.state = 2
            friend.agreeTime = new Date().getTime()
            console.log(friend)
            websql.replaceFriendsRequest(db, friend)
            break
          }
        }
        store.dispatch('addfriends/SetFriendsRequestInfo')
      }
      break
    // 好友删除通知
    case 'FriendDelNotice':
      {
        let message = JSON.parse(msg.message)
        console.log('好友删除通知：FriendDelNotice')
        console.log(message)
        let db = store.getters.db
        websql.deleteFriends(db, message)
        // setTimeout(() => {
        let wechatId = store.getters.currentWechat.WeChatId
        store.dispatch('SetFriendsList', wechatId)
        // }, 1000)
      }
      break
    case 'WeChatLoginNotice':
      console.log('退出登陆通知：WeChatLoginNotice')
      console.log(JSON.parse(msg.message))
      break
    case 'WeChatOfflineNotice':
      console.log('WeChatOfflineNotice')
      console.log(JSON.parse(msg.message))
      break
    case 'WeChatOnlineNotice':
      console.log('WeChatOnlineNotice')
      console.log(JSON.parse(msg.message))
      break
    // 主动获取微信的朋友圈的结果通知 ok
    case 'CirclePushNotice':
      {
        let message = JSON.parse(msg.message)
        let db = store.getters.db
        console.log('返回指定微信ID的朋友圈信息：CirclePushNotice')
        console.log(message)
        if (message.Circles) {
          for (const circle of message.Circles) {
            // console.log(circle)
            let res = {}
            res.wechatId = message.WeChatId
            res.friendId = circle.WeChatId
            res.circleId = circle.CircleId
            res.publishTime = circle.PublishTime
            res.content = circle.Content
            websql.replaceCircles(db, res)
          }
          if (message.WeChatId === message.Circles[0].WeChatId) {
            console.log('自己的朋友圈')
            let info = {
              friendId: message.WeChatId,
              wechatId: message.WeChatId
            }
            store.dispatch('circles/SetCurrentCircles', info)
          } else {
            store.dispatch(
              'circles/SetCurrentCircles',
              store.getters.personalInfo
            )
          }
        } else {
          console.log('该好友仅显示最近三天的朋友圈！')
          store.dispatch('circles/SetHasNoRight', true)
        }
      }
      break
    // 朋友圈图片通知 ok
    case 'CircleDetailNotice':
      {
        let message = JSON.parse(msg.message)
        console.log('返回朋友圈详情：CircleDetailNotice')
        console.log(message)
        let db = store.getters.db
        let res = {
          wechatId: message.WeChatId,
          circleId: message.Circle.CircleId,
          content: message.Circle.Content,
          publishTime: message.Circle.PublishTime
        }
        websql.replaceCirclesDetail(db, res)
        store.dispatch('circles/SetCirclesDetail')
      }
      break
    case 'CircleNewPublishNotice':
      console.log('新朋友圈推送：CircleNewPublishNotice')
      console.log(JSON.parse(msg.message))
      // let message = JSON.parse(msg.message)
      // websql.replaceCircles(circle)
      break
    // 朋友圈评论通知
    case 'CircleCommentNotice':
      console.log('CircleCommentNotice')
      console.log(JSON.parse(msg.message))
      break
    // 删除朋友圈通知 ok
    case 'CircleDelNotice':
      {
        let message = JSON.parse(msg.message)
        console.log('朋友圈删除通知：CircleDelNotice')
        console.log(message)
        let db = store.getters.db
        websql.deleteCircleById(db, message.CircleId)
        let info = {
          wechatId: message.WeChatId,
          friendId: message.WeChatId
        }
        store.dispatch('circles/SetCurrentCircles', info)
      }
      break
    // 朋友圈点赞通知
    case 'CircleLikeNotice':
      console.log('CircleLikeNotice')
      console.log(JSON.parse(msg.message))
      break
    // 删除朋友圈评论 结果通知
    case 'CircleCommentDeleteTaskResultNotice':
      console.log('CircleCommentDeleteTaskResultNotice')
      console.log(JSON.parse(msg.message))

      break
    // 朋友圈评论回复 结果通知
    case 'CircleCommentReplyTaskResultNotice':
      console.log('CircleCommentReplyTaskResultNotice')
      console.log(JSON.parse(msg.message))
      break
    case 'Error':
      console.log('Error')
      console.log(msg.message)
      store.dispatch('SetError', msg.message)
      break
    // 任务处理结果
    case 'MsgReceivedAck':
      console.log('this is MsgReceivedAck')
      break
    // 任务处理结果
    case 'TaskResultNotice':
      let mj = JSON.parse(msg.message)
      console.log('任务执行结果：TaskResultNotice')
      console.log(mj)
      switch (mj.TaskType) {
        case 'AddFriendsTask':
          console.log('通过号码加好友：AddFriendsTask')
          let fh = store.getters['addfriends/history']
          // console.log(fh[0])
          let info = {
            phone: fh[0].phone,
            wechatId: mj.WeChatId,
            result: 1
          }
          if (mj.Success) {
          } else if (mj.Code && mj.ErrMsg === 'not found') {
            info.result = 2
          } else if (mj.Code && mj.ErrMsg === 'already friend') {
            info.result = 3
          }
          // console.log(info)
          store.dispatch('addfriends/UpdateHistory', info)
          store.dispatch('addfriends/SetHistory')

          break
        case 'DeleteSNSNewsTask':
          console.log('删除朋友圈结果通知：DeleteSNSNewsTask')

          break

        default:
          break
      }
      break
    default:
      break
  }
}

// 数据发送
function websocketsend (agentData) {
  websock.send(JSON.stringify(agentData))
}

// 关闭
function websocketclose () {
  // console.log('connection closed (' + e.code + ')')
  console.log('websocket 连接关闭')
  store.dispatch('SetError', '通讯已断开')
  store.dispatch('SetIslogin', false)
}

function websocketOpen () {
  console.log('websoket 连接成功')
}

initWebSocket()

export { sendSock }
