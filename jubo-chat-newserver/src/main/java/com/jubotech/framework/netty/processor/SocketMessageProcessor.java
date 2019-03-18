package com.jubotech.framework.netty.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jubotech.framework.netty.handler.socket.ChatRoomAddNoticeHandler;
import com.jubotech.framework.netty.handler.socket.ChatRoomChangedNoticeHandler;
import com.jubotech.framework.netty.handler.socket.ChatRoomDelNoticeHandler;
import com.jubotech.framework.netty.handler.socket.ChatRoomMembersNoticeHandler;
import com.jubotech.framework.netty.handler.socket.ChatroomPushNoticeHandler;
import com.jubotech.framework.netty.handler.socket.CircleCommentDeleteTaskResultNoticeHandler;
import com.jubotech.framework.netty.handler.socket.CircleCommentNoticeHandler;
import com.jubotech.framework.netty.handler.socket.CircleCommentReplyTaskResultNoticeHandler;
import com.jubotech.framework.netty.handler.socket.CircleDelNoticeHandler;
import com.jubotech.framework.netty.handler.socket.CircleDetailNoticeHandler;
import com.jubotech.framework.netty.handler.socket.CircleLikeNoticeHandler;
import com.jubotech.framework.netty.handler.socket.CircleNewPublishNoticeHandler;
import com.jubotech.framework.netty.handler.socket.CirclePushNoticeHandler;
import com.jubotech.framework.netty.handler.socket.ContactLabelAddNoticeHandler;
import com.jubotech.framework.netty.handler.socket.ContactLabelInfoNoticeHandler;
import com.jubotech.framework.netty.handler.socket.ContactsInfoNoticeHandler;
import com.jubotech.framework.netty.handler.socket.DeviceAuthReqHandler;
import com.jubotech.framework.netty.handler.socket.ErrorHandler;
import com.jubotech.framework.netty.handler.socket.FriendAddNoticeHandler;
import com.jubotech.framework.netty.handler.socket.FriendAddReqeustNoticeHandler;
import com.jubotech.framework.netty.handler.socket.FriendDelNoticeHandler;
import com.jubotech.framework.netty.handler.socket.FriendPushNoticeHandler;
import com.jubotech.framework.netty.handler.socket.FriendTalkNoticeHandler;
import com.jubotech.framework.netty.handler.socket.HeartBeatReqHandler;
import com.jubotech.framework.netty.handler.socket.HistoryMsgPushNoticeHandler;
import com.jubotech.framework.netty.handler.socket.MsgReceivedAckHandler;
import com.jubotech.framework.netty.handler.socket.PostFriendDetectCountNoticeHandler;
import com.jubotech.framework.netty.handler.socket.PostMessageReadNoticeHandler;
import com.jubotech.framework.netty.handler.socket.PostMomentsPraiseCountNoticeHandler;
import com.jubotech.framework.netty.handler.socket.PostSNSNewsTaskResultNoticeHandler;
import com.jubotech.framework.netty.handler.socket.PullChatRoomQrCodeTaskResultNoticeHandler;
import com.jubotech.framework.netty.handler.socket.PullWeChatQrCodeTaskResultNoticeHandler;
import com.jubotech.framework.netty.handler.socket.RequestTalkContentTaskResultNoticeHandler;
import com.jubotech.framework.netty.handler.socket.RequestTalkDetailTaskResultNoticeHandler;
import com.jubotech.framework.netty.handler.socket.TakeMoneyTaskResultNoticeHandler;
import com.jubotech.framework.netty.handler.socket.TalkToFriendTaskResultNoticeHandler;
import com.jubotech.framework.netty.handler.socket.TaskResultNoticeHandler;
import com.jubotech.framework.netty.handler.socket.WeChatOfflineNoticeHandler;
import com.jubotech.framework.netty.handler.socket.WeChatOnlineNoticeHandler;
import com.jubotech.framework.netty.handler.socket.WeChatTalkToFriendNoticeHandler;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息处理分发器
 */

@Service
public class SocketMessageProcessor {
	
	private static final Logger log = LoggerFactory.getLogger(SocketMessageProcessor.class);
	
	@Autowired
	private DeviceAuthReqHandler deviceAuthReqHandler;
	@Autowired
	private HeartBeatReqHandler heartBeatReqHandler;
	@Autowired
	private WeChatOnlineNoticeHandler  weChatOnlineNoticeHandler;
	@Autowired
	private WeChatOfflineNoticeHandler  weChatOfflineNoticeHandler;
	@Autowired
	private FriendTalkNoticeHandler  friendTalkNoticeHandler;
	@Autowired
	private TaskResultNoticeHandler taskResultNoticeHandler;
	@Autowired
	private FriendAddNoticeHandler friendAddNoticeHandler;
	@Autowired
	private FriendDelNoticeHandler friendDelNoticeHandler;
	@Autowired
	private MsgReceivedAckHandler  msgReceivedAckHandler;
	@Autowired
	private WeChatTalkToFriendNoticeHandler  weChatTalkToFriendNoticeHandler;
	@Autowired
	private FriendAddReqeustNoticeHandler  friendAddReqeustNoticeHandler;
	@Autowired
	private TalkToFriendTaskResultNoticeHandler  talkToFriendTaskResultNoticeHandler;
	@Autowired
	private RequestTalkDetailTaskResultNoticeHandler  requestTalkDetailTaskResultNoticeHandler;
	@Autowired
	private PullWeChatQrCodeTaskResultNoticeHandler  pullWeChatQrCodeTaskResultNoticeHandler;
	@Autowired
	private CircleNewPublishNoticeHandler  circleNewPublishNoticeHandler;
	@Autowired
	private CircleDelNoticeHandler  circleDelNoticeHandler;
	@Autowired
	private CircleLikeNoticeHandler circleLikeNoticeHandler;
	@Autowired
	private CircleCommentNoticeHandler  circleCommentNoticeHandler;
	@Autowired
	private CircleCommentReplyTaskResultNoticeHandler circleCommentReplyTaskResultNoticeHandler;
	@Autowired
	private PostMessageReadNoticeHandler  postMessageReadNoticeHandler;
	@Autowired
	private PostSNSNewsTaskResultNoticeHandler  postSNSNewsTaskResultNoticeHandler;
	@Autowired
	private FriendPushNoticeHandler  friendPushNoticeHandler;
	@Autowired
	private PostFriendDetectCountNoticeHandler  postFriendDetectCountNoticeHandler;
	@Autowired
	private CirclePushNoticeHandler  circlePushNoticeHandler;
	@Autowired
	private PostMomentsPraiseCountNoticeHandler postMomentsPraiseCountNoticeHandler;
	@Autowired
	private CircleCommentDeleteTaskResultNoticeHandler circleCommentDeleteTaskResultNoticeHandler;
	@Autowired
	private CircleDetailNoticeHandler circleDetailNoticeHandler;
	@Autowired
	private ContactsInfoNoticeHandler contactsInfoNoticeHandler;
	@Autowired
	private ChatRoomAddNoticeHandler chatRoomAddNoticeHandler;
	@Autowired
	private ContactLabelAddNoticeHandler contactLabelAddNoticeHandler;
	@Autowired
	private TakeMoneyTaskResultNoticeHandler takeMoneyTaskResultNoticeHandler;
	@Autowired
	private ChatRoomDelNoticeHandler chatRoomDelNoticeHandler;
	@Autowired
	private ChatRoomChangedNoticeHandler  chatRoomChangedNoticeHandler;
	@Autowired
	private PullChatRoomQrCodeTaskResultNoticeHandler pullChatRoomQrCodeTaskResultNoticeHandler;
	@Autowired
	private ChatroomPushNoticeHandler chatroomPushNoticeHandler;
	@Autowired
	private ContactLabelInfoNoticeHandler contactLabelInfoNoticeHandler;
	@Autowired
	private HistoryMsgPushNoticeHandler historyMsgPushNoticeHandler;
	@Autowired
	private ChatRoomMembersNoticeHandler chatRoomMembersNoticeHandler;
	@Autowired
	private RequestTalkContentTaskResultNoticeHandler requestTalkContentTaskResultNoticeHandler;
	@Autowired
	private ErrorHandler  errorHandler;

	public  void  handler(ChannelHandlerContext ctx, Object msg) {
		TransportMessage msgVo = (TransportMessage) msg;
		switch (msgVo.getMsgType()) {
		case HeartBeatReq: {// 客户端发送的心跳包
			log.debug("socket:msgtype=HeartBeatReq");
			heartBeatReqHandler.handleMsg(ctx, msgVo);
			break;
		}
		case MsgReceivedAck: {//消息接收确认回复（接收或拒绝接收）
			log.debug("socket:msgtype=MsgReceivedAck");
			msgReceivedAckHandler.handleMsg(ctx, msgVo);
			break;
		}
		case DeviceAuthReq: {// 设备客户端授权  设备(手机客户端、客服客户端)获取通信token请求
			log.debug("socket:msgtype=DeviceAuthReq");
			deviceAuthReqHandler.handleMsg(ctx, msgVo);
			break;
		}
		case WeChatOnlineNotice: {// 手机客户端微信上线通知
			log.debug("socket:msgtype=WeChatOnlineNotice");
			weChatOnlineNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
		case WeChatOfflineNotice: {// 手机客户端微信下线通知
			log.debug("socket:msgtype=WeChatOfflineNotice");
			weChatOfflineNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
		case FriendTalkNotice: {// 微信好友发来聊天消息
			log.debug("socket:msgtype=FriendTalkNotice");
			friendTalkNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
		case TaskResultNotice: {// 任务执行结果通知
			log.debug("socket:msgtype=TaskResultNotice");
			taskResultNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
		case WeChatTalkToFriendNotice: {// 手机上回复好友的聊天消息
			log.debug("socket:msgtype=WeChatTalkToFriendNotice");
			weChatTalkToFriendNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case FriendAddReqeustNotice: {//有好友请求添加好友的通知
        	log.debug("socket:msgtype=FriendAddReqeustNotice");
        	friendAddReqeustNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case FriendAddNotice: {// 微信个人号新增好友通知
        	log.debug("socket:msgtype=FriendAddNotice");
			friendAddNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case FriendDelNotice: {// 微信个人号移除好友通知
        	log.debug("socket:msgtype=FriendDelNotice");
			friendDelNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case TalkToFriendTaskResultNotice: {//手机端向服务端通知聊天执行结果
        	log.debug("socket:msgtype=TalkToFriendTaskResultNotice");
        	talkToFriendTaskResultNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case RequestTalkDetailTaskResultNotice : {//图片或视频消息的详细内容结果
        	log.debug("socket:msgtype=RequestTalkDetailTaskResultNotice");
        	requestTalkDetailTaskResultNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  PullWeChatQrCodeTaskResultNotice : {//上传手机客户端上微信的二维码
        	log.debug("socket:msgtype=PullWeChatQrCodeTaskResultNotice");
        	pullWeChatQrCodeTaskResultNoticeHandler.handleMsg(ctx, msgVo);
			break;
		} 
        case  CircleNewPublishNotice : {//手机上发送了朋友圈通知
        	log.debug("socket:msgtype=CircleNewPublishNotice");
        	circleNewPublishNoticeHandler.handleMsg(ctx, msgVo);
			break;
		} 
        case  CircleDelNotice : {//手机上删除朋友圈通知
        	log.debug("socket:msgtype=CircleDelNotice");
        	circleDelNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  CircleLikeNotice : {//手机检测到有人点赞/取消点赞通知
        	log.debug("socket:msgtype=CircleLikeNotice");
        	circleLikeNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  CircleCommentNotice  : {//手机检测到有人评论/删除朋友圈通知
        	log.debug("socket:msgtype=CircleCommentNotice");
        	circleCommentNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  CircleCommentDeleteTaskResultNotice  : {//朋友圈评论删除任务反馈
        	log.debug("socket:msgtype=CircleCommentDeleteTaskResultNotice");
        	circleCommentDeleteTaskResultNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
 
        case  CircleCommentReplyTaskResultNotice  : {//手机端向服务端发送回复朋友圈评论任务反馈
        	log.debug("socket:msgtype=CircleCommentReplyTaskResultNotice");
      	    circleCommentReplyTaskResultNoticeHandler.handleMsg(ctx, msgVo);
			break;
		} 
        case  PostSNSNewsTaskResultNotice   : {//手机端接收发送朋友圈任务指令后数据回传
        	log.debug("socket:msgtype=PostSNSNewsTaskResultNotice");
      	    postSNSNewsTaskResultNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  PostMessageReadNotice  : {//手机端消息标记为已读
        	log.debug("socket:msgtype=PostMessageReadNotice");
        	postMessageReadNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  ContactsInfoNotice  : {// 联系人信息上传
        	log.debug("socket:msgtype=ContactsInfoNotice");
        	contactsInfoNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  ChatRoomAddNotice  : {//群聊新增通知
        	log.debug("socket:msgtype=ChatRoomAddNotice");
        	chatRoomAddNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  ContactLabelAddNotice  : {//联系人标签新增，修改通知
        	log.debug("socket:msgtype=ContactLabelAddNotice");
        	contactLabelAddNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  TakeMoneyTaskResultNotice  : {//收钱任务执行结果通知
        	log.debug("socket:msgtype=TakeMoneyTaskResultNotice");
        	takeMoneyTaskResultNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  CircleDetailNotice  : {//朋友圈图片上传
        	log.debug("socket:msgtype=CircleDetailNotice");
        	circleDetailNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  ChatRoomDelNotice  : {//群聊删除通知
        	log.debug("socket:msgtype=ChatRoomDelNotice");
        	chatRoomDelNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  ChatRoomChangedNotice  : {//群聊信息变更通知
        	log.debug("socket:msgtype=ChatRoomChangedNotice");
        	chatRoomChangedNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  PullChatRoomQrCodeTaskResultNotice  : {//群二维码
        	log.debug("socket:msgtype=PullChatRoomQrCodeTaskResultNotice");
        	pullChatRoomQrCodeTaskResultNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  FriendPushNotice   : {//手机端推送好友列表
        	log.debug("socket:msgtype=FriendPushNotice");
        	friendPushNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  PostDeviceInfoNotice    : {//手机端推送当前安装版本(暂时不理)
        	log.debug("socket:msgtype=PostDeviceInfoNotice");
        	System.out.println("PostDeviceInfoNotice...");
			break;
		}
        case  PostFriendDetectCountNotice   : {//手机机端回传检测清粉好友数
        	log.debug("socket:msgtype=PostFriendDetectCountNotice");
        	postFriendDetectCountNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  CirclePushNotice: {//手机回传朋友圈数据
        	log.debug("socket:msgtype=CirclePushNotice");
        	circlePushNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  PostMomentsPraiseCountNotice: {//手机回传朋友圈点赞数量
        	log.debug("socket:msgtype=PostMomentsPraiseCountNotice");
        	postMomentsPraiseCountNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  ChatroomPushNotice: {// 手机端推送群聊列表
        	log.debug("socket:msgtype=ChatroomPushNotice");
        	chatroomPushNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  ContactLabelInfoNotice: {// 手机端推送标签列表
        	log.debug("socket:msgtype=ContactLabelInfoNotice");
        	contactLabelInfoNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  HistoryMsgPushNotice: {// 推送历史消息
        	log.debug("socket:msgtype=HistoryMsgPushNotice");
        	historyMsgPushNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  ChatRoomMembersNotice: {// 群成员（陌生人）信息 
        	log.debug("socket:msgtype=ChatRoomMembersNotice");
        	chatRoomMembersNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
        case  RequestTalkContentTaskResultNotice: {// 返回聊天消息的原始内容
        	log.debug("socket:msgtype=RequestTalkContentTaskResultNotice");
        	requestTalkContentTaskResultNoticeHandler.handleMsg(ctx, msgVo);
			break;
		}
		case Error: {// 错误
			log.debug("socket:msgtype=Error");
			errorHandler.handleMsg(ctx, msgVo);
			break;
		}
		default:
			log.debug("socket:msgtype=UnknownMsg - 没有找到消息处理器！！");
			break;
		} 
	}
}
