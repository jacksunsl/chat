package com.hipishare.chat.server.receiver;

import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.ChatObject;

import io.netty.channel.Channel;

public class GroupChatReceiver extends AbstractReceiver<ChatObject> implements HipishareCommand {

	public GroupChatReceiver(String msg, Channel channel) {
		super(msg, channel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		
	}
	

}
