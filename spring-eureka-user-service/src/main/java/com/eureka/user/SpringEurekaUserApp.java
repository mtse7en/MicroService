package com.eureka.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.eureka.db.DbContext;
import com.eureka.user.entities.User;

@SpringBootApplication
@EnableEurekaClient // Enable eureka client.
@EnableCircuitBreaker // Enable circuit breakers
public class SpringEurekaUserApp {

	public static void main(String[] args) {
		Initialize();
		SpringApplication.run(SpringEurekaUserApp.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		;
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
/**
	 * These are the same from auth service.
	 * final List<AppUser> users = Arrays.asList( new AppUser(1, "test1",
	 * encoder.encode("test1"), "ADMIN"), new AppUser(2, "test2",
	 * encoder.encode("test2"), "ADMIN"), new AppUser(3, "test3",
	 * encoder.encode("test3"), "ADMIN") );
	 * 
	 * @author admin
	 *
	 */
	public static void Initialize() {
		DbContext.deleteAll();
		User info = new User("1", "test1");
		User info2 = new User("2", "test2");
		User info3 = new User("3", "test3");
		try {
			DbContext.SaveUser(info);
			DbContext.SaveUser(info2);
			DbContext.SaveUser(info3);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

	


@Configuration
class RestTemplateConfig {
	
	// Create a bean for restTemplate to call services
	@Bean
	@LoadBalanced		// Load balance between service instances running at different ports.
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}