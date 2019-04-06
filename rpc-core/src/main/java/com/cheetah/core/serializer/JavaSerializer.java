package com.cheetah.core.serializer;


import com.cheetah.core.exception.SerializeException;

import java.io.*;

/**
 * java原生的序列化和反序列化方式
 *
 * @author kerry dong
 * @date 2018/7/29
 */
//@Slf4j
public class JavaSerializer implements SerializerHandler {

	@Override
	public <T> byte[] serialize(T obj) {
		//log.info("begin java serialize");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(baos);
			os.writeObject(obj);
		} catch (IOException e) {
			throw new SerializeException("Serialize error", e);
		}
		return baos.toByteArray();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T deserialize(byte[] data, Class<T> clz) {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (T) ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
