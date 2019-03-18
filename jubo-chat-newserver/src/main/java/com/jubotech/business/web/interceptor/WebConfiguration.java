package com.jubotech.business.web.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		System.out.println("进入拦截器。。。。");
//		InterceptorRegistration ir =  registry.addInterceptor(new ExceptionInterceptor());
//		ir.addPathPatterns("/**");
//		ir.excludePathPatterns("/","/cms/**");
		
		registry.addInterceptor(new ExceptionInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin","/admin/","/admin/login","/","/user/login","/fileUpload");
	    super.addInterceptors(registry);
	}
}
