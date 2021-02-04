package com.example.mappers;

import com.example.domain.User;
import org.apache.ibatis.annotations.Mapper;


public interface UserDao {

    User login(User user);
}