package org.debugroom.sample.aws.xray.backend.service.config;

import org.debugroom.sample.aws.xray.backend.service.app.web.interceptor.AuditLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan("org.debugroom.sample.aws.xray.backend.service.app.web")
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    AuditLoggingInterceptor auditLoggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLoggingInterceptor);
    }

}
