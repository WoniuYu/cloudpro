**RequestBody 注解**

springMVC 注解

请求方式必须为 POST

将请求体的json转为指定对象（SpuBoundTo）；

在 feign 组件中，会把@RequestBody将这个对象转为json。发送到 目标服务指定的映射

~~~java
   @PostMapping("/coupon/spubounds/save")
   R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);
~~~

