package com.hipishare.chat.server.manager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.hipishare.chat.server.utils.PropertiesUtil;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisManager {

	private static Logger LOGGER = LogManager.getLogger(RedisManager.class.getName());
	
	protected static PropertiesUtil properties4Redis = null;

	private volatile static RedisManager redisClient;

	private static Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();

	private static JedisCluster jedisCluster;

	static {
		try {
			if (null == properties4Redis) {
				LOGGER.info("redis加载...");
				properties4Redis = new PropertiesUtil("redis.properties");
				Set<String> allKey = properties4Redis.getAllKey();
				for (String key : allKey) {
					String value = properties4Redis.getProperty(key);
					if (null != value && !"".equals(value)) {
						String ip = value.split(":")[0];
						Integer port = Integer.parseInt(value.split(":")[1]);
						jedisClusterNodes.add(new HostAndPort(ip, port));
					}
				}
				jedisCluster = new JedisCluster(jedisClusterNodes);
				LOGGER.info("redis加载成功");
			}
		} catch (IOException e) {
			LOGGER.error("加载redis.properties失败", e);
		}
	}

	private RedisManager() {
	}

	public static RedisManager getRedisClient() {
		if (null == redisClient) {
			synchronized (RedisManager.class) {
				if (null == redisClient) {
					redisClient = new RedisManager();
				}
			}
		}
		LOGGER.info("redis 客户端初始化成功");
		return redisClient;
	}

	public String set(String key, String value, long expSecond) {
		return jedisCluster.set(key, value, "nx", "ex", expSecond);
	}

	public String get(String key) {
		return jedisCluster.get(key);
	}
	
	public Long del(String key){
		return jedisCluster.del(key);
	}
}
