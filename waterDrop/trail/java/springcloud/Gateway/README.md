Gateway 网关   路由 断言 过滤器


    需要依赖
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>


  解决跨域： 添加 CorsConfig 文件
 
  一些工具类：  JsonExceptionHandler， ErrorHandlerConfig
  过滤器： AuthGlobalFilter

  配置文件：
    application.properties
        #使用服务发现路由
        spring.cloud.gateway.discovery.locator.enabled=true

        #设置路由id
        spring.cloud.gateway.routes[0].id=service-acl
        #设置路由的uri   lb://nacos注册服务名称
        spring.cloud.gateway.routes[0].uri=lb://service-acl
        #设置路由断言,代理servicerId为auth-service的/auth/路径
        spring.cloud.gateway.routes[0].predicates= Path=/*/acl/**

        #配置service-edu服务
        spring.cloud.gateway.routes[1].id=service-edu
        spring.cloud.gateway.routes[1].uri=lb://service-edu
        spring.cloud.gateway.routes[1].predicates= Path=/eduservice/**


