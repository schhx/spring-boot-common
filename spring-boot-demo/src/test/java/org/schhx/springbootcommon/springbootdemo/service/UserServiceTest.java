package org.schhx.springbootcommon.springbootdemo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schhx.springbootcommon.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void master() throws Exception {
        User user = new User()
                .setId(UUID.randomUUID().toString())
                .setUsername("master")
                .setAge(20);
        userService.insert(user);

        assertNotEquals(null, userService.getById(user.getId()));
        assertEquals(null, userService.getByIdSlave(user.getId()));

        userService.delete(user.getId());
        assertEquals(null, userService.getById(user.getId()));
    }

    @Test
    public void slave() throws Exception {
        User user = new User()
                .setId(UUID.randomUUID().toString())
                .setUsername("slave")
                .setAge(20);
        userService.insertSlave(user);

        assertEquals(null, userService.getById(user.getId()));
        assertNotEquals(null, userService.getByIdSlave(user.getId()));

        userService.deleteSlave(user.getId());
        assertEquals(null, userService.getByIdSlave(user.getId()));
    }

}