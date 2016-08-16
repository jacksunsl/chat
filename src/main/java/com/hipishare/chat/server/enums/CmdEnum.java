package com.hipishare.chat.server.enums;

/**
 * 
 * @Description: 命令枚举类
 * @date 2016年5月24日
 * @author sunlei
 * @version 1.0
 */
public enum CmdEnum {
	HEART_BEAT((short)0),// 心跳检测
	REGISTER((short)1),// 用户注册
	REGISTER_CODE((short)2),// 注册短信验证码
	LOGIN((short)3),// 登录
	CHAT((short)4),// 聊天
	GET_FRIENDS((short)5),// 获取好友列表
	READY((short)7);// 准备
	
	private final short cmd;
	
	private CmdEnum(short cmd) {
		this.cmd = cmd;
	}
	
	public short getCmd(){
		return cmd;
	}
	
	public static CmdEnum getCmdEnum(short cmd){
		for (CmdEnum cmdEnum : CmdEnum.values()) {
			if (cmd == cmdEnum.getCmd()) {
				return cmdEnum;
			}
		}
		return null;
	}
	
}
