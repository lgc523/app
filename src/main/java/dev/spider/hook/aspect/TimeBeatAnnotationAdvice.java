package dev.spider.hook.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.SourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class TimeBeatAnnotationAdvice {

    private Logger logger = LoggerFactory.getLogger(TimeBeatAnnotationAdvice.class);

    @Around("@annotation(dev.spider.annotation.TimeBeat)")
    public Object logExecuteTime(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = point.proceed();
        long execTime = System.currentTimeMillis() - start;
        String methodName = point.getSignature().getName();
        String simpleName = point.getTarget().getClass().toString();
        logger.info("exec: {} cost:{} ms", simpleName + "." + methodName, execTime);
        return proceed;
    }
}
