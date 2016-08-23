package com.hipishare.chat.server.receiver;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.MsgObject;
import com.hipishare.chat.server.domain.RegisterCode;
import com.hipishare.chat.server.domain.RegisterResp;
import com.hipishare.chat.server.enums.CmdEnum;
import com.hipishare.chat.server.exception.HipishareException;
import com.hipishare.chat.server.manager.MemcachedManager;
import com.hipishare.chat.server.service.UserService;
import com.hipishare.chat.server.utils.Constants;
import com.hipishare.chat.server.utils.SpringContextUtil;

import io.netty.channel.Channel;

/**
 * 
 * @description: 注册
 * @author sunlei
 * @date: 2016年8月14日
 *
 */
public class RegisterReceiver extends AbstractReceiver<RegisterCode> implements HipishareCommand {

	private static Logger LOG = LogManager.getLogger(RegisterReceiver.class.getName());
	
	private UserService userService;
	
	public RegisterReceiver(String msg, Channel channel) {
		super(msg, channel);
		try {
			userService = (UserService)SpringContextUtil.getBean("userService");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute() throws Exception {
		RegisterCode register = getEntityFromMsg(RegisterCode.class);
		Object code = MemcachedManager.get(Constants.REGISTER_CODE+register.getMobile());
		MsgObject msgObj = new MsgObject();
		msgObj.setC(CmdEnum.REGISTER.getCmd());
		RegisterResp registerResp = new RegisterResp();
		if (null != code) {
			if (!register.getCode().equals(code.toString())) {
				try {
					userService.register(register);
					registerResp.setFlag(true);
					registerResp.setMsg("注册成功");
					msgObj.setM(gson.toJson(registerResp));
					sendMsg(msgObj);
				} catch(HipishareException e) {
					LOG.error("[注册失败]:"+e.getMessage());
					registerResp.setFlag(false);
					registerResp.setMsg(e.getMessage());
					msgObj.setM(gson.toJson(registerResp));
					sendMsg(msgObj);
				} catch(Exception e) {
					e.printStackTrace();
					registerResp.setFlag(false);
					registerResp.setMsg("注册异常，请稍后再试");
					msgObj.setM(gson.toJson(registerResp));
					sendMsg(msgObj);
				}
			} else {
				registerResp.setFlag(false);
				registerResp.setMsg("验证码错误");
				msgObj.setM(gson.toJson(registerResp));
				sendMsg(msgObj);
			}
		} else {// 验证码失效，请重新申请
			registerResp.setFlag(false);
			registerResp.setMsg("验证码失效，请重新申请");
			msgObj.setM(gson.toJson(registerResp));
			sendMsg(msgObj);
		}
	}

}
