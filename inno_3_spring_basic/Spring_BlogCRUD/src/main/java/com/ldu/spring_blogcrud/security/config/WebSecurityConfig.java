package com.ldu.spring_blogcrud.security.config;

import com.ldu.spring_blogcrud.global.config.redis.RedisService;
import com.ldu.spring_blogcrud.security.AuthenticationSuccessHandlerImpl;
import com.ldu.spring_blogcrud.security.filter.CustomAuthenticationFilter;
import com.ldu.spring_blogcrud.security.filter.JwtFilter;
import com.ldu.spring_blogcrud.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

@Configuration
@EnableWebSecurity//(debug = true) // 스프링 Security 지원을 가능하게 함
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화를 위한 어노테이션
public class WebSecurityConfig {

    private final CorsFilter corsFilter;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthenticationSuccessHandlerImpl authenticationSuccessHandler;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    // h2-console 접근 해제하기 위한 구성
    @Bean // Security 추가 설정하도록 빈 생성
    public WebSecurityCustomizer WebSecurityCustomizer() throws Exception {
        return (web) -> {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
            web
                    .ignoring() // 진입 허용하도록 시큐리티 무시
                    .antMatchers("/h2-console/**");   // 다음 주소에 접근할 때
        };
    }

    @Bean // Security 추가 설정하도록 빈 생성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 모든 API 에 대해 CSRF 보안 기능 끔
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                // api 요청 접근허용
                .antMatchers("/user/**").permitAll()
                .antMatchers("**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET,"/api/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/api/reply/**").permitAll()
                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated()
                .and()
                //로그아웃 기능 허용
                .logout()
                // 로그아웃 요청 처리 URL
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                // "접근 불가" 페이지 URL 설정
                .accessDeniedPage("/forbidden.html")
                .and()
                // 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설정하여 Session을 사용하지 않는다.
                // 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(corsFilter); // 모든 요청이 CORS 정책에서 우회하도록 설정.;
        return http.build();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationConfiguration.getAuthenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/signin");
        customAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        // BeanFactory에 의해 모든 property가 설정되고 난 뒤 실행
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    /**
     * JWT의 인증 및 권한을 확인하는 필터
     */
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtProvider, redisService);
    }
}