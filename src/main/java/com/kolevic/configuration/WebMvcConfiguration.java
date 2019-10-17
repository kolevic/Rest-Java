package com.kolevic.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kolevic.Component.RequestTimeInterceptor;




@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Autowired
	@Qualifier("requestTimeInterceptor")
	private RequestTimeInterceptor requesTimeInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requesTimeInterceptor);
	}

}
