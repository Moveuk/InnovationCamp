package com.ldu.spring_blogcrud.security.filter;

import com.ldu.spring_blogcrud.global.config.redis.RedisService;
import com.ldu.spring_blogcrud.security.UserDetailsImpl;
import com.ldu.spring_blogcrud.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

//    public static final String AUTHORIZATION_HEADER = "Authorization";
//    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    private static final List<String> EXCLUDE_URL =
            Collections.unmodifiableList(
                    Arrays.asList(
                            "/h2-console",
                            "/api/posts",
                            "/api/posts/**",
                            "/api/replies",
                            "/signup",
                            "/signin",
                            "/favicon.ico",
                            "/error"
                    ));
    /**
     * 토큰 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // Request Header에서 토큰 추출
//        String jwt = resolveToken(request);
        String accessToken = request.getHeader("Access-Token");
        String refreshToken = request.getHeader("Refresh-Token");

        // Token 유효성 검사
        if (StringUtils.hasText(accessToken) && jwtProvider.isValidToken(accessToken)) { // 토큰 들어있고 문제없으면
            // 토큰으로 인증 정보를 추출
            Authentication authentication = jwtProvider.getAuthentication(accessToken); // 토큰으로부터 정보 받아서
            // SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication); // 컨텍스트에 저장.
        } else if ((StringUtils.hasText(accessToken) && !jwtProvider.isValidToken(accessToken))) { // 토큰 들어있고 만료되었으면.
            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
            String redisTokenForRefresh = redisService.getValues(userDetails.getUsername());

            if (redisTokenForRefresh.equals(refreshToken)) { // 같으면 둘다 새로 토큰 뽑고 레디스에 저장
                accessToken = jwtProvider.generateJwtToken(authentication);
                refreshToken = jwtProvider.generateRefreshJwtToken();

                redisService.setValues(userDetails.getUsername(),refreshToken); // 재저장.

                // response header에 다시 올려줘야함. 둘다 변경되었으므로
                response.addHeader("Access-Token", accessToken);
                response.addHeader("Refresh-Token", refreshToken);
            }
        } else if (!StringUtils.hasText(refreshToken) || jwtProvider.isValidToken(refreshToken)) { // 9번 요구사항
            //Refresh Token을 전달하지 않거나 정상 토큰이 아닐 때는 "Token이 유효하지 않습니다." 라는 에러 메세지를 response에 포함하기
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
        // 토큰이 없을때는 그냥 지나감.


        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return true; // 모두 다 걸러짐.
        return EXCLUDE_URL.stream().anyMatch(url -> request.getServletPath().startsWith(url));
    }
/**
     * Request Header에서 토큰 추출
     * 본인은 Access-Token, Refresh-Token로 헤더에 저장해두었으므로 바로 꺼내씀.
     */
//    private String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }

}