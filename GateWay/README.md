**GateWay 网关**

 路由匹配 

会从路由配置表中 ，有上向下匹配， 匹配到则不再向下进行，所有要把精确的匹配规则放在上边，范围广的放在最后

~~~yaml
spring:
  application:
    name: cloudpro-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
    gateway:
      routes:
        - id: product_route
          uri: lb://cloudpro-product
          predicates:
            - Path=/api/product/**
          filters:
          ## localhost:88/api/** -> cloudpro-product/**
            - RewritePath=/api/(?<segment>.*),/$\{segment}

        - id: admin_route
          uri: lb://renren-fast
          predicates:
            - Path=/api/**
          filters:
            ## localhost:88/api/** -> renren-fast/renren-fast/**
            - RewritePath=/api/(?<segment>.*),/renren-fast/$\{segment}
~~~

