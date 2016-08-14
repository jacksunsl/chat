package com.hipishare.chat.server.receiver;

import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.User;

import io.netty.channel.Channel;

/**
 * 
 * @description: 注册
 * @author sunlei
 * @date: 2016年8月14日
 *
 */
public class RegisterReceiver extends AbstractReceiver<User> implements HipishareCommand {

	public RegisterReceiver(String msg, Channel channel) {
		super(msg, channel);
	}

	@Override
	public void execute() throws Exception {
		User user = getEntityFromMsg(User.class);
		user.getAccount();
	}

}
