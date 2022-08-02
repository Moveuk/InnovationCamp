package com.ldu.spring_blogcrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
//@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화를 위한 어노테이션
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean // Security 추가 설정하도록 빈 생성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // API (POST /api/**) 에 대해 CSRF 무시
        http.csrf()
                .ignoringAntMatchers("/**");
        http
                .authorizeHttpRequests((authz) ->
                        authz
                                .antMatchers("/images/**").permitAll() // image 폴더를 login 없이 허용
                                .antMatchers("/css/**").permitAll() // css 폴더를 login 없이 허용
                                .antMatchers("/signup").permitAll() // 회원가입 API 를 login 없이 허용
                                .antMatchers("/signin").permitAll() // 로그인 API 를 login 없이 허용
                                .anyRequest().authenticated()   // 어떤 요청이든 '인증'
                )
                    // 로그인 기능 허용
                    .formLogin()
//                    .loginPage("/user/login")
                    .loginProcessingUrl("/signin")
//                    .defaultSuccessUrl("/")
//                    .failureUrl("/user/login?error")
                    .permitAll()
                .and()
                //로그아웃 기능 허용
                    .logout()
                    .logoutUrl("/logout")
                    .permitAll();

        return http.build();
    }

    // h2-console 접근 해제하기 위한 구성
    // 파비콘 관련 요청은 Spring Security 로직 수행 해제
    @Bean // Security 추가 설정하도록 빈 생성
    public WebSecurityCustomizer WebSecurityCustomizer() throws Exception {
        return (web) -> {
            web
                    .ignoring() // 진입 허용하도록 시큐리티 무시
                    .antMatchers(   // 다음 주소에 접근할 때
                            "/h2-console/**"
                            , "/favicon.ico"
                    );
        };
    }
}