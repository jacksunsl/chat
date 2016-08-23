package com.hipishare.chat.server.manager;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.hipishare.chat.server.exception.HipishareException;
import com.hipishare.chat.server.utils.PropertiesUtil;

/**
 * memcached管理类
 * @author sunlei
 * @date 2016年8月15日
 */
public class MemcachedManager {

	private static MemcachedManager memcachedManager = null;
	
	private static MemCachedClient memcachedClient = new MemCachedClient("memcached");

	private static Logger LOG = LogManager.getLogger(MemcachedManager.class
			.getName());

	protected static PropertiesUtil properties4MemCached = null;

	private static String[] memCachedIPs = null;

	/**
	 * 初始化memcached服务器配置
	 */
	static {
		try {
			properties4MemCached = new PropertiesUtil("memcached.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set<String> allKey = properties4MemCached.getAllKey();
		memCachedIPs = new String[allKey.size()];
		int i = 0;
		for (String key : allKey) {
			if (null != key) {
				String value = properties4MemCached.getProperty(key);
				memCachedIPs[i] = value;
				LOG.info("memcached服务地址：" + value);
			}
			i++;
		}
	}

	private MemcachedManager() {
	}

	public static MemcachedManager getInstance() {
		if (null == memcachedManager) {
			synchronized (MemcachedManager.class) {
				if (null == memcachedManager) {
					memcachedManager = new MemcachedManager();
				}
			}
		}
		return memcachedManager;
	}
	
	public static MemCachedClient getMemCachedClient() {
		return memcachedClient;
	}

	public static void initMemcached() throws Exception {
		String[] serverlist = memCachedIPs;
		LOG.info("memcached地址加载完成");
		SockIOPool pool = SockIOPool.getInstance("memcached");
		pool.setServers(serverlist);
		pool.setFailover(true);
		pool.setInitConn(10);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaintSleep(30);
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setAliveCheck(true);
		pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);
		pool.initialize();

		MemCachedClient memcachedClient = new MemCachedClient("memcached");
		if (null == memcachedClient.stats()
				|| memcachedClient.stats().size() <= 0) {
			LOG.error("memcached初始化失败");
			HipishareException.raise("3001");
		}
		LOG.info("memcached初始化成功");
	}
	
	public static boolean set(String key, Object value){
		return memcachedClient.set(key, value);
	}
	
	public static Object get(String key) {
		return memcachedClient.get(key);
	}
	
	public static boolean del(String key) {
		return memcachedClient.delete(key);
	}
}
