package com.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.common.utils.JsonUtils;
import com.bookshop.pojo.TbBook;
import com.bookshop.pojo.TbUser;
import com.bookshop.service.UserService;

/**
 * 用户管理control
 * @author 剑影随风
 *
 */
@Controller
public class UserContrall {

	@Autowired
	private UserService userService;
	/**
	 * 分页
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/user/list")
	@ResponseBody
	public EasyUIDataGridResult getUserList(Integer page, Integer rows) {
		EasyUIDataGridResult result = userService.getUserList(page, rows);
		return result;
	}
	
	/**
	 * ID删除
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/user/delete/{ids}")
	@ResponseBody
	public BookResult deleteBookById(@PathVariable String ids) {
		userService.deleteUserById(ids);
		return BookResult.ok();
	}
	/**
	 * 根据ID查找用户
	 * @param ids
	 * @return
	 */
	@RequestMapping("/user/{ids}")
	@ResponseBody
	public BookResult getUserById(@PathVariable long ids) {
		BookResult result = userService.getUserById(ids);
		return result;
	}
	
	@RequestMapping(value="/rest/user/update",method=RequestMethod.POST)
	@ResponseBody
	public BookResult updateUser(TbUser user){
		//System.out.println(JsonUtils.objectToJson(user));
		BookResult result = userService.Update(user);
		return result;
	}
	
}
