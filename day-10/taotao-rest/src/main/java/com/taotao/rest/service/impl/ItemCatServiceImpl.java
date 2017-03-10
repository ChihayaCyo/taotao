package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用递归来拼装特定json格式数据
 * Created by zh on 2/27/2017.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CATEGORY_REDIS_KEY}")
    private String INDEX_CATEGORY_REDIS_KEY;

    @Override
    public CatResult getItemCatList() {
        //从缓存中取内容
        try {
            String result = jedisClient.hget(INDEX_CATEGORY_REDIS_KEY,"");
            if(!StringUtils.isBlank(result)){
                CatResult resultList = JsonUtils.jsonToPojo(result, CatResult.class);
                return  resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //查询分类列表, 将结果拼装成特定格式json
        CatResult catResult = new CatResult();
        catResult.setData(getCatList(0));

        //查询结果存入缓存
        try {
            //把pojo转换为json字符串
            String cacheString = JsonUtils.objectToJson(catResult);
            jedisClient.hset(INDEX_CATEGORY_REDIS_KEY,"",cacheString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return catResult;
    }

    private List<?> getCatList(long parentId){
        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //返回list
        List resultList = new ArrayList<>();
        //向list中添加节点
        int count=0;//todo
        for (TbItemCat itemCat:list){
            //判断: 若是父节点,创建一个新的节点,添加name,url,递归添加item; 若是子节点,直接添加url+name完事儿.
            if(itemCat.getIsParent()){
                CatNode catNode = new CatNode();
                if(parentId==0){
                    catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
                }else{
                    catNode.setName(itemCat.getName());
                }
                catNode.setUrl("/products/"+itemCat.getId()+".html");
                catNode.setItem(getCatList(itemCat.getId()));//递归
                resultList.add(catNode);
                //todo
                if(parentId==0) count++;
                if(count>=14) break;
            }else{
                resultList.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
            }
        }
        return resultList;
    }

}
