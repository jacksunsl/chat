package com.hipishare.chat.server.receiver;

import com.hipishare.chat.server.command.HipishareCommand;

import io.netty.channel.Channel;

public class HeartBeatReceiver extends AbstractReceiver<String> implements HipishareCommand {

	public HeartBeatReceiver(String msg, Channel channel) {
		super(msg, channel);
	}

	@Override
	public void execute() throws Exception {}

}
