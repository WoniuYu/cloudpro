**定时任务  异步任务**

定时任务

​	@EnableScheduling 开启定时任务

​	@Scheduled 开启一个 定时任务

​	自动配置类： TaskSchedulingAutoConfiguration

​	定时任务默认是阻塞的，

异步任务

​	@EnableAsync 开启异步功能

​	@Async 需要异步执行的方法加上注解

​	自动配置类： TaskExecutionAutoConfiguration





使用异步+ 定时任务 来完成定时任务不阻塞