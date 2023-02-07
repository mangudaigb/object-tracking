package com.gb.trace.interceptor;

import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.http.server.ServletServerHttpRequest;

import java.io.IOException;
import java.net.URI;


public class OutboudTrackerInterceptor implements ClientHttpRequestInterceptor {


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        String traceId = MDC.get("TRACE-ID");
        String spanId = MDC.get("SPAN-ID");
        headers.add("TRACE-ID", traceId);
        headers.add("SPAN-ID", spanId);
        return execution.execute(request, body);
    }
}
