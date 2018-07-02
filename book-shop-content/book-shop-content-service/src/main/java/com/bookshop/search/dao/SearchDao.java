package com.bookshop.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookshop.common.pojo.SearchBook;
import com.bookshop.common.pojo.SearchResult;

/**
 * 查询索引图书dao
 * @author 剑影随风
 *
 */
@Repository
public class SearchDao {

	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery query) throws Exception{
		//System.out.println(query);
		//根据query对象查询
		QueryResponse response = solrServer.query(query);
		//取查询结果
		SolrDocumentList documentList = response.getResults();
		//取查询结果总记录数
		//System.out.println(documentList);
		long numFound = documentList.getNumFound();
		SearchResult result = new SearchResult();
		result.setRecordCount(numFound);
		List<SearchBook> booklist = new ArrayList<>();
		//把查询结果封装到searchbook对象中
		for (SolrDocument solrDocument : documentList) {
			SearchBook book = new SearchBook();
			book.setCategory_name((String) solrDocument.get("book_category_name"));
			book.setId((String) solrDocument.get("id"));
			String imge = (String) solrDocument.get("book_image");
			if(StringUtils.isNoneBlank(imge)){
				imge = imge.split(",")[0];
			}
			book.setImage(imge);
			book.setPrice((long) solrDocument.get("book_price"));
			book.setSell_point((String) solrDocument.get("book_sell_point"));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get(solrDocument.get("book_title"));
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("book_title");
			}
			book.setTitle(title);
			//添加到图书列表
			booklist.add(book);
		}
		
		//把结果添加到searchresult中
		result.setBookList(booklist);
		//返回
		return result;
	}
	
	
}
