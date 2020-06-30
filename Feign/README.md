 Feign

远程返回数据，可用fast_json 逆转, TypeReference 支持复杂类型

默认不支持泛型

~~~java
    public <T> T getData(TypeReference<T> typeReference) {
		Object data = get("data");
		String str = JSON.toJSONString(data);

		T t = JSON.parseObject(str, typeReference);

		return t;
	}
	public R setDate(Object data) {
		put("data", data);
		return this;
	}
~~~

 获取时

~~~java
// 远程获取
R rWare = wareFeignService.getSkuHasStock(skuIdList);
//  TypeReference
TypeReference<List<SkuHasStockVo>> typeReference = new TypeReference<List<SkuHasStockVo>>(){};
List<SkuHasStockVo> skuHasStock = (List<SkuHasStockVo>)rWare.getData(typeReference);
~~~



###### 远程调用丢失请求头

feign 在远程调用之前需要构造请求，会调用很多拦截器（RequestInterceptor）

~~~java
@Configuration
public class MyFeignConfig{
    
    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor(){
            @Override
            public void apply(RequestTemplate template){
                // RequestContextHolder 可以获取到当前请求， ThreadLocal 实现
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                // 获取到当前请求 cookie
                String cookie = request.getHeader("Cookie");
                
                // 设置由 Feign 创建的请求的请求头
                template.header("Cookie", cookie);
                
            }
        };
    }
}
~~~



在异步模式下，还会出现以上问题

主要因为，异步模式会开启新的线程，ThreadLocal 获取的当前线程，获取不到主线程数据

解决办法

~~~java
// 在主线程获取到 requestAttributes
RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

// 在异步线程内，并且在远程调用之前，设置请求头
RequestContextHolder.setRequestAttributes(requestAttributes);
~~~

