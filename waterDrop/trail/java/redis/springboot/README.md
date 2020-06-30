springboot 整合 Redis


  需要依赖  spring-boot-starter-data-redis
          spring2.X 集成 redis 需要 common-pools 

 创建 Redis 配置类 RedisConfig
        application.properties 添加配置
            spring.redis.host=192.168.44.132
            spring.redis.port=6379
            spring.redis.database= 0
            spring.redis.timeout=1800000

            spring.redis.lettuce.pool.max-active=20
            spring.redis.lettuce.pool.max-wait=-1
            #最大阻塞等待时间(负数表示没限制)
            spring.redis.lettuce.pool.max-idle=5
            spring.redis.lettuce.pool.min-idle=0


 在方法上添加注解； 可用 @Cacheable   @CachePut  @CacheEvict
 @Cacheable  // 根据方法对其返回结果进行缓存，下次请求时，若缓存存在，则直接读取缓存数据；若不存在，则执行方法，返回结果并存入缓存；
     可选属性： value 缓存名， 指定缓存放在哪块命名空间
               cacheNames： 与value差不多， 二选一
               key： 可选属性，可以使用SpEL 标签自定义缓存的key

 @CachePut // 被标记的方法每次都会执行，并将结果存入指定的缓存中。其他方法可以直接重响应的缓存中读取缓存数据， 一般用在新增方法上；
     属性同上；

 @CacheEvict // 使用该注解的方法，会清空指定缓存。一般用在更新或者删除方法上。 
    可选属性： value 缓存名， 指定缓存放在哪块命名空间
              cacheNames： 与value差不多， 二选一
              key： 可选属性，可以使用SpEL 标签自定义缓存的key
              allEntries: 是否清空所有缓存， 默认 false， 若指定为 true， 则方法调用后将立即清空所有缓存。

 redis 中 key 生成规则： 
      示例： @Cacheable(value = "test", key = "'testKey'") // key 需要单引号引起来
      Redis key：  test::testKey