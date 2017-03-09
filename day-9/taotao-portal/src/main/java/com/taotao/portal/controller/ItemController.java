package com.taotao.portal.controller;

import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Item;
import pojo.ItemInfo;

/**
 * 接收页面传递过来的商品id，调用Service查询商品基本信息。传递给jsp页面。返回逻辑视图，展示商品详情页面。
 * Created by zh on 3/9/2017.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model) {
        ItemInfo item = itemService.getItemById(itemId);
        model.addAttribute("item", item);
        return "item";
    }

    /**
     * 接收商品id，调用Service查询商品的描述信息，
     * 返回一个字符串，是商品描述的片段。
     * 这个是因为在后台添加商品desc时,在富文本编辑器里输入的就是html片段, 我们是把它用responsebody返回了!
     */
    @RequestMapping(value="/item/desc/{itemId}", produces= MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemDesc(@PathVariable Long itemId) {
        String string = itemService.getItemDescById(itemId);
        return string;
    }

    /**
     * 页面的ajax请求Controller，请求的url://item/param/{itemId}.html
       响应一个字符串。规格参数的片段。@ResponseBody。
     */
    @RequestMapping(value="/item/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemParam(@PathVariable Long itemId) {
        String string = itemService.getItemParamById(itemId);
        return string;
    }

}
