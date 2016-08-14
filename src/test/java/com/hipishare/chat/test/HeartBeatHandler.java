package com.hipishare.chat.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 
 * @description: 心跳检测
 * @author sunlei
 * @date: 2016年8月2日
 *
 */
public class HeartBeatHandler extends ChannelDuplexHandler {

	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		System.out.println("[HeartBeatHandler.userEventTriggered][begin]");
		if (evt instanceof IdleStateEvent) {
        	IdleStateEvent e = (IdleStateEvent)evt;
        	if (e.state() == IdleState.READER_IDLE) {
        		System.out.println("[HeartBeatHandler.userEventTriggered][state = "+e.state()+"]");
        		ctx.close();
        	} else if (e.state() == IdleState.WRITER_IDLE) {
        		System.out.println("[HeartBeatHandler.userEventTriggered][state = "+e.state()+"]"+"[ChannelId="+ctx.channel().id()+"]");
        		ByteBuf buf = Unpooled.copiedBuffer("ping".getBytes());
//        		ctx.channel().writeAndFlush(buf);
        		ctx.writeAndFlush(buf);
        	}
        }
		System.out.println("[HeartBeatHandler.userEventTriggered][end]");
    }
}
