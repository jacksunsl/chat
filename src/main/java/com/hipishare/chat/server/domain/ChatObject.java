package com.hipishare.chat.server.domain;

/**
 * 
 * @description: 聊天对象 
 * @author sunlei
 * @date: 2016年7月27日
 *
 */
public class ChatObject {
	
	private String msgFrom;// 消息来源
	private String msgTo;// 消息去向
	private String sendType;// 发送类别：单聊，群聊，公众号
	private String msgType;// 消息类型：文本，图片，语音，视频
	private String content;// 消息内容
	private Long msgId;// 消息id
	private Long createTime;// 消息产生时间
	
	public String getMsgFrom() {
		return msgFrom;
	}
	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}
	public String getMsgTo() {
		return msgTo;
	}
	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
