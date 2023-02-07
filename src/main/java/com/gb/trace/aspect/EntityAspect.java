package com.gb.trace.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EntityAspect {

//    private Logger _logger = LoggerFactory.getLogger(EntityAspect.class);

//    @Pointcut("this(org.springframework.data.repository.Repository)")
//    @Pointcut("this(org.springframework.data.jpa.repository.JpaRepository)")
    @Pointcut("execution(* org.springframework.data.repository.CrudRepository.save(..))")
    public void getAllAdvice() {

    }



    @Around("getAllAdvice()")
    public Object logDatabaseCalls(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Repository calls are being made!");
        System.out.println(pjp.getSignature().getName());
        String traceId = MDC.get("TRACE-ID");
        String parentSpanId = MDC.get("PARENT-SPAN-ID");
        String spanId = MDC.get("SPAN-ID");

        Object args = pjp.getArgs();

        // If id == null then CREATE else UPDATE


        return pjp.proceed();
    }
}
