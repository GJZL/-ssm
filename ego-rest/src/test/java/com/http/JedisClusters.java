package com.http;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusters {
	public void testJedisCluster() {

		JedisPoolConfig config = new JedisPoolConfig();
		// 最大连接数
		config.setMaxTotal(30);
		// 最大连接空闲数
		config.setMaxIdle(2);

		// 集群结点
		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		jedisClusterNode.add(new HostAndPort("192.168.74.128", 7001));
		jedisClusterNode.add(new HostAndPort("192.168.74.128", 7002));
		jedisClusterNode.add(new HostAndPort("192.168.74.128", 7003));
		jedisClusterNode.add(new HostAndPort("192.168.74.128", 7004));
		jedisClusterNode.add(new HostAndPort("192.168.74.128", 7005));
		jedisClusterNode.add(new HostAndPort("192.168.74.128", 7006));
		JedisCluster jc = new JedisCluster(jedisClusterNode, config);

		JedisCluster jcd = new JedisCluster(jedisClusterNode);
		jcd.set("name3", "zhangsan3");
		String value = jcd.get("name3");
		System.out.println(value);
	}

	public static void main(String[] args) {
		new JedisClusters().testJedisCluster();
	}
}
