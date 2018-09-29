package org.schhx.springbootcommon.exceptionhandler;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author shanchao
 * @date 2018-06-13
 */
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@Import({GlobalExceptionHandler.class, CommonErrorController.class})
public class ExceptionHandlerAutoConfiguration {
}
