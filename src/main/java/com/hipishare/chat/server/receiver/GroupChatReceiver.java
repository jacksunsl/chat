package com.hipishare.chat.server.receiver;

import com.hipishare.chat.server.command.HipishareCommand;

import io.netty.channel.Channel;

public class GroupChatReceiver implements HipishareCommand {

	private Channel channel;
	
	public GroupChatReceiver(Channel channel){
		this.channel = channel;
	}
	
	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub

	}

}
