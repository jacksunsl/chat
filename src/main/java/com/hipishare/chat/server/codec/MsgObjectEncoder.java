package com.hipishare.chat.server.codec;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.hipishare.chat.server.domain.MsgObject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
public class MsgObjectEncoder extends MessageToMessageEncoder<MsgObject> {

	private static Logger LOG = LogManager.getLogger(MsgObjectEncoder.class.getName());
	
	private Gson gson = new Gson();

	@Override
	protected void encode(ChannelHandlerContext ctx, MsgObject msg, List<Object> out) throws Exception {
		if (null == msg) {
			return;
		}
		LOG.info("encode="+msg);
		out.add(gson.toJson(msg));
	}

}
