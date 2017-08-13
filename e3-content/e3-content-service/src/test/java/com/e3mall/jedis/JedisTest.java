package com.e3mall.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.sun.tools.classfile.Annotation.element_value;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	public void testJedis() throws Exception {
		// 1、把jedis相关的jar包添加到工程中。
		// 2、创建一个Jedis对象，构造方法，两个参数host、port
		Jedis jedis = new Jedis("192.168.25.128", 6379);
		// 3、直接使用Jedis对象操作redis，对应每个redis命令都有一个同名方法。
		jedis.set("mytest", "1000");
		String result = jedis.get("mytest");
		System.out.println(result);
		// 4、关闭连接
		jedis.close();
	}
	
	@Test
	public void testJedisPool() throws Exception {
		// 1、创建一个JedisPool对象，两个参数host、port
		JedisPool jedisPool = new JedisPool("192.168.25.128", 6379);
		// 2、从连接池中获得连接。得到一个Jedis对象。
		Jedis jedis = jedisPool.getResource();
		// 3、使用jedis对象取数据
		String string = jedis.get("mytest");
		// 4、打印结果
		System.out.println(string);
		// 5、关闭jedis，让连接池回收连接。每次使用完毕后都需要关闭。
		jedis.close();
		// 6、系统结束之前关闭JedisPool
		jedisPool.close();
	}
	
	@Test
	public void testJedisCluster() throws Exception {
		// 1、创建一个JedisCluster对象，构造方法参数只有一个nodes是一个Set，set中有多个HostAndPort对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.128", 7001));
		nodes.add(new HostAndPort("192.168.25.128", 7002));
		nodes.add(new HostAndPort("192.168.25.128", 7003));
		nodes.add(new HostAndPort("192.168.25.128", 7004));
		nodes.add(new HostAndPort("192.168.25.128", 7005));
		nodes.add(new HostAndPort("192.168.25.128", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		// 2、直接使用JedisCluster对象管理redis
		jedisCluster.set("mytest", "abcdefg");
		String string = jedisCluster.get("mytest");
		// 3、打印结果
		System.out.println(string);
		// 4、关闭JedisCluster（在系统中可以是单例的）
		jedisCluster.close();
	}
}
