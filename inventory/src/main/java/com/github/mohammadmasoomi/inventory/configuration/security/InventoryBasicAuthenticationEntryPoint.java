package com.github.mohammadmasoomi.inventory.configuration.security;

import com.github.mohammadmasoomi.inventory.core.ontology.InventoryOntology;
import com.github.mohammadmasoomi.inventory.exception.AppErrorMessage;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class InventoryBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    public static AppErrorMessage getAuthenticationErrorMessage(AuthenticationException authException) {
        AppErrorMessage authenticationError = AppErrorMessage.UNKNOWN_AUTHENTICATION_ERROR;
        // wrong credential
        if (authException instanceof BadCredentialsException) {
            authenticationError = AppErrorMessage.WRONG_CREDENTIALS;
        } else if (authException instanceof LockedException) {
            authenticationError = AppErrorMessage.TEMPORARY_ACCOUNT_LOCKED;
        } else if (authException instanceof CredentialsExpiredException) {
            authenticationError = AppErrorMessage.CREDENTIALS_EXPIRED;
        } else if (authException instanceof DisabledException) {
            authenticationError = AppErrorMessage.USER_ALREADY_IS_DISABLED;
        } else if (authException instanceof AccountExpiredException) {
            authenticationError = AppErrorMessage.ACCOUNT_EXPIRED;
        } else if (authException instanceof InsufficientAuthenticationException) {
            authenticationError = AppErrorMessage.INSUFFICIENT_AUTHENTICATION;
        }
        return authenticationError;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        AppErrorMessage authenticationError = getAuthenticationErrorMessage(authException);
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put(InventoryOntology.ERROR_CODE, authenticationError.getCode());
        jsonObject.put(InventoryOntology.ERROR_MESSAGE, authenticationError.getMessage());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setStatus(authenticationError.getHttpCode().value());
        response.getWriter().println(jsonObject);
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("inventory");
        super.afterPropertiesSet();
    }

}