package com.hipishare.chat.server.receiver;

import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.ChatObject;
import com.hipishare.chat.server.domain.MsgObject;
import com.hipishare.chat.server.enums.CmdEnum;
import com.hipishare.chat.server.manager.ChannelManager;
import com.hipishare.chat.server.manager.MemcachedManager;
import com.hipishare.chat.server.manager.RedisManager;
import com.hipishare.chat.server.utils.Constants;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

/**
 * 
 * @description:单聊 
 * @author sunlei
 * @date: 2016年8月14日
 *
 */
public class SingleChatReceiver extends AbstractReceiver<ChatObject> implements HipishareCommand {

	public SingleChatReceiver(String msg, Channel channel) {
		super(msg, channel);
	}

	@Override
	public void execute() throws Exception {
		ChatObject chatObject = getEntityFromMsg(ChatObject.class);
		// 返回消息接受确认包
		// 转发消息
		sendMsgTo(chatObject);
	}
	
	private void sendMsgTo(ChatObject chatObject) {
		System.out.println("chat---------------");
		ChannelManager cm = ChannelManager.getInstance();
		Channel channelTo = cm.getChannel(chatObject.getMsgTo());
		if (null != channelTo) {// 接收消息者在线
			if (channelTo.isActive()) {
				MsgObject msgObj = new MsgObject();
				msgObj.setC(CmdEnum.CHAT.getCmd());
				msgObj.setM(chatObject.getContent());
				String result = gson.toJson(msgObj);
				ByteBuf buf = Unpooled.copiedBuffer(result.getBytes());
				channelTo.writeAndFlush(buf);
			}
		} else {// 用户不在线，消息存入缓存
//			RedisManager redisManager = RedisManager.getRedisClient();
			String key = Constants.MSG_PREFIX + chatObject.getMsgTo();
			MemcachedManager.set(key, gson.toJson(chatObject));
//			redisManager.set(key, gson.toJson(chatObject), 60*60*24*30);// 30天
		}
	}
	

}
