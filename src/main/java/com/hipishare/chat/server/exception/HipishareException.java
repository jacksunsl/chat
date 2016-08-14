package com.hipishare.chat.server.exception;

public class HipishareException extends RuntimeException {
	
	private static final long serialVersionUID = -8416789767189466426L;

	// 错误编码
	private String code;

	// 错误信息
	private String msg;

	public HipishareException() {
	}

	public HipishareException(String code, String message) {
		this.code = code;
		this.msg = message;
	}

	public static void raise(String code, String message) {
		HipishareException exception = new HipishareException(code, message);
		throw exception;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return msg;
	}

	public void setMessage(String message) {
		this.msg = message;
	}
}
