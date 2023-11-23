import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: Jedis练习
 * User: 12569
 * Date: 2023-11-20
 * Time: 23:42
 */
public class RedisDemo {
    /**
     * set get
     * @param jedis
     */
    public static void test1(Jedis jedis) throws InterruptedException {
        //清空数据库，以免残留数据干扰
        jedis.flushAll();
        System.out.println("get 和 set：");
        jedis.set("key", "111");
        jedis.set("key2", "222");

        SetParams params = new SetParams();
//        params.ex(3);
//        params.nx();
        params.xx();
        jedis.set("key3", "333", params);

//        String value = jedis.get("key3");
//        System.out.println("value = " + value);
//        Thread.sleep(4000);
//        String value2 = jedis.get("key3");
//        System.out.println("value = " + value2);

        String value = jedis.get("key3");
        System.out.println("value = " + value);
    }
    public static void test2(Jedis jedis){
        System.out.println("exists 和 del");
        jedis.flushAll();

        jedis.set("key", "111");
        jedis.set("key2", "222");

        boolean ret = jedis.exists("key");
        System.out.println("ret = " + ret);

        ret = jedis.exists("key3");
        System.out.println("ret = " + ret);

        jedis.del("key","key2");
        ret = jedis.exists("key2");
        System.out.println("ret = " + ret);
    }

    public static void test3(Jedis jedis){
        System.out.println("keys *");
        jedis.flushAll();

        jedis.set("key","111");
        jedis.set("key2","222");
        jedis.set("key3","333");
        jedis.set("key4","444");

        Set<String> ret = jedis.keys("*");
        System.out.println(ret);
    }

    public static void test4(Jedis jedis){
        System.out.println("expire 和 ttl");
        jedis.flushAll();

        jedis.set("key","111");

        jedis.expire("key", 10);
        long ttl = jedis.ttl("key");
        System.out.println(ttl);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ttl = jedis.ttl("key");
        System.out.println(ttl);
    }

    public static void test5(Jedis jedis){
        jedis.flushAll();
        System.out.println("type：");

        jedis.set("key","111");
        jedis.lpush("key2","aaa","bbb","ccc");
        jedis.zadd("key3",30,"zhangsan");
        jedis.hset("key4", "f1", "000");
        jedis.sadd("key5", "ppp", "qqq", "www");

        System.out.println(jedis.type("key"));
        System.out.println(jedis.type("key2"));
        System.out.println(jedis.type("key3"));
        System.out.println(jedis.type("key4"));
        System.out.println(jedis.type("key5"));
    }
    public static void main(String[] args) throws InterruptedException {
        //连接到redis服务器
        JedisPool jedisPool = new JedisPool(Config.URL);

        try(Jedis jedis = jedisPool.getResource()) {
            jedis.auth(Config.PASSWORD);
//            String pong = jedis.ping();
//            System.out.println(pong);
//            test1(jedis);
//            test2(jedis);
//            test3(jedis);
//            test4(jedis);
            test5(jedis);
        }
    }
}
