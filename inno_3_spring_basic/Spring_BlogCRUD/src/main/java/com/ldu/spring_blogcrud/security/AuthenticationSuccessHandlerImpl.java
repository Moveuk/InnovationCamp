package com.ldu.spring_blogcrud.security;

import com.ldu.spring_blogcrud.global.config.redis.RedisService;
import com.ldu.spring_blogcrud.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {
    private RedisService redisService;
    private JwtProvider jwtProvider;

    // 토큰에 대한 인증을 실행.
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        // 전달받은 인증정보 SecurityContextHolder에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        // Token 생성 - 라이브러리 이용.
        // userDetails 가지고 header.payload.signature 생성.
        final String authToken = jwtProvider.generateJwtToken(authentication);
        final String refreshToken = jwtProvider.generateRefreshJwtToken();

        // redis 저장 - key: username, value: refreshToken, duration: 일주일
        redisService.setValues(userDetails.getUsername(), refreshToken, Duration.ofDays(7));

        // Authorization : BEARER {TOKEN} 형식 add Header
//        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + authToken);
        response.addHeader("Access-Token", authToken);
        response.addHeader("Refresh-Token", refreshToken);

        System.out.println("로그인 성공 했음");

        // 여기까지가 formLoginFilter의 종료
    }

}
