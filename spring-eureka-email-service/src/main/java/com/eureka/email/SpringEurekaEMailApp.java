

 
package com.eureka.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.eureka.db.DbContext;
import com.eureka.email.entities.MailInfo;

@SpringBootApplication
@EnableEurekaClient 	

public class SpringEurekaEMailApp {

	public static void main(String[] args) {
		Initialize();
		SpringApplication.run(SpringEurekaEMailApp.class, args);
			
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
	public static void Initialize() {
		DbContext.Delete("1");
		MailInfo info = new MailInfo("1","acoount@gmail.com"); 
		try {
			DbContext.SaveMailInfo(info);
		} catch (Exception e) {
 			e.printStackTrace();
		}
 
		System.out.println("Finito");
	}
}
