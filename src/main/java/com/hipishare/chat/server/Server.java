package com.hipishare.chat.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hipishare.chat.server.handler.SecureChatInitializer;
import com.hipishare.chat.server.manager.MemcachedManager;
import com.hipishare.chat.server.utils.SpringContextUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * 
 * @description: 聊天启动服务
 * @author sunlei
 * @date: 2016年8月2日
 *
 */
public class Server {
	
	private static Logger LOG = LogManager.getLogger(Server.class.getName());

	public static final int port = 11210;

	public static void main(String[] args) {
		setUpSocketServer();
	}

	private static void setUpSocketServer() {
		LOG.info("hipishare-chat-server 开始启动...");
		EventLoopGroup bossGroup = null;
		EventLoopGroup workerGroup = null;
		try {
			// ssl安全
	        SelfSignedCertificate ssc = new SelfSignedCertificate();
	        SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
	            .build();
	        
			bossGroup = new NioEventLoopGroup(4); // 接收客户端请求任务线程
			workerGroup = new NioEventLoopGroup(4);// 处理客户端请求任务线程
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)// channel连接超时
					.childOption(ChannelOption.SO_KEEPALIVE, true)
//		            .handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new SecureChatInitializer(sslCtx));// 增加ssl安全
			
			// 初始化memcached
			MemcachedManager.initMemcached();
			// 初始化redis
//			RedisManager.getRedisClient();
			// 初始化spring
			SpringContextUtil.initSpringConfig();
			
			// 绑定端口，开始接收进来的连接
			ChannelFuture f = b.bind(port).sync();
			LOG.info("hipishare-chat-server[port="+port+"] 启动成功...");
			// 等待服务器 socket 关闭 。
			// 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
			f.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			LOG.info("hipishare-chat-server 关闭了。");
		}
	}

}
