**Redisson**

分布式锁



配合 SpringCache 使用

 原理 CacheAutoConfiguration -> RedisCacheConfiguration -> 自动配置 RedisCacheManager -> 初始化缓存 -> 决定缓存使用什么配置

 -> 如果有 redisCacheConfiguration ，则用 redisCacheConfiguration ， 若没有，则用默认配置

需要修改缓存配置，只需给容器放入 redisCacheConfiguration 

自定义redisCacheConfiguration 

~~~java
@EnableConfigurationProperties(CacheProperties.class)
@EnableCaching
@Configuration
public class MyCacheConfig {

    @Bean
    RedisCacheConfiguration redisCacheConfiguration(CacheProperties  cacheProperties) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // key 
        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        // value 默认是 对象序列化缓存， 为了方便，使用 Json 格式
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // 使配置文件中的配置生效
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }

        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
        }

        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }

        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        return config;
    }
}
~~~



配置文件

~~~yaml
spring:
  cache:
    type: redis
    redis:
      time-to-live: 3600000  #设置缓存过期时间， 若不设置， 则没有过期时间
      key-prefix: CATA_  #若指定了前缀， 则用指定的前缀替换value
      use-key-prefix: true  # 若不使用前缀， 则直接用key， 指定了前缀也不生效
      cache-null-values: true # 是否 缓存空值， 可以防止缓存穿透
~~~



SpringCache 可用注解

@Cacheable // 把查询结果存入缓存

~~~java
@Cacheable(value = {"catalog"}, key = "'Level1'")
@Cacheable(value = {"catalog"}, key = "'getCatalogJson'")
~~~

@CacheEvict  // 失效模式， 在数据更新时，删除缓存

~~~java
// 会清空 catalog 分区下的所有缓存
@CacheEvict(value = "catalog", allEntries = true)
~~~

@CachePut // 双写模式， 在数据更新时， 删除缓存， 然后把查询结果存入缓存

@Caching

~~~java
// 会清空 “catalog” 分区下，“Level1”、“getCatalogJson” 这两个缓存
@Caching(
    evict = {
        @CacheEvict(value = "catalog", key = "'Level1'"),
        @CacheEvict(value = "catalog", key = "'getCatalogJson'")
    }
)
~~~



springCache

1. 读模式
   1. 缓存穿透：查询一个null 数据。 解决：缓存空数据，cache-null-values: true
   2. 缓存击穿： 大量并发进来同时查询一个正好过期的数据。 解决： 加锁，  默认是无锁的； @Cacheable(value = {"catalog"}, key = "'Level1'"， sync = true), 只有 Cacheable 可以加锁
   3. 缓存雪崩： 大量的 key同时失效。 解决： 加上过期时间， time-to-live: 3600000
2. 写模式 （缓存与数据库数据一致问题）
   1. 读写加锁
   2. 引入Canal， 感知Mysql 的更新，去更新缓存
   3. 读多写多， 直接去查数据库

总结：

​	常规数据（读多写少、及时性、一致性要求不高的数据），可以用springcache，写模式给加上过期时间即可

​	特殊数据， 需要特殊设计

