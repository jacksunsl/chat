package com.hipishare.chat.server.receiver;

import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.RegisterCode;
import com.hipishare.chat.server.domain.User;
import com.hipishare.chat.server.manager.MemcachedManager;
import com.hipishare.chat.server.utils.Constants;

import io.netty.channel.Channel;

/**
 * 
 * @description: 注册
 * @author sunlei
 * @date: 2016年8月14日
 *
 */
public class RegisterReceiver extends AbstractReceiver<RegisterCode> implements HipishareCommand {

	public RegisterReceiver(String msg, Channel channel) {
		super(msg, channel);
	}

	@Override
	public void execute() throws Exception {
		RegisterCode register = getEntityFromMsg(RegisterCode.class);
		Object code = MemcachedManager.get(Constants.REGISTER_CODE+register.getMobile());
		if (null != code) {
			if (!register.getCode().equals(code.toString())) {
				
			}
		} else {// 验证码失效，请重新申请
			
		}
	}

}
