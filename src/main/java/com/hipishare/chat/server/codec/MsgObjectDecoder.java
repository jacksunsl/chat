package com.hipishare.chat.server.codec;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.hipishare.chat.server.domain.MsgObject;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;

@Sharable
public class MsgObjectDecoder extends MessageToMessageDecoder<ByteBuf> {

	private static Logger LOG = LogManager.getLogger(MsgObjectDecoder.class.getName());
	
	private Gson gson = new Gson();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		String msgObjStr = msg.toString(Charset.defaultCharset());
		LOG.info("decode="+msgObjStr);
		MsgObject msgObject = gson.fromJson(msgObjStr, MsgObject.class);
		out.add(msgObject);
	}

}
