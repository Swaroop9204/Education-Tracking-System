package com.project.ets.security;

import com.project.ets.enums.UserRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
@AllArgsConstructor
public class RefreshFilter extends OncePerRequestFilter {
    private JWT_Service jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       Cookie[] cookies= request.getCookies();
       if(cookies!=null){
           Optional<Cookie> accessTokenCookie= Arrays.stream(cookies)
                   .filter(cookie -> "rt".equals(cookie.getName()))
                   .findFirst();
           if(accessTokenCookie.isPresent()){
               String token=accessTokenCookie.get().getValue();
               if(!token.isEmpty()){
                   Claims claims=jwtService.parseJwt(token);
                   String email=claims.get("email",String.class);
                   String role=claims.get("role",String.class);
                   if(email!=null && role!=null){
                       UserRole userRole=UserRole.valueOf(role);
                       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, userRole.getPrivileges()
                               .stream()
                               .map((privilage) -> {
                                   return new SimpleGrantedAuthority(privilage.name());
                               })
                               .toList());
                       authenticationToken.setDetails(new WebAuthenticationDetails(request));
                       SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                   }
               }

           }
       }
       filterChain.doFilter(request,response);
    }
}
