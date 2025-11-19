package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/")
    @ResponseBody
    public String index(){
        System.out.println("index");
        return "메롱";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        System.out.println("hello");
        return "hi";
    }

}
