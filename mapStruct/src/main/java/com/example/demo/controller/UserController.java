package com.example.demo.controller;

import com.example.demo.beans.CarDTO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@Controller
@RequestMapping("/testBoot")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("getUser/{id}")
    public String GetUser(@PathVariable int id,Model model){
        User user =  userService.Sel(id);
        model.addAttribute("user", user);
        System.out.println("xxxxxxxxxxxxxxxxxx"+user.toString());
        
        return "index";
    }


}
