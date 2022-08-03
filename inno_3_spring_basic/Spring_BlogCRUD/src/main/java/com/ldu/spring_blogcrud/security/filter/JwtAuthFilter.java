package com.ldu.spring_blogcrud.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ldu.spring_blogcrud.common.exceptions.ExpiredTokenException;
import com.ldu.spring_blogcrud.global.config.redis.RedisService;
import com.ldu.spring_blogcrud.security.jwt.JwtDecoder;
import com.ldu.spring_blogcrud.security.jwt.JwtPreProcessingToken;
import com.ldu.spring_blogcrud.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

/**
 * Token 을 내려주는 Filter 가 아닌  client 에서 받아지는 Token 을 서버 사이드에서 검증하는 클레스 SecurityContextHolder 보관소에 해당
 * Token 값의 인증 상태를 보관 하고 필요할때 마다 인증 확인 후 권한 상태 확인 하는 기능
 */
public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtDecoder jwtDecoder;

    @Autowired
    private RedisService redisService;

    public JwtAuthFilter(
            RequestMatcher requiresAuthenticationRequestMatcher,
            JwtDecoder jwtDecoder
    ) {
        super(requiresAuthenticationRequestMatcher);

        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException, IOException {

        // JWT 값을 담아주는 변수 TokenPayload
        String accessTokenPayload = request.getHeader("Access-Token");
        String refreshTokenPayload = request.getHeader("Refresh-Token");
        String nickname = "";
        String password = "";
        DecodedJWT decodedJWT = null;

        // accessToken, refreshTokenPayload 없으면 로그인 하도록 처리
        if (accessTokenPayload == null || refreshTokenPayload == null) {
            response.sendRedirect("/");
            return null;
        }

        // accessToken이 있으나 유효하지 않은 경우 redis에 username으로 조회해서
        // refreshTokenPayload가 redis value와 같은지 체크
        try {
            decodedJWT = jwtDecoder.decodeJWT(accessTokenPayload);// 디코드함. 토큰이 정상적인지 내부에서 검사함.
            jwtDecoder.isExpired(decodedJWT); // 유효기간 체크
        } catch (ExpiredTokenException e) { // 유효기간 지남. redis에서 꺼내서 비교
            nickname = jwtDecoder.decodeUsername(decodedJWT);
            password = jwtDecoder.decodePassword(decodedJWT);
            String redisToken = redisService.getValues(nickname);
            if (redisToken.equals(refreshTokenPayload)) { // refresh 토큰이 같으면 두 토큰 모두 갱신
                accessTokenPayload = JwtTokenUtils.generateJwtToken(nickname, password);
                refreshTokenPayload = JwtTokenUtils.generateRefreshToken();

                // redis 저장 - key: username, value: refreshToken, duration: 일주일
                redisService.setValues(nickname, refreshTokenPayload, Duration.ofDays(7));
            } else { // refreshToken이 다르면
                throw new IllegalArgumentException("잘못된 refreshToken을 입력하셨습니다.");
            }
        }

        // AuthenticationToken 객체 생성해서 전달.
        JwtPreProcessingToken jwtToken = new JwtPreProcessingToken(accessTokenPayload);

        return super
                .getAuthenticationManager()
                .authenticate(jwtToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        /*
         *  SecurityContext 사용자 Token 저장소를 생성합니다.
         *  SecurityContext 에 사용자의 인증된 Token 값을 저장합니다.
         */
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        // FilterChain chain 해당 필터가 실행 후 다른 필터도 실행할 수 있도록 연결실켜주는 메서드
        chain.doFilter(
                request,
                response
        );
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        /*
         *	로그인을 한 상태에서 Token값을 주고받는 상황에서 잘못된 Token값이라면
         *	인증이 성공하지 못한 단계 이기 때문에 잘못된 Token값을 제거합니다.
         *	모든 인증받은 Context 값이 삭제 됩니다.
         */
        SecurityContextHolder.clearContext();

        super.unsuccessfulAuthentication(
                request,
                response,
                failed
        );
    }
}
