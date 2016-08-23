package com.hipishare.chat.server.receiver;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hipishare.chat.server.Server;
import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.LoginResp;
import com.hipishare.chat.server.domain.MsgObject;
import com.hipishare.chat.server.domain.User;
import com.hipishare.chat.server.enums.CmdEnum;
import com.hipishare.chat.server.exception.HipishareException;
import com.hipishare.chat.server.manager.ChannelManager;
import com.hipishare.chat.server.manager.MemcachedManager;
import com.hipishare.chat.server.manager.UserManager;
import com.hipishare.chat.server.service.UserService;
import com.hipishare.chat.server.utils.Constants;
import com.hipishare.chat.server.utils.SpringContextUtil;

import io.netty.channel.Channel;

public class LoginReceiver extends AbstractReceiver<User> implements HipishareCommand {

	private static Logger LOG = LogManager.getLogger(Server.class.getName());
	
	private UserService userService;
	
	public LoginReceiver(String msg, Channel channel){
		super(msg, channel);
		try {
			userService = (UserService)SpringContextUtil.getBean("userService");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void execute() throws Exception {
		LOG.info("登陆处理...");
		User user = getEntityFromMsg(User.class);
		if (null != user) {
			if (null != user.getAccount()) {
				LoginResp loginResp = new LoginResp();
				MsgObject msgObj = new MsgObject();
				msgObj.setC(CmdEnum.LOGIN.getCmd());
				// 通过account获取userId存入ChannelManager
				try {
					userService.login(user);
					// 判断是否已经登陆
					ChannelManager cm = ChannelManager.getInstance();
					if (null == cm.getChannel(user.getAccount())){
						cm.addChannel(user.getAccount(), channel);
						UserManager um = UserManager.getInstance();
						um.addUser(channel.id(), user);
					}
					loginResp.setFlag(true);
					loginResp.setMsg("登陆成功");
					msgObj.setM(gson.toJson(loginResp));
					sendMsg(msgObj);
				} catch(HipishareException e) {
					loginResp.setFlag(false);
					loginResp.setMsg(e.getMessage());
					msgObj.setM(gson.toJson(loginResp));
					sendMsg(msgObj);
					return;
				} catch(Exception e) {
					e.printStackTrace();
					loginResp.setFlag(false);
					loginResp.setMsg("登陆失败，请稍后再试");
					msgObj.setM(gson.toJson(loginResp));
					sendMsg(msgObj);
					return;
				}
				// 获取离线消息
//				RedisManager redisManager = RedisManager.getRedisClient();
				String key = Constants.MSG_PREFIX + user.getAccount();
				Object msg_offline = MemcachedManager.get(key);
				if (null != msg_offline) {
					msgObj.setC(CmdEnum.CHAT.getCmd());
					msgObj.setM(msg_offline.toString());
					boolean flag = MemcachedManager.del(key);// 清理离线消息
					if (flag) {
						LOG.info("离线消息清理完毕...");
					}
					sendMsg(msgObj);
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
