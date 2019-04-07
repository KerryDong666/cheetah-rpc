package com.cheetah.netty.nettynio.rpc;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 处理具体的业务调用
 * 通过构造时传入的“业务接口及实现”handlerMap，来调用客户端所请求的业务方法
 * 并将业务方法返回值封装成response对象写入下一个handler（即编码handler——RpcEncoder）
 *
 * @author Kerry Dong
 */
public class RpcHandler extends SimpleChannelInboundHandler<Request> {

	//private static final Logger LOGGER = LoggerFactory.getLogger(RpcHandler.class);

    //private final Map<String, Object> handlerMap;
	//
    //public RpcHandler(Map<String, Object> handlerMap) {
    //    this.handlerMap = handlerMap;
    //}

    /**
     * 接收消息，处理消息，返回结果
     */
    @Override
    public void channelRead0(final ChannelHandlerContext ctx, Request request)
            throws Exception {
    	System.out.println("----------服务端开始执行业务逻辑--------");
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        try {
            //根据request来处理具体的业务调用
            Object result = handle(request);
            response.setResult(result);
        } catch (Throwable t) {
            response.setError(t);
        }
        //写入 outbundle（即RpcEncoder）进行下一步处理（即编码）后发送到channel中给客户端
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 根据request来处理具体的业务调用
     * 调用是通过反射的方式来完成
     *
     * @param request
     * @return
     * @throws Throwable
     */
    private Object handle(Request request) throws Throwable {
        //String className = request.getClassName();
		//
        ////拿到实现类对象
        //Object serviceBean = handlerMap.get(className);

        //拿到要调用的方法名、参数类型、参数值
        //String methodName = request.getMethodName();
        //Class<?>[] parameterTypes = request.getParameterTypes();
        //Object[] parameters = request.getParameters();
		//
        ////拿到接口类
        //Class<?> forName = Class.forName(className);
		//
        ////调用实现类对象的指定方法并返回结果
        //Method method = forName.getMethod(methodName, parameterTypes);
        //return method.invoke(serviceBean, parameters);
		Class<?> interfaceName = request.getInterfaceName();
		//Class<?> interfaceName = HelloServiceImpl.class;
		Method method = interfaceName.getMethod(request.getMethodName(), request.getParametersType());
		return method.invoke(interfaceName.newInstance(), request.getParameters());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //LOGGER.error("server caught exception", cause);
        ctx.close();
    }

    public static void main(String[] args) throws Exception{
		Class<HelloServiceImpl> clz = HelloServiceImpl.class;
		//Class<?> clz = Class.forName("com.cheetah.netty.nettynio.rpc.HelloServiceImpl");
		Class<? extends Class> aClass = clz.getClass();
		RPC annotation = clz.getAnnotation(RPC.class);
		RPC declaredAnnotation = clz.getDeclaredAnnotation(RPC.class);
		Annotation[] annotations = clz.getAnnotations();
		if (annotations.length == 0) {

		}else {
			for (Annotation annotation1 : annotations) {
				System.out.println(annotation1);
			}
		}
		Class<?> declaringClass = clz.getDeclaringClass();
		Class<?> enclosingClass = clz.getEnclosingClass();

	}
}
