import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class RedisDemoList {

    public static void test1(Jedis jedis){
        jedis.flushAll();
        System.out.println("lpush 和 lrange");
        jedis.lpush("key", "111","222","333");
        List<String> list = jedis.lrange("key", 0, -1);
        System.out.println(list);
    }

    public static void test2(Jedis jedis) {
        System.out.println("rpush");
        jedis.flushAll();
        jedis.rpush("key","111","222","333");
        List<String> list = jedis.lrange("key", 0, -1);
        System.out.println(list);
    }

    public static void test3(Jedis jedis){
        System.out.println("lpop和rpop");
        jedis.flushAll();
        jedis.lpush("key","111","222","333");
        String key = jedis.lpop("key");
        System.out.println(key);
        String key1 = jedis.rpop("key");
        System.out.println(key1);
    }

    public static void test4(Jedis jedis){
        System.out.println("blpop 和 brpop");
        jedis.flushAll();

        List<String> list = jedis.blpop(100, "key");
        System.out.println(list.get(0) + "  " + list.get(1));
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
