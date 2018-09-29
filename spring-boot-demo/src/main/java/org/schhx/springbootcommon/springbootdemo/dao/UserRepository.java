package org.schhx.springbootcommon.springbootdemo.dao;

import org.schhx.springbootcommon.springbootdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {

}
