package com.hipishare.chat.server.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

/**
 * 
 * @description: 通道管理
 * @author sunlei
 * @date: 2016年6月29日
 *
 */
public class ChannelManager {
	
	public static ChannelManager channelManager = new ChannelManager();
	
	private final Map<String, Channel> channelsMap;

	private ChannelManager(){
		channelsMap = new ConcurrentHashMap<String, Channel>();
	}
	
	public static ChannelManager getInstance() {
		return channelManager;
	}

	public Channel getChannel(String userId){
		return channelsMap.get(userId);
	}
	
	public void addChannel(String userId, Channel channel){
		channelsMap.put(userId, channel);
	}
	
	public void removeChannel(String userId){
		channelsMap.remove(userId);
	}

}
