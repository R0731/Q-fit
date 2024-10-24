package com.qfit.mvc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages="com.qfit.mvc.model.dao")
public class DBConfig {
	
}