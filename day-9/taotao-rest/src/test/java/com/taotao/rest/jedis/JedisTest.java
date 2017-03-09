package com.taotao.rest.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

/**
 * Created by zh on 2/28/2017.
 */
public class JedisTest {

    @Test
    public void testJedisSingle() {
        //创建一个jedis的对象。
        Jedis jedis = new Jedis("192.168.25.129", 6379);

        //jedis.auth("itcasr");

        //调用jedis对象的方法，方法名称和redis的命令一致。
        jedis.set("key1", "jedis test");

        //测试
        String string = jedis.get("key1");
        System.out.println(string);

        //关闭jedis。
        jedis.close();
    }

    /**
     * 使用连接池
     */
    @Test
    public void testJedisPool() {
        //创建jedis连接池
        JedisPool pool = new JedisPool("192.168.25.129", 6379);

        //从连接池中获得Jedis对象
        Jedis jedis = pool.getResource();

        //测试
        String string = jedis.get("key1");
        System.out.println(string);

        //关闭jedis对象
        jedis.close();
        pool.close();
    }

    @Test
    public void testJedisCluster() {
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.129", 7001));
        nodes.add(new HostAndPort("192.168.25.129", 7002));
        nodes.add(new HostAndPort("192.168.25.129", 7003));
        nodes.add(new HostAndPort("192.168.25.129", 7004));
        nodes.add(new HostAndPort("192.168.25.129", 7005));
        nodes.add(new HostAndPort("192.168.25.129", 7006));

        JedisCluster cluster = new JedisCluster(nodes);//JedisCluster自带连接池

        cluster.set("key1", "1000");
        String string = cluster.get("key1");
        System.out.println(string);

        cluster.close();
    }

    /**
     * 单机版测试
     * <p>Title: testSpringJedisSingle</p>
     * <p>Description: </p>
     */
    @Test
    public void testSpringJedisSingle() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
        Jedis jedis = pool.getResource();
        String string = jedis.get("key1");
        System.out.println(string);
        jedis.close();
        pool.close();
    }

    @Test
    public void testSpringJedisCluster() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("redisClient");
        String string = jedisCluster.get("key1");
        System.out.println(string);
        jedisCluster.close();
    }

}