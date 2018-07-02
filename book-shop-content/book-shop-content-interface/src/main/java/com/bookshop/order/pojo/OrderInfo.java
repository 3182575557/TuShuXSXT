package com.bookshop.order.pojo;

import java.io.Serializable;
import java.util.List;

import com.bookshop.pojo.TbOrder;
import com.bookshop.pojo.TbOrderBook;
import com.bookshop.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder implements Serializable {
	private List<TbOrderBook> orderBooks;
	private TbOrderShipping orderShipping;
	public List<TbOrderBook> getOrderBooks() {
		return orderBooks;
	}
	public void setOrderBooks(List<TbOrderBook> orderBooks) {
		this.orderBooks = orderBooks;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
}
