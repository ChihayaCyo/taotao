package com.taotao.search.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.ItemImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zh on 3/1/2017.
 */
@Controller
@RequestMapping("/manager")
public class ItemImportController {

    @Autowired
    private ItemImportService itemImportService;

    /**
     * 导入商品数据到索引库
     */
    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult importAllItems() {
        TaotaoResult result = itemImportService.importAllItems();
        return result;//返回是否导入成功 成功200 异常500
    }
}
