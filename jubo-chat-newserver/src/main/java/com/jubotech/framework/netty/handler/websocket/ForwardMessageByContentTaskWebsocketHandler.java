package com.jubotech.framework.netty.handler.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.protobuf.util.JsonFormat;
import com.jubotech.framework.netty.common.Constant;
import com.jubotech.framework.netty.utils.MessageUtil;
import com.jubotech.framework.netty.utils.NettyConnectionUtil;

import Jubo.JuLiao.IM.Wx.Proto.ForwardMessageByContentTask.ForwardMessageByContentTaskMessage;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;

@Service
public class ForwardMessageByContentTaskWebsocketHandler{
	private  final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 转发消息内容---pc端经过服务端转发给手机端
	 * @param ctx
	 * @param vo
	 */
    public  void handleMsg(ChannelHandlerContext ctx, TransportMessage vo, String contentJsonStr) {
        try {
        	log.info(contentJsonStr);
        	ForwardMessageByContentTaskMessage.Builder bd = ForwardMessageByContentTaskMessage.newBuilder();
        	JsonFormat.parser().merge(contentJsonStr, bd);
        	ForwardMessageByContentTaskMessage req = bd.build();
        	//将消息转发送给手机客户端
        	ChannelHandlerContext cx=	NettyConnectionUtil.getClientChannelHandlerContextByUserId(req.getWeChatId());
        	if(null != cx){
        		MessageUtil.sendMsg(cx, EnumMsgType.ForwardMessageByContentTask, NettyConnectionUtil.getNettyId(ctx), null, req);
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