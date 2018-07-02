package com.bookshop.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop.common.pojo.SearchResult;
import com.bookshop.search.dao.SearchDao;
import com.bookshop.search.service.SearchService;
/**
 * 搜索服务功能实现
 * @author 剑影随风
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

	/**
	 * 按title搜索
	 */
	@Autowired
	private SearchDao searchDao;
	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		//根据查询条件拼装查询对象，同时实现模糊查询
		//创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(queryString);
		if (page < 1) page =1;
		query.setStart((page - 1) * rows);
		if (rows < 1) rows = 10;
		query.setRows(rows);
		//设置默认搜索域
		query.set("df", "book_title");
		//设置高亮显示
		query.setHighlight(true);
		query.addHighlightField("book_title");
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		//执行查询
		SearchResult searchResult = searchDao.search(query);
		//计算查询结果的总页数
		long recordCount = searchResult.getRecordCount();
		long pages =  recordCount / rows;
		if (recordCount % rows > 0) {
			pages++;
		}
		searchResult.setTotalPages(pages);
		//返回结果
		return searchResult;
	}
}
