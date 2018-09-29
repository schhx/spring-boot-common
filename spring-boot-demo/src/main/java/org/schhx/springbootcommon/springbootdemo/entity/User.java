package org.schhx.springbootcommon.springbootdemo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Accessors(chain = true)
@Entity
public class User {
    @Id
    private String id;

    private String username;

    private Integer age;

}