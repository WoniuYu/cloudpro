调优

查看慢查询日志 show variables like "%slow_query_log%"

开启慢查询日志 set global slow_query_log=1;  #这对当前数据库生效， 重启MySQL则会失效 

查看大于多久才会记录  show variables  like 'long_query_time%'   #默认为10秒

设置阀值  set global long_query_time= 3;   #需要重新连接到数据库才能看见变化

  mysqldumpslow  可以分析慢查询的日志文件



若开启过慢查询日志，会开启bin-log， 创建函数会报错 This function has none of DETETMINISTIC...

​	解决

​	show variables like "log_bin_trust_function_creators";

​	set global log_bin_trust_function_creators=1

​	永久解决   my.cnf [mysqld] 加上  log_bin_trust_function_creators=1



profiling   off|on

 set profiling  =on;  #开启

show profiles ;  查看最近sql 记录

show profile cpu,block io for query  10 # 查看id 10 号sql的生命周期



全局查询日志    生产环境不能开启

set global general_log=1;

set global log_output="TABLE"

设置完之后，所有执行的sql都将被记录





查看表是否上锁

show  open tables;

unlock tables;

lock table mylock read, book write;  # mylock , book  两张表上锁



show status like "innodb_row_lock%";  #查看锁情况

