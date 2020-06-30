canal 数据同步；  是阿里巴巴一款开源项目，纯java开发，目前只支持 Mysql 数据库


     第一步： 开启目标数据库的  binlog 功能
               show variables like "log_bin";
               若为 OFF 则未开启；
               需要修改 mysql配置文件 my.cnf
                   添加:  log-bin=mysql-bin   # binlog 文件名
                          binlog_format=ROW   # 选择ROW 模式
                          server_id=1         # mysql 实例id， 不能和canal的slaveId 重复
    
    第二步： 安装 canal 并修改配置
             修改 canal 安装目录下的 conf/example/instance.properties
                   canal.instance.master.address=192.168.12.120
                   canal.instance.dbUsername=root
                   canal.instance.dbPassword=root
                   canal.instance.filter.regex=.\\..*

    创建本地数据库， 需要数据库名称、数据表名称、结构和目标数据库一样。


    需要依赖 
        <dependency>
            <groupId>com.alibaba.otter</groupId>
            <artifactId>canal.client</artifactId>
        </dependency>