package com.cheetah.netty.nettynio.rpc;

import com.cheetah.netty.nettynio.HelloService;

/**
 * @author kerry dong
 * @date 2019/4/7
 */
public class HelloServiceImpl implements HelloService {
	@Override
	public String getMsg(String msg) {
		return "接收到的信息是: " + msg;
	}
}
