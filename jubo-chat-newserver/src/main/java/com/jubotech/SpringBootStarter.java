package com.jubotech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//Spring Boot项目的核心注解，主要目的是开启自动配置，加了这个注解，后springboot会扫描本包及子包的所有javabean；
public class SpringBootStarter {
	
	public static void main(String[] args) {
		 //SpringBoot项目入口
		 SpringApplication.run(SpringBootStarter.class, args);
	}

}
