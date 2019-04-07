package com.cheetah.netty.nettynio;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author kerry dong
 * @date 2019/4/6
 */
public class NettyServerHandler0 extends ChannelInboundHandlerAdapter {

	/**
	 * 获取服务端的数据并执行业务逻辑
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("服务端handler0开始读取数据---");
		//ByteBuf buf = (ByteBuf) msg;
		ReferenceCountUtil.retain(msg);
		ctx.fireChannelRead(msg);
		//byte[] req = new byte[buf.readableBytes()];
		//buf.readBytes(req);
		//String body = new String(req, "UTF-8");
		//System.out.println("服务端接受到客户端的信息为: " + body);
		//// 向客户端写数据
		//System.out.println("server向客户端发送数据");
		//String currentTime = new Date().toString();
		//ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		//ctx.write(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}
}
