package com.cheetah.netty.nettynio.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 框架的RPC 客户端（用于发送 RPC 请求）:基于Netty的Handler
 *
 * @author Kerry Dong
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {


	private String host;
	private int port;

	private RpcResponse response;

	private final Object obj = new Object();

	public RpcClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * 链接服务端，发送消息
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public RpcResponse send(Request request) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel channel)
								throws Exception {
							// 向pipeline中添加编码、解码、业务处理的handler
							channel.pipeline()
									// 编码(序列化)
									.addLast(new RpcEncoder(Request.class))  //OUT - 1
									// 解码(反序列化)
									.addLast(new RpcDecoder(RpcResponse.class)) //IN - 1
									// 获取返回值
									.addLast(RpcClient.this);                   //IN - 2

						}
					}).option(ChannelOption.SO_KEEPALIVE, true);
			// 链接服务器
			ChannelFuture future = bootstrap.connect(host, port).sync();
			//将request对象写入outbundle处理后发出（即RpcEncoder编码器）
			future.channel().writeAndFlush(request).sync();

			// 用线程等待的方式决定是否关闭连接
			// 其意义是：先在此阻塞，等待获取到服务端的返回后，被唤醒，从而关闭网络连接
			synchronized (obj) {
				obj.wait();
			}
			if (response != null) {
				future.channel().closeFuture().sync();
			}
			return response;
		} finally {
			group.shutdownGracefully();
		}
	}

	/**
	 * 读取服务端的返回结果
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, RpcResponse response)
			throws Exception {
		System.out.println("客户端获取服务端信息");
		this.response = response;

		synchronized (obj) {
			obj.notifyAll();
		}
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		//LOGGER.error("client caught exception", cause);
		ctx.close();
	}
}
