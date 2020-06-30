**renren-generator**

[renren-generator](https://gitee.com/renrenio/renren-generator) 是码云下一个开源项目， 可快速搭建后台 crud 代码

​	导入项目后，需配置 yml 文件的数据源

​	修改 generator.properties 配置信息

```java
mainPath=com.woniu
package=com.woniu.cloudpro
moduleName=ware

author=heliyu
email=heliyu@gmail.com
    
// 可设置表前缀
tablePrefix=wms_
```

设置完后，可启动项目， 然后在网页访问80端口，可看见当前数据库所有表，选中所以逆向生成代码的表，生成代码后，可直接复制到对应模块即可。

代码中引用到一些工具类，可直接使用 [renren-fast](https://gitee.com/renrenio/renren-fast) 项目的 Utils 工具包下的对应工具类。

所有代码的模板在  ./resources/template 下，可自行修改。

