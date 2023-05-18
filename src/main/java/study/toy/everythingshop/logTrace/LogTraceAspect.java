package study.toy.everythingshop.logTrace;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class LogTraceAspect {
    private final LogTrace logTrace;

//    @Before("@annotation(study.toy.everythingshop.logTrace.Trace)")
//    public void doTrace(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        log.info("[trace] {} args={}", joinPoint.getSignature(), args);
//    }
    @Around("execution(* study.toy.everythingshop..*(..)) && @target(study.toy.everythingshop.logTrace.Trace)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            //로직 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
