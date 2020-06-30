**Docker 安装 MySQL**

​	到 [Docker 镜像库](https://hub.docker.com/)查找 mysql

​		 安装 ： 

```shell
docker pull mysql:5.7
```



​		 查看 docker 镜像： `docker images` 

​		启动镜像

```shell
docker run -p 3306:3306 --name mysql \
-v /mydata/mysql/log:/var/log/mysql \
-v /mydata/mysql/data:/var/lib/mysql \
-v /mydata/mysql/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql:5.7
```



  参数说明：  `-p 3306:3306`  将docker 容器 3306 端口映射到主机3306端口

​						`--name mysql` 将这个进程起名为 mysql

 						`-v /mydata/mysql/log:/var/log/mysql` 将容器内部 var/log/mysql 挂载到 虚拟机 /mydata/mysql/log 这个目录下

​	修改 mysql 配置

​		在 /mydata/mysql/conf 路径下新建 my.cnf 文件

```shell
[client]
default-character-set=utf8

[mysql]
default-character-set=utf8

[mysqld]
init_connect='SET collation_connection = utf8_unicode_ci'
init_connect='SET NAMES utf8'
character-set-server=utf8
collation-server=utf8_unicode_ci
skip-character-set-client-handshake
skip-name-resolve
```

​	重启 mysql 容器： `docker restart mysql`

设置开机自启动：

```she
sudo docker update mysql --restart=always
```

若远程连接，执行sql报错 `[Err] 1055 - Expression #1 of ORDER BY` ， 则可以用以下方式解决

```sql
show variables like "sql_mode";
-- 设置sql_mode
set @@global.sql_mode=''; 
set @@global.sql_mode='NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES';
--SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY,',''));
```

或者修改配置文件my.cnf

```shell
[mysqld]
sql_mode=STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTIO
```

