package org.schhx.springbootcommon.springbootdemo.service;

import org.schhx.springbootcommon.dynamicdatasource.UseSlave;
import org.schhx.springbootcommon.springbootdemo.dao.UserRepository;
import org.schhx.springbootcommon.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shanchao
 * @date 2018-09-28
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void insert(User user) {
        userRepository.save(user);
    }

    @UseSlave
    public void insertSlave(User user) {
        userRepository.save(user);
    }

    public User getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @UseSlave
    public User getByIdSlave(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @UseSlave
    public void deleteSlave(String id) {
        userRepository.deleteById(id);
    }
}
