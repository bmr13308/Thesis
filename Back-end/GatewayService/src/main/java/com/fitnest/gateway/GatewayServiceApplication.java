package com.fitnest.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
	
	//@Configuration
	//@EnableWebFlux
	//public class CorsGlobalConfiguration implements WebFluxConfigurer {
	// 
	//    @Override
	//    public void addCorsMappings(CorsRegistry corsRegistry) {
	//        corsRegistry.addMapping("/**")
	//          .allowedOrigins("http://localhost:3000")
	//          .allowedMethods("PUT")
	//          .allowedMethods("POST")
	//          .allowedMethods("GET")
	//          .allowedMethods("DELETE")
	//          .maxAge(3600);
	//    }
	//}

}
