package com.hipishare.chat.server.receiver;

import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.MsgObject;
import com.hipishare.chat.server.domain.RegisterCode;
import com.hipishare.chat.server.enums.CmdEnum;
import com.hipishare.chat.server.utils.RandomCode;

import io.netty.channel.Channel;

/**
 * 
 * @description: 获取注册短信验证码
 * @author sunlei
 * @date: 2016年8月14日
 *
 */
public class RegisterCodeReceiver extends AbstractReceiver<RegisterCode> implements HipishareCommand {

	public RegisterCodeReceiver(String msg, Channel channel) {
		super(msg, channel);
	}

	@Override
	public void execute() throws Exception {
		RegisterCode registerCode = getEntityFromMsg(RegisterCode.class);
		// 验证签名
		// 校验是否合法手机号
		// 根据设备号辨别是否多次请求验证码（同一个设备号，手机号一天最多请求3次短信验证码）
		// 验证该手机号是否已经注册成功
		// 验证该手机号是否列入注册黑名单（需要客服解封）
		if (null != registerCode.getMobile()) {
			MsgObject msgObj = new MsgObject();
			msgObj.setC(CmdEnum.REGISTER_CODE.getCmd());
			msgObj.setM(RandomCode.getCharAndNumr(4, true));// 短信验证码随机数
			sendMsg(msgObj);
		}
	}

}
