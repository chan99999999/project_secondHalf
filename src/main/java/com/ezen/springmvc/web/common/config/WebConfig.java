package com.ezen.springmvc.web.common.config;

import java.util.Arrays;
import java.util.List;

import com.ezen.springmvc.web.common.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 인터셉터 등록을 위한 자바 설정 클래스 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 로그인 체크가 필요 없는 요청 URI 목록
    public static final  List<String> loginNotEssential =
            Arrays.asList(
                    "/", "/css/**", "/member/image/**","/img/**", "/js/**", "/*.ico", "/vendor/**",
                    "/member/signup/**", "/member/login", "/member/logout", "/member/result", "/member/idcheck/{inputId}",
                    "/member/nicknameCheck/{inputNickname}",
                    "/member/searchIdResult", "/member/searchMember", "/member/searchId", "/daily/image/**","/member/searchPasswd", "/admin/**", "/admin/search",
                    "/member/searchPasswdResult", "/meet", "/meet/3","/map/**", "/daily/{categoryId}", "/daily/2", "/daily", "/daily/image/**", "/daily/{categoryId}/read/**");

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 체크 인터셉터 등록
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(loginNotEssential);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");
    }
}