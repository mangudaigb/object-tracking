package com.gb.trace.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

public class InboundTrackerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader("TRACE-ID");
        String spanId = request.getHeader("SPAN-ID");
        System.out.println("Inside the TrackerInterceptor: ");
        if (traceId == null || traceId.isBlank()) {
            MDC.put("TRACE-ID", UUID.randomUUID().toString());
        } else {
            MDC.put("TRACE-ID", traceId);
        }
        if (spanId == null || spanId.isBlank()) {
            MDC.put("TRACE-ID", UUID.randomUUID().toString());
        } else {
            MDC.put("PARENT-SPAN-ID", spanId);
        }
        MDC.put("SPAN-ID", UUID.randomUUID().toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {

    }
}
