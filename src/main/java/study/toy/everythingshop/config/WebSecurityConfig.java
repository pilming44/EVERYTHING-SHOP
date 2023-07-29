package study.toy.everythingshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * fileName : WebSecurityConfig
 * author   : pilming
 * date     : 2023-03-07
 */
@Configuration
@EnableWebSecurity //Spring Security 구성을 사용하도록 Spring Boot에 지시
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/myPage/admin/**").hasRole("01")// 특정 URL은 ADMIN 역할만 허용
                .antMatchers("/myPage/**").authenticated()
                .antMatchers("/product/**/order").authenticated()
                .antMatchers("/product/**/register").authenticated()
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/users/signIn")
                .loginProcessingUrl("/loginProc")
                .failureHandler(customAuthenticationFailureHandler)     //로그인실패 핸들러
//                .defaultSuccessUrl("/")   //successHandler구현으로 주석처리 successHandler내부에서 리다이렉트 처리함
                .successHandler(customAuthenticationSuccessHandler)     // 로그인 성공 핸들러
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .and()
                //예외처리
                .exceptionHandling()
                    .accessDeniedPage("/error") // 권한 접근 거부 시 커스텀 에러 페이지로 리다이렉트
                .and().build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
