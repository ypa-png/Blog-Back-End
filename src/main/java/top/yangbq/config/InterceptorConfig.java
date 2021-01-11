package top.yangbq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.yangbq.intercepter.JWTIntercepter;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors ( InterceptorRegistry registry ) {
        registry.addInterceptor ( new JWTIntercepter ( ) )
                .addPathPatterns ( "/user/*" )
                .addPathPatterns ( "/article/*" )
                .excludePathPatterns ( "/user/login" )
                .excludePathPatterns ( "/user/regist" )
                .excludePathPatterns ( "/portal/*" );
    }
}
