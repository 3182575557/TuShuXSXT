package com.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.utils.JsonUtils;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.order.service.OrderService;

@Controller
public class OrdereditController {
	@Autowired
	private OrderService orderService;
	/**
	 * 订单页面展示
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/order/list")
	@ResponseBody
	public EasyUIDataGridResult getBookList(Integer page, Integer rows) {
		EasyUIDataGridResult result = orderService.getOrderList(page, rows);
		return result;
	}
	/**
	 * 删除订单
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/order/delete/{ids}")
	@ResponseBody
	public BookResult deleteBookById(@PathVariable String ids) {
		//System.out.println(ids);
		orderService.deleteOrderById(ids);
		return BookResult.ok();
	}
	/**
	 * 设置状态
	 * @param id
	 * @param stauts
	 * @return
	 */
	@RequestMapping("/order/change/stauts")
	@ResponseBody
	public BookResult ChangeOrderStauts(String id, String stauts) {
		//System.out.println(id+stauts);
		orderService.updateOrderByStauts(id, stauts);
		return BookResult.ok();
	}
	
	@RequestMapping("/order/change/Shipping")
	@ResponseBody
	public BookResult ChangeOrderShippingName(String id, String Shipping) {
		//System.out.println("***********************"+id+Shipping);
		//System.out.println(JsonUtils.objectToJson(orderService.updateOrderByShippingName(id, Shipping)));
		orderService.updateOrderByShippingName(id, Shipping);
		return BookResult.ok();
	}
	
}
