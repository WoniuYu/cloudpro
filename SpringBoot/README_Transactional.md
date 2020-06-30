**事务**

在Springboot中，同一个对象内， 事务隔离级别

A 对象有  a(), b(), c() 三个事务方法， 在a（）方法中调用了b和c 方法， 不管b和c任何设置隔离级别，都是公用a方法的事务

原因   由于事务是用代理对象来控制， 若在同一个对象内互调事务方法，就绕开了代理对象。

解决方法 

  使用代理对象来调用事务方法

​	1). 引入 spring-boot-starter-aop， 这个引入了aspectj

​	2). 开启 aspectj 动态代理，替换jdk默认的动态代理   @EnableAspectJAutoProxy(exposeProxy  = true)

~~~java
@Service
public class OrderServiceImpl implements OrderService{
    
    @Transaction
    public void a(){
        // ...
        OrderServiceImpl orderServiceImpl = (OrderServiceImpl) AopContext.currentProxy();
        orderServiceImpl.b();
        orderServiceImpl.c();
    }

    @Transaction(...)
    public void b(){
        // ...
    }

    @Transaction(...)
    public void c(){
        // ...
    }
    
    // ...
}

~~~

