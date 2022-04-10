package com.example.tripbackend3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //비밀번호 암호화.
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
//        http.csrf().disable();
//
//        http.headers().frameOptions().disable();
//
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                // 시큐리티 폼로그인기능 비활성화
//                .formLogin().disable()
//                // 로그인폼 화면으로 리다이렉트 비활성화
//                .httpBasic().disable();

//        http.authorizeRequests()
//
//                // image 폴더를 login 없이 허용
//                .antMatchers("/images/**").permitAll()
//                // css 폴더를 login 없이 허용
//                .antMatchers("/css/**").permitAll()
//                .antMatchers("/api/signup").permitAll()
//                // 어떤 요청이든 '인증'
//                .anyRequest().authenticated()
//                .and()
//                // 로그인 기능 허용
//                .formLogin().disable()

//                .and()
//                // 로그아웃 기능 허용
//                .logout()
//                .permitAll();
//}

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//// 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
//        http.csrf().disable();
////                .ignoringAntMatchers("/user/**");
//
//        http.authorizeRequests()
//                .antMatchers("/**").permitAll()
//// image 폴더를 login 없이 허용
//                .antMatchers("/images/**").permitAll()
//// css 폴더를 login 없이 허용
//                .antMatchers("/css/**").permitAll()
//// 회원 관리 처리 API 전부를 login 없이 허용
//                .antMatchers("/user/**").permitAll()
////                .antMatchers("/user/signup").permitAll()
////                .antMatchers("/").permitAll()
//
//// 그 외 어떤 요청이든 '인증'
//                .anyRequest().authenticated()
//                .and()
//                // [로그인 기능]
//                .formLogin()
//                // 로그인 View 제공 (GET /user/login)
//                .loginPage("/user/login")
//                // 로그인 처리 (POST /user/login)
//                .loginProcessingUrl("/user/login")
//                // 로그인 처리 후 성공 시 URL
//                .defaultSuccessUrl("/")
//                // 로그인 처리 후 실패 시 URL
//                .failureUrl("/user/login?error")
//                .permitAll()
//                .and()
//                // [로그아웃 기능]
//                .logout()
//                // 로그아웃 처리 URL
//                .logoutUrl("/api/logout")
//                .logoutSuccessUrl("/")
//                .permitAll();
//
//        // "접근 불가" 페이지 URL 설정
//
//        //중복 로그인 방지
//        http.sessionManagement()
//                .maximumSessions(1) //세션 최대 허용 수
//                .maxSessionsPreventsLogin(false); // false이면 중복 로그인하면 이전 로그인이 풀린다.
//    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
    // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
//        http.csrf()
//                .ignoringAntMatchers("/user/**");

//        http.authorizeRequests()
//                // image 폴더를 login 없이 허용
//                .antMatchers("/images/**").permitAll()
//                // css 폴더를 login 없이 허용
//                .antMatchers("/css/**").permitAll()
//                // 회원 관리 처리 API 전부를 login 없이 허용
//                .antMatchers("/user/**").permitAll()
//                // 그 외 어떤 요청이든 '인증'
//                .anyRequest().authenticated()
//                .and()
//                // 로그인 기능
//        http
//                .csrf().disable()
//                .cors().disable()
//                .formLogin()
//                .successHandler(new LoginAuthHandler())
////                .loginPage("/user/login")
////                .defaultSuccessUrl("/")
////                .failureUrl("/user/login?error")
//                .permitAll()
//                .and()
//                // 로그아웃 기능
//                .logout()
//                .permitAll();
//        final ExpressionUrlAuthorizationConfigurer <HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = http.authorizeRequests();
//        expressionInterceptUrlRegistry.antMatchers("/api/signup").permitAll();


//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests()
                .antMatchers("/api/comment/**").permitAll()
                .antMatchers("/user/login").permitAll()
                .and()
                .formLogin().disable()
        ;
    }
}