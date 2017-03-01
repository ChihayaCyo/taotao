package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zh on 2/27/2017.
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
        List<EasyUITreeNode> list = contentCategoryService.getCategoryList(parentId);
        return list;
    }

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createContentCategory(Long parentId, String name) {
        TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult createContentCategory(Long id) {//其实parentId可以通过id查询得到,不过既然能返回parentId那就不必多余操作数据库了
        //System.out.println(id); //为什么parentId传不过来呢???
        TaotaoResult result = contentCategoryService.deleteContentCategory(id);
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id, String name) {
        TaotaoResult result = contentCategoryService.updateContentCategory(id, name);
        return result;
    }
}

