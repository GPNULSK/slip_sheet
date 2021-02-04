package com.example.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * t_user
 * @author 
 */
@Data
public class User implements Serializable {
    private Integer uid;

    private String username;

    private String password;

    private String employeeId;

    private static final long serialVersionUID = 1L;
}