package com.bookshop.content.service;

import java.util.List;

import com.bookshop.common.pojo.EasyUITreeNode;
import com.bookshop.common.pojo.BookResult;
/**
 * 内容分类管理
 * @author 剑影随风
 *
 */
public interface ContentCategoryService {

	/**
	 * 查询子节点
	 * @param parentId
	 * @return
	 */
	List<EasyUITreeNode> getContentCategoryList(long parentId);
	/**
	 * 添加子节点
	 * @param parentId
	 * @param name
	 * @return
	 */
	BookResult addContentCategory(Long parentId, String name);
	/**
	 * 更新节点
	 * @param id
	 * @param name
	 * @return
	 */
	BookResult updateContentCategory(long id, String name);
	/**
	 * 删除节点
	 * @param id
	 * @return
	 */
	BookResult delContentCategoryList(long id);
	
}
