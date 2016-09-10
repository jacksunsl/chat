package com.hipishare.chat.server.domain;

public class AbstractResp {
	private boolean flag;// 操作标识。true:成功，false:失败
	private String msg;// 操作消息
	private String code;// 消息编码
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
