package com.cheetah.core.serializer;

/**
 * 序列化和反序列化处理器
 * @author kerry dong
 * @date 2018/7/29
 */
public interface SerializerHandler {

	/**
	 * 序列化
	 * @param obj 需要序列化的对象
	 */
	<T> byte[] serialize(T obj);

	/**
	 * 反序列化
	 * @param data 需要烦序列化的数据
	 * @param clz 反序列化后类型
	 * @return 对象
	 */
	<T> T deserialize(byte[] data, Class<T> clz);
}
