package com.taotao.service.impl;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 接收controller传过来的MultipartFile对象, 进行图片上传, 返回上传结果信息,成功时包括图片在图片服务器的url
 * Created by zh on 2/24/2017.
 */
@Service
public class PictureServiceImpl implements PictureService{

    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    @Override
    public Map uploadPicture(MultipartFile uploadFile) {
        Map resultMap = new HashMap();
        try {
            //生成一个新的文件名+原文件后缀
            String oldName = uploadFile.getOriginalFilename();
            String newName = IDUtils.genImageName();//UUID.randomUUID()生成的文件名太乱太长,我们使用工具类IDUtils
            newName = newName + oldName.substring(oldName.lastIndexOf("."));
            //图片上传
            String imagePath = new DateTime().toString("/yyyy/MM/dd");//最前面有"/",最后面没有
            boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
                    imagePath, newName, uploadFile.getInputStream());
            //上传失败
            if(!result){
                resultMap.put("error",1);
                resultMap.put("message","上传失败!");
                return resultMap;
            }
            resultMap.put("error",0);
            resultMap.put("url",IMAGE_BASE_URL+imagePath+"/"+newName);
            return resultMap;
        } catch (IOException e) {
            resultMap.put("error",1);
            resultMap.put("message","上传发生异常!");
            return resultMap;
        }
    }
}
