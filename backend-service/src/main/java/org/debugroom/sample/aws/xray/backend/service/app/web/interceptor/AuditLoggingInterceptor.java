package org.debugroom.sample.aws.xray.backend.service.app.web.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.debugroom.sample.aws.xray.backend.service.domain.model.dynamodb.Log;
import org.debugroom.sample.aws.xray.backend.service.domain.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;

@Component
public class AuditLoggingInterceptor extends HandlerInterceptorAdapter {

    private static final String HEADER_KEY = "X-Amzn-Trace-Id";

    @Autowired
    AuditLogService auditLogService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        Log log = Log.builder()
                .userId("1")
                .traceId(getTraceId(request))
                .build();

        auditLogService.addLogService(log);
    }

    private String getTraceId(HttpServletRequest request){
        String header = request.getHeader(HEADER_KEY);
        String[] headerElements = StringUtils.split(header, ";");
        String rootElements = Arrays.stream(headerElements)
                .filter(headerElement -> headerElement.startsWith("Root="))
                .findFirst().get();
        String[] traceIdElement = StringUtils.split(rootElements, "=");
        return traceIdElement[1];
    }

}
