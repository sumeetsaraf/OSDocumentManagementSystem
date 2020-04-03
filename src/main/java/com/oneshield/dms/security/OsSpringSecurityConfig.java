package com.oneshield.dms.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class OsSpringSecurityConfig extends WebSecurityConfigurerAdapter {
    public static String REALM = "OS_DMS_REALM";

   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("osUser").password("Os!23DMS@234").roles("USER");
    }

 

    /*
     * @Override protected void configure(AuthenticationManagerBuilder auth) throws
     * Exception { auth.authenticationProvider(authProvider); }
     */



 

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().antMatchers("/api/**").hasRole("USER").and().httpBasic()
        .realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint()).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

 

    @Bean
    public AuthenticationEntryPoint getBasicAuthEntryPoint() {
    return new CustomBasicAuthenticationEntryPoint();
    }

 

}