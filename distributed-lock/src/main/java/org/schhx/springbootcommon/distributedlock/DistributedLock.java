package org.schhx.springbootcommon.distributedlock;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author shanchao
 * @date 2019-01-16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributedLock {

    /**
     * key的前缀
     *
     * @return key的前缀
     */
    String prefix() default "distributed-lock";

    /**
     * 支持 SpEL 表达式
     *
     * @return
     */
    String key();

    /**
     * 过期时间
     *
     * @return
     */
    int expire() default 5;

    /**
     * 过期时间单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

    /**
     * 分隔符，最后生成锁的key = prefix() + delimiter() + key()
     *
     * @return
     */
    String delimiter() default ":";
}
