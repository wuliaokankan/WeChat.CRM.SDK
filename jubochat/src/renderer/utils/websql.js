// import store from '../store'
// let db = store.getters.db

/**
 * 创建表
 * wechats 微信表
 * friends 微信好友表
 * chats 微信消息表
 */
function createTables (db) {
  console.log('创建tables')
  db.transaction(function (tx) {
    // 创建微信表
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS WECHATS (wechatId PRIMARY KEY, wechatNick, wechatNo, deviceName, gender, avatar, province, city, isOnline, loginTime, modifyTime, loginUnionId, isLogined)'
    )
    // 创建好友表
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS FRIENDS (friendId, wechatId, allFriends, friendNo, friendNick, avatar, gender, province, city, type, memo, addTime, deleted, delTime, primary key (friendId,wechatId))'
    )
    // 接收的聊天消息表
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS RECEIVECHATS (id integer PRIMARY KEY autoincrement, content, contentType, friendId, msgId, wechatId, msgSvrId, time, isRead, chatType)'
    )
    // 当前聊天表
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS CURRENTCHATS (content, contentType, friendId, msgId, wechatId, msgSvrId, time, isRead, chatType, messageNumber, primary key (friendId, wechatId))'
    )
    // 聊天消息详情表
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS CHATSDETAIL (content, contentType, friendId, msgId, wechatId, msgSvrId, time, isRead,primary key (msgId))'
    )
    // 客户录入
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS CUSTOMERS (friendId PRIMARY KEY, wechatId, name, alias, phone, remarks)'
    )
    // 常用语
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS REPLAYS (id integer PRIMARY KEY autoincrement, groupId, groupName, content, contentType, remark, unionId, isDeleted)'
    )
    // 朋友圈消息列表
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS CIRCLES (circleId PRIMARY KEY, wechatId, friendId, content, publishTime, comments, likes)'
    )
    // 朋友圈详情
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS CIRCLESDETAIL (circleId PRIMARY KEY, wechatId, content, publishTime)'
    )
    // 好友申请表
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS FRIENDSREQUEST (friendId, wechatId, friendNick, reason, avatar, state, receiveTime, agreeTime, primary key (friendId, wechatId))'
    )
    // 通过号码加好友表
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS ADDFRIENDSHISTORY (phone, wechatId, message, remark, imei, addTime, result, primary key (phone, wechatId))'
    )
    // 自动通过好友请求配置表
    tx.executeSql(
      'CREATE TABLE IF NOT EXISTS AUTOACCEPTFRIENDS (wechatId PRIMARY KEY, accept, reply, content)'
    )
  })
}

/**
 * 删除所有的表
 */
function dropTables (db) {
  db.transaction(function (tx) {
    // tx.executeSql('DROP TABLE WECHATS')
    // tx.executeSql('DROP TABLE FRIENDS')
    // tx.executeSql('DROP TABLE BIGPICTURES')
    // tx.executeSql('DROP TABLE REPLAYS')
    // tx.executeSql('DROP TABLE RECEIVECHATS')
    // tx.executeSql('DROP TABLE CURRENTCHATS')
    // tx.executeSql('DROP TABLE CHATSDETAIL')
    // tx.executeSql('DROP TABLE CIRCLES')
    // tx.executeSql('DROP TABLE CIRCLESDETAIL')
    // tx.executeSql('DROP TABLE FRIENDSREQUEST')
    // tx.executeSql('DROP TABLE ADDFRIENDSHISTORY')
    tx.executeSql('DROP TABLE AUTOACCEPTFRIENDS')
  })
}

/**
 * 插入wechats
 */
function replaceWechats (db, wechat) {
  db.transaction(function (tx) {
    tx.executeSql(
      'INSERT OR REPLACE INTO WECHATS (wechatId, wechatNick, wechatNo, deviceName, gender, avatar, province, city, isOnline, loginTime, modifyTime, loginUnionId, isLogined) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)',
      [
        wechat.WeChatId,
        wechat.WeChatNick,
        wechat.WeChatNo,
        wechat.DeviceName,
        wechat.Gender,
        wechat.Avatar,
        wechat.Province,
        wechat.City,
        wechat.IsOnline,
        wechat.LoginTime,
        wechat.ModifyTime,
        wechat.LoginUnionId,
        wechat.IsLogined
      ],
      function (tx, success) {
        console.log('replaceWechats：微信数据插入成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 将好友写入数据库
 * @param {String} friendId 好友ID
 * @param {*} wechatId 所属微信ID
 * @param {*} allFriends 好友数量
 * @param {*} friendNo 好友
 * @param {*} friendNick 好友昵称
 * @param {*} avatar 好友头像
 * @param {*} gender 好友性别
 * @param {*} province 省
 * @param {*} city 市
 * @param {*} tye 好友类型
 * @param {*} memo  备注
 * @param {*} addTime 添加时间
 * @param {*} deleted 是否删除 0未删除 1已经删除
 * @param {*} delTime 删除时间
 */
function replaceFriends (
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
) {
  db.transaction(function (tx) {
    tx.executeSql(
      'INSERT OR REPLACE INTO FRIENDS (friendId, wechatId, allFriends, friendNo, friendNick, avatar, gender, province, city,type, memo, addTime, deleted, delTime) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)',
      [
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
      ],
      function (tx, result) {
        // console.log('插入成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 添加或更新friends
 */
function updateFriends (db, message) {
  console.log('updataFriends')
  // console.log(message)
  let friend = message.Friend
  let memo = ''
  let type = ''
  let allFriends = -1
  if (friend.Memo) {
    memo = friend.Memo
  }
  if (friend.Type) {
    type = friend.Type
  }
  let addTime = new Date().getTime()
  let deleted = 0
  let delTime = null
  db.transaction(function (tx) {
    tx.executeSql(
      'INSERT OR REPLACE INTO FRIENDS (friendId, wechatId, allFriends, friendNo, friendNick, avatar, gender, province, city,type, memo, addTime, deleted, delTime) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)',
      [
        friend.FriendId,
        message.WeChatId,
        allFriends,
        friend.FriendNo,
        friend.FriendNick,
        friend.Avatar,
        friend.Gender,
        friend.Province,
        friend.City,
        type,
        memo,
        addTime,
        deleted,
        delTime
      ],
      function (tx, result) {
        console.log('好友信息更新成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 删除好友
 * @param {*} message
 */
function deleteFriends (db, message) {
  let friendId = message.FriendId
  let wechatId = message.WeChatId
  let delTime = new Date().getTime()
  db.transaction(function (tx) {
    tx.executeSql(
      // 'DELETE FROM FRIENDS WHERE friendId = ? AND wechatId = ?',
      'UPDATE FRIENDS SET deleted = ?, delTime = ? WHERE friendId = ? AND wechatId = ?',
      [1, delTime, friendId, wechatId],
      function (tx, result) {
        // console.log(result)
        console.log('从数据库删除好友成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 接收的聊天数据存入本地
 * @param {WechatId:'',FriendId:'',ContentType:'',Content:'',time:"",isRead:"",chatType:""} chat
 */
function InsertReceiveChats (db, content) {
  console.log('插入InsertReceiveChats表')
  db.transaction(function (tx) {
    tx.executeSql(
      'INSERT INTO RECEIVECHATS (content, contentType, friendId, msgId, wechatId, msgSvrId, time, isRead, chatType) VALUES (?,?,?,?,?,?,?,?,?)',
      [
        content.Content,
        content.ContentType,
        content.FriendId,
        content.MsgId,
        content.WeChatId,
        content.MsgSvrId,
        content.time,
        content.isRead,
        content.chatType
      ],
      function (tx, results) {
        console.log('聊天信息插入成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 当前的会话
 * @param {*} content
 */
function InsertOrReplaceCurrentChats (db, content) {
  console.log('插入InsertOrReplaceCurrentChats表')
  db.transaction(function (tx) {
    tx.executeSql(
      'INSERT OR REPLACE INTO CURRENTCHATS (content, contentType, friendId, msgId, wechatId, msgSvrId, time, isRead, chatType, messageNumber) VALUES (?,?,?,?,?,?,?,?,?,?)',
      [
        content.Content,
        content.ContentType,
        content.FriendId,
        content.MsgId,
        content.WeChatId,
        content.MsgSvrId,
        content.time,
        content.isRead,
        content.chatType,
        content.messageNumber
      ],
      function (tx, results) {
        console.log('聊天信息插入成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 添加常用语
 * @param {*} replay
 */
function replaceReplays (db, replay) {
  db.transaction(function (tx) {
    tx.executeSql(
      'INSERT OR REPLACE INTO REPLAYS ( groupId, groupName, content, contentType, remark, unionId, isDeleted) VALUES (?,?,?,?,?,?,?)',
      [
        replay.groupId,
        replay.groupName,
        replay.content,
        replay.contentType,
        replay.remark,
        replay.unionId,
        replay.isDeleted
      ],
      function (tx, results) {
        console.log('replays插入成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 删除常用消息
 */
function deletedReplays (db, replay) {
  let ri = replay.id
  db.transaction(function (tx) {
    tx.executeSql(
      'DELETE FROM REPLAYS WHERE id = ?',
      [ri],
      function (tx, results) {
        console.log('replays删除成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 插入或更新朋友圈内容
 * @param {*} res
 */
function replaceCircles (db, res) {
  let content = JSON.stringify(res.content)
  let comments = ''
  let likes = ''
  if (res.comments) {
    comments = JSON.stringify(res.comments)
  }
  if (res.likes) {
    likes = JSON.stringify(res.likes)
  }
  db.transaction(function (tx) {
    tx.executeSql(
      'INSERT OR REPLACE INTO CIRCLES (circleId, wechatId, friendId, content, publishTime, comments, likes) VALUES (?,?,?,?,?,?,?)',
      [
        res.circleId,
        res.wechatId,
        res.friendId,
        content,
        res.publishTime,
        comments,
        likes
      ],
      function (tx, results) {
        // console.log('replaceCircles')
        console.log('好友朋友圈插入成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 朋友圈详情插入成功
 * @param {*} res
 */
function replaceCirclesDetail (db, res) {
  let content = JSON.stringify(res.content)
  // console.log(res)
  db.transaction(function (tx) {
    tx.executeSql(
      'INSERT OR REPLACE INTO CIRCLESDETAIL (circleId, wechatId, content, publishTime) VALUES (?,?,?,?)',
      [res.circleId, res.wechatId, content, res.publishTime],
      function (tx, results) {
        // console.log('replaceCirclesDetail')
        // console.log('朋友圈详情插入成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 删除朋友圈
 */
function deleteCircleById (db, circleId) {
  console.log(circleId)
  db.transaction(function (tx) {
    tx.executeSql(
      'DELETE FROM CIRCLES WHERE circleId = ?',
      [circleId],
      function (tx, results) {
        // console.log('replaceCircles')
        console.log('朋友圈删除成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

// /**
//  * 聊天详情
//  * @param {*} content
//  */
// function InsertChatsDetail (content) {
//   // console.log('插入Chats表')
//   // console.log(content)
//   db.transaction(function (tx) {
//     tx.executeSql(
//       'INSERT OR REPLACE INTO RECEIVECHATS (content, contentType, friendId, msgId, wechatId, msgSvrId, time, isRead) VALUES (?,?,?,?,?,?,?,?)',
//       [
//         content.Content,
//         content.ContentType,
//         content.FriendId,
//         content.MsgId,
//         content.WeChatId,
//         content.MsgSvrId,
//         content.time,
//         content.isRead
//       ],
//       function (tx, results) {
//         console.log('聊天信息插入成功')
//       },
//       function (tx, error) {
//         console.log(error)
//       }
//     )
//   })
// }

// /**
//  * @param {*} bigPicture
//  */
// function replaceBigPictures (bigPicture) {
//   // console.log('replaceBIGPICTURES')
//   // console.log(bigPicture)
//   db.transaction(function (tx) {
//     tx.executeSql(
//       'INSERT OR REPLACE INTO BIGPICTURES (md5, wechatId, friendId, msgId, content, isOriginal) VALUES (?,?,?,?,?,?)',
//       [
//         bigPicture.Md5,
//         bigPicture.WeChatId,
//         bigPicture.FriendId,
//         bigPicture.MsgId,
//         bigPicture.Content,
//         bigPicture.IsOriginal
//       ],
//       function (tx, results) {
//         // console.log('bp插入成功')
//       },
//       function (tx, error) {
//         console.log(error)
//       }
//     )
//   })
// }

// /**
//  * 客户录入
//  * @param {*} customer
//  */
// function replaceCustomers (customer) {
//   db.transaction(function (tx) {
//     tx.executeSql(
//       'INSERT OR REPLACE INTO CUSTOMERS (friendId PRIMARY KEY, wechatId, name, alias, phone, remarks) VALUES (?,?,?,?,?,?)',
//       [
//         customer.friendId,
//         customer.wechatId,
//         customer.name,
//         customer.alias,
//         customer.phone,
//         customer.remark
//       ],
//       function (tx, results) {
//         // console.log('customers插入成功')
//       },
//       function (tx, error) {
//         console.log(error)
//       }
//     )
//   })
// }

/**
 * 添加好友请求
 * @param {*} res {微信id，好友id，好友昵称，验证消息，头像，状态，除了结果，收到的时间，处理的时间}
 */
function replaceFriendsRequest (db, res) {
  db.transaction(function (tx) {
    tx.executeSql(
      'INSERT OR REPLACE INTO FRIENDSREQUEST ( friendId, wechatId, friendNick, reason, avatar, state, receiveTime, agreeTime) VALUES (?,?,?,?,?,?,?,?)',
      [
        res.friendId,
        res.wechatId,
        res.friendNick,
        res.reason,
        res.avatar,
        res.state,
        res.receiveTime,
        res.agreeTime
      ],
      function (tx, results) {
        console.log('replaceFriendsRequest插入成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 通过号码加好友 添加记录
 */
function replaceAddFriendsHistory (db, af) {
  console.log('replaceAddFriendsHistory')
  console.log(af)
  let pn = af.Phones[0]
  console.log(pn)
  db.transaction(function (tx) {
    tx.executeSql(
      'INSERT OR REPLACE INTO ADDFRIENDSHISTORY (phone, wechatId, message, remark, imei, addTime, result) VALUES (?,?,?,?,?,?,?)',
      [pn, af.WeChatId, af.message, af.Remark, af.IMEI, af.addTime, af.result],
      function (tx, results) {
        console.log('replaceAddFriendsHistory:添加成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

/**
 * 自动通过好友请求 配置
 */
function replaceAutoAcceptFriends (db, aaf) {
  console.log('replaceAutoAcceptFriends')
  console.log(aaf)
  db.transaction(function (tx) {
    tx.executeSql(
      'INSERT OR REPLACE INTO AUTOACCEPTFRIENDS (wechatId, accept, reply, content) VALUES (?,?,?,?)',
      [aaf.wechatId, aaf.accept, aaf.reply, aaf.content],
      function (tx, results) {
        console.log('replaceAutoAcceptFriends:添加成功')
      },
      function (tx, error) {
        console.log(error)
      }
    )
  })
}

export default {
  createTables,
  dropTables,
  replaceWechats,
  replaceFriends,
  updateFriends,
  deleteFriends,
  InsertReceiveChats,
  InsertOrReplaceCurrentChats,
  replaceReplays,
  deletedReplays,
  replaceCircles,
  replaceCirclesDetail,
  deleteCircleById,
  replaceFriendsRequest,
  replaceAddFriendsHistory,
  replaceAutoAcceptFriends
  // InsertChatsDetail,
  // replaceBigPictures,
  // replaceCustomers,
}
