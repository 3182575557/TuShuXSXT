package com.bookshop.search.service;

import com.bookshop.common.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryString,int page,int rows) throws Exception;
}
