package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

/**
 * Created by zh on 3/8/2017.
 */
public interface SearchService {

    SearchResult search(String queryString, int page);

}
