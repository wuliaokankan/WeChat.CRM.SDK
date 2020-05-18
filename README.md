# 个人微信号二次开发sdk协议,微信个人号开发API接口
微信SDK程序概要说明
---------------------------------
本微信开发sdk非微信ipad协议、非mac协议,非安卓协议，api可实现微信99%功能；      
无需扫码登录、可收发朋友圈、查看朋友圈、朋友圈互动点赞、评论、      
好友列表、微信消息收发、发文本消息、图片消息、名片消息、动图表情、发文件、删好友      
添加好友、微信转账、微信红包接收、分享小程序、分享名片、加通讯录好友、微信收藏、微信标签等！      
清理僵尸粉、消息群发、通过好友请求、      
微信建群、微信拉人进群、踢群成员、邀请群成员、退群、改群名称、群列表、发布群公告、多群消息同步等      
微信公众号阅读、关注与取消关注公众号、公众号列表信息、清理微信空间...功能太多懒得写了！！！
sdk商用2年多了，一直在更新维护(2020年4月13号)

！！！！sdk会不断更新升级，demo的最新源码请加微信了解详情！！！！

咨询请加微信happybabby110

微信sdk可开发软件说明
----------------------------
本SDK适用于微信营销软件研发、微信营销手机研发、微信手机群控系统研发、云控系统研发、微信客服系统研发、微信营销系统研发、
微客微商营销工具研发、微信scrm客服系统研发、微信聊天机器人、淘宝客微信发单机器人研发、导购返利机器人研发、微信群管理机器人研发等。
使用此sdk可快速实现微信的二次开发，让你轻松开发出上述软件功能。
SDK接入简单、兼容性好、拓展性强，始终支持微信官方最新版本，及时维护升级，也可按需定制！
功能参考接口参考http://www.yunlauncher.com/Blog/articles/119.html

咨询请加微信happybabby110

微信sdk技术方案特色
--------------------
1、无需扫码登陆微信：区别传统的网页协议版微信和ipad协议版微信，我们的解决方案是不需要扫码登陆微信的，更快捷，更高效,更安全！

2、超强二次开发SDK：我们为合作伙伴提供完整全面的二次开发sdk，使用sdk可研发各类基于微信的营销系统及软件等！

3、提供二次开发基础源码：我们为合作伙伴提供sdk的服务端调用源码及pc端演示demo源码，方便二次开发！

4、2W多设备稳定商用：目前已有多家合作伙伴与我们签约合作，累计出货20000多台设备，所有设备稳定！


微信SDK通信交互及协议说明
----------------------------
1、手机端SDK与服务端通信采用普通socket(任何语言都通用，服务端重写更方便)

2、pc端与服务端通信可采用普通socket，也可以使用websocket，具体看公司开发能力！

3、通信协议采用protobuf 3.0协议数据序列化后的数据内容（更高效更安全）


微信SDK安装使用注意事项
----------------------------
1、需要真实手机，安卓7.0以上操作系统

2、手机需要root,微信sdk需要root权限（也有免root版本，具体请加微信了解）

3、自主搭建服务端时，请自主进入设置界面修改服务端ip及端口

微信SDK可以用了做什么
-----------------------
对接企业CRM、SCRM;
智能机器人；群发单；淘客机器人；
云控系统；
营销系统、营销工具；
智能客服系统；
群管理系统；
手机监控，工作手机；
其他可能用到的业务软件系统…

微信SDK接口API全部协议定义
----------------
1、基础消息类型

1、客户端发送的心跳包
HeartBeatReq = 1001;

2、消息接收确认回复（接收或拒绝接收）
MsgReceivedAck = 1002;

3、错误单独提升为一种消息类型
Error = 1003;


2、设备客户端授权类消息

1、设备(手机客户端、客服客户端)获取通信token请求
DeviceAuthReq = 1010;

2、设备(手机客户端、客服客户端)获取通信token响应
DeviceAuthRsp = 1011;

3、设备授权后退出(仅用于服务端内部)
DeviceExitNotice = 1012;

4、账号强制下线通知
AccountForceOfflineNotice = 1013;

3、手机客户端上传的通知类消息

1、手机客户端微信上线通知
WeChatOnlineNotice = 1020;

2、手机客户端微信下线通知
WeChatOfflineNotice = 1021;

3、微信个人号新增好友通知
FriendAddNotice = 1022;

4、微信个人号移除好友通知
FriendDelNotice = 1023;

5、微信好友发来聊天消息
FriendTalkNotice = 1024;

6、任务执行结果通知
TaskResultNotice = 1025;

7、手机上回复好友的聊天消息
WeChatTalkToFriendNotice = 1026;

8、有好友请求添加好友的通知
FriendAddReqeustNotice = 1027;

9、手机端向服务端通知聊天执行结果
TalkToFriendTaskResultNotice = 1028;

10、图片或视频消息的详细内容结果
RequestTalkDetailTaskResultNotice = 1029;

11、上传手机客户端上微信的二维码
PullWeChatQrCodeTaskResultNotice = 1030;

12、手机上发送了朋友圈通知
CircleNewPublishNotice = 1031;

13、手机上删除朋友圈通知
CircleDelNotice = 1032;

14、手机检测到有人点赞/取消点赞通知
CircleLikeNotice = 1033;

15、手机检测到有人评论/删除朋友圈通知
CircleCommentNotice = 1034;

16、消息标记为已读
PostMessageReadNotice = 1035;

17、联系人信息上传
ContactsInfoNotice = 1036;

18、群聊新增通知
ChatRoomAddNotice = 1037;

19、联系人标签新增，修改通知
ContactLabelAddNotice = 1038;

20、收钱任务执行结果通知
TakeMoneyTaskResultNotice = 1039;

21、朋友圈图片上传
CircleDetailNotice = 1040;

22、群聊删除通知
ChatRoomDelNotice = 1041;

23、群聊信息变更通知
ChatRoomChangedNotice = 1042;

24、群二维码
PullChatRoomQrCodeTaskResultNotice = 1043;

4、服务端、客服客户端发给设备的指令类消息

1、给好友发消息任务
TalkToFriendTask = 1070;

2、发送朋友圈任务
PostSNSNewsTask = 1071;

3、主动添加好友任务
AddFriendsTask = 1072;

4、发送朋友圈任务后数据回传
PostSNSNewsTaskResultNotice = 1073;

5、删除朋友圈
DeleteSNSNewsTask = 1074;

6、客户端或者服务端接受好友请求通知
AcceptFriendAddRequestTask = 1075;

7、群发消息任务
WeChatGroupSendTask = 1076;

8、执行养号动作命令
WeChatMaintenanceTask = 1077;

9、请求图片或视频消息的详细内容
RequestTalkDetailTask = 1078;

10、服务端主动要求手机上传当前登录的微信二维码
PullWeChatQrCodeTask = 1079;

11、触发手机推送好友列表任务
TriggerFriendPushTask = 1080;

12、触发手机推送朋友圈列表任务
TriggerCirclePushTask = 1081;

13、朋友圈评论删除任务
CircleCommentDeleteTask = 1082;

14、朋友圈评论删除任务反馈
CircleCommentDeleteTaskResultNotice = 1083;

15、朋友圈评论回复任务
CircleCommentReplyTask = 1084;

16、朋友圈评论回复反馈
CircleCommentReplyTaskResultNotice = 1085;

17、通知手机将某个聊天窗口置为已读
TriggerMessageReadTask = 1086;

18、消息撤回
RevokeMessageTask = 1087;

19、转发消息
ForwardMessageTask = 1088;

20、通知手机推送聊天记录
TriggerHistoryMsgPushTask = 1089;

21、获取群聊二维码
PullChatRoomQrCodeTask = 1090;

5、服务端通知执行的命令

1、服务端通知pc客户版本升级
UpgradeAppNotice = 1093;

2、通知手机客户端软件升级
UpgradeDeviceAppNotice = 1094;

3、清粉任务
PostFriendDetectTask = 1095;

4、终止清粉任务
PostStopFriendDetectTask = 1096;

5、删除设备通知
PostDeleteDeviceNotice = 1097;

6、朋友圈点赞任务
PostMomentsPraiseTask = 1098;

7、停止朋友圈点赞任务
PostStopMomentsPraiseTask = 1099;

8、养号任务停止
PostStopWeChatMaintenanceTask = 1100;

9、修改备注任务
ModifyFriendMemoTask = 1101;

10、领取红包或转账
TakeLuckyMoneyTask = 1200;

11、获取指定好友朋友圈
PullFriendCircleTask = 1201;

12、获取朋友圈图片
PullCircleDetailTask = 1202;

13、单条朋友圈点赞任务
CircleLikeTask = 1203;

14、触发手机推送群聊列表
TriggerChatroomPushTask = 1210;

15、请求具体群聊的详细信息
RequestChatRoomInfoTask = 1211;

16、获取联系人详细信息（不一定是好友，如群聊成员）
RequestContactsInfoTask = 1212;

17、群聊管理
ChatRoomActionTask = 1213;

18、群内加好友
AddFriendInChatRoomTask = 1214;

19、通讯录加好友
AddFriendFromPhonebookTask = 1215;

20、删除好友
DeleteFriendTask = 1216;

21、发红包
SendLuckyMoneyTask = 1217;

22、获取聊天消息的原始内容（主要是xml内容）
RequestTalkContentTask = 1218;

23、返回聊天消息的原始内容
RequestTalkContentTaskResultNotice = 1219;

24、转发消息内容
ForwardMessageByContentTask = 1220;

6、手机端主动发出的交互类消息

1、手机端推送好友列表
FriendPushNotice = 2026;

2、手机端推送当前安装版本
PostDeviceInfoNotice = 2027;

3、手机端回传检测清粉好友数
PostFriendDetectCountNotice = 2028;

4、手机回传朋友圈数据
CirclePushNotice = 2029;

5、手机回传朋友圈点赞数量
PostMomentsPraiseCountNotice = 2030;

9、手机端推送群聊列表
ChatroomPushNotice = 2031;

7、手机端推送标签列表
ContactLabelInfoNotice = 2032;

8、推送历史消息
HistoryMsgPushNotice = 2033;

9、群成员（陌生人）信息
ChatRoomMembersNotice = 2034;

新增接口不再列举，具体请加微信happybabby110咨询

目前【聚客通】等等等scrm都是基于这个API二次开发的。
--------------------
咨询请加微信happybabby110

以下为PC端演示demo,老的demo源码已上传到github，二次开发有疑问请加微信tangjinjinwx
--------------------
![Image text](http://www.yunlauncher.com/kf/1.png)
![Image text](http://www.yunlauncher.com/kf/2.png)
![Image text](http://www.yunlauncher.com/kf/3.png)
![Image text](http://www.yunlauncher.com/kf/4.png)
![Image text](http://www.yunlauncher.com/kf/5.png)

新版本的演示demo请参考http://www.wlkankan.cn/cate41/183.html

微信sdk方案可实现功能说明
--------------
1、个人微信多账号管理，聚合便捷聊天

多个微信号同时登陆，多个微信号集中在一个窗口进行聊天，实现一人
多号同时沟通快速提升沟通效率；助力企业养号，提升微信营销效能

2、客服灵活分配

客服主管可自由分配微信号给指定客服，方便及时处理问题，也可随时转接给公司商务人员

3、自动化快捷回复

关键词触发，话术快捷回复；
话术提前准备，点击即可快捷回复；灵活高效、节省输入时间，提高客服沟通效率

4、聊天记录永久保存

微信文字、语音、图片、视频、文件等各种类型聊天内容实时上传云端存储，防止数据丢失

5、敏感词提醒

支持设置各类型敏感词汇库，敏感词预警，有效避免员工不规范和客户沟通


6、智能发布朋友圈，朋友圈自动点赞

完整的朋友圈营销解决方案，自定义内容推送、互动评论，提高商家品牌在朋友圈曝光度和活跃度，大幅度提升真实性，培育会员对品牌认可和忠诚度

7、微信自动群发，营销工具辅助推广

群发好友，群发群、微信转账、微信红包接收、分享小程序、分享名片、加通讯录好友、微信收藏、微信标签、清理僵尸粉、清理微信空间等！


8、会员沉淀，粉丝自动添加，好友自动通过

多种加好友方式，将买家沉淀到微信，打造私域流量池；智能欢迎语、智能回复、自动化操作，高效节省人力成本

9、粉丝标签化管理

根据好友情况，及时打标签，自定义标签，直接同步微信标签；智能标签化数据化管理，千人千面精准化营销


10、群管理

通过进群自动打招呼、发广告踢人、群公告、群加好友、建群等一系列群功能，大幅度提升群管理和营销效果

11、微信数据分析统计，重复好友统计

微信也能制定KPI，评估每个客服的工作量，合理分配，提高工作效率；员工微信工作量实时统计，可视化
报表，支持查看团队或个人统计数据
一键统计重复的好友、群分别隶属于哪些微信客服号，有效解决企业客户的判定问题及群梳理

技术交流QQ群
------------------
![Image text](http://www.yunlauncher.com/kf/qqqun.png)

QQ群：<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=32b5ffa731c3dc853dd58900d3381aa13209ee03ff64b4e962560c5ccd3c801d">787065908</a>
