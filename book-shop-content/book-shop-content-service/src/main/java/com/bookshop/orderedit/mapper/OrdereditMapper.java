package com.bookshop.orderedit.mapper;

import java.util.List;

import com.bookshop.common.pojo.OrderPojo;





public interface OrdereditMapper {
	List<OrderPojo> getOrderList();
	List<OrderPojo> getOrderByUserId(long userId);
}
