**Nacos 配置中心**

主要配置文件

~~~yaml
spring.application.name=cloudpro-coupon

spring.cloud.nacos.config.server-addr=127.0.0.1:8848
# coupon 命名空间
spring.cloud.nacos.config.namespace=f7792091-a3e3-4f92-8f48-dd0b673f4ec6
spring.cloud.nacos.config.file-extension=yaml
# 若不配置file-extension， 默认去找 coupon 命名空间下 dev 分组的 cloudpro-coupon.properties
#spring.cloud.nacos.config.group=dev

#在 Nacos Spring Cloud 中，dataId 的完整格式如下：
#${prefix}-${spring.profile.active}.${file-extension}
#prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置。
#spring.profile.active 即为当前环境对应的 profile，详情可以参考 Spring Boot文档。 
#注意：当 spring.profile.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 ${prefix}.${file-extension}
#file-exetension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。
# 目前只支持 properties 和 yaml 类型！！！

## 若 上边总配置和下边的集成配置有相同的 
## 则 总配置生效， 集成的配置不生效！！！

spring.cloud.nacos.config.ext-config[0].data-id=spring.yaml
spring.cloud.nacos.config.ext-config[0].group=dev
#  远程修改 自动刷新
spring.cloud.nacos.config.ext-config[0].refresh=true

spring.cloud.nacos.config.ext-config[1].data-id=mybatis.yaml
#spring.cloud.nacos.config.ext-config[1].group=dev
spring.cloud.nacos.config.ext-config[1].refresh=true

spring.cloud.nacos.config.ext-config[2].data-id=other.yaml
#spring.cloud.nacos.config.ext-config[2].group=dev
spring.cloud.nacos.config.ext-config[2].refresh=true

spring.cloud.nacos.config.ext-config[3].data-id=uu.yaml
spring.cloud.nacos.config.ext-config[3].group=dev
spring.cloud.nacos.config.ext-config[3].refresh=true

~~~

