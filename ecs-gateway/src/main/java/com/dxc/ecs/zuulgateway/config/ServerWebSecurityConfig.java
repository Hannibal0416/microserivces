package com.dxc.ecs.zuulgateway.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableGlobalMethodSecurity(securedEnabled = true)
//@Order(-1)
public class ServerWebSecurityConfig extends WebSecurityConfigurerAdapter {

//  @Override
//  @Bean
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return super.authenticationManagerBean();
//  }

//	@Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
	    configuration.setAllowCredentials(true);
	    configuration.setMaxAge(0L);
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
//	    configuration.applyPermitDefaultValues();
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // super.configure(http);
//    http.csrf().disable().cors().and().httpBasic().disable().anonymous().and().authorizeRequests().antMatchers("/user/**").authenticated()
//    .antMatchers("/**").permitAll().and().headers().cacheControl();
//    http.csrf().disable().cors().and()
//    .authorizeRequests().antMatchers("/**").permitAll()
//    .anyRequest().authenticated().and()
//    .anyRequest().permitAll().and()
//    .httpBasic().and().sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  http.csrf().disable().headers().disable().cors().configurationSource(corsConfigurationSource());
//	  .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
  }

  

}
