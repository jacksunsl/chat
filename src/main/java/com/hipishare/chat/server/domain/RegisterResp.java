package com.hipishare.chat.server.domain;

public class RegisterResp {
	private boolean flag;// 操作标识
	private String msg;// 操作结果
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
