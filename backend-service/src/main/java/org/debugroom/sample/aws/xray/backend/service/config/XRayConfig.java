package org.debugroom.sample.aws.xray.backend.service.config;

import java.io.IOException;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.spring.aop.AbstractXRayInterceptor;
import com.amazonaws.xray.spring.aop.XRayInterceptorUtils;
import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;


@Slf4j
@Aspect
@Configuration
@EnableAspectJAutoProxy
public class XRayConfig extends AbstractXRayInterceptor {

    @Bean
    public AWSXRayServletFilter awsXrayServletFitler(){
        return new AWSXRayServletFilter("SampleXrayBackendService");
    }

    static {
        try{
            AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard()
                    .withSamplingStrategy(new LocalizedSamplingStrategy(
                            ResourceUtils.getURL("classpath:sampling-rules.json")));
            AWSXRay.setGlobalRecorder(builder.build());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(awsXrayServletFitler());
        return filterRegistrationBean;
    }

    @Override
    @Pointcut("@within(com.amazonaws.xray.spring.aop.XRayEnabled) " +
            "&& execution(* org.debugroom.sample.aws.xray.backend.service..*.*(..))")
    protected void xrayEnabledClasses() {
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup(){
        AWSXRay.endSegment();
    }

}
