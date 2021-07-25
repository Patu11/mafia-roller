package com.github.patu11.mafiaroller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().configurationSource(httpServletRequest -> {
//					CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
//					corsConfiguration.setAllowedMethods(Arrays.asList(
//							HttpMethod.GET.name(),
//							HttpMethod.HEAD.name(),
//							HttpMethod.POST.name(),
//							HttpMethod.PUT.name(),
//							HttpMethod.DELETE.name()));
//					return corsConfiguration;
//				}
//		);


		http.csrf().disable();
	}
}
