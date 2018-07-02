package com.bookshop.service;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.pojo.TbBook;
import com.bookshop.pojo.TbBookDesc;
import com.bookshop.pojo.TbUser;
public interface UserService {
	/**
	 * 校验
	 * @param date
	 * @param type
	 * @return
	 */

	
	//分页显示
	EasyUIDataGridResult getUserList(int page, int rows);

	//按ID删除
	BookResult deleteUserById(String bookId);
	
	//按name查询
	BookResult getUserByname(String username);
	
	//按id查询
	BookResult getUserById(long Id);

	//修改
	BookResult Update(TbUser user);

	BookResult checkDate(String date, int type, long id);

}
