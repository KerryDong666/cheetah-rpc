package com.cheetah.netty.nettynio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author kerry dong
 * @date 2019/4/6
 */
public class NettyServer {

	private final int port = 8082;

	public static void main(String[] args) throws Exception {
		new NettyServer().start();
	}

	/**
	 * 服务端启动
	 * @throws Exception
	 */
	public void start() throws Exception {
		EventLoopGroup eventLoopGroup = null;
		try {
			// 创建serverBootStrap实例来引导绑定和启动服务
			ServerBootstrap bootstrap = new ServerBootstrap();

			// 创建nioEventLoopGroup来处理事件,如接受新连接 接收数据 写数据,相当于一个线程组
			eventLoopGroup = new NioEventLoopGroup();

			// 指定通道类型为NioServerSocketChannel,一种异步模式, OIO阻塞为OioServer
			bootstrap.group(eventLoopGroup).channel(NioServerSocketChannel.class).localAddress(port).childHandler(new ChannelInitializer<Channel>() {
				// 设置channel执行的所有连接请求
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline()
							.addLast(new NettyServerHandler0())
							.addLast(new NettyServerHandler());
				}
			});

			ChannelFuture channelFuture = bootstrap.bind().sync();
			System.out.println("开始监听端口:" + channelFuture.channel().localAddress());
			channelFuture.channel().closeFuture().sync();
		} finally {
			eventLoopGroup.shutdownGracefully().sync();
		}
	}
}
