package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zh on 2/24/2017.
 */
public interface PictureService {

    Map uploadPicture(MultipartFile uploadFile);

}
