package com.cheetah.netty.nettynio.rpc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kerry dong
 * @date 2019/4/7
 */
@Data
public class Request implements Serializable{

	private String requestId;

	private String methodName;

	private Object[] parameters;

	private Class<?> interfaceName;

	private String className;

	private Class<?> returnType;

	private Class[] parametersType;

}
