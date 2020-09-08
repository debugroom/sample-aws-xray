package org.debugroom.sample.aws.xray.config;

import java.io.IOException;
import java.net.URI;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorder;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.Subsegment;
import com.amazonaws.xray.entities.TraceHeader;
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.spring.aop.AbstractXRayInterceptor;
import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import org.debugroom.sample.aws.xray.domain.ServiceProperties;

@Aspect
@Configuration
@EnableAspectJAutoProxy
public class XRayConfig extends AbstractXRayInterceptor {

    @Autowired
    ServiceProperties serviceProperties;


    private final AWSXRayRecorder recorder = AWSXRay.getGlobalRecorder();

    @Bean
    public AWSXRayServletFilter awsXrayServletFitler(){
        return new AWSXRayServletFilter("SampleXrayApp");
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

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(serviceProperties.getDns())
                .filter(exchangeFilterFunction())
                .build();
    }

    private ExchangeFilterFunction exchangeFilterFunction(){
        return (clientRequest, nextFilter) -> {
            Segment segment = AWSXRay.getCurrentSegment();
            Subsegment subsegment = AWSXRay.getCurrentSubsegment();
            TraceHeader traceHeader = new TraceHeader(segment.getTraceId(),
                    segment.isSampled() ? subsegment.getId() : null,
                    segment.isSampled() ? TraceHeader.SampleDecision.SAMPLED : TraceHeader.SampleDecision.NOT_SAMPLED);
            ClientRequest newClientRequest = ClientRequest.from(clientRequest)
                    .header(TraceHeader.HEADER_KEY, traceHeader.toString())
                    .build();
           return nextFilter.exchange(newClientRequest);
        };
    }


    @Override
    @Pointcut("@within(com.amazonaws.xray.spring.aop.XRayEnabled) " +
            "&& execution(* org.debugroom.sample.aws.xray..*.*(..))")
    protected void xrayEnabledClasses() {

    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup(){
        AWSXRay.endSegment();
    }

}
