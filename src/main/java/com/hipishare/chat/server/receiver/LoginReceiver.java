package com.hipishare.chat.server.receiver;

import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.MsgObject;
import com.hipishare.chat.server.domain.User;
import com.hipishare.chat.server.enums.CmdEnum;

import io.netty.channel.Channel;

public class LoginReceiver extends AbstractReceiver<User> implements HipishareCommand {
	
	public LoginReceiver(String msg, Channel channel){
		super(msg, channel);
	}
	
	@Override
	public void execute() throws Exception {
		System.out.println("处理登录...");
		User user = getEntityFromMsg(User.class);
		if (null != user) {
			if ("admin".equals(user.getAccount()) && "666666".equals(user.getPwd())) {
				MsgObject msgObj = new MsgObject();
				msgObj.setC(CmdEnum.LOGIN.getCmd());
				msgObj.setM("登录成功");
				sendMsg(msgObj);
			} else {
				MsgObject msgObj = new MsgObject();
				msgObj.setC(CmdEnum.LOGIN.getCmd());
				msgObj.setM("登录失败，用户名或密码错误");
				sendMsg(msgObj);
			}
		}
	}

}
