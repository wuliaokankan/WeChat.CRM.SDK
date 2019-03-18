package com.jubotech.framework.netty.handler.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jubotech.framework.netty.utils.MessageUtil;
import com.jubotech.framework.netty.utils.NettyConnectionUtil;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumErrorCode;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;
@Service
public class HeartBeatReqHandler{
	private  final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 心跳接口
	 * @param ctx
	 * @param req
	 */
    public  void handleMsg(ChannelHandlerContext ctx, TransportMessage req) {
    	
    	if(!NettyConnectionUtil.getNettyId(ctx).equals(req.getAccessToken())){
    		MessageUtil.sendErrMsg(ctx, EnumErrorCode.NoRight, "token过期，需重新登录!");
    	}else{
    		log.info("心跳token : "+req.getAccessToken());
    		MessageUtil.sendMsg(ctx, EnumMsgType.MsgReceivedAck, req.getAccessToken(), req.getId(), null);
    	}
    	
    	
    }
}