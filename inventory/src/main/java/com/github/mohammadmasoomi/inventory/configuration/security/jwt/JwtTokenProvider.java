package com.github.mohammadmasoomi.inventory.configuration.security.jwt;

import com.github.mohammadmasoomi.inventory.configuration.security.service.InventoryUserDetailsService;
import com.github.mohammadmasoomi.inventory.core.entity.security.User;
import com.github.mohammadmasoomi.inventory.exception.AppErrorMessage;
import com.github.mohammadmasoomi.inventory.exception.InventoryJWTException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final InventoryUserDetailsService userDetailsService;
    @Value("${inventory.security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${inventory.security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    public JwtTokenProvider(InventoryUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(User user) {

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("auth", user.getAuthorities());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new InventoryJWTException(AppErrorMessage.JWT_EXPIRED_OR_INVALID_TOKEN_ERROR);
        } catch (UnsupportedJwtException e) {
            throw new InventoryJWTException(AppErrorMessage.UNSUPPORTED_JWT_TOKEN_ERROR);
        } catch (MalformedJwtException e) {
            throw new InventoryJWTException(AppErrorMessage.MALFORMED_JWT_TOKEN_ERROR);
        } catch (SignatureException e) {
            throw new InventoryJWTException(AppErrorMessage.SIGNATURE_TOKEN_ERROR);
        } catch (IllegalArgumentException e) {
            throw new InventoryJWTException(AppErrorMessage.JWT_TOKEN_IS_NULL_ERROR);
        }
    }

}
