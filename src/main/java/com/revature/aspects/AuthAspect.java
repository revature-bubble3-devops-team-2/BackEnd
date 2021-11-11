package com.revature.aspects;

import com.revature.models.Profile;
import com.revature.utilites.SecurityUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Aspect
public class AuthAspect {
    private static final Logger logger = LogManager.getLogger(AuthAspect.class);

    @Around("execution(* com.revature.controllers.*.*(..))" +
            "&& !@annotation(com.revature.aspects.annotations.NoAuthIn)" +
            "&& !@target(com.revature.aspects.annotations.NoAuthIn)")
    public ResponseEntity<?> authenticateToken(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if (token == null) {
            logger.warn("No Authorization Token Received");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            Profile profile = SecurityUtil.validateToken(token);
            if (profile == null) {
                logger.warn("Received Invalid Token");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                logger.info("Received Valid Token");
                request.setAttribute("profile", profile);
                return (ResponseEntity<?>) pjp.proceed();
            }
        }
    }

    @AfterReturning(pointcut = "within(com.revature.controllers.*)", returning = "response")
    public ResponseEntity<?> exposeHeaders(ResponseEntity<?> response) throws Throwable {

        if (response != null && !response.getHeaders().keySet().isEmpty()) {
            HttpHeaders headers = HttpHeaders.writableHttpHeaders(response.getHeaders());
            headers.setAccessControlExposeHeaders(new ArrayList<>(response.getHeaders().keySet()));
            return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
        }
        return response;
    }
}
