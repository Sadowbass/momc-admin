package com.momc.admin.infra.config;

import com.momc.admin.utils.PageRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class HandlerArgumentResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageRequest.PageRequestArgumentResolver pageRequestArgumentResolver = new PageRequest.PageRequestArgumentResolver();
        resolvers.add(pageRequestArgumentResolver);
    }
}
