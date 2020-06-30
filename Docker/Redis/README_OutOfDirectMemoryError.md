**OutOfDirectMemoryError**

springboot2.0 以后引入redis后，时间长了之后会报 OutOfDirectMemoryError 错误

原因

springboot2.0 以后默认使用 lettuce 作为操作 redis 的客户端， lettuce 使用 netty 进行网络通信， lettuce 存在 bug 导致 netty 堆外内存溢出，默认为 -Xmx 300m， 可以使用 -Dio.netty.maxDirectMemory 进行设置

存在问题

调整 -Dio.netty.maxDirectMemory 的大小，只能延后 OutOfDirectMemoryError的出现，并不能解决问题

解决方法 

升级lettuce 客户端， 或者使用 jedis替换掉 lettuce





解释

lettuce 可能原因

netty 底层有内存使用量计数，当计数超过了最大内存限制，则会导致OutOfDirectMemoryError

而 lettuce  在使用netty 的时候，可能时没有及时调用减内存计数，导致了该问题。

