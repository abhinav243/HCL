package com.hcl;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class HclApplication {

	@Bean(name = "asyncExecutor")
	public Executor asyncExecutor() {
	       ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	       executor.setCorePoolSize(5);
	       executor.setMaxPoolSize(10);
	       executor.setQueueCapacity(100);
	       executor.setThreadNamePrefix("AsynchThread-");
	       executor.initialize();
	       return executor;
	}
	public static void main(String[] args) {
		SpringApplication.run(HclApplication.class, args);
	}

}
