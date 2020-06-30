Sentinel

~~~pro
spring.cloud.sentinel.transport.port=8719
spring.cloud.sentinel.transport.dashboard=8333
management.endpoints.web.exposure.include=*
~~~



自定义被限制后的返回

~~~java
@Configuration
public class MySentinelConfig{
    public MySentinelConfig() {
        // url 被限制后的统一返回
        WebCallbackManager.setUrlBlockHandler(new UrlBlockHandler() {
            @Overried
           public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws IOException {
               R error = R.error("3000", "服务器忙，请稍后再试 ！");
               response.getWriter().write(JSON.toJSONString(error));
    	   }
        });
  
    }
  
}
~~~



服务降级

~~~java
服务熔断
~~~

