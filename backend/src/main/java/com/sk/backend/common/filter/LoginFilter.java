package com.sk.backend.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.backend.common.util.JWTUtil;
import com.sk.backend.domain.dto.user.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
            final HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.info("로그인을 시도합니다. email = {}, pwd = {}", username, password);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, password, null);

        // token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }


    // 로그인 성공시 실행되는 메서드 JWT 2개를 반환한다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException, ServletException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("로그인 이후 JWT를 발급 받을 유저입니다. = {}", customUserDetails.getUser().getUsername());

        String username = customUserDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // accessToken 발급하기
        String accessToken = jwtUtil.createAccessJwt(username, role);
        response.addHeader("Authorization", "Bearer " + accessToken);

        // refreshToken 발급하기
        String refreshToken = jwtUtil.createRefreshJwt(username, role);
        response.addCookie(createCookie("refreshToken", refreshToken));

        log.info("발급한 accessToken 입니다. = {}", accessToken);
        log.info("발급한 refreshToken 입니다. = {}", refreshToken);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    // 로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed) {
        log.info("로그인 실패");
        response.setStatus(401);
    }
}
