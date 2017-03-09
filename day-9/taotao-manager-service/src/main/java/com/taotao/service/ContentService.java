package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * Created by zh on 2/27/2017.
 */
public interface ContentService {

    EasyUIDataGridResult getContentListByCategoryId(long categoryId, int page, int rows);

    TaotaoResult insertContent(TbContent content);

}
