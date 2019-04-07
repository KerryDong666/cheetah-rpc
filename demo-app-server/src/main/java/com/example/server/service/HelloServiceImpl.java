package com.example.server.service;

import com.example.share.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author kerry dong
 * @date 2019/4/7
 */
@Service
public class HelloServiceImpl implements HelloService {
	@Override
	public String hello(String msg) {
		return "接收到消息 : " + msg;
	}
}
