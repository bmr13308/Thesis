package com.fitnest.gateway.config;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fitnest.gateway.services.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
	public CustomFilter() {
		super(Config.class);
	}
	
    @Autowired
    private JwtUtil jwtUtil;

	@Override
	public GatewayFilter apply(Config config) {
		//Custom Pre Filter. Suppose we can extract JWT and perform Authentication
	
		return (exchange, chain) -> {
			System.out.println("First pre filter" + exchange.getRequest());
			ServerHttpRequest req = exchange.getRequest();
			HttpHeaders headers = req.getHeaders();
			 if (!req.getHeaders().containsKey("Authorization")) {
                 return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
             };
			
			final String authorizationHeader = headers.getFirst("Authorization");
			String jwt = "";
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            jwt = authorizationHeader.substring(7);
	        }
			
			if(jwtUtil.isTokenExpired(jwt)) {
				return this.onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
			}
			
			

            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().
                    header("secret", RandomStringUtils.random(10)).
                    header("Access-Control-Allow-Origin","*")
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
		};
	}
	
	 private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus)  {
         ServerHttpResponse response = exchange.getResponse();
         response.setStatusCode(httpStatus);
 
         return response.setComplete();
     }

	public static class Config {
		// Put the configuration properties
	}
}
