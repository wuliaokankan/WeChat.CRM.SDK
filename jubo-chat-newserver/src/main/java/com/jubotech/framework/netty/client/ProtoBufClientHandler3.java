package com.jubotech.framework.netty.client;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtoBufClientHandler3 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	
    	  
    	TransportMessage req = TransportMessage.newBuilder()
    			.setAccessToken("7fee97f0")//这里请填写真实的token，不然后台验证过不了
    			.setMsgType(EnumMsgType.HeartBeatReq)
    			.build();
    	
    		ctx.channel().writeAndFlush(req);
    	  
    	 
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	TransportMessage  base = (TransportMessage)msg;
    	System.out.println("客户端3读取："+base.getMsgTypeValue());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}