package com.hipishare.chat.server.receiver;

import io.netty.channel.Channel;

import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.request.SearchUserReq;

public class SearchUserReceiver extends AbstractReceiver<SearchUserReq> implements HipishareCommand {

	public SearchUserReceiver(String msg, Channel channel) {
		super(msg, channel);
	}

	@Override
	public void execute() throws Exception {

	}

}
