package com.jubotech.framework.netty;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.jubotech.framework.netty.decoder.SelfDecoder;
import com.jubotech.framework.netty.decoder.SelfEncoder;
import com.jubotech.framework.netty.handler.SocketProtoBufHandler;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass.TransportMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

@Service
public class SocketServer {

	@Autowired
	private Environment env;

	@Autowired
	private SocketProtoBufHandler protoBufBaseServerHandler;

	// 日志记录器
	Logger logger = LoggerFactory.getLogger(getClass());

	// 程序初始方法入口注解，提示spring这个程序先执行这里
	@PostConstruct
	public void nettyMain() {
		new Thread(new Runnable() {
			public void run() {
				// 1 创建线两个程组
				// 一个是用于处理服务器端接收客户端连接的
				// 一个是进行网络通信的（网络读写的）
				EventLoopGroup bossGroup = new NioEventLoopGroup();
				EventLoopGroup workerGroup = new NioEventLoopGroup();
				try {
					// 2 创建辅助工具类，用于服务器通道的一系列配置
					ServerBootstrap b = new ServerBootstrap();
					b.group(bossGroup, workerGroup);
					b.channel(NioServerSocketChannel.class);// 指定NIO的模式
					b.option(ChannelOption.SO_BACKLOG, 1024); // 设置tcp缓冲区
					b.option(ChannelOption.SO_SNDBUF, 32 * 1024); // 设置发送缓冲大小
					b.option(ChannelOption.SO_RCVBUF, 32 * 1024); // 这是接收缓冲大小
					b.option(ChannelOption.SO_KEEPALIVE, true); // 保持连接
					b.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new SelfDecoder());
							ch.pipeline().addLast(new ProtobufDecoder(TransportMessage.getDefaultInstance()));
							ch.pipeline().addLast(new SelfEncoder());
							ch.pipeline().addLast(protoBufBaseServerHandler);
						}
					});
					// 3、绑定端口 同步等待成功
					Integer port = getNettyPort(env);
					ChannelFuture f = b.bind(port).sync();
					logger.info("netty启动成功。。。" + "占用tcp端口" + port);
					// 4、等待服务端监听端口关闭
					f.channel().closeFuture().sync();
				} catch (Exception e) {
					logger.info("netty启动失败。。。");
				} finally {
					workerGroup.shutdownGracefully();
					bossGroup.shutdownGracefully();
				}
			}
		}).start();
	}

	// 获取netty端口
	private static Integer getNettyPort(Environment env) {
		return Integer.valueOf(env.getProperty("com.jubotech.netty.socket.port"));
	}
}
