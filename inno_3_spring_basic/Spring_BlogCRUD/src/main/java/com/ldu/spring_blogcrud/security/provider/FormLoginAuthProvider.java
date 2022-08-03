package com.ldu.spring_blogcrud.security.provider;

import com.ldu.spring_blogcrud.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

public class FormLoginAuthProvider implements AuthenticationProvider {

    @Resource(name="userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    public FormLoginAuthProvider(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // 로그인 인증 절차
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // FormLoginFilter 에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String username = token.getName();
        String password = (String) token.getCredentials();

        // UserDetailsService 를 통해 DB에서 username 으로 사용자 조회
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
        }

        // 인증 성공하면 다음을 호출하고 결과적으로 FormLoginSuccessHandler를 찾아가게 됨.(onAuthenticationSuccess() 메소드로)
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // supports 메서드의 authentication 객체가 우리가 filter에서 만든 UsernamePasswordAuthenticationToken객체를 같은지 비교하기 때문에 이 provider에서 처리 가능한지 알 수 있음.
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
