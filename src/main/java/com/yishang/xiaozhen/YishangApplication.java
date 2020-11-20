package com.yishang.xiaozhen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync // 启用异步任务
@SpringBootApplication
public class YishangApplication {

	public static void main(String[] args) {
		SpringApplication.run(YishangApplication.class, args);
	}

}
