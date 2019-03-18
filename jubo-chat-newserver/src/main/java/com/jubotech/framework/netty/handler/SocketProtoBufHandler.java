package com.jubotech.framework.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jubotech.framework.netty.processor.SocketMessageProcessor;
import com.jubotech.framework.netty.utils.NettyConnectionUtil;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Service
@Sharable
public class SocketProtoBufHandler extends ChannelInboundHandlerAdapter {
	@Autowired
	private SocketMessageProcessor messageProcessor;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());  
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("socket登录");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		NettyConnectionUtil.exit(ctx);
		log.info("socket退出");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("socket读取并处理消息");
		//需要放到单独的分发器中处理
		messageProcessor.handler(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}