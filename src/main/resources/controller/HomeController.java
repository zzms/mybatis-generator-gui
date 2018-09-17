package com.css.platform.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by leosoft on 2018/8/1.
 */
@Controller()
@RequestMapping("home")
public class HomeController {


    @GetMapping("/index")
    public String index(@PathVariable String id){
         String str=id;


        return "home/index";
    }


    /**
     * 404页面控制器
     * @return
     */
    @GetMapping("/pageNotFound")
    public String pageNotFound(){
        return "error/404";
    }


    /**
     * 500错误页
     * @return
     */
    @GetMapping("/error")
    public String error(){
        return "error/500";
    }
}
