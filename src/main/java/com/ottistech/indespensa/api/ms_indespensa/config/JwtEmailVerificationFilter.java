//package com.ottistech.indespensa.api.ms_indespensa.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class JwtEmailVerificationFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
//            throws ServletException, IOException {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
//
//            String emailFromToken = jwtAuth.getToken().getClaim("email");
//            String userIdFromToken = jwtAuth.getToken().getClaim("user_id");
//
//            String emailFromRequest = request.getParameter("email");
//            String userIdFromRequest = request.getParameter("user_id");
//
//            if (!emailFromToken.equals(emailFromRequest) && !userIdFromToken.equals(userIdFromRequest)) {
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
