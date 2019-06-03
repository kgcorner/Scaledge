package com.kgcorner.scaledge.security.conf;

/*
Description : Defines security configuration
Author: kumar
Created on : 1/6/19
*/

import com.kgcorner.scaledge.security.filters.BasicTokenFilter;
import com.kgcorner.scaledge.security.filters.BearerTokenFilter;
import com.kgcorner.scaledge.security.filters.CORSFilter;
import com.kgcorner.scaledge.security.providers.BasicTokenAuthenticationProvider;
import com.kgcorner.scaledge.security.providers.BearerTokenAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static String[] PUBLIC_URLS = {"/**"}; //These URLS should be available publicly
    private static String[] USER_SECURED_URLS = {"/secure/**"}; //These URLS should be accessible by Users and Admin only
    private static String[] ADMIN_SECURED_URLS = {"/admin/**"}; //These URLS should be accessible by Admin only
    private static String[] EXCEPTIONS = {"/register"};


    @Autowired
    private BasicTokenAuthenticationProvider basicTokenAuthenticationProvider;


    //@Autowired
    //private BearerTokenAuthenticationProvider bearerTokenAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(basicTokenAuthenticationProvider);
            //.authenticationProvider(bearerTokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .addFilterBefore(new CORSFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(new BasicTokenFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(new BearerTokenFilter(), BasicAuthenticationFilter.class)
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(EXCEPTIONS).permitAll()
            .antMatchers(HttpMethod.POST).hasAnyRole("user","admin")
            .antMatchers(HttpMethod.PUT).hasAnyRole("user","admin")
            .antMatchers(HttpMethod.DELETE).hasAnyRole("user","admin")
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers(PUBLIC_URLS).permitAll()
            .antMatchers(USER_SECURED_URLS).hasAnyRole("user","admin")
            .antMatchers(ADMIN_SECURED_URLS).hasRole("admin");
    }
}