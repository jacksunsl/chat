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
package com.hipishare.chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hipishare.chat.server.command.CommandController;
import com.hipishare.chat.server.command.HipishareCommand;
import com.hipishare.chat.server.domain.MsgObject;
import com.hipishare.chat.server.exception.HipishareException;

/**
 * Handles a server-side channel.
 */
public class SecureChatHandler extends SimpleChannelInboundHandler<MsgObject> {

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	private static Logger LOG = LogManager.getLogger(SecureChatHandler.class.getName());

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
    	LOG.info("[掉线]handlerRemoved");
		channels.remove(ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
    	LOG.info("[离线]handlerRemoved");
	}
	
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        // Once session is secured, send a greeting and register the channel to the global channel
        // list so the channel received the messages from others.
    	LOG.info("[上线]服务端channel激活");
        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
            new GenericFutureListener<Future<Channel>>() {
                @Override
                public void operationComplete(Future<Channel> future) throws Exception {
                	if (future.isSuccess()) {
                    	LOG.info("SSL安全模块加载成功");
                        channels.add(ctx.channel());
                	}
                }
        });
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MsgObject msg) throws Exception {
        // Send the received message to all channels but the current one.
    	LOG.info("服务端收到消息："+msg.getC());
    	// getReceiverByCmd(short cmd);
    	HipishareCommand command = null;
    	try {
    		command = CommandController.getReceiverByCmd(msg.getC(), msg.getM(), ctx.channel());
        	command.execute();
    	} catch (HipishareException e) {
    		e.printStackTrace();
    		LOG.error(e.getMessage());
    	}
        /*for (Channel c: channels) {
            if (c != ctx.channel()) {
                c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
            } else {
                c.writeAndFlush("[you] " + msg + '\n');
            }
        }
        // Close the connection if the client has sent 'bye'.
        if ("bye".equals(msg.getC())) {
            ctx.close();
        }*/
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	LOG.error("[异常]"+cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
