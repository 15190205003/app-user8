package com.hiwoo;

//import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients
@MapperScan("com.hiwoo.mapper")
@ServletComponentScan
@ImportResource(locations = {"shiro/spring-shiro-web.xml","shiro/spring-cache.xml"})
public class AppUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppUserApplication.class, args);
	}

}
