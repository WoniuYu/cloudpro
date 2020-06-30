mybatis
  修改xml之后 报错 not found

  解决方法
    第一种: 直接把 xml 文件复制到 target 目录下

    第二种: 把 xml 移动到 resources 目录下

    第三种: 1) 在 pom.xml 文件中配置 
                <build>
                  <resources>
                    <resource>
                    <directory>src/main/java</directory>
                    <includes>
                        <include>**/*.xml</include>
                    </includes>
                    <filtering>false</filtering>
                  </resources>
                </build>
           
           2) 在 application.properties 中配置
                #配置mapper xml文件的路径
                mybatis-plus.mapper-locations=classpath:com/atguigu/staservice/mapper/xml/*.xml

                #mybatis日志
                mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
