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
package com.hipishare.chat;

import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a client-side channel.
 */
public class SecureChatClientHandler extends ChannelInboundHandlerAdapter {
	private static Logger LOG = LogManager.getLogger(SecureChatClientHandler.class.getName());

	private final ByteBuf firstMessage;

	public SecureChatClientHandler() {
		/*firstMessage = Unpooled.buffer(EchoClient.SIZE);
		for (int i = 0; i < firstMessage.capacity(); i++) {
			firstMessage.writeByte((byte) i);
		}*/
		firstMessage = Unpooled.copiedBuffer("hello".getBytes());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		LOG.info("链接建立成功，第一次发消息：" + firstMessage.toString());
		ctx.writeAndFlush(firstMessage);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		LOG.info("客户端读到消息：" + msg);
		if ("ping".equals(msg)) {
			ByteBuf buf = Unpooled.copiedBuffer(("[id=" + ctx.channel().id() + "]" + "ok").getBytes());
			ctx.writeAndFlush(buf);
		}
		System.err.println(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
