package com.bookshop.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.OrderPojo;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.common.utils.CookieUtils;
import com.bookshop.common.utils.JsonUtils;
import com.bookshop.order.pojo.OrderInfo;
import com.bookshop.order.service.OrderService;
import com.bookshop.pojo.TbBook;
import com.bookshop.pojo.TbBookDesc;
import com.bookshop.pojo.TbUser;

/**
 * 订单确认页面处理
 * @author 剑影随风
 *
 */
@Controller
public class OrderCartController {
	
	@Value("${CART_KEY}")
	private String CART_KEY;
	
	@Autowired
	private OrderService orderService;
	
	
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request){
		//用户必须是登录状态
		//取用户id,拦截器那已放入
		TbUser user = (TbUser) request.getAttribute("user");
		//System.out.println(user.getUsername()+user.getId());
		//根据用户信息取收货地址列表，使用静态数据。
		//把收货地址列表取出传递给页面
		//从cookie中取购物车图书列表展示到页面
		
		request.setAttribute("orderId", user.getId());
		List<TbBook> cartBookList = getCartBookList(request);
		request.setAttribute("cartList", cartBookList);
		//System.out.println(JsonUtils.objectToJson(cartBookList));
		//返回逻辑视图
		return "order-cart";
	}
	
	/**
	 * 生成订单处理
	 */
	@RequestMapping(value="/order/create",method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo,Model model){
		//System.out.println(JsonUtils.objectToJson(orderInfo));
		//生成订单
		BookResult result = orderService.createOrder(orderInfo);
		//返回逻辑视图
		model.addAttribute("orderId", result.getData().toString());
		//System.out.println("orderId"+result.getData().toString());
		model.addAttribute("payment", orderInfo.getPayment());
		//预计送达时间，预计三天后送达 使用joda-time jar包里的方法处理
		DateTime datetime = new DateTime();
		datetime = datetime.plusDays(3);
		model.addAttribute("date", datetime.toString("yyyy-MM-dd"));
		return "success";
	}
	

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
	 * 按userID查订单
	 * 应用于前台首页订单查看展示
	 * @param userId
	 * @return
	 */
	@RequestMapping("/order/userid")
	@ResponseBody
	public EasyUIDataGridResult getOrderByUserId(HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("user");
		//System.out.println(JsonUtils.objectToJson(user));//检测是否含有ID
		Long userId = user.getId();
		EasyUIDataGridResult result = orderService.getOrderByUserId(userId.toString());
		return result;
	}
	
	
}
