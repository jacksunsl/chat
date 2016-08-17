package com.hipishare.chat.server.dao.po;

import java.util.Date;

/**
 * <b>uc_user[uc_user]数据持久化对象</b>
 * 
 * @author sunlei
 * @date 2016-08-16 12:09:17
 */
public class Uc_userPO {

	/**
	 * 主键id
	 */
	private Integer id;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 账号
	 */
	private String account;
	
	/**
	 * 密码
	 */
	private String pwd;
	
	/**
	 * userid,用户唯一标识
	 */
	private Integer userid;
	
	/**
	 * openid
	 */
	private String openid;
	
	/**
	 * 用户状态:1.正常 2.停用
	 */
	private Integer status;
	
	/**
	 * 注册时间
	 */
	private Date register_time;
	
	/**
	 * 数据是否有效:0.无效 1.有效
	 */
	private Integer is_valid;
	

	/**
	 * 主键id
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * 手机号
	 * 
	 * @return mobile
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * 账号
	 * 
	 * @return account
	 */
	public String getAccount() {
		return account;
	}
	
	/**
	 * 密码
	 * 
	 * @return pwd
	 */
	public String getPwd() {
		return pwd;
	}
	
	/**
	 * userid,用户唯一标识
	 * 
	 * @return userid
	 */
	public Integer getUserid() {
		return userid;
	}
	
	/**
	 * openid
	 * 
	 * @return openid
	 */
	public String getOpenid() {
		return openid;
	}
	
	/**
	 * 用户状态:1.正常 2.停用
	 * 
	 * @return status
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * 注册时间
	 * 
	 * @return register_time
	 */
	public Date getRegister_time() {
		return register_time;
	}
	
	/**
	 * 数据是否有效:0.无效 1.有效
	 * 
	 * @return is_valid
	 */
	public Integer getIs_valid() {
		return is_valid;
	}
	

	/**
	 * 主键id
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * 手机号
	 * 
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 账号
	 * 
	 * @param account
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	
	/**
	 * 密码
	 * 
	 * @param pwd
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	/**
	 * userid,用户唯一标识
	 * 
	 * @param userid
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	/**
	 * openid
	 * 
	 * @param openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	/**
	 * 用户状态:1.正常 2.停用
	 * 
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * 注册时间
	 * 
	 * @param register_time
	 */
	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}
	
	/**
	 * 数据是否有效:0.无效 1.有效
	 * 
	 * @param is_valid
	 */
	public void setIs_valid(Integer is_valid) {
		this.is_valid = is_valid;
	}
	

}