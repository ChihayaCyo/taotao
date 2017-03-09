package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by zh on 3/9/2017.
 */
public interface ItemService {

    TaotaoResult getItemBaseInfo(long itemId);

    TaotaoResult getItemDesc(long itemId);

    TaotaoResult getItemParam(long itemId);

}