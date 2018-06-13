package org.schhx.springbootcommon.springbootdemo.controller;

import org.schhx.springbootcommon.exceptionhandler.BaseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shanchao
 * @date 2018-06-13
 */
@RestController
public class HelloController {

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        if("exception".equals(name)) {
            throw new BaseException("异常");
        }
        if("error".equals(name)) {
            int a = 1 / 0;
        }
        return "Hello " + name;
    }

}
