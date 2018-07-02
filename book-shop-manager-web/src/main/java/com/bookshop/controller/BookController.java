package com.bookshop.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.pojo.TbBook;
import com.bookshop.pojo.TbBookDesc;
import com.bookshop.service.BookService;

/**
 * 
 * 图书管理Controller
 * @author 剑影随风
 *
 */
@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	/**
	 * ID查询图书
	 * @param bookId
	 * @return
	 */
	@RequestMapping("/book/{bookId}")
	@ResponseBody
	public TbBook getBookById(@PathVariable Long bookId) {
		TbBook tbBook = bookService.getBookById(bookId);
		return tbBook;
	}
	/**
	 * ID查询图书内容
	 * @param bookId
	 * @return
	 */
	@RequestMapping("/rest/book/desc/{bookId}")
	@ResponseBody
	public BookResult getBookDescById(@PathVariable Long bookId) {
		TbBookDesc tbBook = bookService.getBookDescById(bookId);
		return BookResult.ok(tbBook);
	}
	/**
	 * 分页
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/book/list")
	@ResponseBody
	public EasyUIDataGridResult getBookList(Integer page, Integer rows) {
		EasyUIDataGridResult result = bookService.getBookList(page, rows);
		return result;
	}
	/**
	 * 添加
	 * @param book
	 * @param desc
	 * @return
	 */
	@RequestMapping(value="/book/save",method=RequestMethod.POST)
	@ResponseBody
	public BookResult addBook(TbBook book,String desc){
		BookResult result = bookService.addBook(book, desc);
		return result;
	}
	/**
	 * 更新
	 * @param book
	 * @param desc
	 * @return
	 */
	@RequestMapping(value="/rest/book/update",method=RequestMethod.POST)
	@ResponseBody
	public BookResult updateBook(TbBook book,String desc){
		BookResult result = bookService.updateBook(book, desc);
		return result;
	}
	
	/**
	 * ID删除
	 * @param bookId
	 * @return
	 */
	@RequestMapping(value="/rest/book/delete/{ids}")
	@ResponseBody
	public BookResult deleteBookById(@PathVariable String ids) {
		//System.out.println(ids);
		bookService.deleteBookById(ids);
		return BookResult.ok();
	}
	
	/**
	 * 下架
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/book/instock/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public BookResult instockBook(@PathVariable String ids){
		BookResult result = bookService.instockBookById(ids);
		return result;
	}
	
	/**
	 * 上架
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/book/reshelf/{ids}",method=RequestMethod.POST)
	@ResponseBody
	public BookResult reshelfBook(@PathVariable String ids){
		BookResult result = bookService.reshelfBookById(ids);
		return result;
	}
	
}
