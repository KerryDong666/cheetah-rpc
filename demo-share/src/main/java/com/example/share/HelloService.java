package com.example.share;

import com.cheetah.core.annotation.RpcService;

/**
 * @author kerry dong
 * @date 2019/4/7
 */
@RpcService
public interface HelloService {

	String hello(String msg);

}
