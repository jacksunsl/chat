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
	
	public static ChannelManager channelUserMap = new ChannelManager();
	
	private final Map<Long, Channel> channelsMap;

	private ChannelManager(){
		channelsMap = new ConcurrentHashMap<Long, Channel>();
	}

	public Channel getChannel(Long userId){
		return channelsMap.get(userId);
	}
	
	public void addChannel(Long userId, Channel channel){
		channelsMap.put(userId, channel);
	}
	
	public void removeChannel(Long userId){
		channelsMap.remove(userId);
	}

}
