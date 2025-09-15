package com.pm.backend.filter;

import com.pm.backend.service.JWTService;
import com.pm.backend.service.MyUserDetailService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {
    private final MyUserDetailService userDetailService;
    private final JWTService jwtService;

    public JWTFilter(MyUserDetailService userDetailService, JWTService jwtService) {
        this.userDetailService = userDetailService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader!=null && !authHeader.isBlank() && authHeader.startsWith("Bearer")){
            String jwt = authHeader.substring(7);
            if(jwt == null || jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Token");
            } else {
                try{
                    String username = jwtService.extractUserID(jwt);
                    UserDetails userDetails = userDetailService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,userDetails.getPassword(),userDetails.getAuthorities());

                    if(SecurityContextHolder.getContext().getAuthentication() == null){
                        SecurityContextHolder.getContext().setAuthentication(authToken);
//                        SecurityContextHolder.getContext().set;
                    }
                } catch(JWTVerificationException exc){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid Token");
                }
            }
        }
System.out.print("here1");

        filterChain.doFilter(request,response);
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer")) {
//            filterChain.doFilter(request, response);
//            System.out.print("here2");
//
//            return;
//        }
//
//        try {
//            String jwt = authHeader.substring(7);
//            System.out.print("gewre2");
//
//            String userId = jwtService.extractUserID(jwt);
//            UserDetails userDetails = userDetailService.loadUserByUsername(userId);
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, userDetails.getPassword(), userDetails.getAuthorities());
//            if (SecurityContextHolder.getContext().getAuthentication() == null) {
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        } catch (JWTVerificationException exc) {
//            log.error("e: ", exc);
//
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Token");
//        }
//        System.out.print("here3");
//        filterChain.doFilter(request, response);
    }
}
