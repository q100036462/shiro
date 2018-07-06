package com.boot.shiro.controller;

import com.boot.shiro.entity.User;
import com.boot.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String login(){
        return "login";
    }

    @RequestMapping("/ajax")
    public String toindex(User user){
        boolean login = userService.login(user);
        if (login){
            return "index";
        }
        return "login";
    }

    @RequestMapping("/main")
    public String main(){
        return "page/main";
    }

    @RequestMapping("/totest")
    public String totest(){
        return "test";
    }

}
