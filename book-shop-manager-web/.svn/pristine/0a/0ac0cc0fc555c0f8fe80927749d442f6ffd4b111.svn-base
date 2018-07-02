package com.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.common.pojo.EasyUITreeNode;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.content.service.ContentCategoryService;

/**
 * 内容分类管理Controller
 * <p>Title: ContentCategoryController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	/**
	 * 遍历子类目
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(
			@RequestParam(value="id", defaultValue="0")Long parentId) {
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
		
	}
	/**
	 * 新建子类目
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public BookResult addContentCategory(Long parentId, String name) {
		BookResult result = contentCategoryService.addContentCategory(parentId, name);
		return result;
	}
	
	/**
	 * 修改子类目
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public BookResult updateContentCategory(Long id, String name) {
		BookResult result = contentCategoryService.updateContentCategory(id, name);
		return result;
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public BookResult delContentCategoryList(Long id) {
		BookResult result = contentCategoryService.delContentCategoryList(id);
		return result;
		
	}
	
}

