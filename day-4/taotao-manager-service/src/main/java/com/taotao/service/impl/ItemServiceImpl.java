package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zh on 2/21/2017.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItem getItemById(Long itemId) {
        //TbItem item = itemMapper.selectByPrimaryKey(itemId);
        TbItemExample example = new TbItemExample();
        //创建查询条件
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list = itemMapper.selectByExample(example);
        //判断list中是否为空
        TbItem item = null;
        if (list != null && list.size() > 0) {
            item = list.get(0);
        }
        return item;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //分页处理
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        //返回处理结果
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);

        return result;
    }

    @Override
    public TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception {
        //补全页面提交来的item信息
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //插入数据库TbItem
        itemMapper.insert(item);
        //添加商品描述(必须在同一个service中,来保证在同一个事物中)
        TaotaoResult result = insertItemDesc(itemId, desc);
        if(result.getStatus()!=200){
            throw new Exception();
        }
        //添加商品规格参数(必须在同一个service中,来保证在同一个事物中)
        result = insertItemParamItem(itemId, itemParam);
        if(result.getStatus()!=200){
            throw new Exception();
        }
        return TaotaoResult.ok();
    }

    /**
     * 插入商品描述信息(在另一个表中)
     * @param itemId
     * @param desc
     * @return
     */
    public TaotaoResult insertItemDesc(Long itemId, String desc){
        //补全传入的itemDesc信息
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //插入数据库表TbItemDesc
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    /**
     * 插入商品规格信息(又在另一个表中)
     * @param itemId
     * @param itemParam
     * @return
     */
    public TaotaoResult insertItemParamItem(Long itemId,String itemParam){
        //创建pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        //插入数据
        itemParamItemMapper.insert(itemParamItem);
        return TaotaoResult.ok();
    }

}