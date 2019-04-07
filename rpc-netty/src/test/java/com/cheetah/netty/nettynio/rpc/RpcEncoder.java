package com.cheetah.netty.nettynio.rpc;

import com.cheetah.core.serializer.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * RPC 编码器(序列化)
 * @author Kerry Dong
 */
public class RpcEncoder extends MessageToByteEncoder {

	private Class<?> genericClass;

	// 构造函数传入向反序列化的class
	public RpcEncoder(Class<?> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	public void encode(ChannelHandlerContext ctx, Object inob, ByteBuf out)
			throws Exception {
		//序列化
		if (genericClass.isInstance(inob)) {
			//System.out.println(inob.toString());
			byte[] data = new JsonSerializer().serialize(inob);
			out.writeInt(data.length);
			out.writeBytes(data);
		}
	}
}