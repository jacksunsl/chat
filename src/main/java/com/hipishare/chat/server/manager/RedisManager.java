package com.hipishare.chat.server.manager;

import java.io.IOException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hipishare.chat.server.exception.HipishareException;
import com.hipishare.chat.server.utils.PropertiesUtil;

//import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisCluster;

public class RedisManager {

	private static Logger LOGGER = LogManager.getLogger(RedisManager.class.getName());
	
	protected static PropertiesUtil properties4Redis = null;

	private volatile static RedisManager redisManager;

//	private static Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();

//	private static JedisCluster jedisCluster;
	
	private static int port;
	
	private static String ip;
	
	private static Jedis jedisClient = null;
	
	static {
		try {
			if (null == properties4Redis) {
				LOGGER.info("redis加载...");
				properties4Redis = new PropertiesUtil("redis.properties");
				Set<String> allKey = properties4Redis.getAllKey();
				for (String key : allKey) {
					String value = properties4Redis.getProperty(key);
					if (null != value && !"".equals(value)) {
						LOGGER.info("redis服务地址："+value);
						ip = value.split(":")[0];
						port = Integer.parseInt(value.split(":")[1]);
//						jedisClusterNodes.add(new HostAndPort(ip, port));
					}
				}
//				jedisCluster = new JedisCluster(jedisClusterNodes);
			}
		} catch (IOException e) {
			LOGGER.error("加载redis.properties失败", e);
		}
	}

	private RedisManager() {
	}
	
	public static void initRedisClient(){
		if (0 == port || null == ip) {
			LOGGER.error("redis 配置加载失败");
			HipishareException.raise("3002");
		}
		if (null == jedisClient) {
			jedisClient = new Jedis(ip, port);
			LOGGER.info("redis 客户端初始化成功");
		}
	}

	public static RedisManager getRedisClient() {
		if (null == redisManager) {
			synchronized (RedisManager.class) {
				if (null == redisManager) {
					redisManager = new RedisManager();
					LOGGER.info("redisManager 实例创建成功");
				}
			}
		}
		return redisManager;
	}

	public String set(String key, String value, long expSecond) {
		return jedisClient.set(key, value, "nx", "ex", expSecond);
	}

	public String get(String key) {
		return jedisClient.get(key);
	}
	
	public Long del(String key){
		return jedisClient.del(key);
	}
}
