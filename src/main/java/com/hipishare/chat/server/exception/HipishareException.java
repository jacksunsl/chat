package com.hipishare.chat.server.exception;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hipishare.chat.server.utils.PropertiesUtil;

/**
 * 
 * 异常信息处理类
 * @author sunlei
 * @date 2016年8月15日
 */
public class HipishareException extends RuntimeException {
	private static final long serialVersionUID = -4844724237083469147L;

	private static Logger LOG = LogManager.getLogger(HipishareException.class.getName());

	// 错误编码
	private String code;

	// 错误信息
	private String msg;

	// 错误信息配置文件
	protected static PropertiesUtil properties4Message = null;
	
	public static PropertiesUtil getMessageProperties(){
		try {
			if (null == properties4Message) {
				properties4Message = new PropertiesUtil("message.properties");
			}
		} catch (IOException e) {
			LOG.error("load properties is error in Component.", e);
		}
		return properties4Message;
	}

	public HipishareException() {
	}

	public HipishareException(String code, String message) {
		this.code = code;
		this.msg = message;
	}

	public static void raise(String code) {
		String message = getMessageProperties().getProperty(code);
		if (null == message || "".equals(message)) {
			message = "未知异常编码：" + code;
		}
		HipishareException exception = new HipishareException(code, message);
		throw exception;
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
