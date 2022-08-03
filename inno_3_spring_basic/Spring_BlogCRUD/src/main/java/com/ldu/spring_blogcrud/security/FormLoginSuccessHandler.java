package com.ldu.spring_blogcrud.security;

import com.ldu.spring_blogcrud.global.config.redis.RedisService;
import com.ldu.spring_blogcrud.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";

    @Autowired
    private RedisService redisService;

    // 토큰에 대한 인증을 실행.
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        // Token 생성 - 라이브러리 이용.
        // userDetails 가지고 header.payload.signature 생성.
        final String authToken = JwtTokenUtils.generateJwtToken(userDetails.getUsername(), userDetails.getPassword());
        final String refreshToken = JwtTokenUtils.generateRefreshToken();

        // redis 저장 - key: username, value: refreshToken, duration: 일주일
        redisService.setValues(userDetails.getUsername(), refreshToken, Duration.ofDays(7));

        // Authorization : BEARER {TOKEN} 형식 add Header
//        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + authToken);
        response.addHeader("Access-Token", authToken);
        response.addHeader("Refresh-Token", refreshToken);

        // 여기까지가 formLoginFilter의 종료
    }

}
