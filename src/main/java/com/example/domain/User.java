package com.example.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_user
 * @author 
 */
@Data
public class User implements Serializable {
    private Integer id;

    private String username;

    private String passport;

    private Date createTime;

    private Byte test;

    private byte[] token;
    private int age;

    private static final long serialVersionUID = 1L;
}