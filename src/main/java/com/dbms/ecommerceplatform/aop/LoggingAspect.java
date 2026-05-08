package com.dbms.ecommerceplatform.aop;

import com.dbms.ecommerceplatform.repository.SystemLogEntity;
import com.dbms.ecommerceplatform.repository.SystemLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {

    private final SystemLogRepository systemLogRepository;

    public LoggingAspect(SystemLogRepository systemLogRepository) {
        this.systemLogRepository = systemLogRepository;
    }

    @Pointcut("within(com.dbms.ecommerceplatform.controller..*) && !within(com.dbms.ecommerceplatform.controller.PageController)")
    public void controllerPointcut() {}

    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        saveLog(joinPoint, "SUCCESS");
    }

    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        saveLog(joinPoint, "ERROR: " + error.getMessage());
    }

    private void saveLog(JoinPoint joinPoint, String details) {
        String username = "Anonymous";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            username = authentication.getName();
        }

        String action = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        SystemLogEntity log = new SystemLogEntity(username, action, details, LocalDateTime.now());
        systemLogRepository.save(log);
    }
}
