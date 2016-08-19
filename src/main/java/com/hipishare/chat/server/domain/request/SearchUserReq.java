package com.hipishare.chat.server.domain.request;

/**
 * 查找用户请求实体
 * @author sunlei
 * @date 2016年8月19日
 */
public class SearchUserReq {
	private String fromUser;// 查找人
	private String account;// 被查找人账号
	private String sign;// 签名
	private String searchType;// 查找方式：1.账号 2.二维码 3.手机号
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
}
