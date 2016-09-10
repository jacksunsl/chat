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
import com.hipishare.chat.server.manager.RedisManager;
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
		LOG.info("登陆处理中...");
		User user = getEntityFromMsg(User.class);
		if (null != user) {
			LoginResp loginResp = new LoginResp();
			if (null != user.getAccount()) {
				MsgObject msgObj = new MsgObject();
				msgObj.setC(CmdEnum.LOGIN.getCmd());
				// 通过account获取userId存入ChannelManager
				try {
					userService.login(user);
					// 判断是否已经登陆
					ChannelManager cm = ChannelManager.getInstance();
					if (null == cm.getChannel(user.getAccount())){// 没有登录就
						cm.addChannel(user.getAccount(), channel);
						UserManager um = UserManager.getInstance();
						um.addUser(channel.id(), user);
					} else {// 已经登录，更新channel
						if (!channel.id().equals(cm.getChannel(user.getAccount()).id())) {
							loginResp.setMsg("重新登录");
							cm.removeChannel(user.getAccount());
							cm.addChannel(user.getAccount(), channel);
							UserManager um = UserManager.getInstance();
							um.addUser(channel.id(), user);
						}
					}
					loginResp.setFlag(true);
					loginResp.setMsg("登陆成功");
					msgObj.setM(gson.toJson(loginResp));
					sendMsg(msgObj);
					LOG.info("登陆成功...");
				} catch(HipishareException e) {
					loginResp.setFlag(false);
					loginResp.setCode(e.getCode());
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
//				Object msg_offline = MemcachedManager.get(key);
				String msg_offline = RedisManager.get(key);
				if (null != msg_offline) {
					msgObj.setC(CmdEnum.CHAT.getCmd());
					msgObj.setM(msg_offline.toString());
					long flag = RedisManager.del(key);// 清理离线消息
					LOG.info("离线消息清理完毕..."+flag);
					sendMsg(msgObj);
				}
			} else {
				MsgObject msgObj = new MsgObject();
				msgObj.setC(CmdEnum.LOGIN.getCmd());
				loginResp.setFlag(true);
				loginResp.setMsg("用户名或密码不能为空");
				msgObj.setM(gson.toJson(loginResp));
				sendMsg(msgObj);
			}
		}
	}

}
