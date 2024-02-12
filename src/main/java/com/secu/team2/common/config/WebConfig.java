package com.secu.team2.common.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Value("${download.file-path}")
	private String filePath;
	@Value("${download.resource-url}")
	private String resourceUrl;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(resourceUrl)
		.addResourceLocations(filePath);

	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration ccf = new CorsConfiguration();
		ccf.setAllowCredentials(true);
		ccf.setAllowedOrigins(List.of("*"));
		ccf.setAllowedMethods(List.of("POST", "PUT", "PATCH", "GET", "DELETE", "OPTIONS"));
		ccf.setAllowedHeaders(List.of("*"));
		UrlBasedCorsConfigurationSource ubccs = new UrlBasedCorsConfigurationSource();
		ubccs.registerCorsConfiguration("/**", ccf);
		return ubccs;
	}
	
	public void configure(WebSecurity web) throws Exception {
	    web.httpFirewall(defaultHttpFirewall());
	}
	 
	@Bean
	public HttpFirewall defaultHttpFirewall() {
	    return new DefaultHttpFirewall();
	}
}
