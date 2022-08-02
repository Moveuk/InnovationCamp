package com.ldu.spring_blogcrud.security;

import com.ldu.spring_blogcrud.security.jwt.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        // Token 생성 - 라이브러리 이용.
        // userDetails 가지고 header.payload.signature 생성.
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
        // Authorization : BEARER {TOKEN} 형식 add Header
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);
    }

}
