package com.bookshop.search.mapper;

import java.util.List;

import com.bookshop.common.pojo.SearchBook;

public interface SearchBookMapper {
	List<SearchBook> getBookList();
	SearchBook getBookById(long bookId);
}
