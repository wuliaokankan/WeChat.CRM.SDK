package com.jubotech.framework.netty.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.googlecode.protobuf.format.JsonFormat;
import com.jubotech.framework.netty.handler.websocket.AcceptFriendAddRequestTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.AccountLogoutNoticeWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.AddFriendFromPhonebookTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.AddFriendInChatRoomTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.AddFriendsTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.ChatRoomActionTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.CircleCommentDeleteTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.CircleCommentReplyTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.CircleLikeTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.DeleteFriendTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.DeleteSNSNewsTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.DeviceAuthReqWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.ForwardMessageByContentTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.ForwardMessageTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.GetWeChatsReqWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.HeartBeatReqWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.MsgReceivedAckWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.PostFriendDetectTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.PostMomentsPraiseTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.PostSNSNewsTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.PostStopFriendDetectTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.PostStopMomentsPraiseTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.PostStopWeChatMaintenanceTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.PullCircleDetailTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.PullFriendCircleTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.PullWeChatQrCodeTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.RequestChatRoomInfoTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.RequestContactsInfoTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.RequestTalkContentTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.RequestTalkDetailTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.RevokeMessageTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.SendLuckyMoneyTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.TalkToFriendTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.TriggerChatroomPushTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.TriggerCirclePushTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.TriggerFriendPushTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.TriggerHistoryMsgPushTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.TriggerMessageReadTaskWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.WeChatGroupSendTaskWesocketHandler;
import com.jubotech.framework.netty.handler.websocket.WeChatLoginNoticeWebsocketHandler;
import com.jubotech.framework.netty.handler.websocket.WeChatMaintenanceTaskWebsocketHandler;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 消息处理分发器
 */
@Service
public class WebSocketMessageProcessor {
	private static final Logger log = LoggerFactory.getLogger(WebSocketMessageProcessor.class);
	
	@Autowired
	private HeartBeatReqWebsocketHandler heartBeatReqWebsocketHandler;
	@Autowired
	private GetWeChatsReqWebsocketHandler getWeChatsReqWebsocketHandler;
	@Autowired
	private DeviceAuthReqWebsocketHandler deviceAuthReqWebsocketHandler;
	@Autowired
	private AccountLogoutNoticeWebsocketHandler accountLogoutNoticeWebsocketHandler;
	@Autowired
	private WeChatLoginNoticeWebsocketHandler weChatLoginNoticeWebsocketHandler;
	@Autowired
	private MsgReceivedAckWebsocketHandler msgReceivedAckWebsocketHandler;
	@Autowired
	private TalkToFriendTaskWebsocketHandler talkToFriendTaskWebsocketHandler;
	@Autowired
	private RequestTalkDetailTaskWebsocketHandler requestTalkDetailTaskWebsocketHandler;
	@Autowired
	private AddFriendsTaskWebsocketHandler addFriendsTaskWebsocketHandler;
	@Autowired
	private PostSNSNewsTaskWebsocketHandler postSNSNewsTaskWebsocketHandler;
	@Autowired
	private DeleteSNSNewsTaskWebsocketHandler deleteSNSNewsTaskWebsocketHandler;
	@Autowired
	private AcceptFriendAddRequestTaskWebsocketHandler acceptFriendAddRequestTaskWebsocketHandler;
	@Autowired
	private WeChatGroupSendTaskWesocketHandler weChatGroupSendTaskWesocketHandler;
	@Autowired
	private TriggerFriendPushTaskWebsocketHandler triggerFriendPushTaskWebsocketHandler;
	@Autowired
	private PostFriendDetectTaskWebsocketHandler postFriendDetectTaskWebsocketHandler;
	@Autowired
	private TriggerCirclePushTaskWebsocketHandler triggerCirclePushTaskWebsocketHandler;
	@Autowired
	private CircleCommentDeleteTaskWebsocketHandler  circleCommentDeleteTaskWebsocketHandler;
	@Autowired
	private CircleCommentReplyTaskWebsocketHandler circleCommentReplyTaskWebsocketHandler;
	@Autowired
	private WeChatMaintenanceTaskWebsocketHandler weChatMaintenanceTaskWebsocketHandler;
	@Autowired
	private PostMomentsPraiseTaskWebsocketHandler postMomentsPraiseTaskWebsocketHandler;
	@Autowired
	private PostStopFriendDetectTaskWebsocketHandler postStopFriendDetectTaskWebsocketHandler;
	@Autowired
	private PostStopMomentsPraiseTaskWebsocketHandler  postStopMomentsPraiseTaskWebsocketHandler;
	@Autowired
	private PostStopWeChatMaintenanceTaskWebsocketHandler postStopWeChatMaintenanceTaskWebsocketHandler;
	@Autowired
	private TriggerMessageReadTaskWebsocketHandler triggerMessageReadTaskWebsocketHandler;
	@Autowired
	private RevokeMessageTaskWebsocketHandler  revokeMessageTaskWebsocketHandler;
	@Autowired
	private ForwardMessageTaskWebsocketHandler forwardMessageTaskWebsocketHandler;
	@Autowired
	private TriggerHistoryMsgPushTaskWebsocketHandler triggerHistoryMsgPushTaskWebsocketHandler;
	@Autowired
	private PullFriendCircleTaskWebsocketHandler  pullFriendCircleTaskWebsocketHandler;
	@Autowired
	private PullCircleDetailTaskWebsocketHandler  pullCircleDetailTaskWebsocketHandler;
	@Autowired
	private CircleLikeTaskWebsocketHandler circleLikeTaskWebsocketHandler;
	@Autowired
	private TriggerChatroomPushTaskWebsocketHandler triggerChatroomPushTaskWebsocketHandler;
	@Autowired
	private RequestChatRoomInfoTaskWebsocketHandler requestChatRoomInfoTaskWebsocketHandler;
	@Autowired
	private RequestContactsInfoTaskWebsocketHandler requestContactsInfoTaskWebsocketHandler;
	@Autowired
	private ChatRoomActionTaskWebsocketHandler chatRoomActionTaskWebsocketHandler;
	@Autowired
	private AddFriendInChatRoomTaskWebsocketHandler addFriendInChatRoomTaskWebsocketHandler;
	@Autowired
	private AddFriendFromPhonebookTaskWebsocketHandler addFriendFromPhonebookTaskWebsocketHandler;
	@Autowired
	private DeleteFriendTaskWebsocketHandler deleteFriendTaskWebsocketHandler;
	@Autowired
	private SendLuckyMoneyTaskWebsocketHandler sendLuckyMoneyTaskWebsocketHandler;
	@Autowired
	private RequestTalkContentTaskWebsocketHandler requestTalkContentTaskWebsocketHandler;
	@Autowired
	private ForwardMessageByContentTaskWebsocketHandler forwardMessageByContentTaskWebsocketHandler;
	@Autowired
	private PullWeChatQrCodeTaskWebsocketHandler  pullWeChatQrCodeTaskWebsocketHandler;
	public void handler(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
		String text = msg.text();
		if (!StringUtils.isEmpty(text)) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(text);
				Object objMsgType = jsonObject.get("MsgType");
				if (null != objMsgType) {
					TransportMessage.Builder builder = TransportMessage.newBuilder();
					try {
					    String jsonFormat = jsonObject.toJSONString();
						JsonFormat.merge(jsonFormat, builder);
					} catch (Exception e) {
						log.debug("传入参数==="+jsonObject);
						log.debug("websocket传入json字符串转换proto失败！！");
						e.printStackTrace();
					}
					TransportMessage vo = builder.build();
					String contentJsonStr = jsonObject.getString("Content"); 
					switch (objMsgType.toString()) {// 消息类型
						case "HeartBeatReq": {// 客户端发送的心跳包
							log.debug("websocket:msgtype=HeartBeatReq。。。。。");
							heartBeatReqWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "MsgReceivedAck": {// 消息接收确认回复
							log.debug("websocket:msgtype=MsgReceivedAck。。。。。");
							msgReceivedAckWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "DeviceAuthReq": {// 设备(手机客户端、客服客户端)获取通信token请求
							log.debug("websocket:msgtype=DeviceAuthReq。。。。。");
							deviceAuthReqWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "AccountForceOfflineNotice": {// 账号强制下线通知(用不到)
							log.debug("websocket:msgtype=AccountForceOfflineNotice。。。。。");
							break;
						}
						case "GetWeChatsReq": {// 拉取当前微信个人号列表请求（立即）
							log.debug("websocket:msgtype=GetWeChatsReq。。。。。");
							getWeChatsReqWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "AccountLogoutNotice": {// 客户端退出通知
							log.debug("websocket:msgtype=AccountLogoutNotice。。。。。");
							accountLogoutNoticeWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "WeChatLoginNotice": {// 客户端选择微信号登陆/登出通知
							log.debug("websocket:msgtype=WeChatLoginNotice。。。。。");
							weChatLoginNoticeWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "TalkToFriendTask": {// 给好友发消息任务
							log.debug("websocket:msgtype=TalkToFriendTask。。。。。");
							talkToFriendTaskWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "RequestTalkDetailTask": {// 请求图片或视频消息的详细内容
							log.debug("websocket:msgtype=RequestTalkDetailTask。。。。。");
							requestTalkDetailTaskWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "AddFriendsTask": {// 主动添加好友任务
							log.debug("websocket:msgtype=AddFriendsTask。。。。。");
							addFriendsTaskWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "PostSNSNewsTask": {// 发送朋友圈任务
							log.debug("websocket:msgtype=PostSNSNewsTask。。。。。");
							postSNSNewsTaskWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "DeleteSNSNewsTask": {// 删除朋友圈
							log.debug("websocket:msgtype=DeleteSNSNewsTask。。。。。");
							deleteSNSNewsTaskWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "AcceptFriendAddRequestTask": {// 客户端或者服务端接受好友请求通知
							log.debug("websocket:msgtype=AcceptFriendAddRequestTask。。。。。");
							acceptFriendAddRequestTaskWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "WeChatGroupSendTask": {// 群发消息任务
							log.debug("websocket:msgtype=WeChatGroupSendTask。。。。。");
							weChatGroupSendTaskWesocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "WeChatMaintenanceTask": {// 执行养号动作命令
							log.debug("websocket:msgtype=WeChatMaintenanceTask。。。。。");
							weChatMaintenanceTaskWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "PostStopWeChatMaintenanceTask": {// 养号任务停止
							log.debug("websocket:msgtype=PostStopWeChatMaintenanceTask。。。。。");
							postStopWeChatMaintenanceTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "TriggerMessageReadTask": {// 通知手机将某个聊天窗口置为已读
							log.debug("websocket:msgtype=TriggerMessageReadTask。。。。。");
							triggerMessageReadTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "PostFriendDetectTask": {// 清粉任务
							log.debug("websocket:msgtype=PostFriendDetectTask。。。。。");
							postFriendDetectTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "PostStopFriendDetectTask": {// 终止清粉任务
							log.debug("websocket:msgtype=PostStopFriendDetectTask。。。。。");
							postStopFriendDetectTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "PostDeleteDeviceNotice": {// 删除设备通知(没用到)
							log.debug("websocket:msgtype=PostDeleteDeviceNotice。。。。。");
							break;
						}
						case "TriggerFriendPushTask": {// 触发手机推送好友列表任务
							log.debug("websocket:msgtype=TriggerFriendPushTask。。。。。");
							triggerFriendPushTaskWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "TriggerCirclePushTask": {// 触发手机推送朋友圈列表任务
							log.debug("websocket:msgtype=TriggerCirclePushTask。。。。。");
							triggerCirclePushTaskWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "PostMomentsPraiseTask": {// 朋友圈点赞任务
							log.debug("websocket:msgtype=PostMomentsPraiseTask。。。。。");
							postMomentsPraiseTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "PostStopMomentsPraiseTask": {// 停止朋友圈点赞任务
							log.debug("websocket:msgtype=PostStopMomentsPraiseTask。。。。。");
							postStopMomentsPraiseTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "CircleCommentDeleteTask": {// 朋友圈评论删除任务
							log.debug("websocket:msgtype=CircleCommentDeleteTask。。。。。");
							circleCommentDeleteTaskWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "CircleCommentReplyTask": {// 朋友圈评论回复任务
							log.debug("websocket:msgtype=CircleCommentReplyTask。。。。。");
							circleCommentReplyTaskWebsocketHandler.handleMsg(ctx, vo,contentJsonStr);
							break;
						}
						case "RevokeMessageTask": {//  消息撤回
							log.debug("websocket:msgtype=RevokeMessageTask。。。。。");
							revokeMessageTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "ForwardMessageTask": {//  转发消息
							log.debug("websocket:msgtype=ForwardMessageTask。。。。。");
							forwardMessageTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "TriggerHistoryMsgPushTask": {//通知手机推送聊天记录  
							log.debug("websocket:msgtype=TriggerHistoryMsgPushTask。。。。。");
							triggerHistoryMsgPushTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "PullChatRoomQrCodeTask": {// 获取群聊二维码 
							log.debug("websocket:msgtype=PullChatRoomQrCodeTask。。。。。");
							break;
						}
						case "ModifyFriendMemoTask": {// 修改备注任务
							log.debug("websocket:msgtype=ModifyFriendMemoTask。。。。。");
							break;
						}
						case "TakeLuckyMoneyTask": {//领取红包或转账
							log.debug("websocket:msgtype=TakeLuckyMoneyTask。。。。。");
							break;
						}
						case "PullFriendCircleTask": {// 获取指定好友朋友圈
							log.debug("websocket:msgtype=PullFriendCircleTask。。。。。");
							pullFriendCircleTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "PullCircleDetailTask": {// 获取朋友圈图片
							log.debug("websocket:msgtype=PullCircleDetailTask。。。。。");
							pullCircleDetailTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "CircleLikeTask": {// 单条朋友圈点赞任务
							log.debug("websocket:msgtype=CircleLikeTask。。。。。");
							circleLikeTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "TriggerChatroomPushTask": {// 触发手机推送群聊列表
							log.debug("websocket:msgtype=TriggerChatroomPushTask。。。。。");
							triggerChatroomPushTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "RequestChatRoomInfoTask": {// 请求具体群聊的详细信息
							log.debug("websocket:msgtype=RequestChatRoomInfoTask。。。。。");
							requestChatRoomInfoTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "RequestContactsInfoTask": {//  获取联系人详细信息（不一定是好友，如群聊成员）
							log.debug("websocket:msgtype=RequestContactsInfoTask。。。。。");
							requestContactsInfoTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "ChatRoomActionTask": {// 群聊管理
							log.debug("websocket:msgtype=ChatRoomActionTask。。。。。");
							chatRoomActionTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "AddFriendInChatRoomTask": {// 群内加好友
							log.debug("websocket:msgtype=AddFriendInChatRoomTask。。。。。");
							addFriendInChatRoomTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "AddFriendFromPhonebookTask": {//通讯录加好友 
							log.debug("websocket:msgtype=AddFriendFromPhonebookTask。。。。。");
							addFriendFromPhonebookTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "DeleteFriendTask": {//删除好友
							log.debug("websocket:msgtype=DeleteFriendTask。。。。。");
							deleteFriendTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "SendLuckyMoneyTask": {//发红包 
							log.debug("websocket:msgtype=SendLuckyMoneyTask。。。。。");
							sendLuckyMoneyTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "RequestTalkContentTask": {// 获取聊天消息的原始内容（主要是xml内容）
							log.debug("websocket:msgtype=RequestTalkContentTask。。。。。");
							requestTalkContentTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "ForwardMessageByContentTask": {//转发消息内容
							log.debug("websocket:msgtype=ForwardMessageByContentTask。。。。。");
							forwardMessageByContentTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "PullWeChatQrCodeTask": {//主动要求手机上传当前登录的微信二维码
							log.debug("websocket:msgtype=PullWeChatQrCodeTask。。。。。");
							pullWeChatQrCodeTaskWebsocketHandler.handleMsg(ctx, vo, contentJsonStr);
							break;
						}
						case "Error": {// 将错误单独提升为一种消息类型
							log.debug("websocket:msgtype=Error。。。。。");
							break;
						}
						default: {
							log.debug("websocket:msgtype=UnknownMsg - 没有找到消息处理器！！");
							break;
						}
					}
				} else {
					log.debug("消息类型传入错误！");
					ctx.channel().writeAndFlush(new TextWebSocketFrame("消息类型传入错误！"));
				}
			} catch (Exception e) {
				log.debug("参数传入错误！");
				e.printStackTrace();
				ctx.channel().writeAndFlush(new TextWebSocketFrame("参数传入错误！"));
			}
		}
	}
}
