package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转Controller
 * Created by zh on 2/22/2017.
 */
@Controller
public class PageController {

    @RequestMapping("/") //自定义欢迎页; 至于welcome-file-list 它根本没用到
    public String showIndex(){
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }



}
