package com.hipishare.chat.server.receiver;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.ChatObject;
import com.hipishare.chat.server.domain.MsgObject;
import com.hipishare.chat.server.enums.CmdEnum;
import com.hipishare.chat.server.manager.ChannelManager;
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

	private static Logger LOG = LogManager.getLogger(SingleChatReceiver.class.getName());
	
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
		LOG.info("chat---------------");
		ChannelManager cm = ChannelManager.getInstance();
		Channel channelTo = cm.getChannel(chatObject.getMsgTo());
		Channel channelFrom = cm.getChannel(chatObject.getMsgFrom());
		
		// 发送给自己
		if (null != channelFrom) {
			MsgObject msgObj = new MsgObject();
			msgObj.setC(CmdEnum.CHAT.getCmd());
			msgObj.setM(gson.toJson(chatObject));
			String result = gson.toJson(msgObj);
			ByteBuf buf = Unpooled.copiedBuffer(result.getBytes());
			channelFrom.writeAndFlush(buf);
		}
		
		if (null != channelTo) {// 接收消息者在线
			if (channelTo.isActive()) {
				MsgObject msgObj = new MsgObject();
				msgObj.setC(CmdEnum.CHAT.getCmd());
				msgObj.setM(gson.toJson(chatObject));
				String result = gson.toJson(msgObj);
				ByteBuf buf = Unpooled.copiedBuffer(result.getBytes());
				channelTo.writeAndFlush(buf);
			}
		} else {// 用户不在线，消息存入缓存
//			RedisManager redisManager = RedisManager.getRedisClient();
			String key = Constants.MSG_PREFIX + chatObject.getMsgTo();
//			MemcachedManager.set(key, gson.toJson(chatObject));
			RedisManager.set(key, gson.toJson(chatObject), 60*60*24*30);// 30天
		}
	}
	

}
