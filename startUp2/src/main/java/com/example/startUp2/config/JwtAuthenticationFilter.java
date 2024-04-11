package com.example.startUp2.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final Cookie[] cookies = request.getCookies();
        String jwt = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                String authorizationResult = allowUserToEndpoint(userDetails, request);
                if (!"allow".equals(authorizationResult)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, authorizationResult);
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
    private String allowUserToEndpoint(UserDetails userData, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        List<String> roles = userData.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        if (roles.isEmpty()) {
            return "User does not have any role";
        }

        if (endpoint.contains("/user")){
            if (roles.contains("USER")) return "allow";
        }

        if (endpoint.contains("/announcement")){
            if (roles.contains("USER")) return "allow";
        }

        if (endpoint.contains("/api/media")){
            if (roles.contains("USER")) return "allow";
        }

        if (endpoint.contains("/tariff")){
            if (roles.contains("USER")) return "allow";
        }

        if (endpoint.contains("/comments")){
            if (roles.contains("USER")) return "allow";
        }

        if (endpoint.contains("/api/media/upload")){
            if (roles.contains("USER")) return "allow";
        }

        return "Restricted!";
    }
}
