package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by zh on 2/22/2017.
 */
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(long parentId);

}
