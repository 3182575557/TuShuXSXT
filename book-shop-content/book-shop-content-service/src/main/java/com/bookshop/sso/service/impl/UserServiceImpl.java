package com.bookshop.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.bookshop.common.pojo.BookResult;
import com.bookshop.common.utils.JsonUtils;
import com.bookshop.jedis.JedisClient;
import com.bookshop.mapper.TbUserMapper;
import com.bookshop.pojo.TbUser;
import com.bookshop.pojo.TbUserExample;
import com.bookshop.pojo.TbUserExample.Criteria;
import com.bookshop.sso.service.UserService;
/**
 * 用户处理service
 * @author 剑影随风
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${USER_SESSION}")
	private String USER_SESSION;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	
	/**
	 * 校验
	 */
	@Override
	public BookResult checkDate(String date, int type) {
		//从用户表中查询数据
		System.out.println("*************"+date);
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//1代表用户名、2代表电话、3代表邮箱
		if(type==1){
			criteria.andUsernameEqualTo(date);
		}else if(type==2){
			criteria.andPhoneEqualTo(date);
		}else if(type==3){
			criteria.andEmailEqualTo(date);
		}else{
			return BookResult.build(400, "非法参数");
		}
		List<TbUser> list = userMapper.selectByExample(example);
		//判断查询结果
		//如果查询到数据返回false。
		if(list==null||list.size()==0){
			//如果没有返回true。
			return BookResult.ok(true);
		}
		return BookResult.ok(false);
	}
	
	/**
	 * 用户注册功能实现
	 */
	@Override
	public BookResult register(TbUser user) {
		if(StringUtils.isBlank(user.getUsername())){
			return BookResult.build(400, "用户名为空！请输入用户名！");
		}
		if(StringUtils.isBlank(user.getPassword())){
			return BookResult.build(400, "密码为空！请输入用户名");
		}
		//检查用户名是否已经被用
		BookResult result = checkDate(user.getUsername(), 1);
		if(!(boolean)result.getData()){
			return BookResult.build(400, "用户名已被使用！换一个吧！");
		}
		//检查电话是否已经被用
		if(StringUtils.isNotBlank(user.getPhone())){
			result = checkDate(user.getPhone(), 2);
			if(!(boolean)result.getData()){
				return BookResult.build(400, "电话已被使用！换一个吧！");
			}
		}
		//检查邮箱是否已经被用
		if(StringUtils.isNotBlank(user.getEmail())){
			result = checkDate(user.getEmail(), 3);
			if(!(boolean)result.getData()){
				return BookResult.build(400, "邮箱已被使用！换一个吧！");
			}
		}
		//补全用户表的创建时间和更新时间。
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//密码采用MD5加密，提高安全性。
		String md5pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5pass);
		userMapper.insert(user);
		return BookResult.ok();
	}
	
	/**
	 * 用户登录功能实现
	 */
	@Override
	public BookResult login(String username, String password) {
	//判断用户名和密码是否正确
	TbUserExample example = new TbUserExample();
	Criteria criteria = example.createCriteria();
	criteria.andUsernameEqualTo(username);
	List<TbUser> list = userMapper.selectByExample(example);
	if(list==null||list.size()==0){
		//没有返回结果既登录失败
		return BookResult.build(400, "用户名或密码有误！");
	}
	TbUser user = list.get(0);
	//密码进行md5加密后才能校验是否正确
	if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
		//返回登录失败
		return BookResult.build(400, "用户名或密码有误！");
	}
	//使用uuid工具类生成token
	String token = UUID.randomUUID().toString();
	//清空密码
	user.setPassword(null);
	//把用户信息添加到redis
	jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
	//设置key的过期时间，保证安全性
	jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
	//返回登录成功，并把token返回。
	return BookResult.ok(token);
	}

	/**
	 * 根据token查询用户信息
	 */
	@Override
	public BookResult getUserByToken(String token) {
		String json = jedisClient.get(USER_SESSION + ":" + token);
		if(StringUtils.isBlank(json)){
			return BookResult.build(400, "登录信息已过期！");
		}
		//用户在操作，重置session的过期时间，避免用户频繁登录
		jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
		//把json转换为tbuser对象
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		return BookResult.ok(user);
	}
/**
 * 退出登录
 */
	@Override
	public BookResult loseUserToken(String token) {
		String json = jedisClient.get(USER_SESSION + ":" + token);
		
		//设置session过期时间为0即可
		jedisClient.expire(USER_SESSION + ":" + token, 0);
		//把json装换为tbuser对象并返回
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		return BookResult.ok(user);
	}
	
}
