**redis 原子操作**

需要使用脚本

~~~java
// 原子验证 并删除
String script = "if redis.call('get', KEYS[1]) == ARGV[1]  then
    return redis.call('del', KEYS[1]) else return 0 end";
    
Long result = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList(key), val);// , KEYS[1] 键, ARGV[1] 值
if(result == 0L) {
    // 验证失败
} else{
    // 验证成功，并且删除成功
}
~~~

