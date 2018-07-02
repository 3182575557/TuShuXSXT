package com.bookshop.book.listener;
/*package com.bookshop.book.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.bookshop.book.pojo.Book;
import com.bookshop.pojo.TbBook;
import com.bookshop.pojo.TbBookDesc;
import com.bookshop.service.BookService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class BookAddMessageListener implements MessageListener {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;
	@Override
	public void onMessage(Message message) {
		try {
			//从消息中取图书id
			TextMessage textMessage = (TextMessage) message;
			String strId = textMessage.getText();
			Long bookId = Long.parseLong(strId);
			//等待事务提交
			Thread.sleep(1000);
			//根据图书id查询图书信息及图书描述
			TbBook tbBook = bookService.getBookById(bookId);
			Book book = new Book(tbBook);
			TbBookDesc bookDesc = bookService.getBookDescById(bookId);
			//使用freemarker生成静态页面
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			//1.创建模板
			//2.加载模板对象
			Template template = configuration.getTemplate("book.ftl");
			//3.准备模板需要的数据
			Map data = new HashMap<>();
			data.put("book", book);
			data.put("bookDesc", bookDesc);
			//4.指定输出的目录及文件名
			Writer out = new FileWriter(new File(HTML_OUT_PATH + strId + ".html"));
			//5.生成静态页面
			template.process(data, out);
			//关闭流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
*/