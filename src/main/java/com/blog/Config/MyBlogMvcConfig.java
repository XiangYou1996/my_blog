package com.blog.Config;

import com.blog.Interceptor.AdminLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class MyBlogMvcConfig {
    @Autowired
    AdminLoginInterceptor adminLoginInterceptor;

    public void addInterceptor(InterceptorRegistry registry){
        // add interceptor
        registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login").excludePathPatterns("/admin/dist/**").excludePathPatterns("/admin/plugins/**");

    }

    public void addResourceHandlers(ResourceHandlerRegistry handlerRegistry){
        handlerRegistry.addResourceHandler("/upload/**").addResourceLocations("file:"+Constants.FILE_UPLOAD_DIC);
    }
}
