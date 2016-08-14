/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.hipishare.chat.securetest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.hipishare.chat.server.domain.MsgObject;
import com.hipishare.chat.server.enums.CmdEnum;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a client-side channel.
 */
public class SecureChatClientHandler extends SimpleChannelInboundHandler<MsgObject> {
	private static Logger LOG = LogManager.getLogger(SecureChatClientHandler.class.getName());

	private final ByteBuf firstMessage;

	public SecureChatClientHandler() {
		/*firstMessage = Unpooled.buffer(EchoClient.SIZE);
		for (int i = 0; i < firstMessage.capacity(); i++) {
			firstMessage.writeByte((byte) i);
		}*/
		MsgObject msgObj = new MsgObject();
		Gson gson = new Gson();
		msgObj.setC(CmdEnum.HEART_BEAT.getCmd());
		msgObj.setM("o");
		String msg = gson.toJson(msgObj);
		firstMessage = Unpooled.copiedBuffer(msg.getBytes());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		LOG.info("链接建立成功，第一次发消息：" + firstMessage.toString());
		ctx.writeAndFlush(firstMessage);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MsgObject msg) throws Exception {
		LOG.info("客户端读到消息：" + msg.getM());
		if (CmdEnum.HEART_BEAT.getCmd() == msg.getC()) {
			MsgObject msgObj = new MsgObject();
			Gson gson = new Gson();
			msgObj.setC(CmdEnum.HEART_BEAT.getCmd());
			msgObj.setM("o");
			String msg2 = gson.toJson(msgObj);
			ByteBuf buf = Unpooled.copiedBuffer(msg2.getBytes());
			ctx.writeAndFlush(buf);
		}
	}
}
