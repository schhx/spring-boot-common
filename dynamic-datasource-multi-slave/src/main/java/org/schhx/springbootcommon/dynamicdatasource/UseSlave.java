package org.schhx.springbootcommon.dynamicdatasource;

import java.lang.annotation.*;

/**
 * @author shanchao
 * @date 2018-08-30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UseSlave {

}
