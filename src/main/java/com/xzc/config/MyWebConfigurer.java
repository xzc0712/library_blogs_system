package com.xzc.config;

import com.xzc.interceptor.LoginInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/file/**").addResourceLocations("file:"+"d:/workspace/img/");
    }

    //登录拦截时使用的拦截器
    //由于shiro的权限机制要靠它自身提供的过滤器实现，因此弃用
    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //    //对所有路径应用拦截器，除了以下地址
    //    registry.addInterceptor(getLoginInterceptor())
    //            .addPathPatterns("/**")
    //            .excludePathPatterns("/index.html")
    //            .excludePathPatterns("/api/login")
    //            .excludePathPatterns("/api/logout");
    //
    //    //拦截指定的list里面的页面，可以把要拦截的内容存进去
    //    //registry.addInterceptor(new UserInterceptor()).addPathPatterns(list);
    //}

    //CORS是一种标准的跨域解决方案，
    // 它允许服务器在响应头中声明允许跨域的源、请求头、响应头等信息，从而实现安全的跨域数据传输和资源共享
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
