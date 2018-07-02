package com.bookshop.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.OrderPojo;
import com.bookshop.common.utils.JsonUtils;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.jedis.JedisClient;
import com.bookshop.mapper.TbOrderBookMapper;
import com.bookshop.mapper.TbOrderMapper;
import com.bookshop.mapper.TbOrderShippingMapper;
import com.bookshop.order.pojo.OrderInfo;
import com.bookshop.order.service.OrderService;
import com.bookshop.orderedit.mapper.OrdereditMapper;
import com.bookshop.pojo.TbOrder;
import com.bookshop.pojo.TbOrderBook;
import com.bookshop.pojo.TbOrderBookExample;
import com.bookshop.pojo.TbOrderBookExample.Criteria;
import com.bookshop.pojo.TbOrderShipping;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 订单处理server实现类
 * @author 剑影随风
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private OrdereditMapper ordereditMapper;

	@Autowired
	private TbOrderBookMapper orderBookMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	@Value("${ORDER_ID_BEGIN_VALUE}")
	private String ORDER_ID_BEGIN_VALUE;
	@Value("${ORDER_BOOK_ID_GEN_KEY}")
	private String ORDER_BOOK_ID_GEN_KEY;
	/**
	 * 生成订单
	 */
	@Override
	public BookResult createOrder(OrderInfo orderInfo) {
		if(!jedisClient.exists(ORDER_ID_GEN_KEY)){
			//设置初始值
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGIN_VALUE);
		}
		//使用redis的incr生成订单号
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		//补全订单表
		orderInfo.setOrderId(orderId);
		//免邮费设置邮费为0
		orderInfo.setPostFee("0");
		orderInfo.setShippingName("订单已提交！");
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		//订单创建和更新时间
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		//插入订单表数据
		orderMapper.insert(orderInfo);
		//向订单图书表插入数据。
		List<TbOrderBook> orderBooks = orderInfo.getOrderBooks();
		for (TbOrderBook tbOrderBook : orderBooks) {
			//生成key
			String oid = jedisClient.incr(ORDER_BOOK_ID_GEN_KEY).toString();
			tbOrderBook.setId(oid);
			tbOrderBook.setOrderId(orderId);
			orderBookMapper.insert(tbOrderBook);
		}
		//向订单地址表插入数据
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingMapper.insert(orderShipping);
		//返回订单号
		return BookResult.ok(orderId);
	}

	/**
	 * 设置分页查询
	 */
	@Override
	public EasyUIDataGridResult getOrderList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		//TbOrderExample example = new TbOrderExample();
		List<OrderPojo> list = ordereditMapper.getOrderList();
		
		//System.out.println(JsonUtils.objectToJson(list));
		//取查询结果
		PageInfo<OrderPojo> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		//System.out.println(list.toString());
		//返回结果
		return result;
	}

	/**
	 * 设置状态
	 */
	@Override
	public BookResult updateOrderByStauts(String orderId, String stauts) {
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		TbOrder tbOrder = orderMapper.selectByPrimaryKey(orderId);
		tbOrder.setStatus(Integer.parseInt(stauts));
		tbOrder.setUpdateTime(new Date());
		orderMapper.updateByPrimaryKey(tbOrder);
		return BookResult.ok(tbOrder);
	}
	
	/**
	 * 设置物流
	 */
	@Override
	public BookResult updateOrderByShippingName(String orderId, String shippingName) {
		//System.out.println("+++++++++++++++++"+orderId+shippingName);
		TbOrder tbOrder = orderMapper.selectByPrimaryKey(orderId);
		tbOrder.setShippingName(shippingName);
		tbOrder.setUpdateTime(new Date());
		orderMapper.updateByPrimaryKey(tbOrder);
		return BookResult.ok(tbOrder);
	}

	/**
	 * 删除
	 */
	@Override
	public BookResult deleteOrderById(String Ids) {
		String[] orderIds = null;   
		orderIds = Ids.split(","); 
		for (String orderId : orderIds) {
			try {
				orderMapper.deleteByPrimaryKey(orderId);
				TbOrderBookExample example = new TbOrderBookExample();
				Criteria criteria = example.createCriteria();
				criteria.andOrderIdEqualTo(orderId);
				List<TbOrderBook> list = orderBookMapper.selectByExample(example);
				for (TbOrderBook tbOrderBook : list) {		
					orderBookMapper.deleteByPrimaryKey(tbOrderBook.getId());
				}
				orderShippingMapper.deleteByPrimaryKey(orderId);
			} catch (Exception e) {
				return BookResult.build(400, "删除"+orderId+"失败！");
			}
		}
		return BookResult.ok();
	}

	/**
	 * 按userID取订单
	 */
	@Override
	public EasyUIDataGridResult getOrderByUserId(String userId) {
		PageHelper.startPage(1, 100);
		List<OrderPojo> list = ordereditMapper.getOrderByUserId(Long.parseLong(userId));
		//取查询结果
		PageInfo<OrderPojo> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		//返回结果
		return result;
	}

}
