package com.cheetah.netty.nettynio.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * 框架的RPC 服务器（用于将用户系统的业务类发布为 RPC 服务）
 * 使用时可由用户通过spring-bean的方式注入到用户的业务系统中
 * 由于本类实现了ApplicationContextAware InitializingBean
 * spring构造本对象时会调用setApplicationContext()方法，从而可以在方法中通过自定义注解获得用户的业务接口和实现
 * 还会调用afterPropertiesSet()方法，在方法中启动netty服务器
 *
 * @author Kerry Dong
 */
public class RpcServer  {

	public static final Map<String, Class<?>> map = new HashMap<>();

	static {
		map.put("HelloService", HelloServiceImpl.class);
	}

	public static void main(String[] args) throws Exception {
		new RpcServer().afterPropertiesSet("", 8088);
	}


	/**
	 * 在此启动netty服务，绑定handle流水线：
	 * 1、接收请求数据进行反序列化得到request对象
	 * 2、根据request中的参数，让RpcHandler从handlerMap中找到对应的业务imple，调用指定方法，获取返回结果
	 * 3、将业务调用结果封装到response并序列化后发往客户端
	 */
	public void afterPropertiesSet(String host, int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap
					.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class).localAddress(port)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel channel)
								throws Exception {
							channel.pipeline()
									// 注册解码 IN-1
									.addLast(new RpcDecoder(Request.class))
									// 注册编码 OUT
									.addLast(new RpcEncoder(RpcResponse.class))
									// 注册RpcHandler IN-2
									.addLast(new RpcHandler());
						}
					}).option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);


			//String[] array = serverAddress.split(":");
			//String host = array[0];
			//int port = Integer.parseInt(array[1]);

			ChannelFuture future = bootstrap.bind().sync();
			//ChannelFuture future = bootstrap.bind(host, port).sync();
			//LOGGER.debug("server started on port {}", port);
			////向ZK注册服务
			//if (serviceRegistry != null) {
			//	serviceRegistry.register(serverAddress);
			//}
			System.out.println("开始监听端口:" + future.channel().localAddress());
			future.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
