package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * Created by zh on 2/24/2017.
 */
public interface ItemParamService {

    TaotaoResult getItemParamByCid(long cid);

    TaotaoResult insertItemParam(TbItemParam itemParam);

}
