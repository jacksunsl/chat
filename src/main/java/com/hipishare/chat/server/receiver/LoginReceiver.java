package com.hipishare.chat.server.receiver;

import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.MsgObject;
import com.hipishare.chat.server.domain.User;
import com.hipishare.chat.server.enums.CmdEnum;
import com.hipishare.chat.server.manager.ChannelManager;
import com.hipishare.chat.server.manager.RedisManager;
import com.hipishare.chat.server.manager.UserManager;
import com.hipishare.chat.server.utils.Constants;

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
			if (null != user.getAccount()) {
				MsgObject msgObj = new MsgObject();
				msgObj.setC(CmdEnum.LOGIN.getCmd());
				// 通过account获取userId存入ChannelManager
				// 判断是否已经登陆
				ChannelManager cm = ChannelManager.getInstance();
				if (null != cm.getChannel(user.getAccount())){
					msgObj.setM(user.getAccount()+"已经登陆");
				} else {
					cm.addChannel(user.getAccount(), channel);
					UserManager um = UserManager.getInstance();
					um.addUser(channel.id(), user);
					msgObj.setM(user.getAccount()+"登录成功");
				}
				sendMsg(msgObj);
				// 获取离线消息
				RedisManager redisManager = RedisManager.getRedisClient();
				String key = Constants.MSG_PREFIX + user.getAccount();
				String msg_offline = redisManager.get(key);
				if (null != msg_offline) {
					msgObj.setC(CmdEnum.CHAT.getCmd());
					msgObj.setM(msg_offline);
					sendMsg(msgObj);
					System.out.println("read off line msg:"+redisManager.del(key));
				}
			} else {
				MsgObject msgObj = new MsgObject();
				msgObj.setC(CmdEnum.LOGIN.getCmd());
				msgObj.setM("登录失败，用户名或密码错误");
				sendMsg(msgObj);
			}
		}
	}

}
