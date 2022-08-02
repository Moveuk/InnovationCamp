package com.ldu.spring_blogcrud.security;

import com.ldu.spring_blogcrud.security.filter.FormLoginFilter;
import com.ldu.spring_blogcrud.security.filter.JwtAuthFilter;
import com.ldu.spring_blogcrud.security.jwt.HeaderTokenExtractor;
import com.ldu.spring_blogcrud.security.provider.FormLoginAuthProvider;
import com.ldu.spring_blogcrud.security.provider.JWTAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화를 위한 어노테이션
public class WebSecurityConfig {

    private final CorsFilter corsFilter;
    private final JWTAuthProvider jwtAuthProvider;
    private final HeaderTokenExtractor headerTokenExtractor;
//    private final AuthenticationManager authenticationManager;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean // Security 추가 설정하도록 빈 생성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 모든 API 에 대해 CSRF 보안 기능 끔
        http.csrf().disable()
                .headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'")).frameOptions().disable();

        // 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다.
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하면 stateful 처럼 사용하게 되는 것이고 그것을 session을 끔으로써 다시 stateless 하게 사용하도록 함.
                .and()
                .addFilter(corsFilter) // 모든 요청이 CORS 정책에서 우회하도록 설정.
                .formLogin().disable() // 로그인 폼 기능 끄기
                .httpBasic().disable(); //Http basic Auth 기반의 로그인 인증창으로 로그인 기능을 끔.

        /*
         * 1.
         * UsernamePasswordAuthenticationFilter 이전에 FormLoginFilter, JwtFilter 를 등록합니다.
         * FormLoginFilter : 로그인 인증을 실시합니다.
         * JwtFilter       : 서버에 접근시 JWT 확인 후 인증을 실시합니다.
         */
        http
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeHttpRequests((authz) ->
                        authz
                                .antMatchers("/images/**").permitAll() // image 폴더를 login 없이 허용
                                .antMatchers("/css/**").permitAll() // css 폴더를 login 없이 허용
                                .antMatchers("/signup").permitAll() // 회원가입 API 를 login 없이 허용
                                .antMatchers("/signin").permitAll() // 로그인 API 를 login 없이 허용
                                .antMatchers("/h2-console/*").permitAll() // 로그인 API 를 login 없이 허용
                                .anyRequest().authenticated()   // 어떤 요청이든 '인증'
                )
                // 로그인 기능 허용
//                .formLogin()
//                    .loginPage("/user/login")
//                .loginProcessingUrl("/signin")
//                    .defaultSuccessUrl("/")
//                    .failureUrl("/user/login?error")
//                .permitAll()
//                .and()
                        //로그아웃 기능 허용
                .logout()
                // 로그아웃 요청 처리 URL
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                // "접근 불가" 페이지 URL 설정
                .accessDeniedPage("/forbidden.html");

        return http.build();
    }

    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationConfiguration.getAuthenticationManager());
        formLoginFilter.setFilterProcessesUrl("/signin");
        formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler());
        formLoginFilter.afterPropertiesSet();
        return formLoginFilter;
    }

    @Bean
    public FormLoginSuccessHandler formLoginSuccessHandler() {
        return new FormLoginSuccessHandler();
    }

    @Bean
    public FormLoginAuthProvider formLoginAuthProvider() {
        return new FormLoginAuthProvider(encodePassword());
    }

    private JwtAuthFilter jwtFilter() throws Exception {
        List<String> skipPathList = new ArrayList<>();

        // Static 정보 접근 허용
        skipPathList.add("GET,/images/**");
        skipPathList.add("GET,/css/**");

        // h2-console 허용
        skipPathList.add("GET,/h2-console/**");
        skipPathList.add("POST,/h2-console/**");

        // 회원 관리 API 허용
        skipPathList.add("GET,/user/**");
        skipPathList.add("POST,/signup");

        skipPathList.add("GET,/");
        skipPathList.add("GET,/basic.js");

        skipPathList.add("GET,/favicon.ico");

        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPathList,
                "/**"
        );

        JwtAuthFilter filter = new JwtAuthFilter(
                matcher,
                headerTokenExtractor
        );
        filter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());

        return filter;
    }

//    // h2-console 접근 해제하기 위한 구성
//    // 파비콘 관련 요청은 Spring Security 로직 수행 해제
//    @Bean // Security 추가 설정하도록 빈 생성
//    public WebSecurityCustomizer WebSecurityCustomizer() throws Exception {
//        return (web) -> {
//            web
//                    .ignoring() // 진입 허용하도록 시큐리티 무시
//                    .antMatchers(   // 다음 주소에 접근할 때
//                            "/h2-console/**"
//                            , "/favicon.ico"
//                    );
//        };
//    }
}