package com.bookshop.content.service;

import java.util.List;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.pojo.TbContent;
/**
 * 内容管理
 * @author 剑影随风
 *
 */
public interface ContentService {
	/**
	 * 添加内容
	 * @param content
	 * @return
	 */
	BookResult addContent(TbContent content);
	/**
	 * 按CID查询内容
	 * @param cid
	 * @return
	 */
	List<TbContent> getContentByCid(long cid);
	/**
	 * 按CID分页查询内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGridResult getContentList(long cid, int page, int rows);
	/**
	 * 按ID删除
	 * @param id
	 * @return
	 */
	BookResult deleteContentByid(String id);
	/**
	 * 编辑内容
	 * @param content
	 * @return
	 */
	BookResult editContent(TbContent content);
	
}
