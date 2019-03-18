package com.jubotech.framework.netty.client;

import com.jubotech.framework.netty.decoder.SelfDecoder;
import com.jubotech.framework.netty.decoder.SelfEncoder;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

public class NettyConnect {
	public static void connect(String host, int port,ChannelInboundHandlerAdapter handler) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new SelfDecoder());
                    ch.pipeline().addLast(new ProtobufDecoder(TransportMessageOuterClass.TransportMessage.getDefaultInstance()));
                    ch.pipeline().addLast(new SelfEncoder());
                    ch.pipeline().addLast(handler);
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
