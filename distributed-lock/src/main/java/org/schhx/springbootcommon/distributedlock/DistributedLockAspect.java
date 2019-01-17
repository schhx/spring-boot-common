package org.schhx.springbootcommon.distributedlock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author shanchao
 * @date 2019-01-16
 */
@Aspect
@Component
public class DistributedLockAspect {

    @Autowired
    private LockOperation lockOperation;

    @Around("@annotation(org.schhx.springbootcommon.distributedlock.DistributedLock)")
    public Object distributedLock(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        DistributedLock annotation = AnnotationUtils.findAnnotation(targetMethod, DistributedLock.class);

        String redisKey = getRediskey(joinPoint);
        String value = UUID.randomUUID().toString();
        boolean success = lockOperation.lock(redisKey, value, annotation.expire(), annotation.timeUnit());
        if (!success) {
            throw new RuntimeException("访问频繁，请稍后重试");
        }
        try {
            return joinPoint.proceed();
        } finally {
//            lockOperation.unlock(redisKey, value);
        }
    }

    private String getRediskey(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Object target = joinPoint.getTarget();
        Object[] arguments = joinPoint.getArgs();
        DistributedLock annotation = AnnotationUtils.findAnnotation(targetMethod, DistributedLock.class);
        String spel = null;
        if (annotation != null) {
            spel = annotation.key();
        }
        return annotation.prefix() + annotation.delimiter() + SpelUtil.parse(target, spel, targetMethod, arguments);
    }
}
