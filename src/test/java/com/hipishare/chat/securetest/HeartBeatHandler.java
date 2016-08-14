package com.hipishare.chat.securetest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	private static Logger LOG = LogManager.getLogger(HeartBeatHandler.class.getName());
	
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		LOG.info("[HeartBeatHandler.userEventTriggered][begin]");
		if (evt instanceof IdleStateEvent) {
        	IdleStateEvent e = (IdleStateEvent)evt;
        	if (e.state() == IdleState.READER_IDLE) {
        		LOG.info("[HeartBeatHandler.userEventTriggered][state = "+e.state()+"]");
        		ctx.close();
        	} else if (e.state() == IdleState.WRITER_IDLE) {
        		LOG.info("[HeartBeatHandler.userEventTriggered][ping state = "+e.state()+"]"+"[ChannelId="+ctx.channel().id()+"]");
        		ByteBuf buf = Unpooled.copiedBuffer("ping".getBytes());
//        		ctx.channel().writeAndFlush(buf);
        		ctx.writeAndFlush(buf);
        	}
        }
		LOG.info("[HeartBeatHandler.userEventTriggered][end]");
    }
}
