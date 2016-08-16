package com.hipishare.chat.server.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hipishare.chat.server.domain.User;

import io.netty.channel.ChannelId;

/**
 * 
 * @description: 用户管理类
 * @author sunlei
 * @date: 2016年8月2日
 *
 */
public class UserManager {

	private static UserManager userManager = new UserManager();
	
	private final Map<ChannelId, User> usersMap;

	private UserManager(){
		usersMap = new ConcurrentHashMap<ChannelId,User>();
	}
	
	public static UserManager getInstance() {
		return userManager;
	}
	
	public void addUser(ChannelId channelId,  User user){
		usersMap.put(channelId, user);
	}
	
	public void removeUser(ChannelId channelId){
		usersMap.remove(channelId);
	}
	
	public User getUser(ChannelId channelId){
		return usersMap.get(channelId);
	}
}
