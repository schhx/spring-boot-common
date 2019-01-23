package org.schhx.springbootcommon.dynamicdatasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

/**
 * @author shanchao
 * @date 2018-08-30
 */
@Aspect
@Slf4j
@Order(1)
public class DataSourceAspect {

    @Pointcut("@annotation(UseSlave)")
    public void aspect() {
    }

    @Before("aspect()")
    public void before(JoinPoint point) {
        try {
            DBContextHolder.setDbType(DataSourceConstant.SLAVE);
        } catch (Exception e) {
            log.error("数据源切换切面异常", e);
        }
    }

    @After("aspect()")
    public void after() {
        DBContextHolder.clearDbType();
    }

    @AfterThrowing("aspect()")
    public void afterThrow() {
        DBContextHolder.clearDbType();
    }

}
