package com.jubotech.framework.netty.handler.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jubotech.framework.netty.utils.MessageUtil;

import Jubo.JuLiao.IM.Wx.Proto.ErrorMessageOuterClass.ErrorMessage;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;
@Service
public class ErrorHandler{
	Logger log = LoggerFactory.getLogger(getClass());
	 
    public  void handleMsg(ChannelHandlerContext ctx, TransportMessage vo) {
        try {
        	ErrorMessage req = vo.getContent().unpack(ErrorMessage.class);
        	log.info("错误信息:"+req.getErrorMsg());
        	//2、告诉客户端消息已收到
    		MessageUtil.sendMsg(ctx, EnumMsgType.MsgReceivedAck, vo.getAccessToken(), vo.getId(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}