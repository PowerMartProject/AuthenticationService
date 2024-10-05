package com.powermart.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.powermart.security.SecurityUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfiguration {
//	
//	@Autowired
//	JwtAuthenticationFilter jwtFilter;
	
	@Autowired
	SecurityUser securityUser;
	
	
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			
			http
			.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.disable())
			.authorizeHttpRequests(request -> request.requestMatchers("/auth/login","/auth/signup","/h2-console/**").permitAll())
			.authorizeHttpRequests(request -> request.anyRequest().authenticated())
//			.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider())
//			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.headers(header-> header.frameOptions(frame -> frame.disable()));
		//	.exceptionHandling(exception-> exception.authenticationEntryPoint(customauth))
			return http.build();
		}
	    
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder(); 
		}
		
		@Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
		
		@Bean
	    public AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

	        authProvider.setUserDetailsService(securityUser);
	        authProvider.setPasswordEncoder(passwordEncoder());

	        return authProvider;
	    }
		
		 @Bean
		    public AuthenticationFailureHandler authenticationFailureHandler() {
		        return new SimpleUrlAuthenticationFailureHandler() {
		            @Override
		            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		                response.getWriter().write("Authentication failed: " + exception.getMessage());
		            }
		        };
		    }
}
