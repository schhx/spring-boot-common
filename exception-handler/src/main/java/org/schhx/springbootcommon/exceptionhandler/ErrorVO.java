package org.schhx.springbootcommon.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author shanchao
 * @date 2018-04-30
 */
@Data
public class ErrorVO {

    private String error;

    @JsonProperty("original_error")
    private String originalError;

    private ErrorVO(String error, String originalError) {
        this.error = error;
        this.originalError = originalError;
    }

    public static ErrorVO of(String error) {
        return new ErrorVO(error, error);
    }

    public static ErrorVO of(String error, Throwable originalThrowable) {
        return new ErrorVO(error, originalThrowable.getClass().getName() + " : " +  originalThrowable.getMessage());
    }

}
