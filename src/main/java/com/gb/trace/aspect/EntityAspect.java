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
    @Pointcut("this(org.springframework.data.repository.Repository)")
    public void getAllAdvice() {

    }



    @Around("getAllAdvice()")
    public Object logDatabaseCalls(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Repository calls are being made!");
        System.out.println(pjp.getSignature().getName());
        System.out.println(MDC.get("thread-local"));
//        ThreadLocal<String> threadLocal = ThreadLocal.
        return pjp.proceed();
    }
}
