**分布式系统共享session**

1.  复制 session  只需要配置 tomcat

   ​	缺点： 占内存， 复制session过程占带宽，影响系统性能

2. 客户端缓存， 在浏览器端缓存session，每次发送请求都带着session

   ​	缺点： 不安全，所有数据都暴露， 有限制， cookie最大限制为4k（不同浏览器可能不同）

3. SpringSession   *****  只能解决同一 一级域名下的session问题， 不能解决跨域名（www.cloudpro.com, www.cloudstudy.com 即为不是同一个一级域名 ） ，跳转查看实现 [单点登录](./SSO.md/#单点登录) 方式

   ~~~properties
   ## 配置redis 
   ### ...
   
   
   # 配置springsession
   spring.session.store-type=redis
   server.servlet.session.timeout=30m
   ~~~

   启用springsession @EnableRedisHttpSession

   修改 session 的作用域，默认只在当前域生效

   ~~~java
   @Configuration
   public class MySessionConfig{
       
       @Bean
       public CookieSerializer cookieSerializer(){
           DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
       	/**
       	* 配置 session 作用域
       	* 默认当前作用域 auth.cloudpro.com
       	* 有 www.cloudpro.com product.cloudpro.com search.cloudpro.com mall.cloudpro.com auth.cloudpro.com ...
       	* 配置为 cloudpro.com， 则以上域名都能获取到
       	*/
           cookieSerializer.setDomainName("cloudpro.com"); 
           cookieSerializer.setCookieName("CLOUDPROSESSION"); // 设置session 名称
           
           retrun cookieSerializer;
       }
       
       /**
       * 配置 序列化机制
       *  配置成 json 格式
       */
       @Bean
       public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
           return new GenericJackson2JsonRedisSerializer();
       }
   }
   ~~~

   使用
   
   ~~~java
   @PostMapping("/reg")
   public String reg(Sring s, HttpSession session) {
       
   	RVO rvo = regServer.getData(s);
       session.setAttribute("regkey", rvo);
       
       return "reg";
       
   }
   ~~~
   
   
   
   原理 ![原理图](.\assets\RedisSession.png)

