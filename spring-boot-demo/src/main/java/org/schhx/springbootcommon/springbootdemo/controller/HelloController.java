package org.schhx.springbootcommon.springbootdemo.controller;

import org.schhx.springbootcommon.distributedlock.DistributedLock;
import org.schhx.springbootcommon.exceptionhandler.BaseException;
import org.schhx.springbootcommon.springbootdemo.entity.User;
import org.schhx.springbootcommon.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shanchao
 * @date 2018-06-13
 */
@RestController
public class HelloController {

    @Autowired
    private UserService userService;

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

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userService.getByIdSlave(id);
    }


    @GetMapping("/lock")
    @DistributedLock(prefix = "lock-test", key = "#id")
    public String lock(String id) {
        return id;
    }

}
