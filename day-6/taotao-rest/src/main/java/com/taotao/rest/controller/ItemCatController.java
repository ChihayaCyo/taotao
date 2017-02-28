package com.taotao.rest.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 返回json商品分类列表
 * Created by zh on 2/27/2017.
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

/*
    @RequestMapping(value = "/itemcat/list", produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){
        CatResult catResult = itemCatService.getItemCatList();
        //把pojo转换成json字符串
        String json = JsonUtils.objectToJson(catResult);
        //拼装返回值 跨域jsonp调用
        String result = callback + "("+ json +");";
        return result;
    }
*/

    @RequestMapping("/itemcat/list")
    @ResponseBody
    public Object getItemCatList(String callback){
        CatResult catResult = itemCatService.getItemCatList();
        //spring4.1新特性-jsonp支持
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

}
