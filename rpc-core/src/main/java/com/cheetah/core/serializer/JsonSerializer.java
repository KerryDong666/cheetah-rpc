package com.cheetah.core.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author kerry dong
 * @date 2018/7/29
 */
public class JsonSerializer implements SerializerHandler {
	@Override
	public <T> byte[] serialize(T obj) {
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat).getBytes();
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> clz) {
		return JSON.parseObject(new String(data), clz);
	}

}
