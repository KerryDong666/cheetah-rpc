package com.cheetah;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kerry dong
 * @date 2019/4/6
 */
@Data
public class TestBean implements Serializable {

	private Long id;

	private String name;

}
