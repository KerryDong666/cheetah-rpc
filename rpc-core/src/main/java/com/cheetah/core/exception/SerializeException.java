package com.cheetah.core.exception;

/**
 * 自定义序列化异常
 *
 * @author kerry dong
 * @date 2018/7/29
 */
public class SerializeException extends RuntimeException {

	public SerializeException() {
		super();
	}

	public SerializeException(String message) {
		super(message);
	}

	public SerializeException(String message, Throwable cause) {
		super(message, cause);
	}
}
