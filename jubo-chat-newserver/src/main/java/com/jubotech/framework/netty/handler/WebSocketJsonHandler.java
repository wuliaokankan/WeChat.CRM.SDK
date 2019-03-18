package com.jubotech.framework.netty.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jubotech.framework.netty.processor.WebSocketMessageProcessor;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

@Service
@Sharable
public class WebSocketJsonHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	@Autowired
	private WebSocketMessageProcessor messageProcessor;
	/**
	 * 覆盖了 channelRead0() 事件处理方法。 每当从服务端读到客户端写入信息时， 其中如果你使用的是 Netty 5.x 版本时， 需要把
	 * channelRead0() 重命名为messageReceived()
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		//System.out.println("收到消息：" + msg.text());
		//ctx.channel().writeAndFlush(new TextWebSocketFrame(msg.text()));
		messageProcessor.handler(ctx, msg);
	}

	/**
	 * 每当从服务端收到新的客户端连接时
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// 打印出channel唯一值，asLongText方法是channel的id的全名
		System.out.println("handlerAdded：" + ctx.channel().id().asLongText());
	}

	/**
	 * 每当从服务端收到客户端断开时
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved：" + ctx.channel().id().asLongText());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("异常发生");
		ctx.close();
	}

}