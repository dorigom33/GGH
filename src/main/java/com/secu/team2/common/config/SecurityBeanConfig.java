package com.secu.team2.common.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secu.team2.common.filter.CustomAuthenticationFilter;
import com.secu.team2.common.filter.CustomAuthorizationFilter;
import com.secu.team2.common.provider.CustomAuthenticationProvider;
import com.secu.team2.common.provider.JWTTokenProvider;
import com.secu.team2.user.service.UserInfoService;



@Configuration
public class SecurityBeanConfig {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private JWTTokenProvider jwtProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> {
			web.ignoring()
			.antMatchers("/css/**", "/js/**", "/imgs/**", "/resources/**", "/react/**", "/assets/**","/chat/**");
		};
	}

	@Bean
	UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManager(),jwtProvider,objectMapper);
		filter.setFilterProcessesUrl("/api/login");
		filter.afterPropertiesSet();
		return filter;
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		return new CustomAuthenticationProvider(userInfoService, passwordEncoder);
	}
	@Bean
	AuthenticationManager authenticationManager() {
		return new ProviderManager(authenticationProvider());
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity hs) throws Exception {

		hs.authorizeRequests(req->req
				.antMatchers("/", "/api/login", "/login","/join/**","/html/join", 
						"/board-infos", "/board-infos-hot", "/html/board/board-list",
						"/count-deal-info-by-di-stat", "/count-new-message-chat-room"
						,"/error", "/chat-list")
				.permitAll()
				.antMatchers("/html/admin/**").hasRole("ADMIN")
				.antMatchers("/html/user/**").hasRole("USER")
				.anyRequest().authenticated())
		.formLogin(formLogin->formLogin
				.loginPage("/html/login")
				.usernameParameter("uiId")
				.passwordParameter("uiPwd")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/",true)
				.failureUrl("/html/login?error=true")
				.permitAll())

		.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")) // 로그아웃하면 메인 페이지로
		.csrf(csrf->csrf.disable())
		.exceptionHandling(handling -> handling.accessDeniedPage("/html/denied"))
		.userDetailsService(userInfoService)
		.cors(cors->cors.configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration ccf = new CorsConfiguration();
				ccf.setAllowedOrigins(List.of("http://localhost:8081","http://localhost:3000", "https://ggh.today"));
				ccf.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
				ccf.setAllowedHeaders(List.of("*"));
				ccf.setAllowCredentials(true);
				return ccf;
			}
		}))
		.addFilterBefore(new CustomAuthorizationFilter(jwtProvider, userInfoService), UsernamePasswordAuthenticationFilter.class);

		return hs.build();
	}
	
	

}
