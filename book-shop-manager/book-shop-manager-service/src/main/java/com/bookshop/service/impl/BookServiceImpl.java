package com.bookshop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.common.utils.IDUtils;
import com.bookshop.common.utils.JsonUtils;
import com.bookshop.jedis.JedisClient;
import com.bookshop.mapper.TbBookCatMapper;
import com.bookshop.mapper.TbBookDescMapper;
import com.bookshop.mapper.TbBookMapper;
import com.bookshop.pojo.TbBook;
import com.bookshop.pojo.TbBookCat;
import com.bookshop.pojo.TbBookDesc;
import com.bookshop.pojo.TbBookExample;
import com.bookshop.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.fabric.xmlrpc.base.Data;

/**
 * 图书管理Service
 * <p>Title: BookServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private TbBookMapper bookMapper;
	
	@Autowired
	private TbBookCatMapper bookCatMapper;

	@Autowired
	private TbBookDescMapper bookdescmapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;	
	
	@Autowired
	private JedisClient jedisClient;
	//添加队列key
	@Resource(name="bookAddtopic")
	private Destination destination;
	//上架队列key
	@Resource(name="booksreshelftopic")
	private Destination reshelftination;
	//删除与下架队列key
	@Resource(name="bookDeltopic")
	private Destination deltination;

	@Value("${BOOK_INFO}")
	private String BOOK_INFO;
	
	@Value("${BOOK_EXPIRE}")
	private Integer BOOK_EXPIRE;
	
	/**
	 * 按ID查询
	 */
	@Override
	public TbBook getBookById(long bookId) {
		//先查询缓存，缓存没有则查数据库并将结果添加到缓存
		try {
			String json = jedisClient.get(BOOK_INFO+":"+bookId+":BASE");
			if(StringUtils.isNotBlank(json)){
				TbBook tbBook = JsonUtils.jsonToPojo(json, TbBook.class);
				return tbBook;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbBook book = bookMapper.selectByPrimaryKey(bookId);
		try {
			//添加缓存
			jedisClient.set(BOOK_INFO+":"+bookId+":BASE", JsonUtils.objectToJson(book));
			//设置过期时间，提高缓存利用率
			jedisClient.expire(BOOK_INFO+":"+bookId+":BASE", BOOK_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}

	/**
	 * 分页显示
	 */
	@Override
	public EasyUIDataGridResult getBookList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbBookExample example = new TbBookExample();
		List<TbBook> list = bookMapper.selectByExample(example);
		//取查询结果
		PageInfo<TbBook> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		//返回结果
		return result;
	}

	/**
	 * 图书添加
	 */
	@Override
	public BookResult addBook(TbBook book, String desc) {
		//生成图书ID
		long BookID = IDUtils.genBookId();
		//补全图书属性
		book.setId(BookID);
		//图书状态，1-正常，2-下架，3-删除
		book.setStatus((byte) 1);
		book.setCreated(new Date());
		book.setUpdated(new Date());
		//执行插入
		bookMapper.insert(book);
		//创建一个图书描述表对应pojo
		TbBookDesc bookdesc = new TbBookDesc();
		//补全图书描述表
		bookdesc.setBookId(BookID);
		bookdesc.setBookDesc(desc);
		bookdesc.setCreated(new Date());
		bookdesc.setUpdated(new Date());
		//图书描述表提交
		bookdescmapper.insert(bookdesc);
		//向activemq发送图书添加消息
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送图书ID
				TextMessage textMessage = session.createTextMessage(BookID+"");
				return textMessage;
			}
		});
		
		try {
			//删除缓存
			jedisClient.set(BOOK_INFO+":"+BookID+":DESC", JsonUtils.objectToJson(bookdesc));
			//设置过期
			jedisClient.expire(BOOK_INFO+":"+BookID+":DESC", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BookResult.ok();
	}

	/**
	 * 图书描述内容
	 * @param bookId
	 * @return
	 */
	@Override
	public TbBookDesc getBookDescById(long bookId) {
		//查询数据库之前先查询缓存，缓存没有再查数据库并添加到缓存
		//查询缓存
		try {
			String json = jedisClient.get(BOOK_INFO+":"+bookId+":DESC");
			if(StringUtils.isNotBlank(json)){
				//把json转成pojo
				TbBookDesc bookDesc = JsonUtils.jsonToPojo(json, TbBookDesc.class);
				return bookDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbBookDesc bookDesc = bookdescmapper.selectByPrimaryKey(bookId);
		try {
			//添加缓存
			jedisClient.set(BOOK_INFO+":"+bookId+":DESC", JsonUtils.objectToJson(bookDesc));
			//设置过期时间提高缓存利用率
			jedisClient.expire(BOOK_INFO+":"+bookId+":DESC", BOOK_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookDesc;
	}
	/**
	 * 更新图书信息
	 */
	@Override
	public BookResult updateBook(TbBook book, String desc) {
		book.setUpdated(new Date());
		//执行更新
		bookMapper.updateByPrimaryKeySelective(book);
		//创建一个图书描述表对应pojo
		TbBookDesc bookdesc = new TbBookDesc();
		bookdesc = bookdescmapper.selectByPrimaryKey(book.getId());
		//补全图书描述表
		bookdesc.setUpdated(new Date());
		bookdesc.setBookDesc(desc);
		//System.out.println(desc);
		//图书描述表提交
		bookdescmapper.updateByPrimaryKeySelective(bookdesc);
		//向activemq发送图书添加消息
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送图书ID
				TextMessage textMessage = session.createTextMessage(book.getId()+"");
				return textMessage;
			}
		});
		
		try {
			//删除缓存
			jedisClient.set(BOOK_INFO+":"+bookdesc.getBookId()+":DESC", JsonUtils.objectToJson(bookdesc));
			//设置过期
			jedisClient.expire(BOOK_INFO+":"+bookdesc.getBookId()+":DESC", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BookResult.ok();
	}

	/**
	 * 删除图书
	 */
	@Override
	public BookResult deleteBookById(String bookIds) {
		String[] bookId = null;   
        bookId = bookIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray
       for (String i : bookId) {
    	   bookMapper.deleteByPrimaryKey(Long.parseLong(i));
    	   bookdescmapper.deleteByPrimaryKey(Long.parseLong(i));
    	   
    		try {
    			//添加缓存
    			jedisClient.set(BOOK_INFO+":"+i+":DESC", JsonUtils.objectToJson(bookdescmapper.selectByPrimaryKey(Long.parseLong(i))));
    			//设置过期
    			jedisClient.expire(BOOK_INFO+":"+i+":DESC", 0);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	 //  System.out.println(Long.parseLong(i));
   		
	}
     //向activemq发送图书删除消息
     		jmsTemplate.send(deltination, new MessageCreator() {
     			
     			@Override
     			public Message createMessage(Session session) throws JMSException {
     				//发送图书ID
     				TextMessage textMessage = session.createTextMessage(bookIds+"");
     				return textMessage;
     			}
     		});
		return BookResult.ok();
	}

	/**
	 * 下架
	 */
	@Override
	public BookResult instockBookById(String bookIds) {
		String[] bookId = null;   
        bookId = bookIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray
       for (String i : bookId) {
    	 //获得图书ID
   		long BookID = Long.parseLong(i);
   		//拿到图书信息
   		TbBook book = bookMapper.selectByPrimaryKey(BookID);
   		
   		//图书状态，1-正常，2-下架，3-删除
   		book.setStatus((byte) 2);
   		book.setUpdated(new Date());
   		//执行插入
   		bookMapper.updateByPrimaryKey(book);
   		//创建一个图书描述表对应pojo
   		
	}
       //向activemq发送图书下架消息
       jmsTemplate.send(deltination, new MessageCreator() {
    	   
    	   @Override
    	   public Message createMessage(Session session) throws JMSException {
    		   //发送图书ID
    		   TextMessage textMessage = session.createTextMessage(bookIds+"");
    		   return textMessage;
    	   }
       });
		return BookResult.ok();
	}

	/**
	 * 上架
	 */
	@Override
	public BookResult reshelfBookById(String bookIds) {
		String[] bookId = null;   
        bookId = bookIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray
       for (String i : bookId) {
    	 //获得图书ID
   		long BookID = Long.parseLong(i);
   		//拿到图书信息
   		TbBook book = bookMapper.selectByPrimaryKey(BookID);
   		
   		//图书状态，1-正常，2-下架，3-删除
   		book.setStatus((byte) 1);
   		book.setUpdated(new Date());
   		//执行插入
   		bookMapper.updateByPrimaryKey(book);
   		//创建一个图书描述表对应pojo
   		
	}
       //向activemq发送图书上架消息
       jmsTemplate.send(reshelftination, new MessageCreator() {
    	   
    	   @Override
    	   public Message createMessage(Session session) throws JMSException {
    		   //发送图书ID
    		   TextMessage textMessage = session.createTextMessage(bookIds+"");
    		   return textMessage;
    	   }
       });
		return BookResult.ok();
	}
	/**
	 * 查询类目信息
	 */
	@Override
	public BookResult getBookCatById(long bookCatId) {
		TbBookCat result = bookCatMapper.selectByPrimaryKey(bookCatId);
		return BookResult.ok(result);
	}



}
