**gateway  nginx**



请求指向 Nginx  -> Nginx 代理到网关(GateWay)  ->  网关转发到具体服务

域名   cloudpro.com  地址映射到 Nginx 地址

Nginx 配置

~~~json
upstream cloudpro {
    server 192.168.199.102:88; ### 网关地址
    server 192.168.199.102:89; ### 网关地址
}
~~~

~~~jso
location /  {
	proxy_pass http://cloudpro;
}
~~~

网关路由到具体服务

~~~pr
spring:
  application:
    name: cloudpro-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
    gateway:
      routes:
        - id: cloudpro_host_route
          uri: lb://cloudpro-product
          predicates:
            - Host=**.cloudpro.com
~~~

以上配置，有可能失败， 原因： Nginx代理到网关的时候，会丢失请求的 Host 信息

解决办法 ，修改Nginx 配置

~~~json

listen  80;
server_name   cloudpro.com *.cloudpro.com 423asdf.dasdfsdfghttp.net;

location /static/ {
    root /usr/share/nagix/html
}

location /payed/notify  {
    //// 配置支付宝回调 设置请求头
    proxy_set_header Host order.cloudpro.com;
	proxy_pass http://cloudpro;
}

location /  {
    proxy_set_header Host $host;
	proxy_pass http://cloudpro;
}
~~~

