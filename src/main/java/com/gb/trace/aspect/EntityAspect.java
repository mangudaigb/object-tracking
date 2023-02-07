package com.gb.trace.aspect;

import com.gb.trace.entity.Ship;
import com.gb.trace.entity.track.ShipTrackerEvent;
import com.gb.trace.repository.ShipTrackerRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class EntityAspect {

//    private Logger _logger = LoggerFactory.getLogger(EntityAspect.class);

//    @Pointcut("this(org.springframework.data.repository.Repository)")
//    @Pointcut("this(org.springframework.data.jpa.repository.JpaRepository)")

    private static final Logger _logger = LoggerFactory.getLogger(EntityAspect.class);

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${spring.application.instance.id}")
    private String instanceId;

    @Autowired
    private ShipTrackerRepository trackerRepository;

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

        Object[] args = pjp.getArgs();
        if (args[0] instanceof Ship) {
            Ship ship = (Ship) args[0];
            ShipTrackerEvent tracker = new ShipTrackerEvent();
            tracker.setName(ship.getName());
            tracker.setPort(ship.getPort());
            tracker.setDate(ship.getDate());

            tracker.setProgramName(applicationName);
            tracker.setInstanceId(instanceId);
            tracker.setTraceId(traceId);
            tracker.setSpanId(spanId);
            tracker.setParentSpanId(parentSpanId);

            if (ship.getId() != null) {
                // find the last entry for this entity
                // assign the entity as parent of the current object
                Optional<ShipTrackerEvent> optionalParentEvent = trackerRepository.findFirst1ByNameOrderByDateDesc(ship.getName());
                if (optionalParentEvent.isPresent()) {
                    ShipTrackerEvent parentTrackerEvent = optionalParentEvent.get();
                    tracker.setParentShipTrackerEvent(parentTrackerEvent);
                } else {
                    _logger.error("This condition should never happen!");
                }
            }

            trackerRepository.save(tracker);
        }


        // If id == null then CREATE else UPDATE


        return pjp.proceed();
    }
}
