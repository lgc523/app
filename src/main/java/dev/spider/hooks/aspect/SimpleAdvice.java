package dev.spider.hooks.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SimpleAdvice {
    Logger logger = LoggerFactory.getLogger(SimpleAdvice.class);

    //[修饰符] 返回类型-* package class method (param)
    @Pointcut(value = "execution(public * dev.spider.api.AspectController.*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object apply(ProceedingJoinPoint point) throws Throwable {
        String methodName = point.getSignature().getName();
        String className = point.getTarget().getClass().toGenericString();
        ObjectMapper m = new ObjectMapper();
        Object[] args = point.getArgs();
        logger.info("\nclassName:{}\nmethodName:{}\nargs:{}", className, methodName, m.writeValueAsString(args));
        Object proceed = point.proceed();
        logger.info("proceed:{}", proceed);
        return proceed;
    }
}
