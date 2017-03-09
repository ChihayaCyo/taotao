package com.taotao.rest.service;

import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by zh on 2/27/2017.
 */
public interface ContentService {

    List<TbContent> getContentList(long categoryId);

}
