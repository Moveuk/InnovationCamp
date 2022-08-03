package com.ldu.spring_blogcrud.security.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {
    final private ObjectMapper objectMapper;

    public FormLoginFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        // 객체 만들어서 초기화
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override // 인증 시도
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try {
            // stream의 requestBody를 자바 객체형태로 변환
            JsonNode requestBody = objectMapper.readTree(request.getInputStream());

            // JSON으로 들어온 것은 reqeustBody에서 꺼내서 String 변수 저장
            String nickname = requestBody.get("nickname").asText();
            String password = requestBody.get("password").asText();

            // 인증을 위한 토큰객체 생성해서 인증 request에 값 저장장
            authRequest = new UsernamePasswordAuthenticationToken(nickname, password);
        } catch (Exception e) {
            throw new RuntimeException("username, password 입력이 필요합니다. (JSON)");
        }

        setDetails(request, authRequest);
        // username과 password가 담긴 객체를 들고 AuthenticationManager한테 인증 요청.
        // -> formLoginProvider의 authenticate()으로 이동
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
