package com.bookshop.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.common.pojo.BookResult;
import com.bookshop.common.utils.CookieUtils;
import com.bookshop.common.utils.JsonUtils;
import com.bookshop.pojo.TbBook;
import com.bookshop.service.BookService;


@Controller
public class CartController {

	@Autowired
	private BookService bookService;
	
	
	@Value("${CART_KEY}")
	private String CART_KEY;
	@Value("${CART_EXPIER}")
	private Integer CART_EXPIER;
	/**
	 * 添加购物车
	 * @param bookId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/add/{bookId}")
	public String addBookCart(@PathVariable Long bookId, 
			@RequestParam(defaultValue="1")Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		//取购物车图书列表
		List<TbBook> cartBookList = getCartBookList(request);
		//判断图书在购物车中是否存在
		boolean flag = false;
		for (TbBook tbBook : cartBookList) {
			if (tbBook.getId() == bookId.longValue()) {
				//如果存在数量相加
				tbBook.setNum(tbBook.getNum() + num);
				flag = true;
				break;
			}
		}
		//如果不存在，添加一个新的图书
		if (!flag) {
			//需要调用服务取图书信息
			TbBook tbBook = bookService.getBookById(bookId);
			//设置购买的图书数量
			tbBook.setNum(num);
			//取一张图片
			String image = tbBook.getImage();
			if (StringUtils.isNotBlank(image)) {
				String[] images = image.split(",");
				tbBook.setImage(images[0]);
			}
			//把图书添加到购物车
			cartBookList.add(tbBook);
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartBookList),
				CART_EXPIER, true);
		//返回添加成功页面
		return "cartSuccess";
	}
	/**
	 * 查询购物车
	 * @param request
	 * @return
	 */
	private List<TbBook> getCartBookList(HttpServletRequest request) {
		//从cookie中取购物车图书列表
		String json = CookieUtils.getCookieValue(request, CART_KEY, true);
		if (StringUtils.isBlank(json)) {
			//如果没有内容，返回一个空的列表
			return new ArrayList<>();
		}
		List<TbBook> list = JsonUtils.jsonToList(json, TbBook.class);
		return list;
	}
	/**
	 * 购物车页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String ShowCartList(HttpServletRequest request){
		//从cookie中获得购物车列表
		List<TbBook> cartBookList = getCartBookList(request);
		//把购物车传递给jsp
		request.setAttribute("cartList", cartBookList);
		return "cart";
	}
	
	
/*	@RequestMapping("/cart/update/num/{bookId}/{num}")
	@ResponseBody
	public TaotaoResult updateBookNum(@PathVariable Long bookId, @PathVariable Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		//从cookie中取购物车列表
		List<TbBook> cartList = getCartBookList(request);
		//查询到对应的图书
		for (TbBook tbBook : cartList) {
			if (tbBook.getId() == bookId.longValue()) {
				//更新图书数量
				tbBook.setNum(num);
				break;
			}
		}
		//把购车列表写入 cookie
		CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartList),
				CART_EXPIER, true);
		//返回成功
		return TaotaoResult.ok();
	}*/
	/**
	 * 修改购物车图书
	 * @param bookid
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/update/num/{bookid}/{num}")
	@ResponseBody
	public BookResult updateBookNum(@PathVariable Long bookid,@PathVariable Integer num,
			HttpServletRequest request,HttpServletResponse response){
		//从cookie中取购物车列表信息
		List<TbBook> cartBookList = getCartBookList(request);
		//查询对应图书
		for (TbBook tbBook : cartBookList) {
			if(tbBook.getId()==bookid.longValue()){
				tbBook.setNum(num);
				break;
			}
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, CART_KEY, 
				JsonUtils.objectToJson(cartBookList),CART_EXPIER, true);
		//返回成功
		return BookResult.ok();
		
	}
	/**
	 * 删除购物车图书
	 * @param bookid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/delete/{bookid}")
	public String deleteCartBook(@PathVariable Long bookid,HttpServletRequest request,
			HttpServletResponse response){
		//从cookie中取购物车列表信息
		List<TbBook> cartBookList = getCartBookList(request);
		for (TbBook tbBook : cartBookList) {
			//删除对应得图书
			if(tbBook.getId()==bookid.longValue()){
				cartBookList.remove(tbBook);
				break;
			}
		}
		//将购物车信息写入cookie
		CookieUtils.setCookie(request, response, CART_KEY, 
				JsonUtils.objectToJson(cartBookList), CART_EXPIER, true);
		//返回购物车列表
		return "redirect:/cart/cart.html";
	}

}
