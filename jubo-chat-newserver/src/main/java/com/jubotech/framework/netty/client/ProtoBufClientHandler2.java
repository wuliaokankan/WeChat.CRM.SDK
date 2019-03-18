package com.jubotech.framework.netty.client;

import com.google.protobuf.Any;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumGender;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.EnumMsgType;
import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import Jubo.JuLiao.IM.Wx.Proto.WeChatOnlineNotice.WeChatOnlineNoticeMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtoBufClientHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	WeChatOnlineNoticeMessage msg = WeChatOnlineNoticeMessage.newBuilder()
    			.setWeChatId("wxid_036kg5tnlfoa22")
    			.setWeChatNo("dandanenheng0110")
    			.setWeChatNick("涵涵")
    			.setGender(EnumGender.Female)
    			.setCountry("CN")
    			.setProvince("奥地利")
    			.setCity("施蒂利亚")
    			.setAvatar("http://wx.qlogo.cn/mmhead/ver_1/TM6r8cCfoe8t44Vic5smP3rJONS7noKCBWalpWp2dQcPJa9GeKgL1sPicgmkh6BDW66tzm68ySjf5RIDvnJ25HSZ5zglBTYuvKnZQKCYc1qBs/96")
    			.build();
    	  
    	TransportMessage req = TransportMessage.newBuilder()
    			.setAccessToken("7fee97f0")//这里请填写真实的token，不然后台验证过不了
    			.setMsgType(EnumMsgType.WeChatOnlineNotice)
    			.setContent(Any.pack(msg))
    			.build();
    	
    		ctx.channel().writeAndFlush(req);
    	  
    	 
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	TransportMessage  vo = (TransportMessage)msg;
    	 
    	System.out.println("客户端2读取："+vo.getAccessToken());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}