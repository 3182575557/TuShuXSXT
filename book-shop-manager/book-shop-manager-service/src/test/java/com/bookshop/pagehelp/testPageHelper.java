package com.bookshop.pagehelp;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bookshop.mapper.TbBookMapper;
import com.bookshop.pojo.TbBook;
import com.bookshop.pojo.TbBookExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class testPageHelper {
	@Test
	public void testPageHelper() throws Exception {
		//初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//获得Mapper的代理对象
		TbBookMapper bookMapper = applicationContext.getBean(TbBookMapper.class);
		//设置分页信息
		PageHelper.startPage(1, 30);
		//执行查询
		TbBookExample example = new TbBookExample();
		List<TbBook> list = bookMapper.selectByExample(example);
		//取分页信息
		PageInfo<TbBook> pageInfo = new PageInfo<>(list);
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.getPages());
		System.out.println(pageInfo.getPageNum());
		System.out.println(pageInfo.getPageSize());
	}

}
