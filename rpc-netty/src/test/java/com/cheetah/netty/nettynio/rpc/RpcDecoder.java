package com.cheetah.netty.nettynio.rpc;

import com.cheetah.core.serializer.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * RPC解码器(反序列化),用于把二进制文件转换为java对象
 * @author Kerry Dong
 */
public class RpcDecoder extends ByteToMessageDecoder {

	//private static final Logger log = LoggerFactory.getLogger(RpcDecoder.class);

    private Class<?> genericClass;

	// 构造函数传入向反序列化的class
    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    	System.out.println("开始解码");
    	// netty可读字节小于4个表示没有内容
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        // 处理tcp包不全的情况
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
        }
        //将ByteBuf转换为byte[]
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        //将data转换成object
        Object obj = new JsonSerializer().deserialize(data, genericClass);
        out.add(obj);
    }
}
