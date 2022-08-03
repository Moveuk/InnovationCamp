package com.ldu.spring_blogcrud.security.provider;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ldu.spring_blogcrud.security.UserDetailsImpl;
import com.ldu.spring_blogcrud.security.jwt.JwtDecoder;
import com.ldu.spring_blogcrud.security.jwt.JwtPreProcessingToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTAuthProvider implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    @Override // JwtAuthFilter에서 privider로 넘어옴
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        DecodedJWT decodedJWT = jwtDecoder.decodeJWT(token);
        String username = jwtDecoder.decodeUsername(decodedJWT);
        String password = jwtDecoder.decodePassword(decodedJWT);

        // 조회 쿼리 날리기 위해서 각각 token에서 username password 받아옴.
        UserDetailsImpl userDetails = new UserDetailsImpl(username, password);
        System.out.println("userDetails.getUsername() = " + userDetails.getUsername());
        System.out.println("userDetails.getPassword() = " + userDetails.getPassword());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication);
    }
}
