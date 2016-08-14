package com.hipishare.chat.server.receiver;

import com.hipishare.chat.server.command.HipishareCommand;

import io.netty.channel.Channel;

/**
 * 
 * @description:单聊 
 * @author sunlei
 * @date: 2016年8月14日
 *
 */
public class SingleChatReceiver implements HipishareCommand {
	
	private Channel channel;
	
	public SingleChatReceiver(Channel channel){
		this.channel = channel;
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub

	}

}
