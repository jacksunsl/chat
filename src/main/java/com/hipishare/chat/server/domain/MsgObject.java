package com.hipishare.chat.server.domain;

import java.io.Serializable;

/**
 * 
 * @description: 聊天消息报文格式
 * @author sunlei
 * @date: 2016年8月13日
 *
 */
public class MsgObject implements Serializable {

	private static final long serialVersionUID = -2679514379747039414L;
	
	/**
	 * 命令
	 */
	private short c;
	
	/**
	 * 消息
	 * 根据不同的命令，数据格式不同。大部分是json格式。
	 */
	private String m;

	public short getC() {
		return c;
	}

	public void setC(short c) {
		this.c = c;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

}
