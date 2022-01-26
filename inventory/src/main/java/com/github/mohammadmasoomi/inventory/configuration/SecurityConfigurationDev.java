package com.github.mohammadmasoomi.inventory.configuration;

import com.github.mohammadmasoomi.inventory.profiles.Development;
import org.springframework.context.annotation.Bean;
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
@Development
public class SecurityConfigurationDev extends WebSecurityConfigurerAdapter {


    private final InventoryUserDetailsService inventoryUserDetailsService;

    private final InventoryBasicAuthenticationEntryPoint inventoryBasicAuthenticationEntryPoint;

    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfigurationDev(InventoryUserDetailsService inventoryUserDetailsService, InventoryBasicAuthenticationEntryPoint inventoryBasicAuthenticationEntryPoint, BCryptPasswordEncoder passwordEncoder) {
        this.inventoryUserDetailsService = inventoryUserDetailsService;
        this.inventoryBasicAuthenticationEntryPoint = inventoryBasicAuthenticationEntryPoint;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

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

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


