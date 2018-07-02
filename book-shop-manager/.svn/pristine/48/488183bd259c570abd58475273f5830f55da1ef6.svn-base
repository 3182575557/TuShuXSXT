package com.bookshop.service;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.pojo.TbBook;
import com.bookshop.pojo.TbBookDesc;
public interface BookService {
	//按ID查图书信息
	TbBook getBookById(long bookId);
	//分页显示
	EasyUIDataGridResult getBookList(int page, int rows);
	//添加
	BookResult addBook(TbBook book, String desc);
	//修改
	BookResult updateBook(TbBook book, String desc);
	//按ID删除
	BookResult deleteBookById(String bookId);
	//下架
	BookResult instockBookById(String bookIds);
	//上架
	BookResult reshelfBookById(String bookIds);
	//图书描述内容
	TbBookDesc getBookDescById(long bookId);
	//按类目ID查
	BookResult getBookCatById(long bookCatId);
}
