package com.cheetah.netty.nettynio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * @author kerry dong
 * @date 2019/4/6
 */
public class NettyClientHandler0 extends SimpleChannelInboundHandler<ByteBuf> {


	/**
	 * 从服务端接受到数据后调用, 在netty5.0中将被改名为messageReceived
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("handler1 收到消息");
		ReferenceCountUtil.retain(msg);
		ctx.fireChannelRead(msg);


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
