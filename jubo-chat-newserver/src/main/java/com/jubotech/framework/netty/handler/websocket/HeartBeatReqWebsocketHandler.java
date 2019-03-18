package com.jubotech.framework.netty.handler.websocket;

import org.springframework.stereotype.Service;

import com.jubotech.framework.netty.utils.MessageUtil;
import com.jubotech.framework.netty.utils.NettyConnectionUtil;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;
@Service
public class HeartBeatReqWebsocketHandler{
	 
	/**
	 * websocket 心跳接口
	 * @param ctx
	 * @param req
	 */
    public  void handleMsg(ChannelHandlerContext ctx,TransportMessage vo, String contentJsonStr) {
    	if(!NettyConnectionUtil.getNettyId(ctx).equals(vo.getAccessToken())){
    		MessageUtil.sendJsonErrMsg(ctx, EnumErrorCode.NoRight, "token过期，需重新登录!");
    	}else{
    		System.out.println("心跳token : "+vo.getAccessToken());
    		MessageUtil.sendJsonMsg(ctx, EnumMsgType.MsgReceivedAck, vo.getAccessToken(), vo.getId(), null);
    	}
    	
    }
}