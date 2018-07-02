package com.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.content.service.ContentService;
import com.bookshop.pojo.TbContent;
/**
 * 内容管理
 * @author 剑影随风
 *
 */
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	/**
	 * 添加广告位内容
	 * @param cont
	 * @return
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public BookResult addConten(TbContent cont){
		BookResult result = contentService.addContent(cont);
		return result;
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public BookResult delConten(String ids){
		contentService.deleteContentByid(ids);
		return BookResult.ok();
	}
	
	/**
	 * 分页查询
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getBookList(long categoryId, Integer page, Integer rows) {
		EasyUIDataGridResult result = contentService.getContentList(categoryId, page, rows);
		return result;
	}
	
	/**
	 * 更新内容
	 * @param cont
	 * @return
	 */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public BookResult updateConten(TbContent cont){
		BookResult result = contentService.editContent(cont);
		return result;
	}
	
	
}
