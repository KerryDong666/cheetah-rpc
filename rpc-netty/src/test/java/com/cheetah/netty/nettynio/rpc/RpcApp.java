package com.cheetah.netty.nettynio.rpc;

import com.cheetah.netty.nettynio.HelloService;

/**
 * @author kerry dong
 * @date 2019/4/7
 */
public class RpcApp {

	public static void main(String[] args) {
		RpcProxy proxy = new RpcProxy();
		HelloService helloService = proxy.create(HelloService.class);
		String msg = helloService.getMsg("你好啊");
		System.out.println(msg);
	}
}
