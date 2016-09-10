package com.hipishare.chat.server.receiver;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hipishare.chat.server.domain.MsgObject;
import com.hipishare.chat.server.exception.HipishareException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

public class AbstractReceiver<E> {
	
	protected Gson gson = new Gson();
	
	protected String msg;
	
	protected Channel channel;
	
	public AbstractReceiver(String msg, Channel channel){
		this.msg = msg;
		this.channel = channel;
	}
	
	public E getEntityFromMsg(Class<E> clazz) throws HipishareException {
		if (null != msg && !"".equals(msg)) {
			try {
				return gson.fromJson(msg, clazz);
			} catch (JsonSyntaxException e) {
				HipishareException.raise("", "msg格式错误:"+msg);
			}
		} else {
			HipishareException.raise("", "msg不能为空");
		}
		return null;
	}
	
	public void sendMsg(MsgObject msgObj){
		String result = gson.toJson(msgObj)+"\r\n";
		ByteBuf buf = Unpooled.copiedBuffer(result.getBytes());
		channel.writeAndFlush(buf);
	}
}
