package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理
 * Created by zh on 2/27/2017.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getCategoryList(long parentId) {
        //根据parentid查询节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            //创建一个节点
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            //添加节点
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult insertContentCategory(long parentId, String name) {
        //创建一个pojo,补全数据库表
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setIsParent(false);//也需要改变它的父节点的isParent的值
        //'状态。可选值:1(正常),2(删除)',
        contentCategory.setStatus(1);
        contentCategory.setParentId(parentId);
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //添加记录
        contentCategoryMapper.insert(contentCategory);//mapper中写了SELECT LAST_INSERT_ID(),插入后得到了主键

        //查看父节点的isParent列是否为false，如果是false改成true
        TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
        //判断是否为false
        if(!parentCat.getIsParent()) {
            parentCat.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentCat);
        }
        //返回结果
        return TaotaoResult.ok(contentCategory);//要返回contentCategory,所以需要得到插入后主键的值
    }

    @Override
    public TaotaoResult deleteContentCategory(long id) {
        //删除前获得parentId
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        long parentId = contentCategory.getParentId();
        //删除id对应的记录
        contentCategoryMapper.deleteByPrimaryKey(id);
        //查看父节点是否还有子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> leaves = contentCategoryMapper.selectByExample(example);//这种时候leaves已经被初始化了,不可能为null,而是为空
        //System.out.println("leaves = "+leaves);
        if(leaves.isEmpty()){
            //如果没有叶子节点 则将isParent设为false (原来一定是true,不用判断)
            TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
            parentCat.setIsParent(false);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentCat);
        }
        //返回结果
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContentCategory(long id, String name) {
        //修改需要更新的数据
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setName(name);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //修改记录
        contentCategoryMapper.updateByPrimaryKey(contentCategory);
        //返回结果
        return TaotaoResult.ok(contentCategory);
    }

}