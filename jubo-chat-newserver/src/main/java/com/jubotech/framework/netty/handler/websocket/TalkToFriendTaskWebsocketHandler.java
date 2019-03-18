package com.jubotech.framework.netty.handler.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.protobuf.util.JsonFormat;
import com.jubotech.framework.netty.common.Constant;
import com.jubotech.framework.netty.utils.MessageUtil;
import com.jubotech.framework.netty.utils.NettyConnectionUtil;

import Jubo.JuLiao.IM.Wx.Proto.TalkToFriendTask.TalkToFriendTaskMessage;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;

@Service
public class TalkToFriendTaskWebsocketHandler{
	private  final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 *  服务端、客服客户端发给设备的指令类消息
	 * @param ctx
	 * @param vo
	 */
    public  void handleMsg(ChannelHandlerContext ctx,TransportMessage vo, String contentJsonStr) {
        try {
        	log.info(contentJsonStr);
        	TalkToFriendTaskMessage.Builder bd = TalkToFriendTaskMessage.newBuilder();
    		JsonFormat.parser().merge(contentJsonStr, bd);
    		TalkToFriendTaskMessage req = bd.build();
    		System.out.println(JsonFormat.printer().print(req));
        	//TalkToFriendTaskMessage req = vo.getContent().unpack(TalkToFriendTaskMessage.class);
        	//将消息转发送给手机客户端
        	ChannelHandlerContext cx=	NettyConnectionUtil.getClientChannelHandlerContextByUserId(req.getWeChatId());
        	if(null != cx){
        		//.setMsgId(MsgIdBuilder.getId())// 发送给手机端的时候需要赋值，用于TalkToFriendTaskResultNotice中
        		MessageUtil.sendMsg(cx, EnumMsgType.TalkToFriendTask, NettyConnectionUtil.getNettyId(ctx), null, req);
        		// 告诉发送方消息已收到
    			MessageUtil.sendJsonMsg(ctx, EnumMsgType.MsgReceivedAck, vo.getAccessToken(), vo.getId(), null);
        	}else{
        		// 对方不在线
				MessageUtil.sendJsonErrMsg(ctx, EnumErrorCode.TargetNotOnline, Constant.ERROR_MSG_NOTONLINE);
        	}
        	 
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtil.sendJsonErrMsg(ctx, EnumErrorCode.InvalidParam, Constant.ERROR_MSG_DECODFAIL);
        }
    }
}