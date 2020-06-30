分布式事务

建表

~~~sql
-- 注意此处0.3.0+ 增加唯一索引 ux_undo_log
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
~~~

导入依赖 spring-cloud-starter-alibaba-seata

修改registry.conf 配置并启动 seata-server

需要用到分布式事务，需要使用seata DataSourceProxy 代理数据源

~~~java
@Configuration
public class MySeataConfig {
    
    @Autowired
    DataSourceProperties dataSourceProperties;
    
    @Bean
    public void (DataSourceProperties dataSourceProperties){
    	HikariDataSource dataSource = dataSourceProperties.initalizeDataSourceBuilder().type(HikariDataSource.class).build();
    	if(StringUtils.hasText(dataSourceProperties.getName())) {
			dataSource.setPoolName(dataSourceProperties.getName);
        }
        retrun new DataSourceProxy(dataSource); // io.seata.rm.datasource
       
    }
}
~~~

每个微服务都需要导入  registry.conf 和 file.conf

​		修改 file.conf  vgroup_mapping.my_test_tx_group = "default" -> 

​				vgroup_mapping.{application-name}} = "default"

给分布式大事务入口标注 @GlobalTransactional 注解， 远程的小事务标注 @Transactional  就行