package com.cheetah.core.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author kerry dong
 * @date 2018/7/29
 */
public class HessianSerializer implements SerializerHandler {

	@Override
	public <T> byte[] serialize(T obj) {
		if (obj == null) {
			throw new IllegalArgumentException("序列化对象不能为空");
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HessianOutput ho = new HessianOutput(os);
		try {
			ho.writeObject(obj);
			return os.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T deserialize(byte[] data, Class<T> clz) {
		if (data == null) {
			throw new IllegalArgumentException("反序列化对象不能为空");
		}
		ByteArrayInputStream is = new ByteArrayInputStream(data);
		HessianInput hi = new HessianInput(is);
		try {
			return (T) hi.readObject();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
