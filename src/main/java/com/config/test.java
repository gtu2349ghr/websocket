package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;


public class test {

    public static void main(String[] args) {
        Set<String> hashSet = new HashSet();
        Set<String> hashSet1 = new HashSet();

        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.auth("518610");
         jedis.sadd("hashSet","9","87");
         jedis.sadd("hashSet1","9");
        jedis.sadd("hashSet","9","999");
        Set<String> set = jedis.sdiff("hashSet","hashSet1");
        for(String s:set){
            System.out.println("这是集合"+s);
        }
        System.out.println(jedis.get("user"));


    }
}
