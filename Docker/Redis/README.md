**Docker 安装 Redis**

下载镜像：

```she
docker pull redis
```

创建实例并启动：

```shell
mkdir -p /mydata/redis/conf
touch /mydata/redis/conf/redis.conf

docker run -p 6379:6379 --name redis \
-v /mydata/redis/data:/data \
-v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf \
-d redis redis-server /etc/redis/redis.conf
```

启动 redis 客户端： docker exec -it redis-cli

开启 Redis 持久化功能： `vim /mydata/redis/conf/redis.conf`

​		`appendonly yes`

重启 redis： docker restart redis

设置开机自启动：

```she
sudo docker update redis --restart=always
```

setIfAbsent(...)  原子操作