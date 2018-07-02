package com.bookshop.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.bookshop.common.pojo.EasyUIDataGridResult;
import com.bookshop.common.pojo.BookResult;
import com.bookshop.common.utils.JsonUtils;
import com.bookshop.mapper.TbUserMapper;
import com.bookshop.pojo.TbUser;
import com.bookshop.pojo.TbUserExample;
import com.bookshop.pojo.TbUserExample.Criteria;
import com.bookshop.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 用户管理
 * @author 剑影随风
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public EasyUIDataGridResult getUserList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbUserExample example = new TbUserExample();
		List<TbUser> list = userMapper.selectByExample(example);
		//取查询结果
		PageInfo<TbUser> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		//返回结果
		return result;
	}
	/**
	 * 按ID删除
	 */
	@Override
	public BookResult deleteUserById(String bookIds) {
		String[] bookId = null;   
        bookId = bookIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray
       for (String i : bookId) {
    	   userMapper.deleteByPrimaryKey(Long.parseLong(i));
    	 //  System.out.println(Long.parseLong(i));
   		
	}
    return BookResult.ok();
	}
	
	/**
	 * 根据用户名查找
	 */
	@Override
	public BookResult getUserByname(String username) {
		TbUserExample example = new TbUserExample();
		com.bookshop.pojo.TbUserExample.Criteria criteria = example.createCriteria();
		//System.out.println("设置查询条件");
		//设置查询条件
		criteria.andUsernameEqualTo(username);
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		if(list.size()!=1) {
			return BookResult.build(400, "没有符合要求的用户存在");
		}else {
			//有且只有一条
			for (TbUser tbUser : list) {
				return BookResult.ok(tbUser);
			}
		}
		return null;
	}
	
	/**
	 * 按ID查找用户
	 */
	@Override
	public BookResult getUserById(long id) {
		TbUser user = userMapper.selectByPrimaryKey(id);
		return BookResult.ok(user);
	}
	
	/**
	 * 更新用户
	 */
	@Override
	public BookResult Update(TbUser user) {
		// 1、使用TbUser接收提交的请求。
		if(StringUtils.isBlank(user.getUsername())){
			return BookResult.build(400, "用户名不能为空！");
		}
		if(StringUtils.isBlank(user.getPassword())){
			return BookResult.build(400, "密码不能为空");
		}
		//校验数据是否可用
		BookResult result = checkDate(user.getUsername(), 1, user.getId());
		if(!(boolean)result.getData()){
			return BookResult.build(400, "此用户名已被使用！");
		}
		//校验电话是否可以
		if(StringUtils.isNotBlank(user.getPhone())){
			result = checkDate(user.getPhone(), 2, user.getId());
			if(!(boolean)result.getData()){
				return BookResult.build(400, "此电话已被使用！");
			}
		}
		//校验email是否可用
		if(StringUtils.isNotBlank(user.getEmail())){
			result = checkDate(user.getEmail(), 3, user.getId());
			if(!(boolean)result.getData()){
				return BookResult.build(400, "该邮箱已被使用");
			}
		}
		// 2、补全TbUser其他属性。
		user.setUpdated(new Date());
		// 3、密码要进行MD5加密。
		String md5pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5pass);
		// 4、把用户信息插入到数据库中。
		//System.out.println("service"+JsonUtils.objectToJson(user));
		userMapper.updateByPrimaryKeySelective(user);
		//System.out.println(JsonUtils.objectToJson(user));
		// 5、返回TaotaoResult。
		return BookResult.ok();
	}

	/**
	 * 校验
	 */
	@Override
	public BookResult checkDate(String date, int type, long id) {
		// 1、从tb_user表中查询数据
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 2、查询条件根据参数动态生成。
		//1、2、3分别代表username、phone、email
		if(type==1){
			criteria.andUsernameEqualTo(date);
		}else if(type==2){
			criteria.andPhoneEqualTo(date);
		}else if(type==3){
			criteria.andEmailEqualTo(date);
		}else{
			return BookResult.build(400, "非法参数");
		}
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		// 3、判断查询结果，如果查询到数据返回false。
		if(list==null||list.size()==0){
			// 4、如果没有返回true。
			return BookResult.ok(true);
		}
		if(list==null||list.size()==1){
			// 4、如果同ID返回true。
			for (TbUser tbUser : list) {
				if(tbUser.getId()==id) {
					return BookResult.ok(true);
				}else {
					return BookResult.ok(false);
				}
			}
			
		}
		// 5、使用TaotaoResult包装，并返回。
		return BookResult.ok(false);
	}

}
