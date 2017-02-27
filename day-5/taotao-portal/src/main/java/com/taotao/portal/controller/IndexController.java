package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zh on 2/27/2017.
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }

}
