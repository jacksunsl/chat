package com.hipishare.chat.server.domain;

/**
 * 
 * @description: 短信注册码
 * @author sunlei
 * @date: 2016年8月14日
 *
 */
public class RegisterCode {
	private String mobile;// 手机号
	private String imei;// 手机设备号
	private String code;// 短信验证码(4位数字)
	private String sign;// 签名（避免刷短信）
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
