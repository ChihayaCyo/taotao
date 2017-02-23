package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zh on 2/21/2017.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")//测试-查看json用
    @ResponseBody
    private TbItem getItemById(@PathVariable Long itemId) {
        TbItem item = itemService.getItemById(itemId);
        return item;
    }

    @RequestMapping("/item/list")//easyui-datagrid会自动给请求加上?page=1&rows=30
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

}
