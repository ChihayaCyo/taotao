package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by zh on 2/28/2017.
 */
public interface RedisService {

    TaotaoResult syncContent(long categoryId);

}
