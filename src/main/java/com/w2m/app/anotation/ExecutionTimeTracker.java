package com.w2m.app.anotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeTracker {

    @Around("@annotation(com.w2m.app.anotation.TrackExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long endTime = System.currentTimeMillis();

        log.info("Method "+ pjp.getSignature() +" took "+(endTime-startTime)+" milliseconds to execute: ");
        return obj;
    }

}
