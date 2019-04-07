package com.cheetah.netty.nettynio.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 创建代理对象, 拼接请求, 发送到服务端
 * @author kerry dong
 * @date 2019/4/7
 */
public class RpcProxy {


	/**
	 * 利用动态代理创建对象
	 * @param clz
	 * @param <T>
	 * @return
	 */
	public <T> T create(Class<T> clz){

		return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// 封装客户端请求
				Request request = wapperRequest(method, clz, args);

				// 创建netty客户端,
				RpcClient client = new RpcClient("localhost", 8088);
				// 发送请求
				RpcResponse response = client.send(request);
				if (response.isError()) {
					return response.getError();
				}else {
					return response.getResult();
				}

			}

			@SuppressWarnings("unchecked")
			private Request wapperRequest(Method method, Class<T> clz, Object[] args) {
				String requestId = UUID.randomUUID().toString();
				String methodName = method.getName();
				Class<?>[] parameterTypes = method.getParameterTypes();
				Class<?> returnType = method.getReturnType();
				Request request = new Request();
				request.setRequestId(requestId);
				request.setInterfaceName(clz);
				request.setMethodName(methodName);
				request.setParameters(args);
				request.setReturnType(returnType);
				request.setParametersType(parameterTypes);
				request.setClassName(clz.getSimpleName());
				return request;
			}
		});
	}
}
