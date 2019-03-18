package com.jubotech.framework.netty.handler.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.protobuf.util.JsonFormat;
import com.jubotech.framework.netty.common.Constant;
import com.jubotech.framework.netty.utils.MessageUtil;
import com.jubotech.framework.netty.utils.NettyConnectionUtil;

import Jubo.JuLiao.IM.Wx.Proto.RequestContactsInfoTask.RequestContactsInfoTaskMessage;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;

@Service
public class RequestContactsInfoTaskWebsocketHandler{
	private  final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 获取联系人详细信息（不一定是好友，如群聊成员）---pc端经过服务端转发给手机端
	 * @param ctx
	 * @param vo
	 */
    public  void handleMsg(ChannelHandlerContext ctx, TransportMessage vo, String contentJsonStr) {
        try {
        	log.info(contentJsonStr);
        	RequestContactsInfoTaskMessage.Builder bd = RequestContactsInfoTaskMessage.newBuilder();
        	JsonFormat.parser().merge(contentJsonStr, bd);
        	RequestContactsInfoTaskMessage req = bd.build();
        	//将消息转发送给手机客户端
        	ChannelHandlerContext cx=	NettyConnectionUtil.getClientChannelHandlerContextByUserId(req.getWeChatId());
        	if(null != cx){
        		MessageUtil.sendMsg(cx, EnumMsgType.RequestContactsInfoTask, NettyConnectionUtil.getNettyId(ctx), null, req);
        		//告诉对方消息已收到
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