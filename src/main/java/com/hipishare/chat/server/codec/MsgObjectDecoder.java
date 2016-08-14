package com.hipishare.chat.server.codec;

import java.nio.charset.Charset;
import java.util.List;

import com.google.gson.Gson;
import com.hipishare.chat.server.domain.MsgObject;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;

@Sharable
public class MsgObjectDecoder extends MessageToMessageDecoder<ByteBuf> {
	
	private Gson gson = new Gson();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		String msgObjStr = msg.toString(Charset.defaultCharset());
		MsgObject msgObject = gson.fromJson(msgObjStr, MsgObject.class);
		out.add(msgObject);
	}

}
