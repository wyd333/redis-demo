import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class RedisDemoString {

    public static void test1(Jedis jedis){
        System.out.println("mget 和 mset:");
        jedis.flushAll();

        jedis.mset("key1","111","key2","222","key3","333");
        List<String> ret = jedis.mget("key1", "key100","key2", "key3");
        System.out.println("values = " + ret);
    }

    public static void test2(Jedis jedis){
        jedis.flushAll();
        System.out.println("getrange 和 setrange:");
        jedis.set("key", "hello world!");
        String s = jedis.getrange("key", 0, 5);
        System.out.println(s);

        jedis.setrange("key", 6, "hellooooo");
        System.out.println(jedis.get("key"));
    }

    public static void test3(Jedis jedis){
        jedis.flushAll();
        System.out.println("append:");

        jedis.set("key", "hello");
        jedis.append("key", " world!");

        System.out.println(jedis.get("key"));
    }

    public static void test4(Jedis jedis){
        jedis.flushAll();
        System.out.println("incr 和 decr");

        jedis.set("key", "0" ); //"000"不行
        long ret = jedis.incr("key");
        System.out.println(ret);
        System.out.println(jedis.get("key"));

        jedis.decr("key");
        System.out.println(jedis.get("key"));
    }
    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(Config.URL);
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.auth(Config.PASSWORD);
//            test1(jedis);
//            test2(jedis);
//            test3(jedis);
            test4(jedis);
        }
    }
}
