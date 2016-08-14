package com.hipishare.chat.server.command;

import com.hipishare.chat.server.enums.CmdEnum;
import com.hipishare.chat.server.exception.HipishareException;
import com.hipishare.chat.server.receiver.HeartBeatReceiver;
import com.hipishare.chat.server.receiver.LoginReceiver;
import com.hipishare.chat.server.receiver.RegisterCodeReceiver;
import com.hipishare.chat.server.receiver.RegisterReceiver;

import io.netty.channel.Channel;

/**
 * 
 * @description: 命令控制器
 * @author sunlei
 * @date: 2016年8月14日
 *
 */
public class CommandController {
	
	public static HipishareCommand getReceiverByCmd(short cmd, String msg, Channel channel) throws HipishareException {
		CmdEnum cmdEnum = CmdEnum.getCmdEnum(cmd);
		if (null == cmdEnum) {
			HipishareException.raise("1", "不支持的命令");
		}
		switch (cmdEnum) {
		case HEART_BEAT:
			return new HeartBeatReceiver(msg, channel);
			
		case LOGIN:
			return new LoginReceiver(msg, channel);
			
		case REGISTER_CODE:
			return new RegisterCodeReceiver(msg, channel);
			
		case REGISTER:
			return new RegisterReceiver(msg, channel);
			
		default:
			break;
		}
		return null;
	}
}
