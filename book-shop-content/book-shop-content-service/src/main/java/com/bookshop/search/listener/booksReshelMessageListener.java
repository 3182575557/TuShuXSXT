package com.bookshop.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.bookshop.common.pojo.SearchBook;
import com.bookshop.search.mapper.SearchBookMapper;
/**
 * 监听图书上架事件，同步索引库
 * @author 剑影随风
 *
 */
public class booksReshelMessageListener implements MessageListener {

	@Autowired
	private SearchBookMapper searchBookMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public void onMessage(Message message) {
		try {
			//取图书ID
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			String[] bookIds = null;   
	        bookIds = text.split(","); //拆分字符为"," ,然后把结果交给数组strArray
	        //等待事物提交,有几个等几秒
	        int wait = bookIds.length*1000;
	        //System.out.println(wait);
	        Thread.sleep(wait);
	        for (String s : bookIds) {
			
	        	long bookId = Long.parseLong(s);
	        	//根据图书ID查询数据    取图书信息
	        	SearchBook searchBook = searchBookMapper.getBookById(bookId);
	        	//创建文档对象
	        	SolrInputDocument document = new SolrInputDocument();
	        	//向文档对象中添加域
	        	document.addField("id", searchBook.getId());
	        	document.addField("book_title", searchBook.getTitle());
	        	document.addField("book_sell_point", searchBook.getSell_point());
	        	document.addField("book_price", searchBook.getPrice());
	        	document.addField("book_image", searchBook.getImage());
	        	document.addField("book_category_name", searchBook.getCategory_name());
	        	document.addField("book_desc", searchBook.getBook_desc());
	        	//把文件对象写入索引库
	        	solrServer.add(document);
	        	//提交
	        	solrServer.commit();
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
