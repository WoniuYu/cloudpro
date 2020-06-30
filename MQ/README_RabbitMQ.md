**RabbitMQ**

安装

~~~shell
docker run -d --name rabbitmq -p 5671:5671 -p 5672:5672 -p 4369:4369 -p 25672:25672 -p 15671:15671  -p 15672:15672
~~~

Springboot 整合

依赖 spring-boot-starter-amqp

使用：启用RabbtiMQ    @EnableRabbit

配置

~~~properties
spring.rabbitmq.host=192.168.18.180
spring.rabbitmq.port=5672
#虚拟主机
spring.rabbitmq.virtual-host=/

# 开启 发送端确认  默认 false
spring.rabbitmq.publisher-confirms=true

###  设置消息正确抵达队列回调 配置以下两项
# 开启消息抵达队列确认 默认 false
spring.rabbitmq.publisher-returns=true
# 只要消息抵达队列，以异步方式优先回调 returnconfirm
spring.rabbitmq.template.mandatory=true

# 手动ack 确认消息
spring.rabbitmq.listener.simple.acknowledge-mode=manual
~~~

~~~java
@Configuration
public class MyRabbitConfig{
    
    // 使用Json 序列化机制，进行消息转换
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    
    @Autowired
    RabbitTemplate rabbitTemplate;
    
    /*
    * 定制RabbitTemplate
    * 消息 -> Borker(Exchange) -> Queue
    * 服务端确认
    * 消息抵达 Borker
    * 需要配置 spring.rabbitmq.publisher-confirms=true 
    *
    * 消息抵达 队列
    *    spring.rabbitmq.publisher-returns=true
	*    spring.rabbitmq.template.mandatory=true
	*
    *
    * 消费端确认
    *
    */
    public void initRabbitTemplate() {
        /**
        * 设置消息抵达 Borker 确认回调
        */
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback(){
            
            /**
            * 只要消息抵达 broker， 就ack=true
            *
            * @param correlationData 当前消息的唯一关联数据 （消息的额唯一关联id）
            * @param ack  消息是否收到
            * @param cause 失败原因
            */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                /// ****
            }
        });
        
        /**
        * 设置消息抵达 队列 确认回调
        */
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            
            /**
            * 只要消息没有正确投递给指定队列，就触发这个失败回调
            * 
            * @param message 详细信息
            * @param relpy 回复的状态码
            * @param replyText 回复的内容
            * @param exchange 消息发送给哪个交换机
            * @param routingkey 消息发送给哪个路由键
            */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String routingKey) {
                /// ****
            }
        })
    }
}
~~~



使用

~~~java
@Autowired
AmqpAdmin amqpAdmin;

@Autowired
RabbitTemplate rabbitTemplate;

public void creatExchange() {
    DirectExchange directExchange = new DirectExchange("hello-exchange",true, false);// name, durable, autoDelete
    amqpAdmin.declareExchange(directExchange);
}

public void createQueue(){
    Queue queue = new Queue("hello-queue", true, false, false);// name, durable,  exclusive,autoDelete
    amqpAdmin.declareQueue(queue);
}

public void createBinding(){
    // destination 目的地
    // destinationType 目的地类型
    // exchange 交换机
    // routingKey 路由键
    // Map(String, Object)自定义参数
    Binding binding = new Binding("hello-queue", Binding.DestinationType.Queue, "hello-exchange", "hello-binding", null);
    amqpAdmin.declareBinding(binding);
}

public void send(){
    User user = new User();
    user.setName("woniu");
    user.setAge(24);
    String route = "hello";
    rabbitTemplate.convertAndSend("hello-exchange","hello-binding", user)
}
~~~



消费消息

~~~java
// 所在类型要放到 spring容器中
//@RabbitListener //能标注在类和方法上
// @RabbitHandler  //只能标注在方法上  可以用来重载去区分不同消息类型

// 第二个参数是发送的时候的参数类型，spring会自动封装
// 第三个参数是当前传送数据的通道
@RabbitListener(queues = {"hello-queue"})
public void recieveMessage(Message message, User user,  Channel channel){
    byte[] body = message.getBody();
    
    MessageProperties  properties = message.getMessageProperties();
    
    
    
    ///  开启手动确认
    // 需要手动签收
    try {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        
        // 收货
        // deliveryTag, multiple 是否批量确认
        //channel.basicAck(deliveryTag, false);
        
        
        // 退货
        // deliveryTag, multiple, requeue=false/true false 丢弃、 true 重新入队
        //channel.basicNack(deliveryTag, false, false);
        
    } catch(Exception e) {
        ///  一般是网络中断才会出错
    }
}
~~~







~~~java
/**
* 若容器中没有以下队列 交换机 ... 
* 则会自动创建
* 只要有， 修改了参数也不会重新创建
*/
@Configuration
public class MyMQAutoConfig{
    
    @Bean
    public Queue orderDelayQueue(){
        /// 死信队列
        Map<String, Object> arguments = new Hash<>();
        arguments.put("x-dead-letter-exchange","order-event-exchange");
        arguments.put("x-dead-letter-routing-key","order.release.order");
        arguments.put("x-message-ttl", 60000);
        Queue queue = new Queue("order.delay.queue", true, false, false, arguments);
        
        return queue;
    }
    
    @Bean
    public Queue orderRelaseQueue(){
        
        Queue queue = new Queue("order.release.order.queue", true, false, false);
        
        return queue;
    }
    
    @Bean
    public Exchange orderEventExchange(){
        
        return new TopicExchange("order-event-exchange", true, false);
    }
    
    @Bean
    public Binding orderCreateOrdderBinding(){
        return new Binding("order.delay.queue", Binding.DestinationType.Queue, "order-event-exchange", "order-crear-order", null);
    }
    
    @Bean
    public Binding  orderReleaseOrderBinding(){
        return new Binding("order.release.order.queue", Binding.DestinationType.Queue, "order-event-exchange", "order-release-order", null);
    }
}
~~~

