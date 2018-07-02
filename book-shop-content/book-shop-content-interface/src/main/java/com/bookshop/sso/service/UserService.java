package com.bookshop.sso.service;

import com.bookshop.common.pojo.BookResult;
import com.bookshop.pojo.TbUser;
/**
 * 单点登录
 * @author 剑影随风
 *
 */
public interface UserService {
	BookResult checkDate(String date,int type);
	BookResult register(TbUser user);
	BookResult login(String username,String password);
	BookResult getUserByToken(String token);
	BookResult loseUserToken(String token);
}
