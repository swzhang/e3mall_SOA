package com.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.common.pojo.SearchResult;
import com.e3mall.search.dao.SearchDao;
import com.e3mall.search.service.SearchService;


/**
 * 商品搜索服务
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String keyWord, int page, int rows) throws Exception {
		// 1、创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		// 2、向SolrQuery对象中设置查询条件
		query.setQuery(keyWord);
		//分页条件
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		//设置默认搜索域
		query.set("df", "item_title");
		//开启高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		// 3、调用dao执行查询
		SearchResult searchResult = searchDao.search(query);
		// 4、需要计算总页数
		long recourdCount = searchResult.getRecourdCount();
		long pageCount = recourdCount / rows;
		if (recourdCount % rows != 0) {
			pageCount++;
		}
		searchResult.setTotalPages(pageCount);
		// 5、返回查询结果
		return searchResult;
	}

}
