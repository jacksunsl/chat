package com.hipishare.chat.server.service;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hipishare.chat.server.dao.mapper.Uc_userMapper;
import com.hipishare.chat.server.dao.po.Uc_userPO;
import com.hipishare.chat.server.domain.RegisterCode;
import com.hipishare.chat.server.domain.User;
import com.hipishare.chat.server.exception.HipishareException;
import com.hipishare.chat.server.utils.RandomCode;


@Service("userService")
public class UserService {

	private static Logger LOG = LogManager.getLogger(UserService.class.getName());
	
	@Autowired
	private Uc_userMapper userMapper;
	
	public void addUser(Uc_userPO user) {
		userMapper.insert(user);
	}
	
	public Uc_userPO getUserByAccount(String account){
		return userMapper.getUserByAccount(account);
	}
	
	@Transactional
	public void register(RegisterCode register) throws HipishareException {
		Uc_userPO exitUser = userMapper.getUserByAccount(register.getMobile());
		if (null != exitUser) {
			HipishareException.raise("2001");
		}
		Uc_userPO user = new Uc_userPO();
		user.setAccount(register.getMobile());
		user.setMobile(register.getMobile());
		user.setIs_valid(1);
		user.setStatus(1);
		user.setUserid(Integer.parseInt(RandomCode.getCharAndNumr(8, true)));
		user.setRegister_time(new Date());
		if (StringUtils.isEmpty(register.getPwd())) {
			user.setPwd("666666");
		} else {
			user.setPwd(register.getPwd());
		}
		userMapper.insert(user);
	}
	
	public void login(User user) throws HipishareException {
		LOG.info("开始读取用户数据...");
		Uc_userPO userPO = userMapper.getUserByAccount(user.getAccount());
		LOG.info("开始读取用户数据...结束");
		if (null == userPO) {
			HipishareException.raise("2002");
		}
		if (!user.getPwd().equals(userPO.getPwd())) {// 密码错误
			HipishareException.raise("2003");
		}
		// 记录登陆日志
	}
}
