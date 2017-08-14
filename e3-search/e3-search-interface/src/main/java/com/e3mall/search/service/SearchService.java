package com.e3mall.search.service;

import com.e3mall.common.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String keyWord, int page, int rows) throws Exception;
}
