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
 * 监听图书删除事件，同步索引库
 * @author 剑影随风
 *
 */
public class booksDelMessageListener implements MessageListener {

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
	        for (String s : bookIds) {
	        	//根据图书ID删除
	        	solrServer.deleteById(s);
	        	//提交
	        	solrServer.commit();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
