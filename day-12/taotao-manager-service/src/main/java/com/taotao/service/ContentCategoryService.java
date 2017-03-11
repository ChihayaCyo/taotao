package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

/**
 * Created by zh on 2/27/2017.
 */
public interface ContentCategoryService {

    List<EasyUITreeNode> getCategoryList(long partentId);

    TaotaoResult insertContentCategory(long partentId, String name);

    TaotaoResult deleteContentCategory(long id);

    TaotaoResult updateContentCategory(long id, String name);
}
