package com.jobRecomment.config;

import com.jobRecomment.interceptors.loginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
@Configuration
public class webConfig implements WebMvcConfigurer {
    @Resource
    private loginInterceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludePatterns = new String[]{"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                "/api", "/api-docs", "/api-docs/**", "/doc.html/**","/*.ico","/student/login","/student/register",
                "/enterprise/login","/enterprise/register","/public/**" ,"/admin/login"};
        registry.addInterceptor(interceptor).excludePathPatterns(excludePatterns);
    }
}
