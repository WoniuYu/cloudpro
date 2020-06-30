**README_SpringBoot**

SpringBoot 整合 SpringMvc

配置直接跳转映射

~~~java
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    /**
    * 添加视图映射
    * @param registry
    */
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
		/**
		*   @GetMapping("/login")
		*   public String login() {
		*     return "login";
		*   }
		*/
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/registry").setViewName("/registry");
    }
    
    /**
    * 添加拦截器
    */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求
        registry.addInterceptors(new MyInterceptor()).addPathPatterns("/**");
    }
}
~~~



自定义拦截器类

~~~java
public class MyInterceptor implements HandlerInterceptor{

    private static ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();
    
    /**
    * 执行业务之前
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        // 放行 /order/order/** 这个请求地址， 无需登录 
        String uri = request.getRequestURI();
        boolean match = new AntPathMatcher().match("/order/order/**", uri);
        if(match){
            return true;
        }
        
        HttpSession session = request.getSession();
       
        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        if(userInfo != null) {
            ///
        }
        
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
               String name = cookie.getName();
                if("XXX".equals(name)) {
                    ////
                }
            }
        } else {
            response.sendRedirect("http://XXXX.com");
        }
       
        threadLocal.set(userInfo);
        return true;
    }
}
~~~

使用ThreadLocal

~~~java
// 同一线程内， 都能获取到 threadLocal 的值
UserInfo userInfo = MyIntercepter.threadLocal.get();
~~~

