package com.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.common.pojo.EasyUITreeNode;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.service.BookCatService;
import com.bookshop.service.BookService;
/**
 * 图书类目
 * @author 剑影随风
 *
 */
@Controller
public class BookCatController {

	@Autowired
	private BookCatService bookcatservice;
	
	@Autowired
	private BookService bookService;
	
	/**
	 * 查询
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/book/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getBookCatList(@RequestParam(name="id",defaultValue="0")long parentId){
		List<EasyUITreeNode> result = bookcatservice.getBookCatList(parentId);
		return result;
	}
	
	
	/**
	 * 新建子类目
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/book/cat/create")
	@ResponseBody
	public BookResult addBookCat(Long parentId, String name) {
		BookResult result = bookcatservice.addBookCat(parentId, name);
		return result;
	}
	
	/**
	 * 修改子类目
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/book/cat/update")
	@ResponseBody
	public BookResult updateBookCat(Long id, String name) {
		BookResult result = bookcatservice.updateBookCat(id, name);
		return result;
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/book/cat/delete")
	@ResponseBody
	public BookResult delBookCatList(Long id) {
		BookResult result = bookcatservice.delBookCatList(id);
		return result;
		
	}
	
}
