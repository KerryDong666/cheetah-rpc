package com.example.server;

import com.cheetah.core.annotation.RpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Map;

@SpringBootApplication
public class DemoAppServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoAppServerApplication.class, args);
		Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(RpcService.class);
		for (Map.Entry<String, Object> stringObjectEntry : beansWithAnnotation.entrySet()) {
			System.out.println(stringObjectEntry.getKey() + " = " + stringObjectEntry.getValue());
			Class<?> clz = stringObjectEntry.getValue().getClass();
			Class<?> superclass = clz.getSuperclass();
			Class<?>[] interfaces = clz.getInterfaces();
			for (Class<?> interfaceItem : interfaces) {
				RpcService annotation = interfaceItem.getAnnotation(RpcService.class);
				Annotation[] annotations = interfaceItem.getAnnotations();

			}
		}
		System.out.println(beansWithAnnotation);
	}

}
