package com.sk.backend.common.filter;
//
//import com.sk.backend.common.util.JWTUtil;
//import com.sk.backend.domain.dto.user.CustomOAuth2User;
//import com.sk.backend.domain.dto.user.UserDto;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@RequiredArgsConstructor
//public class JWTFilter extends OncePerRequestFilter {
//
//    private final JWTUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
//
//        String path = request.getRequestURI();
//
//        if (path.equals("/users/reissue")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String accessToken = null;
//        String authorizationHeader = request.getHeader("Authorization");
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            accessToken = authorizationHeader.substring(7);
//        }
//
//        if (accessToken == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        if(jwtUtil.isExpired(accessToken)){
//            PrintWriter writer = response.getWriter();
//            writer.print("accessToken expired");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//        String category = jwtUtil.getCategory(accessToken);
//        if (!category.equals("access")) {
//
//            PrintWriter writer = response.getWriter();
//            writer.print("invalid accessToken");
//
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//        Long userId = jwtUtil.getUserId(accessToken);
//        String username = jwtUtil.getUsername(accessToken);
//        String role = jwtUtil.getRole(accessToken);
//
//        UserDto userDTO = UserDto.builder()
//                .userId(userId)
//                .username(username)
//                .role(role)
//                .build();
//
//        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);
//
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        filterChain.doFilter(request, response);
//    }
//}

import com.sk.backend.common.util.JWTUtil;
import com.sk.backend.domain.dto.user.CustomUserDetails;
import com.sk.backend.domain.dto.user.UserDto;
import com.sk.backend.domain.entity.User;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        log.info("요청 URL: {}", request.getRequestURL());
//        log.info("Servlet 경로: {}", request.getServletPath());

        String authorization = request.getHeader("Authorization");
//        log.info("헤더에서 찾은 Authorization 정보입니다. = {}", authorization);

        try {
            if (authorization != null && authorization.startsWith("Bearer ")) {
                log.info("Access 토큰 인증을 시작합니다.");
                if (!handleAccessToken(authorization, request, response)) {
                    return; // 토큰 처리 중 오류 발생 시 필터 체인 중단
                }
            } else {
                log.info("유효한 Authorization 헤더가 없습니다.");
            }
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            log.error("JWT validation error가 발생했습니다.", e);
        } catch (Exception e) {
            log.error("JWT 필터 처리 중 예외 발생", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal Server Error");
        }
    }

    private boolean handleAccessToken(String authorization, HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        String token = authorization.split(" ")[1];

        try {
            if (jwtUtil.isExpired(token)) {
                log.info("만료된 JWT 토큰입니다.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("AccessTokenExpired");
                return false; // 필터 체인 중단
            }
            log.info("유효한 JWT 토큰입니다.");

            // 유저 정보 저장하기
            setAuthenticationToContext(token);
            return true; // 필터 체인 계속 진행

        } catch (Exception e) {
            log.error("JWT 토큰 처리 중 예외 발생", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT Token");
            return false; // 필터 체인 중단
        }
    }

    private void setAuthenticationToContext(String token) {
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        UserDto userDTO = UserDto.builder()
                .username(username)
                .role(role)
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(userDTO);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}