package com.taotao.portal.service;

import com.taotao.pojo.TbItem;
import pojo.Item;
import pojo.ItemInfo;

/**
 * Created by zh on 3/9/2017.
 */
public interface ItemService {

    ItemInfo getItemById(Long itemId);

    String getItemDescById(Long itemId);

    String getItemParamById(Long itemId);

}
