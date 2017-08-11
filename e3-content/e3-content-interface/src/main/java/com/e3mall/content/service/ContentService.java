package com.e3mall.content.service;

import java.util.List;

import com.e3mall.common.pojo.DataGridResult;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.pojo.TbContent;

public interface ContentService {

	DataGridResult getContentList(Long categoryId, int page, int rows);
	E3Result addContent(TbContent content);
	E3Result editContent(TbContent content);
	E3Result deleteContent(String ids);
	List<TbContent> getContentListByCid(long cid);
}
