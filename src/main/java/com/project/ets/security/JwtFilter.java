package com.project.ets.security;

import com.project.ets.enums.UserRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JWT_Service jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getHeaders("Authorization"));
        String token = request.getHeader("Authorization");

        if(token!=null) {
            token = token.substring(7);
            if (!token.isEmpty()) {
                Claims claims = jwtService.parseJwt(token);
                String role = claims.get("role", String.class);
                String email = claims.get("email", String.class);
                if (role != null && email != null) {
                    UserRole userRole = UserRole.valueOf(role);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, userRole.getPrivileges()
                            .stream()
                            .map((privilage) -> {
                                return new SimpleGrantedAuthority(privilage.name());
                            })
                            .toList());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.info("Token authenticated successfully.");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
