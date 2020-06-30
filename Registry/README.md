**Registry 注册方案**

短信验证码

防止刷新页面，一直发送

解决方案

首先查询缓存中是否有当前手机号的缓存， 若有，解析自定义格式的缓存数据，获取到上一次缓存的时间戳，然后和当前时间比较，若大于预定的发送时间（常见60s之后可再次发送），则可以再次发送

备注： 不能直接根据缓存中是否有值来判断， 有可能第三方短信发送服务真的没有发送成功

缓存验证码时，带上当前时间戳，并设置过期时间（一般短信有效期可以设为15分钟）



密码加密

~~~java
BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

String encode = passwordEncoder.encode("123456");

boolean matches = passwordEncoder.matches("123456", encode); // true 密码匹配
// 即使的密码，经过这个盐值加密后得到的密文都不相同，原理，加密过程的盐值都不相同，该算法能通过加密后的密文知道盐值
~~~

