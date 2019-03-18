package com.jubotech.framework.netty.handler.socket;

import org.springframework.stereotype.Service;

import com.jubotech.framework.netty.utils.MessageUtil;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;
@Service
public class MsgReceivedAckHandler{
	/**
	 * 消息接收确认回复（接收或拒绝接收）
	 * @param ctx
	 * @param vo
	 */
    public  void handleMsg(ChannelHandlerContext ctx, TransportMessage vo) {
        try {
        	//告诉客户端消息已收到
    		MessageUtil.sendMsg(ctx, EnumMsgType.MsgReceivedAck, vo.getAccessToken(), vo.getId(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}