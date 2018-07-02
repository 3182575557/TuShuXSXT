package com.bookshop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop.common.pojo.BookResult;
import com.bookshop.common.pojo.EasyUITreeNode;
import com.bookshop.mapper.TbBookCatMapper;
import com.bookshop.pojo.TbBookCat;
import com.bookshop.pojo.TbBookCatExample;
import com.bookshop.pojo.TbBookCatExample.Criteria;
import com.bookshop.service.BookCatService;
/**
 * 图书分类管理
 * @author 剑影随风
 *
 */
@Service
public class BookCatServiceImpl implements BookCatService {
	/**
	 * 查询
	 */
	@Autowired
	private TbBookCatMapper bookCatMapper;

	@Override
	public List<EasyUITreeNode> getBookCatList(long parentId) {
		//1.根据parentId查询节点列表
		TbBookCatExample example = new TbBookCatExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbBookCat> list = bookCatMapper.selectByExample(example);
		// 2、转换成EasyUITreeNode列表。
		List<EasyUITreeNode> result = new ArrayList<>();
		for (TbBookCat tbBookCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbBookCat.getId());
			node.setText(tbBookCat.getName());
			//节点下有子节点"closed"没有子节点"open"
			node.setState(tbBookCat.getIsParent()?"closed":"open");
			//添加到列表
			result.add(node);
		}
		// 3、返回。
		return result;
	}

	/**
	 * 添加子节点
	 */
	@Override
	public BookResult addBookCat(Long parentId, String name) {
		//创建一个pojo对象
		TbBookCat BookCat = new TbBookCat();
		//补全对象的属性
		BookCat.setParentId(parentId);
		BookCat.setName(name);
		//状态。可选值:1(正常),2(删除)
		BookCat.setStatus(1);
		//排序，默认为1
		BookCat.setSortOrder(1);
		BookCat.setIsParent(false);
		BookCat.setCreated(new Date());
		BookCat.setUpdated(new Date());
		//插入到数据库
		bookCatMapper.insert(BookCat);
		//判断父节点的状态
		TbBookCat parent = bookCatMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			//如果父节点为叶子节点应该改为父节点
			parent.setIsParent(true);
			//更新父节点
			bookCatMapper.updateByPrimaryKey(parent);
		}
			
		//返回结果
		return BookResult.ok(BookCat);
	}

	/**
	 * 更新节点
	 */
	@Override
	public BookResult updateBookCat(long id, String name) {
		TbBookCat BookCat = bookCatMapper.selectByPrimaryKey(id);
		BookCat.setName(name);
		BookCat.setUpdated(new Date());
		bookCatMapper.updateByPrimaryKeySelective(BookCat);
		return BookResult.ok();
	}

	/**
	 * 删除节点
	 */
	@Override
	public BookResult delBookCatList(long id) {
		//查询当前ID的节点信息
		TbBookCat BookCat = bookCatMapper.selectByPrimaryKey(id);
		long parentid = BookCat.getParentId();
		//当前是否为父节点
		Boolean isParent = BookCat.getIsParent();
		if(isParent) {
			//删除父节点所有子孙
			TbBookCatExample example = new TbBookCatExample();
			Criteria criteria = example.createCriteria();
			//System.out.println("设置查询条件");
			//设置查询条件
			criteria.andParentIdEqualTo(id);
			//执行查询
			List<TbBookCat> list = bookCatMapper.selectByExample(example);
			for (TbBookCat tbBookCat : list) {
				delBookCatList(tbBookCat.getId());
			}
		} 

		//根据父节点下的节点个数判断是否要改变父节点状态
		TbBookCatExample example = new TbBookCatExample();
		Criteria criteria = example.createCriteria();
		//System.out.println("设置查询条件");
		//设置查询条件
		criteria.andParentIdEqualTo(BookCat.getParentId());
		//执行查询
		List<TbBookCat> list = bookCatMapper.selectByExample(example);
		if(list.size()>1) {
			//直接删除
			bookCatMapper.deleteByPrimaryKey(id);
		}else {
			//删除并设置父节点为子节点
			bookCatMapper.deleteByPrimaryKey(id);
			TbBookCat pBookCat = bookCatMapper.selectByPrimaryKey(parentid);
			pBookCat.setIsParent(false);
			bookCatMapper.updateByPrimaryKey(pBookCat);
		}
		
		return BookResult.ok();
	}
	
	
}
