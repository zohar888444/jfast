package com;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.zengtengpeng.annotation.EnableMQ;

@SpringBootApplication
@ComponentScan("com.jfast")
@EnableMethodCache(basePackages = "com.jfast")
@EnableMQ
public class AdminApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
	}

}
