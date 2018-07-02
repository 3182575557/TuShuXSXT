package com.bookshop.order.service;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.order.pojo.OrderInfo;
/**
 * 订单处理接口
 * @author 剑影随风
 *
 */
public interface OrderService {
	//创建订单
	BookResult createOrder(OrderInfo orderInfo);
	
	//分页显示
	EasyUIDataGridResult getOrderList(int page, int rows);
	
	//修改
	BookResult updateOrderByStauts(String id, String stauts);
	//按ID删除
	BookResult deleteOrderById(String bookId);
	//按userID查询
	EasyUIDataGridResult getOrderByUserId(String userId);

	BookResult updateOrderByShippingName(String orderId, String shippingName);
}
