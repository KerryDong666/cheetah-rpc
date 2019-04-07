package com.cheetah.netty.nettynio;

import com.cheetah.netty.nettynio.rpc.RPC;

/**
 * @author kerry dong
 * @date 2019/4/7
 */
@RPC
public interface HelloService {

	String getMsg(String msg);
}
