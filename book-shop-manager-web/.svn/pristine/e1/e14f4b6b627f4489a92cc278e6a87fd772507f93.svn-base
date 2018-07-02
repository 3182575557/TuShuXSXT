package com.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 索引库维护
 * @author 剑影随风
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.common.pojo.BookResult;
import com.bookshop.search.service.SearchBookService;
@Controller
/**
 * 导入图书信息
 * @author 剑影随风
 *
 */
public class IndexManagerContrall {
	@Autowired
	private SearchBookService searchBookService;
	@RequestMapping("/index/import")
	@ResponseBody
	public BookResult importIndex(){
		BookResult result = searchBookService.importBooksToIndex();
		return result;
	}
	
}
