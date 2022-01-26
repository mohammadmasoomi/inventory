package com.github.mohammadmasoomi.inventory.configuration.security;

import com.github.mohammadmasoomi.inventory.profiles.Production;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
@Production
public class WebSecurityConfigurerAdapterProd extends WebSecurityConfigurerAdapter {


    private final InventoryUserDetailsService inventoryUserDetailsService;

    private final InventoryBasicAuthenticationEntryPoint inventoryBasicAuthenticationEntryPoint;

    private final BCryptPasswordEncoder passwordEncoder;

    public WebSecurityConfigurerAdapterProd(InventoryUserDetailsService inventoryUserDetailsService, InventoryBasicAuthenticationEntryPoint inventoryBasicAuthenticationEntryPoint, BCryptPasswordEncoder passwordEncoder) {
        this.inventoryUserDetailsService = inventoryUserDetailsService;
        this.inventoryBasicAuthenticationEntryPoint = inventoryBasicAuthenticationEntryPoint;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // for xssProtection
        http.headers().xssProtection().xssProtectionEnabled(true).block(true);

        // All requests send to the Web Server request must be authenticated
        http.authorizeRequests().anyRequest().authenticated();

        http.httpBasic().authenticationEntryPoint(inventoryBasicAuthenticationEntryPoint);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(inventoryUserDetailsService).passwordEncoder(passwordEncoder);
    }

}


