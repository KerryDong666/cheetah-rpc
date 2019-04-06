package com.cheetah.core.serializer;

import com.cheetah.TestBean;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kerry dong
 * @date 2019/4/6
 */
public class HessianSerializerTest {

	private TestBean bean;

	HessianSerializer serializer = new HessianSerializer();
	@Before
	public void before(){
		this.bean = new TestBean();
		bean.setId(1L);
		bean.setName("kerry");
	}
	@Test
	public void serialize() throws Exception {
		byte[] serialize = serializer.serialize(bean);
		System.out.println(serialize);
	}

	@Test
	public void deserialize() throws Exception {
		byte[] serialize = serializer.serialize(bean);
		TestBean deserialize = serializer.deserialize(serialize, TestBean.class);
		System.out.println(deserialize);
	}

}