package com.juke.controller;

import com.juke.pojo.NewUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class UserController {
    @ResponseBody
    @RequestMapping("ruser")
    public NewUser registerUser(){

        return new NewUser("name","112233","19861016",new String[]{"1","2"},0);
    }
}
