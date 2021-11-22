package com.revature.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Aspect
@Component
public class LoggerAspect {
    public LoggerAspect() {
        super();
    }

    @Before("within(com.revature.controllers.*)")
    public void logHit(final JoinPoint joinPoint) {
        log.info(joinPoint.getSignature() + " successfully hit.");
    }

    @AfterReturning(pointcut = "within(com.revature.controllers.*)", returning = "response")
    public void log(final JoinPoint joinPoint, final ResponseEntity<?> response){
        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (response.getStatusCodeValue() >= 400) {
            if (log.isWarnEnabled()) {
                log.warn(joinPoint.getSignature().getDeclaringTypeName().split("\\.")[3] +
                        " resolved " + request.getMethod() +
                        " returning status code " + response.getStatusCode());
            }
        } else {
            if (log.isInfoEnabled()) {
                log.info(joinPoint.getSignature().getDeclaringTypeName().split("\\.")[3] +
                        " successfully resolved " + request.getMethod() +
                        " with status code " + response.getStatusCode());
            }
        }
    }

}
