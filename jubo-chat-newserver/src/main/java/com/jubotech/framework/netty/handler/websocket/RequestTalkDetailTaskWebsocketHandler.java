package com.jubotech.framework.netty.handler.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.protobuf.util.JsonFormat;
import com.jubotech.framework.netty.common.Constant;
import com.jubotech.framework.netty.utils.MessageUtil;
import com.jubotech.framework.netty.utils.NettyConnectionUtil;

import Jubo.JuLiao.IM.Wx.Proto.RequestTalkDetailTask.RequestTalkDetailTaskMessage;
import Jubo.JuLiao.IM.Wx.Proto.TaskResultNotice.TaskResultNoticeMessage;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;

@Service
public class RequestTalkDetailTaskWebsocketHandler{
	private  final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 请求图片或视频消息的详细内容
	 * @param ctx
	 * @param vo
	 */
    public  void handleMsg(ChannelHandlerContext ctx,TransportMessage vo, String contentJsonStr) {
        try {
        	log.info(contentJsonStr);
        	RequestTalkDetailTaskMessage.Builder bd = RequestTalkDetailTaskMessage.newBuilder();
        	JsonFormat.parser().merge(contentJsonStr, bd);
    		RequestTalkDetailTaskMessage req = bd.build();
        	//RequestTalkDetailTaskMessage req = vo.getContent().unpack(RequestTalkDetailTaskMessage.class);
        	//校验用户信息
			System.out.println("解包-用户的微信id：" + req.getWeChatId());
			 
			//将消息转发送给手机客户端
        	ChannelHandlerContext cx=	NettyConnectionUtil.getClientChannelHandlerContextByUserId(req.getWeChatId());
        	if(null != cx){
        		MessageUtil.sendMsg(cx, EnumMsgType.RequestTalkDetailTask, NettyConnectionUtil.getNettyId(ctx), null, req);
        		//告诉客户端消息已收到
        		TaskResultNoticeMessage resp = TaskResultNoticeMessage.newBuilder()
        				.setSuccess(true)
        				.setTaskId(vo.getId())
        		.build();
	    		MessageUtil.sendJsonMsg(ctx, EnumMsgType.TaskResultNotice, vo.getAccessToken(), vo.getId(), resp);
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