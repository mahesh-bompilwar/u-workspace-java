package com.techdenovo.rolebackend.config;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = extractJwtFromRequest(httpServletRequest);
            System.out.println("---------------"+jwtToken);
            if(StringUtils.hasText(jwtToken) && jwtUtil.validateToken(jwtToken)){
                UserDetails userDetails = new User(jwtUtil.getUsernameFromToken(jwtToken),"",jwtUtil.getRolesFromToken(jwtToken));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,"",jwtUtil.getRolesFromToken(jwtToken));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("CannotSet the security context");
            }
        } catch (ExpiredJwtException e) {
           httpServletRequest.setAttribute("exception",e);
        }catch (BadCredentialsException e){
            httpServletRequest.setAttribute("exception",e);
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private String extractJwtFromRequest(HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames =  httpServletRequest.getHeaderNames();
        while(headerNames.hasMoreElements()){
            System.out.println(headerNames.nextElement());
        }
        String bearerToken = httpServletRequest.getHeader("Authorization");
        System.out.println("token ..."+bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7,bearerToken.length());
        }
        return null;

    }
}
