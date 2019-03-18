package com.jubotech.framework.netty.client;

import com.google.protobuf.Any;

import Jubo.JuLiao.IM.Wx.Proto.DeviceAuthReq.DeviceAuthReqMessage;
import Jubo.JuLiao.IM.Wx.Proto.DeviceAuthReq.DeviceAuthReqMessage.EnumAuthType;
import Jubo.JuLiao.IM.Wx.Proto.DeviceAuthRsp.DeviceAuthRspMessage;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import sun.misc.BASE64Encoder;

public class ProtoBufClientHandler1 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	DeviceAuthReqMessage msg = DeviceAuthReqMessage.newBuilder()
    			.setAuthType(EnumAuthType.Username)
    			.setCredential(new BASE64Encoder().encode("yutou:123456".getBytes()))
    			.build();
    	 
    	TransportMessage req = TransportMessage.newBuilder()
    			.setAccessToken("")
    			.setMsgType(EnumMsgType.DeviceAuthReq)
    			.setContent(Any.pack(msg))
    			.build();
    	
    	 
    		ctx.channel().writeAndFlush(req);
    	 
    	 
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	TransportMessage  base = (TransportMessage)msg;
    	
    	DeviceAuthRspMessage vo = base.getContent().unpack(DeviceAuthRspMessage.class);
    	System.out.println("客户端1服务端返回token="+vo.getAccessToken()+
    			" 昵称="+vo.getExtra().getNickName()+
    			" 客户名="+vo.getExtra().getSupplierName()
    			);
    	 
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}