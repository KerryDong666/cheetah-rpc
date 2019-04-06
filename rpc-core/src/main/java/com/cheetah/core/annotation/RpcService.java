package com.cheetah.core.annotation;

import java.lang.annotation.*;

/**
 * @author kerry dong
 * @date 2019/4/6
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcService {

	String value() default "";

	Class<?> type();

}
