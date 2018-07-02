package com.bookshop.service;

import java.util.List;

import com.bookshop.common.pojo.BookResult;
import com.bookshop.common.pojo.EasyUITreeNode;
/**
 * 图书类目管理接口
 * @author 剑影随风
 *
 */

public interface BookCatService {
	/**
	 * 查询图书类目
	 * @param parentId
	 * @return
	 */
	List<EasyUITreeNode> getBookCatList(long parentId);
	/**
	 * 添加图书类目
	 * @param parentId
	 * @param name
	 * @return
	 */
	BookResult addBookCat(Long parentId, String name);
	/**
	 * 更新节点
	 * @param id
	 * @param name
	 * @return
	 */
	BookResult updateBookCat(long id, String name);
	/**
	 * 删除节点
	 * @param id
	 * @return
	 */
	BookResult delBookCatList(long id);
}
