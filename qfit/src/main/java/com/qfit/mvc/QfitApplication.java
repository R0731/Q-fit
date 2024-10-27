package com.qfit.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class QfitApplication {

	public static void main(String[] args) {
		SpringApplication.run(QfitApplication.class, args);
	}

}
