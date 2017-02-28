package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zh on 2/27/2017.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    @Override
    public List<TbContent> getContentList(long categoryId) {
        //从缓存中取内容,若缓存中没有,再从mysql中取,顺便存入缓存中
        try {
            String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, categoryId + "");
            if(!StringUtils.isBlank(result)){//???result.isEmpty
                List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
                return  resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据内容分类id查询内容列表
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        //执行查询
        List<TbContent> list = contentMapper.selectByExample(example);

        //查询结果存入缓存
        try {
            //把list转换为json字符串
            String cacheString = JsonUtils.objectToJson(list);
            jedisClient.hset(INDEX_CONTENT_REDIS_KEY,categoryId+"",cacheString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
