package com.cheetah.netty.nettynio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author kerry dong
 * @date 2019/4/6
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	/**
	 * 客户端与服务端建立连接后执行
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端连接服务端, 开始执行业务逻辑");
		byte[] req = "kerry dong".getBytes();
		ByteBuf buffer = Unpooled.buffer(req.length);
		buffer.writeBytes(req);
		ctx.writeAndFlush(buffer);
	}

	/**
	 * 从服务端接受到数据后调用, 在netty5.0中将被改名为messageReceived
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("客户端接受到服务端的信息,");
		// 服务端返回消息后
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("服务端数据为 :" + body);

	}

	/**
	 * 遇到异常是执行
	 * @param ctx
	 * @param cause
	 * @throws Exception
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("客户端发生异常, msg:" + cause.getMessage());
		ctx.close();
	}
}
