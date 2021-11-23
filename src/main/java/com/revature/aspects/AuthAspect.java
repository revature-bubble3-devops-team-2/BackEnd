package com.revature.aspects;

import com.revature.models.Profile;
import com.revature.utilites.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Aspect
@Log4j2
@Component
public class AuthAspect {
    public AuthAspect() {
        super();
    }

    @Around("execution(* com.revature.controllers.*.*(..))" +
            "&& !@annotation(com.revature.aspects.annotations.NoAuthIn)" +
            "&& !@target(com.revature.aspects.annotations.NoAuthIn)")
    public ResponseEntity<?> authenticateToken(final ProceedingJoinPoint pjp) {
        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final String token = request.getHeader("Authorization");
        ResponseEntity<?> response = null;
        if (token == null) {
            log.warn("No Authorization Token Received");
            response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            final Profile profile = SecurityUtil.validateToken(token);
            if (profile == null) {
                log.warn("Received Invalid Token");
                response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                log.info("Received Valid Token");
                request.setAttribute("profile", profile);
                try {
                    response = (ResponseEntity<?>) pjp.proceed();
                } catch (Throwable e) {
                    log.error("Unable to Proceed from Previous Join Point");
                }
            }
        }
        return response;
    }

    @AfterReturning(pointcut = "within(com.revature.controllers.*)", returning = "response")
    public ResponseEntity<?> exposeHeaders(final ResponseEntity<?> response) {
        if (response == null) {
            log.error("No Response Sent.");
        }
        if (response != null && !response.getHeaders().keySet().isEmpty()) {
            final HttpHeaders headers = HttpHeaders.writableHttpHeaders(response.getHeaders());
            headers.setAccessControlExposeHeaders(new ArrayList<>(response.getHeaders().keySet()));
            return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
        }
        return response;
    }
}
