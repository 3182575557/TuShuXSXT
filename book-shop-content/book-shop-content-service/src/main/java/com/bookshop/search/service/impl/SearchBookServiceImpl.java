package com.bookshop.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop.common.pojo.SearchBook;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.search.mapper.SearchBookMapper;
import com.bookshop.search.service.SearchBookService;


/**
 * 导入索引库
 * @author 剑影随风
 *
 */
@Service
public class SearchBookServiceImpl implements SearchBookService {

	@Autowired
	private SearchBookMapper searchBookMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public BookResult importBooksToIndex() {
		try {
			// 1、查询所有图书数据。
			List<SearchBook> list = searchBookMapper.getBookList();	
				// 2、创建一个SolrServer对象。
				// 3、为每个图书创建一个SolrInputDocument对象。
				for (SearchBook searchBook : list) {
					SolrInputDocument document = new SolrInputDocument();
					// 4、为文档添加域
					document.addField("id", searchBook.getId());
					document.addField("book_title", searchBook.getTitle());
					document.addField("book_sell_point", searchBook.getSell_point());
					document.addField("book_price", searchBook.getPrice());
					document.addField("book_image", searchBook.getImage());
					document.addField("book_category_name", searchBook.getCategory_name());
					document.addField("book_desc", searchBook.getBook_desc());
					// 5、向索引库中添加文档。
					solrServer.add(document);
				}
				//提交修改
				solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 6、返回TaotaoResult。
			BookResult.build(500, "导入数据失败！");
		}
		// 6、返回TaotaoResult。
		return BookResult.ok();
	}

}
