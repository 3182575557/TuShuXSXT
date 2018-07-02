package com.bookshop.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop.common.pojo.EasyUITreeNode;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.content.service.ContentCategoryService;
import com.bookshop.mapper.TbContentCategoryMapper;
import com.bookshop.pojo.TbContentCategory;
import com.bookshop.pojo.TbContentCategoryExample;
import com.bookshop.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类管理service
 * <p>Title: ContentCategoryServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	/**
	 * 查询子节点
	 */
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		//根据parentId查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			//添加到结果列表
			resultList.add(node);
		}
		return resultList;
	}

	/**
	 * 添加子节点
	 */
	@Override
	public BookResult addContentCategory(Long parentId, String name) {
		//创建一个pojo对象
		TbContentCategory contentCategory = new TbContentCategory();
		//补全对象的属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		//状态。可选值:1(正常),2(删除)
		contentCategory.setStatus(1);
		//排序，默认为1
		contentCategory.setSortOrder(1);
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//插入到数据库
		contentCategoryMapper.insert(contentCategory);
		//判断父节点的状态
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			//如果父节点为叶子节点应该改为父节点
			parent.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
			
		//返回结果
		return BookResult.ok(contentCategory);
	}

	/**
	 * 更新节点
	 */
	@Override
	public BookResult updateContentCategory(long id, String name) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return BookResult.ok();
	}

	/**
	 * 删除节点
	 */
	@Override
	public BookResult delContentCategoryList(long id) {
		//查询当前ID的节点信息
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		long parentid = contentCategory.getParentId();
		//当前是否为父节点
		Boolean isParent = contentCategory.getIsParent();
		if(isParent) {
			//删除父节点所有子孙
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();
			//System.out.println("设置查询条件");
			//设置查询条件
			criteria.andParentIdEqualTo(id);
			//执行查询
			List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
			for (TbContentCategory tbContentCategory : list) {
				delContentCategoryList(tbContentCategory.getId());
			}
		} 

		//根据父节点下的节点个数判断是否要改变父节点状态
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		//System.out.println("设置查询条件");
		//设置查询条件
		criteria.andParentIdEqualTo(contentCategory.getParentId());
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if(list.size()>1) {
			//直接删除
			contentCategoryMapper.deleteByPrimaryKey(id);
		}else {
			//删除并设置父节点为子节点
			contentCategoryMapper.deleteByPrimaryKey(id);
			TbContentCategory pcontentCategory = contentCategoryMapper.selectByPrimaryKey(parentid);
			pcontentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(pcontentCategory);
		}
		
		return BookResult.ok();
	}

}
