package com.hipishare.chat.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hipishare.chat.server.dao.mapper.Uc_userMapper;
import com.hipishare.chat.server.dao.po.Uc_userPO;


@Service("userService")
public class UserService {

	@Autowired
	private Uc_userMapper userMapper;
	
	public void addUser(Uc_userPO user) {
		userMapper.insert(user);
	}
	
	public Uc_userPO getUserByAccount(String account){
		return userMapper.getUserByAccount(account);
	}
}
