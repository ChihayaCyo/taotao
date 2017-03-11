package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;

/**
 * Created by zh on 3/1/2017.
 */
public interface SearchService {

    SearchResult search(String queryString, int page, int rows) throws Exception;

}
