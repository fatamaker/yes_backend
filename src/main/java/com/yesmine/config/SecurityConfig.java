


package com.yesmine.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	

	   
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception
	{
		http.sessionManagement( session -> 
		session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.csrf( csrf -> csrf.disable()) 
		
		.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
			 @Override
			    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
			        CorsConfiguration cors = new CorsConfiguration();
			        cors.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
			        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
			        cors.setAllowedHeaders(List.of("Authorization", "Content-Type"));
			        cors.setExposedHeaders(List.of("Authorization"));
			        cors.setAllowCredentials(true);
			        return cors;
			    }
        }))
	
	
		 .authorizeHttpRequests(auth -> auth
		            .requestMatchers("/login").permitAll() // <-- Allow access to login endpoint
		            .requestMatchers("/api/all/**").hasAnyAuthority("ADMIN", "USER")
		            .requestMatchers("/api/debiteurs/**").hasAnyAuthority("USER", "ADMIN","RESONSABLE FRAIS")
		            .requestMatchers("/api/dossiers/**").hasAnyAuthority("USER", "ADMIN","RESONSABLE FRAIS")
		            .requestMatchers("/api/operations/**").hasAnyAuthority("USER", "ADMIN","RESONSABLE FRAIS")
		            .requestMatchers("/api/personnes/**").hasAnyAuthority("USER", "ADMIN","RESONSABLE FRAIS")
		            .requestMatchers("/api/risques/**").hasAnyAuthority("USER", "ADMIN","RESONSABLE FRAIS")
		            .requestMatchers("/api/frais/**").hasAnyAuthority("USER", "ADMIN","RESONSABLE FRAIS")
		            
		            .anyRequest().authenticated()
		        )
	 .addFilterBefore(new JWTAuthorizationFilter(),
			 UsernamePasswordAuthenticationFilter.class);
	return http.build();
	}
	}
	
	
	
  




