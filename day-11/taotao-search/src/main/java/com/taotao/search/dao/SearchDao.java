package com.taotao.search.dao;

import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by zh on 3/1/2017.
 */
public interface SearchDao {

    SearchResult search(SolrQuery query) throws Exception;

}
