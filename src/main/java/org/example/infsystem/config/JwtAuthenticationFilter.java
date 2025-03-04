package org.example.infsystem.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import jakarta.servlet.ServletException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        if (path.equals("/auth/login") || path.equals("/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            System.out.println("Received token: " + token); // Логирование токена
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("secretKeySecretKey12345678secretKeySecretKey12345678secretKeySecretKey" +
                                "12345678secretKeySecretKey12345678secretKeySecretKey12345678")
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();
                System.out.println("Extracted username: " + username); // Логирование username
                if (username != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                System.out.println("Token validation failed: " + e.getMessage()); // Логирование ошибки
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    public static Long extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("secretKeySecretKey12345678secretKeySecretKey12345678secretKeySecretKey" +
                        "12345678secretKeySecretKey12345678secretKeySecretKey12345678")
                .parseClaimsJws(token)
                .getBody();
        return claims.get("id", Long.class);
    }


}
