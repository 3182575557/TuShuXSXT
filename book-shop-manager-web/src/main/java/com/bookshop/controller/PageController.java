package com.bookshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.common.pojo.BookResult;

/**
 * 页面展示Controller
 * <p>Title: PageController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class PageController {
	/**
	 * 拦截无匹配访问    进入登录
	 * @return
	 */
	@RequestMapping("/")
	public String showLogin() {
		return "login";
	}
	/**
	 * 管理员登录验证
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/admin/login")
	@ResponseBody
	public String showIndex(String username, String password, HttpServletRequest request) {
		if(username.equals("admin")&&password.equals("admin")) {
			HttpSession session = request.getSession();
			session.setAttribute("adminlogin", "true");
			return "adminok";
		}
		return "fail";
	}
	
	/**
	 * 显示页
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page, HttpServletRequest request) {
		if(page.equals("index")) {
			String s = null;
			try {
				s = request.getSession().getAttribute("adminlogin").toString();
			} catch (Exception e) {
				return "login";
			}
			if(s==null) {
				return "login";
			}
		}
		return page;
	}
	
}
