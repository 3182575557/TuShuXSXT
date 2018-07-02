package com.bookshop.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
	private long totalPages;
	private long recordCount;
	private List<SearchBook> bookList;
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public List<SearchBook> getBookList() {
		return bookList;
	}
	public void setBookList(List<SearchBook> bookList) {
		this.bookList = bookList;
	}
	
}
