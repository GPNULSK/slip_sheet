package com.example.controller;

import com.example.common.CommonResult;
import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public CommonResult login(String employeeId,String password){

        User user = new User();
        user.setEmployeeId(employeeId);
        user.setPassword(password);
        User user1 = userService.login(user);

        CommonResult commonResult = new CommonResult();
        if (user1 == null){
            commonResult.setCode(201);
            commonResult.setMessage("没有找到账号");
            commonResult.setResult(null);
        }else {
            if (!password.equals(user1.getPassword())){
                commonResult.setCode(202);
                commonResult.setMessage("密码错误");
            }
            if (password.equals(user1.getPassword())){
                commonResult.setCode(200);
                commonResult.setMessage("登录成功");
                commonResult.setResult(user1);
            }
        }

        return commonResult;
    }
}
