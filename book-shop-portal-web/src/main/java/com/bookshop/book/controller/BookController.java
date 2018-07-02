package com.bookshop.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.book.pojo.Book;
import com.bookshop.pojo.TbBook;
import com.bookshop.pojo.TbBookDesc;
import com.bookshop.service.BookService;
/**
 * 图书详情界面展示control
 * @author 剑影随风
 *
 */
@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	
	@RequestMapping("/book/{bookId}")
	public String ShowBook(@PathVariable Long bookId,Model model){
		//取图书基本信息
		//System.out.println(bookId);
		TbBook tbBook = bookService.getBookById(bookId);
		//System.out.println(tbBook);
		Book book = new Book(tbBook);
		//取图书详情
		TbBookDesc bookDesc = bookService.getBookDescById(bookId);
		//把数据传递给页面
		model.addAttribute("book", book);
		model.addAttribute("bookDesc", bookDesc);
		//返回逻辑视图
		return "book";
	}
	
}
