package com.taotao.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by zh on 2/24/2017.
 */
@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    @ResponseBody//它的功能不是把java对象转换为json对象,只是表现为这样的结果, java转json实际上是springmvc干的
                 //@ResponseBody的功能是把返回结果写入页面
    public String pictureUpload(MultipartFile uploadFile){
        Map result = pictureService.uploadPicture(uploadFile);
        //因为js的KindEditor插件兼容性不好 为保证兼容性 不能直接@ResponseBody来返回java对象 要人工转json
        String json = JsonUtils.objectToJson(result);
        return json;
    }

}
