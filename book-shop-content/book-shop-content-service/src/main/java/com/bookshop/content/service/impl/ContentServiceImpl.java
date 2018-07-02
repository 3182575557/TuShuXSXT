package com.bookshop.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.common.utils.JsonUtils;
import com.bookshop.content.service.ContentService;
import com.bookshop.jedis.JedisClient;
import com.bookshop.mapper.TbContentMapper;
import com.bookshop.pojo.TbContent;
import com.bookshop.pojo.TbContentExample;
import com.bookshop.pojo.TbBook;
import com.bookshop.pojo.TbContentExample.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 内容管理
 * @author 剑影随风
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;
	/**
	 * 添加内容
	 */
	@Override
	public BookResult addContent(TbContent content) {
		//补全pojo的属性
		content.setCreated( new Date());
		content.setUpdated(new Date());
		//插入到内容表
		contentMapper.insert(content);
		//同步缓存
		//删除对应的缓存信息
		jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		return BookResult.ok();
	}
	/**
	 * 查询内容
	 */
	@Override
	public List<TbContent> getContentByCid(long cid) {
		//先查询缓存
		//为了缓存不对正常业务逻辑产生影响，需要捕获可能出现的异常
		try {
			//查询缓存
			String json = jedisClient.hget(INDEX_CONTENT, cid + "");
			//查询到结果，直接把json转换成List返回，结束操作
			if (StringUtils.isNotBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//缓存查询不存在，查询数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		//把结果添加到缓存
		try {
			jedisClient.hset(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回结果
		return list;
	}
	/**
	 * 分页查询
	 */
	@Override
	public EasyUIDataGridResult getContentList(long cid, int page, int rows) {

		//设置分页信息
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		if(cid!=0) {	
			Criteria criteria = example.createCriteria();
			//System.out.println("设置查询条件");
			//设置查询条件
			criteria.andCategoryIdEqualTo(cid);
		}
		//执行查询
		//System.out.println("执行查询");
		List<TbContent> list = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	/**
	 * 删除
	 */
	@Override
	public BookResult deleteContentByid(String ids) {
	
			String[] Id = null; 
			System.out.println(ids);
	        Id = ids.split(",");
	        System.out.println(Id.length);
			for (String id : Id) {
				//先获得CategoryId
				String CategoryId = contentMapper.selectByPrimaryKey(Long.parseLong(id)).getCategoryId().toString();
				//删除数据库信息
				contentMapper.deleteByPrimaryKey(Long.parseLong(id));
				//删除对应的缓存信息
				jedisClient.hdel(INDEX_CONTENT, CategoryId);
			}
		
			return BookResult.ok();
		
	}
	
	/**
	 * 更新内容
	 */
	@Override
	public BookResult editContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKeySelective(content);
		//同步缓存
		//删除对应的缓存信息
		jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		return BookResult.ok();
	}

}
