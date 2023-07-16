package com.example.demo.jwt;

import javax.print.event.PrintJobAttributeEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
//	@Autowired
//	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;

	
	@Bean 
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
	}
	
	
	@Bean
	public PasswordEncoder	passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return 
				http.csrf().disable()
				.authorizeHttpRequests().requestMatchers("/user/login","/user/signup").permitAll()
				.and()
//				.authorizeHttpRequests().requestMatchers("/user/demo").authenticated()
//				.and().formLogin().and().build();
				.authorizeHttpRequests().requestMatchers("/category/**","/products/**","/dashboard/**").authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
//				.and().authenticationManager(null)
	}

	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
		}
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http.cors().configurationSource(requesy->new CorsConfiguration().applyPermitDefaultValues())
//				.and().csrf().disable()
//				.authorizeHttpRequests().requestMatchers("/user/login","/user/signup").permitAll()
//				.and()
////				.authorizeHttpRequests().requestMatchers("/user/demo").authenticated()
////				.and().formLogin().and().build();
//				.authorizeHttpRequests().anyRequest().authenticated()
//				.and().exceptionHandling()
//				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//				.build();
////				.and().authenticationManager(null)
//	}
	
}
