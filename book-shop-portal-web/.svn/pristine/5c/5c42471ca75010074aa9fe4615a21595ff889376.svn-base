package com.bookshop.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.common.pojo.BookResult;
import com.bookshop.common.utils.CookieUtils;
import com.bookshop.common.utils.JsonUtils;
import com.bookshop.pojo.TbUser;
import com.bookshop.sso.service.UserService;
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private com.bookshop.service.UserService userservice;
	
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	
	
	
	/**
	 * 校验
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping("/user/check")
	@ResponseBody
	public BookResult UserCheckDate(String param,Integer type){
		BookResult result = userService.checkDate(param, type);
		return result;
	}
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public BookResult register(TbUser user){
		//System.out.println(JsonUtils.objectToJson(user));
		BookResult result = userService.register(user);
		return result;
	}
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public BookResult login(String username, String password, 
			HttpServletResponse response, HttpServletRequest request) {
		BookResult result = userService.login(username, password);
		//登录成功后写cookie
		if (result.getStatus() == 200) {
			//System.out.println("登录成功");
			//把token写入cookie
			CookieUtils.setCookie(request, response, TOKEN_KEY, result.getData().toString());
		}
		//System.out.println(result.getData().toString());

		return result;
	}
/*	
	
	@RequestMapping(value="/user/token/{token}", method=RequestMethod.GET,
			//指定返回数据的content-type
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE
			)
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback){
		TaotaoResult result = userService.getUserByToken(token);
		//判断是否为jsonp请求
		//System.out.println(callback);
		if(StringUtils.isNotBlank(callback)){
			//System.out.println("放回jsonp");
			return callback+"("+JsonUtils.objectToJson(result)+");";
		}
		//System.out.println("放回json");
		return JsonUtils.objectToJson(result);
	}*/
	
	//第二种方法，仅适用于Spring4.1以上
	/**
	 * 拿到登录信息
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping(value="/user/token/{token}", method=RequestMethod.GET)
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback){
		BookResult result = userService.getUserByToken(token);
		//判断是否为jsonp请求
		if(StringUtils.isNotBlank(callback)){
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}
	/**
	 * 退出登录
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/user/logout/{token}", method=RequestMethod.GET)
	@ResponseBody
	public Object delteUsrLogin(@PathVariable String token){
		BookResult result = userService.loseUserToken(token);
		return result;
	}
	/**
	 * 根据用户名查找用户ID
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/user/get/{username}", method=RequestMethod.GET)
	@ResponseBody
	public BookResult getuser(@PathVariable String username) {
		BookResult result = userservice.getUserByname(username);
		

		return result;
	}
	
	
	
}
